package com.blitzr;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String concatStringsWSep(Iterable<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(String s: strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }

    public static String concatOptionsWSep(Iterable<? extends BlitzrOptions> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(BlitzrOptions s: strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }
}
