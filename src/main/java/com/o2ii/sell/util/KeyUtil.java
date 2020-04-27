package com.o2ii.sell.util;

import java.util.Random;

public class KeyUtil {
    // synchronized 多线程处理
    public static synchronized String genUniqueKey() {
        Random random = new Random();

        Integer number = random.nextInt(900000) + 100000;

        Long ctm = System.currentTimeMillis();

        return ctm + String.valueOf(number);
    }

    public static synchronized String genShortKey() {
        Random random = new Random();

        return String.valueOf(random.nextInt(666666) + 233);
    }
}
