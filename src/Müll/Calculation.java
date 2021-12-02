package Müll;

public class Calculation {

    public static int convertInt(String x){
        int z1= 0;
        try {
            z1 = Integer.parseInt(x);
        } catch(NumberFormatException e) {

        }
        return z1;
    }

    public static double convertDouble(String x) {
        double z1= 0.0;
        try {
            z1 = Double.parseDouble(x);
        } catch(NumberFormatException e) {

        }
        return z1;
    }
    public static double convertMultiply(String scheZuschlag, String dieselFloater) {
        if (convertDouble(scheZuschlag) < 0 || convertInt(dieselFloater) < 0)
        {
            return 0;
        } else {
            return (double) convertDouble(scheZuschlag) * (1.0+((double)convertInt(dieselFloater))/100);
        }
    }
}
