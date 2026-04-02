package com.kaushal.KafkaProject.java17Feature;
 sealed class A permits B,C {

 }

 non-sealed class B extends A {

 }

 final class C extends A{

 }

 sealed interface I permits J,K {

 }

 non-sealed interface J extends I {

 }

 sealed interface K extends I {

 }

 final class L implements K {

 }

 class M implements J {

 }


public class SealedClass {
    public static void main (String[] args) {
        System.out.println ("Hi" );
    }
}
