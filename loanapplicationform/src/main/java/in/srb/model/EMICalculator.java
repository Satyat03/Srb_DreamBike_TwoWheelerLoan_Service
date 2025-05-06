package in.srb.model;
public class EMICalculator {
    private static EMICalculator instance;

    private EMICalculator() { }

    public static EMICalculator getInstance() {
        if (instance == null) {
            synchronized (EMICalculator.class) {
                if (instance == null) {
                    instance = new EMICalculator();
                }
            }
        }
        return instance;
    }

    public double calculateEMI(double principal, double rate, int tenure) {
        rate = rate / (12 * 100); // Converting annual interest rate to monthly
        tenure=tenure*12;
        double emi = (principal * rate * Math.pow(1 + rate, tenure))
              / (Math.pow(1 + rate, tenure) - 1);
		return emi;
    }
}
