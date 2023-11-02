package com.example.m_hiker.utils;

import java.util.Random;

public class debug {

    private debug(){

    }

    public static String generate_faker_text(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum length cannot be greater than maximum length.");
        }

        Random random = new Random();
        int length = random.nextInt(max - min + 1) + min;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Generate random lowercase letters
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static int generate_faker_int(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than the maximum value.");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
