package com.bbots.mfin.service;
 
public interface EmailService {
 
    void sendUserCreationMail(Long orgCode,String toMail,
                              String username);
 
    void sendOtpMail(Long orgCode,String toMail, String username, String otp);
    
    void sendPasswordUpdateMail(Long orgCode,String toMail, String username, String newPassword);

}