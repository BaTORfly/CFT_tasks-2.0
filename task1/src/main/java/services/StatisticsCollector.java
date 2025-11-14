package services;

public enum StatisticsCollector {
    INSTANCE;

    private int intCount = 0;
    private int doubleCount = 0;
    private int stringCount = 0;

    private long minInt = Long.MAX_VALUE;
    private long maxInt = Long.MIN_VALUE;
    private long sumInt = 0;

    private double minDouble = Double.MAX_VALUE;
    private double maxDouble = Double.MIN_VALUE;
    private double sumDouble = 0;

    private int minLengthString = Integer.MAX_VALUE;
    private int maxLengthString = Integer.MIN_VALUE;

    public void updateIntStats(long value){
        intCount++;
        minInt = Math.min(minInt, value);
        maxInt = Math.max(maxInt, value);
        sumInt += value;
    }

    public void updateDoubleStats(double value){
        doubleCount++;
        minDouble = Math.min(minDouble, value);
        maxDouble = Math.max(maxDouble, value);
        sumDouble += value;
    }

    public void updateStringStats(String value){
        stringCount++;
        int length = value.length();
        minLengthString = Integer.min(minLengthString, length);
        maxLengthString = Integer.max(maxLengthString, length);
    }

    public void printStatistics(boolean shortStats, boolean fullStats) {
        if (shortStats) {
            System.out.println("Short statistics:");
            System.out.println("Integers: " + intCount);
            System.out.println("Floats: " + doubleCount);
            System.out.println("Strings: " + stringCount);
        } else if (fullStats) {
            System.out.println("Full statistics:");
            System.out.println("Integers: " + intCount);
            if (intCount > 0) {
                System.out.println("Min: " + minInt + ", Max: " + maxInt + ", Sum: " + sumInt
                        + ", Average: " + (double) sumInt / intCount);
            }
            System.out.println("Floats: " + doubleCount);
            if (doubleCount > 0) {
                System.out.println("Min: " + minDouble + ", Max: " + maxDouble + ", Sum: " + sumDouble
                        + ", Average: " + sumDouble / doubleCount);
            }
            System.out.println("Strings: " + stringCount);
            if (stringCount > 0) {
                System.out.println("Shortest: " + minLengthString + ", Longest: " + maxLengthString);
            }
        }
    }
}
