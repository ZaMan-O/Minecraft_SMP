package zaman.plugin.smp.utility;

import java.text.DecimalFormat;

public class NumberFormatter {
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    public static String format(int number) {
        return formatter.format(number);
    }

    public static String format(long number) {
        return formatter.format(number);
    }
}