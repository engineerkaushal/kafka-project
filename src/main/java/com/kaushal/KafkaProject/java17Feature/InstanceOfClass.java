package com.kaushal.KafkaProject.java17Feature;

public class InstanceOfClass {
    public static void main (String[] args) {

        Object a = "Kaushal";

        // Traditional approach before java 17
        if ( a instanceof String ) {
            String v = (String) a;
            System.out.println (v );
        } else if ( a instanceof Integer ) {
            Integer v = (Integer) a;
            System.out.println (v );
        }

        //In java 17
        if ( a instanceof String s ) {
            System.out.println (s );
        } else if ( a instanceof Integer i ) {
            System.out.println (i );
        }
    }


}
