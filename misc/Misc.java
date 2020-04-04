package com.company.game.misc;

public class Misc {
    public static StringBuilder BytesToString(byte[] input) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length && input[i] != 0; ++i) {
            result.append((char)input[i]);
        }

        return result;
    }
}
