package com.kaushal.KafkaProject.java17Feature;

public class SwitchClass {
    public static void main (String[] args) {

        String s = "K";
        Integer i = 1;

        //Traditional approach before java 17
        switch (s) {
            case "K":
            {
                System.out.println (s );
                break;
            }
            case "L":
            {
                System.out.println (s );
                break;
            }
            default:
            {
                System.out.println (s );
            }
        }

        // In java 17
        switch (s) {
            case "K", "L":
            {
                System.out.println (s );
                break;
            }
            default:
            {
                System.out.println (s );
            }
        }

        // In java 17
        switch (i) {
            case 1, 2:
            {
                System.out.println (1 );
                break;
            }
            default:
            {
                System.out.println (0 );
            }
        }


    }
}
