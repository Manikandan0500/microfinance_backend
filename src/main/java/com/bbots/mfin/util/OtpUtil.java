package com.bbots.mfin.util;
 
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
public class OtpUtil {
 
    private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
 
    public static String generateOtp() {
 
        SecureRandom random = new SecureRandom();
        List<Character> otpList = new ArrayList<>();
 
        // Add 3 alphabets
        for (int i = 0; i < 3; i++) {
            otpList.add(ALPHABETS.charAt(random.nextInt(ALPHABETS.length())));
        }
 
        // Add 3 numbers
        for (int i = 0; i < 3; i++) {
            otpList.add(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }
 
        // Shuffle to mix alphabets and numbers
        Collections.shuffle(otpList);
 
        // Convert list to string
        StringBuilder otp = new StringBuilder();
        for (char ch : otpList) {
            otp.append(ch);
        }
 
        return otp.toString();
    }
}