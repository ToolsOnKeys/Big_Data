package com.dh.utils;

import java.util.Random;

/**
 * @author dinghao
 * @create 2020-02-17 15:22
 * @message
 */
public class RandomNum {
    public static int getRandInt(int fromNum,int toNum){
        return   fromNum+ new Random().nextInt(toNum-fromNum+1);
    }

}
