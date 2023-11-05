package com.example.csc311_wk9hw;

public class FarmingdaleValidEmailChecker {
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@farmingdale\\.\\w+$");
    }
}
