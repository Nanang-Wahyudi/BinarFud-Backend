package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.config.EmailUtil;
import app.BinarFudBackend.config.OtpUtil;
import app.BinarFudBackend.exception.BadRequestException;
import app.BinarFudBackend.model.Users;
import app.BinarFudBackend.model.response.UsersResponse;
import app.BinarFudBackend.repository.UsersRepository;
import app.BinarFudBackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Transactional
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private OtpUtil otpUtil;

    @Override
    public String verifyAccount(String email, String otp) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (users.getOtp().equals(otp) &&
                Duration.between(users.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (5 * 60)) {
            users.setActive(true);
            usersRepository.save(users);
            return "OTP verified, you can log in";
        }

        return "Please regenerate OTP and try again";
    }

    @Override
    public String regenerateOtp(String email) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send OTP please try again : " + e.getMessage());
        }

        users.setOtp(otp);
        users.setOtpGeneratedTime(LocalDateTime.now());
        usersRepository.save(users);
        return "Email sent... please verify account withing 1 minute";
    }

    @Override
    public String forgotPassword(String email) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send Set Password email please try again : " + e.getMessage());
        }

        return "Please check your email to Set New Password to your account";
    }

    @Override
    public String setPassword(String email, String newPassword) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));

        String encodedPassword = passwordEncoder.encode(newPassword);
        users.setPassword(encodedPassword);
        usersRepository.save(users);
        return "New Password set successful login with New Password";
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteUserByUsername(String userName) {
        return CompletableFuture.supplyAsync(() -> {
            Users users = usersRepository.findByUserName(userName)
                    .orElseThrow(() -> new BadRequestException("Username Not Found"));

            if (users != null) {
                usersRepository.delete(users);
                return true;
            } else {
                return false;
            }
        });
    }

    @Async
    @Override
    public CompletableFuture<Boolean> updateUserByUsername(String oldUsername, Users users) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                usersRepository.updateUserByUsernameWithQuery(oldUsername, users.getUserName(), users.getEmail(), passwordEncoder.encode(users.getPassword()));
                return true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return false;
            }
        });
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<List<UsersResponse>> getAllUser() {
        return CompletableFuture.supplyAsync(() -> usersRepository.findAll().stream()
                .map(users -> UsersResponse.builder()
                        .dtoUsername(users.getUserName())
                        .dtoEmail(users.getEmail())
                        .dtoPassword(users.getPassword())
                        .dtoRole(users.getRoles().toString())
                        .build())
                .collect(Collectors.toList()));
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<UsersResponse> getUserDetailByUsername(String username) {
        return CompletableFuture.supplyAsync(() -> usersRepository.findByUserName(username)
                .map(users -> UsersResponse.builder()
                        .dtoUsername(users.getUserName())
                        .dtoEmail(users.getEmail())
                        .dtoPassword(users.getPassword())
                        .dtoRole(users.getRoles().toString())
                        .build())
                .orElse(null));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUserName(username);
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<UUID>> findUserIdByUsername(String username) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(usersRepository.findUserIdByUsername(username)));
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<Users>> findByUserId(String userId) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(usersRepository.findByUserId(userId)));
    }

}
