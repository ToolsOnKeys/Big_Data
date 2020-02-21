package com.dh.utils;

/**
 * @author dinghao
 * @create 2020-02-17 15:21
 * @message
 */
public class RanOpt<T> {
    T value ;
    int weight;

    public RanOpt ( T value, int weight ){
        this.value=value ;
        this.weight=weight;
    }

    public T getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

}
