package com.xixi.lab.dp.ox3_behavioral.ox3_01_strategy.simple;


public class SimpleIfHandle {

    public static void main(String[] args) {

    }

    public void handle(String type) {
        if ("image".equals(type)) {
            System.out.println("handle image...");
        } else if ("video".equals(type)) {
            System.out.println("handle video...");
        } else if ("audio".equals(type)) {
            System.out.println("handle audio...");
        } else {
            System.out.println("fail to match any type");
        }
    }
}
