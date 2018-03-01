public class Application {

    public static void main (String[] args) {
        //units of the variable
        double yearsIntoTheFuture = 747 * Math.pow(10, 9);               //years
        double planetMass = 2 * 1.898 * Math.pow(10, 27);                //kilograms
        double starMass = 1.989 * Math.pow(10,10);                      //kilograms
        double distance = 7;//1.496 * Math.pow(10,11);                      //meters
        double starWavelength = 500;                                    //NANOMETERS
        double starRadius = 8.5 * Math.pow(10, 9);                    //meters
        double albedoRatio = 0.7439864200159;                                        //number between 1-0 (0 means that no energy is bounced off)
        double planetRadius = 8.5 * Math.pow(10, 9);                  //meters
        double eccentricityOfEllipse = .51766;                           //number between 1-0. (0 is a perfect circle)

        MassModule.CheckMass(planetMass, yearsIntoTheFuture);
        System.out.println();
        EnergyModule.CheckEnergy(starMass, distance, starWavelength, starRadius, albedoRatio, planetRadius, eccentricityOfEllipse);
    }


}