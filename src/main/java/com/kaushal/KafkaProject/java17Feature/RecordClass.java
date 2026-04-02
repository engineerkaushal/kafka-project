package com.kaushal.KafkaProject.java17Feature;

record Person(String name, int age, String sex, String occu) {

     Person(String name, int age) {
        this(name, age, "F", "HW");
    }

    //Instance method

    public void print() {
        System.out.println ("This is instance method" );
    }

    //Static method
    public static void staticMethod() {
        System.out.println ("This is static method" );
    }
}

public class RecordClass {
    public static void main (String[] args) {

        Person p1 = new Person ("Kaushal", 29, "M", "SE");

        System.out.println (p1.name () );
        p1.print ();
        Person.staticMethod ();

        Person p2 = new Person ("Shivani", 29);

        System.out.println ( p2.name ());

    }
}
