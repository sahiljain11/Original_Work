public class EnergyModule {

    static final double WIEN_DISPLACEMENT_CONSTANT = 2.898 * Math.pow(10, -3);
    static final double SIGMA = 5.67 * Math.pow(10, -8);
    static final double UNI_GRAVITATIONAL_CONSTANT = 6.67 * Math.pow(10, -11);
    static double starMass;                     //kilograms
    static double distance;                     //meters
    static double starWavelength;               //nanometers
    static double radiusMin;                    //meters
    static double radiusMax;                    //meters
    static double revolutionTime;               //seconds
    static double starTemperature;              //degrees Kelvin
    static double starLuminosity;               //joules per second
    static double starRadius;                   //meters
    static double initialVelocity;              //meters per second
    static double albedoFactor;                 //number
    static double planetRadius;                 //meters
    static double eccentricityOfRadius;

    public static void CheckEnergy (double sm, double d, double sw, double sr, double albedo, double pr, double e) {
        starMass = sm;
        distance = d;
        starWavelength = sw;
        starRadius = sr;
        albedoFactor = albedo;
        planetRadius = pr;
        eccentricityOfRadius = e;
        wienLaw(starWavelength);
        starLuminosity = stefanBoltzman(planetRadius, starTemperature);
        setRadiusMinMax(distance, eccentricityOfRadius);
        initialVelocityEquation();
        getAverageTempOfPlanet();
    }

    public static void wienLaw (double lambda) {
        lambda = lambda / Math.pow(10, 9); //conversion from nm to m
        double temperature = WIEN_DISPLACEMENT_CONSTANT / (lambda); //wien law
        starTemperature = temperature;
    }

    public static double stefanBoltzman (double radius, double temperature) {
        double surfaceArea = 4 * Math.PI * radius * radius;
        double luminosity = surfaceArea * SIGMA * Math.pow(temperature, 4);
        return luminosity;
    }

    public static double inverseSquare (double d2) {
        return starLuminosity * (starRadius * starRadius) / (d2 * d2);
    }

    public static void initialVelocityEquation () {
        double temp = (UNI_GRAVITATIONAL_CONSTANT) * starMass / distance;
        initialVelocity = Math.sqrt(temp);
    }

    public static void setRadiusMinMax (double r, double e) {
        radiusMin = r * (1 - e);
        radiusMax = r * (1 + e);
    }

    public static void  getAverageTempOfPlanet() {

        double perimeter = 2 * Math.PI * Math.sqrt(radiusMax * radiusMax + radiusMin
                * radiusMin / 2);
        double distanceCovered = initialVelocity;
        double time = 1;
        double momentum = initialVelocity * distance;

        double highestTemp = Integer.MIN_VALUE;
        double lowestTemp = Integer.MAX_VALUE;

        double totalTemperature = 0;

        while (distanceCovered < perimeter) {
            double thetaInRadians = distanceCovered / perimeter * 2 * Math.PI;

            double radius = (radiusMax * radiusMin) /
                    (Math.sqrt((radiusMax * radiusMax * Math.cos(thetaInRadians)
                            * Math.cos(thetaInRadians)) + ((radiusMin * radiusMin *
                            Math.sin(thetaInRadians) * Math.sin(thetaInRadians)))));
            radius = Math.abs(radius);
            double x = radius * Math.cos(thetaInRadians);
            double y = radius * Math.sin(thetaInRadians);

            double z = x - Math.sqrt(radiusMax * radiusMax - radiusMin * radiusMin);
            double smallerTriangleTheta = Math.atan(y / z);

            double radius2 = Math.abs(y / Math.sin(smallerTriangleTheta));

            double newLuminosity = inverseSquare(radius2);
            double withAlbedo = albedoFunction(newLuminosity);
            double temperature = inverseBoltzmanLaw(withAlbedo);
            totalTemperature += temperature;

            if (temperature > highestTemp) {
                highestTemp = temperature;
            }

            if (temperature < lowestTemp) {
                lowestTemp = temperature;
            }

            double newVelocity = momentum / radius2;
            if (distanceCovered + newVelocity >= perimeter) {
                time += (perimeter - distanceCovered) / newVelocity;
                revolutionTime = time;
                break;
            }
            else {
                time++;
                distanceCovered += newVelocity;
            }
        }

        double averageTemperature = totalTemperature / revolutionTime;
        System.out.println("Energy Module Results:");
        System.out.println("Average Temperature: " + averageTemperature + " K or " + (averageTemperature - 273) + " C");

        System.out.println("Highest temperature of your planet: " + highestTemp + " K or " + (highestTemp - 273) + " C");
        System.out.println("Lowest temperature of your planet: " + lowestTemp + " K or " + (lowestTemp - 273) + " C");

        if (highestTemp < 500 && lowestTemp > 0) {
            System.out.println("The planet's temperature is suitable for life");
        }
        else {
            if (lowestTemp < 0) {
                System.out.println("Congrats on getting below absolute zero...");
            }
            System.out.println("The planet's temperature is unsuitable for life");
        }
    }

    public static double albedoFunction(double x) {
        return x * albedoFactor;
    }

    public static double inverseBoltzmanLaw (double x) {
        double temporary = x / (4 * Math.PI * planetRadius * planetRadius * SIGMA);
        return Math.pow(temporary, .25);
    }

}