package com.vientamthuong.chronotrigger.random;

import java.util.Random;

public class RandomSingleton {

    private static RandomSingleton randomSingleton;
    private Random random;

    private RandomSingleton() {
        random = new Random();
    }

    public static RandomSingleton getInstance() {
        if (randomSingleton == null) {
            randomSingleton = new RandomSingleton();
        }
        return randomSingleton;
    }

    public Random getRandom(){
        return random;
    }

}
