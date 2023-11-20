package app.BinarFudBackend.service.impl;

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

    /*
    * Method addNewUser Sudah tidak digunakan, karena add user sudah menggunakan signin & signup
    */
//    @Override
//    public Users addNewUser(Users users) {
//        return usersRepository.save(users);
//    }

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
