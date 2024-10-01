package com.sgp.qa.service;

import com.sgp.qa.model.UnverifiedUser;
import com.sgp.qa.model.VerifiedUser;
import com.sgp.qa.repository.UnverifiedUserRepository;
import com.sgp.qa.repository.VerifiedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UnverifiedUserRepository unverifiedUserRepository;

    @Autowired
    private VerifiedUserRepository verifiedUserRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register the unverified user and send OTP
    public UnverifiedUser registerUser(UnverifiedUser user) {
        String otp = generateOtp();
        user.setOtp(otp);
        sendOtpEmail(user.getEmail(), otp);
        return unverifiedUserRepository.save(user);
    }


    // OTP verification logic
    public boolean verifyOtp(String otp) {
        Optional<UnverifiedUser> unverifiedUserOpt = unverifiedUserRepository.findByOtp(otp);
        if (unverifiedUserOpt.isPresent()) {
            UnverifiedUser unverifiedUser = unverifiedUserOpt.get();

            // Move user to verified user table
            VerifiedUser verifiedUser = new VerifiedUser();
            verifiedUser.setUsername(unverifiedUser.getUsername());
            verifiedUser.setEmail(unverifiedUser.getEmail());

            // Check if the password is already encoded. If not, encode it.
            String plainPassword = unverifiedUser.getPassword();
            if (!plainPassword.startsWith("$2a$")) { // BCrypt hashes typically start with '$2a$'
                verifiedUser.setPassword(passwordEncoder.encode(plainPassword)); // Hash the password
            } else {
                verifiedUser.setPassword(plainPassword); // Already hashed
            }

            verifiedUserRepository.save(verifiedUser);
            unverifiedUserRepository.delete(unverifiedUser);

            // Manually authenticate the user after successful OTP verification
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    verifiedUser.getUsername(),
                    verifiedUser.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER")) // Assuming role is ROLE_USER
            );

            // Set the authentication in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authToken);

            return true;
        }
        return false;
    }


    // Helper methods to send OTP and generate it
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    public Optional<VerifiedUser> findByEmail(String email) {
        return verifiedUserRepository.findByEmail(email);
    }

    public Optional<VerifiedUser> findByUsername(String username) {
        return verifiedUserRepository.findByUsername(username);
    }
}
