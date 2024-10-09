package com.sissilab.dp.ox3_behavioral.ox31_strategy.simple;


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
