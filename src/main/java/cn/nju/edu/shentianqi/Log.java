package cn.nju.edu.shentianqi;

/**
 * 基本上 这个类就是为了统一管理输出 除这里外，程序其他地方将不允许有stdout stderr
 */
public class Log {
    public static boolean verbose = false;

    public static void printRes(double res) {
        //仅用于输出结果
        System.out.printf("%.1f%n", res * 100);
    }

    public static void out(Object x) {
        if (verbose)
            System.out.println(x);
    }

    public static void err(Object x) {
        System.err.println(x);
    }

    public static void main(String[] args) {
        printRes(0.11111111);
        printRes(0.15555);
        printRes(0.000000);
        printRes(0.505000000);
        printRes(0.544);
        verbose = true;
        out(233);
        out("hello");
        out(12 + "  ");
        err("err: 21412");
        verbose = false;
        out(233);
        out("hello");
        out(12 + "  ");
        err("err: 21412");
    }
}
