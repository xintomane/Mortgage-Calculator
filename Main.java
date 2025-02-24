package org.example;

import java.text.NumberFormat;
import java.util.Scanner;

public class Main {

    final static byte MONTHS_IN_YEAR = 12;
    final  static byte PERCENT = 100;

    public static void main(String[] args) {
        //avoid magic numbers
        /*int principal = 0;
        float monthlyInterestRate = 0;
        int Period = 0;
        float annualInterestRate = 0;*/

        Scanner scanner = new Scanner(System.in);

        /*System.out.println("Principal: ");
        int principal = scanner.nextInt();*/

        int principal = (int) readNumber("Principal",1000,1000_000);

        float annualInterestRate = (float) readNumber("Annual Interest Rate %: ",0,30);

        int year = (int) readNumber("Period (Years): ",1,30);

        double mortgage = calculateMortgage(principal,annualInterestRate,year);


        String result = NumberFormat.getCurrencyInstance().format(mortgage);

        printMortage(result);

        printPaymentSchedule(year, principal, annualInterestRate);

    }

    private static void printMortage(String result) {
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("---------");
        System.out.println("Monthly Payments: " + result);
    }

    private static void printPaymentSchedule(int year, int principal, float annualInterestRate) {
        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("-----------------");
        for (short month = 1; month <= year * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal, annualInterestRate, year, month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    public static double readNumber(String prompt, double min, double max){
        Scanner scanner = new Scanner(System.in);
        double value;
        while(true){
            System.out.println(prompt);
            value = scanner.nextDouble();
            if(value >= min && value <= max)
                break;

            System.out.println("Enter a number greater than" + min + "and less than " + max);
        }

        return value;
    }

    public static double calculateBalance(
            int principal,
            float annualInterestRate,
            int Period,
            short numberOfPaymentsMade){
        //Balance = Principal*[(1+c)^n - (1+c)^p]/[(1+c)^n -1]


        int numberOfPayments = Period * MONTHS_IN_YEAR;
        float monthlyInterestRate = annualInterestRate/PERCENT/MONTHS_IN_YEAR;

        double balance = principal
                * (Math.pow(1 + monthlyInterestRate, numberOfPayments) - Math.pow(1 + monthlyInterestRate, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);

        return balance;

    }

    public static double calculateMortgage(int principal,
                                           float annualInterestRate,
                                           int Period){


        Period = Period * MONTHS_IN_YEAR;
        float monthlyInterestRate = annualInterestRate/PERCENT/MONTHS_IN_YEAR;

        float exp = (1 + monthlyInterestRate);

        double mortgage = (principal*((monthlyInterestRate*(Math.pow(exp,Period)))/(Math.pow(exp,Period)-1)));

        return mortgage;

    }


}