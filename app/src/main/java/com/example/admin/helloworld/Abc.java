package com.example.admin.helloworld;

public class Abc {
    public static int count=0;
    public static void main(String args[]){

        (new Thread(){
            @Override
            public void run(){
                System.out.println("hello world1");
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    System.out.println("+1");
                }
            }
        }).start();
        (new Thread(){
            @Override
            public void run(){
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(count!=0){
                    System.out.println("=>"+count);
                    count=0;
                    }
                }
            }
        }).start();
    }
}
