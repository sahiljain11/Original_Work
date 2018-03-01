public class MassModule {

    static double earthMass = 5.972 * Math.pow(10,24);
    static double massMin = earthMass / 3;
    static double massMax = earthMass * 10;

    public static void CheckMass(double planetMass, double yearsIntoTheFuture) {

        double additionalMassFromAsteroids = 57500 * 907.185 * yearsIntoTheFuture;
        double newMass = planetMass + additionalMassFromAsteroids;

        System.out.println("Mass Module Results:");
        System.out.println("Mass after " + yearsIntoTheFuture + " years: " + newMass + " kilograms");

        if (newMass <= massMin && newMass >= massMax) {
            System.out.println("The planet's mass is unsuitable for life");
        }
        else {
            System.out.println("The planet's mass is suitable for life");
        }
    }

}
