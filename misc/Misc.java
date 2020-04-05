package com.company.game.misc;

import java.net.InetAddress;

public class Misc {
    private final static char END_CHAR = '!';

    public static String BytesToString(byte[] input) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length && input[i] != 0; ++i) {
            if ((char)input[i] == END_CHAR)
                break;

            result.append((char)input[i]);
        }

        return result.toString();
    }

    public static byte[] StringToBytes(String str) {
        return (str + END_CHAR).getBytes();
    }
}
