package org.oasumainline.SpringStarter.utils;

import java.util.Random;

public class RandomUtils {

    public static String getRandomString(int length) {
        int min = 97;
        int max = 122;
        Random random = new Random();
        return random.ints(min,max + 1).limit(length).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
