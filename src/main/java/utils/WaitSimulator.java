package main.java.utils;

public class WaitSimulator {
    public static long timeBase = 500;

    public static void simulateShortWait() {
        try {
            Thread.sleep(timeBase);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void simulateLongWait() {
        try {
            Thread.sleep(timeBase * 3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
