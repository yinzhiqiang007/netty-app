package com.quinn.app.test;



public class D extends C implements B {
    @Override
    public void b() {
        System.out.println("b");

    }

    @Override
    public void c() {
        super.c();

    }

    @Override
    public void a() {
        System.out.println("da");
    }

    public static A getA(){
        A a = new D();
        return a;
    }

    public static void main(String[] args) {

        A a = new D();
        a.c();

        A e = new E();
        e.c();

        Integer i=128;
        System.out.println(i>>5);
        System.out.println(4<<5);


    }
}
