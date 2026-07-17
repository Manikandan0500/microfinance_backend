package com.bbots.mfin.serviceImpl;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.bbots.mfin.dto.SmtpMailConfigDto;
import com.bbots.mfin.repository.SmtpMailConfigRepository;
import com.bbots.mfin.service.EmailService;


@Service
public class EmailServiceImpl implements EmailService {

        @Autowired
        private SmtpMailConfigRepository smtpRepo;

        private SmtpMailConfigDto getSmtpConfig(Long orgCode) {
                return smtpRepo.findById(orgCode)
                                .orElseThrow(() -> new RuntimeException(
                                                "SMTP configuration not found for orgCode: " + orgCode));
        }

        private JavaMailSender getMailSender(Long orgCode) {

                SmtpMailConfigDto config = getSmtpConfig(orgCode);

                JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

                mailSender.setHost(config.getHostname());
                mailSender.setPort(config.getSmtpport().intValue());
                mailSender.setUsername(config.getUsername());
                mailSender.setPassword(config.getPasshash());

                Properties props = mailSender.getJavaMailProperties();

                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.connectiontimeout", "100000");
                props.put("mail.smtp.timeout", "100000");
                props.put("mail.smtp.writetimeout", "100000");

                if ("TLS".equalsIgnoreCase(config.getEncryption())) {
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.starttls.required", "true");
                        props.put("mail.smtp.ssl.trust", config.getHostname());
                }

                if ("SSL".equalsIgnoreCase(config.getEncryption())) {
                        props.put("mail.smtp.ssl.enable", "true");
                        props.put("mail.smtp.ssl.trust", config.getHostname());
                }

                return mailSender;
        }

        @Override
        public void sendUserCreationMail(Long orgCode,
                        String toMail,
                        String username) {

                SmtpMailConfigDto config = getSmtpConfig(orgCode);

                JavaMailSender mailSender = getMailSender(orgCode);

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom(config.getFromid());
                message.setTo(toMail);
                message.setSubject("User Account Created");

                message.setText(
                                "Dear " + username + ",\n\n"
                                                + "Your account has been created successfully.\n\n"
                                                + "Username : " + toMail + "\n\n"
                                                + "Regards,\n"
                                                + config.getDisplayname());

                mailSender.send(message);
        }

        @Override
        public void sendOtpMail(Long orgCode,
                        String toMail,
                        String username,
                        String otp) {

                SmtpMailConfigDto config = getSmtpConfig(orgCode);

                JavaMailSender mailSender = getMailSender(orgCode);

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom(config.getFromid());
                message.setTo(toMail);
                message.setSubject("Email Verification OTP");

                message.setText(
                                "Dear " + username + ",\n\n"
                                                + "Your OTP is : " + otp + "\n\n"
                                                + "OTP valid for 5 minutes.\n\n"
                                                + "Regards,\n"
                                                + config.getDisplayname());

                mailSender.send(message);
        }

        @Override
        public void sendPasswordUpdateMail(Long orgCode,
                        String toMail,
                        String username,
                        String newPassword) {

                SmtpMailConfigDto config = getSmtpConfig(orgCode);

                JavaMailSender mailSender = getMailSender(orgCode);

                SimpleMailMessage message = new SimpleMailMessage();

                message.setFrom(config.getFromid());
                message.setTo(toMail);
                message.setSubject("Password Updated Successfully");

                message.setText(
                                "Dear " + username + ",\n\n"
                                                + "Your password has been updated successfully.\n\n"
                                                + "Username : " + toMail + "\n"
                                                + "Password : " + newPassword + "\n\n"
                                                + "Regards,\n"
                                                + config.getDisplayname());

                mailSender.send(message);
        }
}