package com.obify.hy.ims.controller;

import java.time.LocalDateTime;

public class TestMain {

    public static void main(String[] args) {
        String dt = LocalDateTime.now().toString();
        System.out.println(dt);
        LocalDateTime dtt = LocalDateTime.parse(dt);
        System.out.println(dtt);
    }
}
