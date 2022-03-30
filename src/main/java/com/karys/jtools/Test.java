package com.karys.jtools;

public class Test {

    public static void main(String[] args) {

        System.out.println(Test.class.getResource("icon.png"));
        System.out.println(Test.class.getResource("/icons/icon.png"));
        System.out.println(Test.class.getResource(""));
    }
}
