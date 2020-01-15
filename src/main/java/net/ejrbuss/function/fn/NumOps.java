package net.ejrbuss.function.fn;

public final class NumOps {

    private NumOps() {}

    public static int inc(int a) {
        return a + 1;
    }

    public static long inc(long a) {
        return a + 1;
    }

    public static double inc(double a) {
        return a + 1;
    }

    public static int dec(int a) {
        return a - 1;
    }

    public static long dec(long a) {
        return a - 1;
    }

    public static double dec(double a) {
        return a - 1;
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static long add(long a, long b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static int sub(int a, int b) {
        return a - b;
    }

    public static long sub(long a, long b) {
        return a - b;
    }

    public static double sub(double a, double b) {
        return a - b;
    }

    public static int mul(int a, int b) {
        return a * b;
    }

    public static long mul(long a, long b) {
        return a * b;
    }

    public static double mul(double a, double b) {
        return a * b;
    }

    public static int div(int a, int b) {
        return a / b;
    }

    public static long div(long a, long b) {
        return a / b;
    }

    public static double div(double a, double b) {
        return a / b;
    }

    public static long mod(long a, long b) {
        return a % b;
    }

    public static double exp(double a, double b) {
        return Math.pow(a, b);
    }

}
