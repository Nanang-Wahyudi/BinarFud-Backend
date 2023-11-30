package app.BinarFudBackend.service;

import app.BinarFudBackend.model.Users;
import app.BinarFudBackend.model.response.UsersResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UsersService {

    /*
     * Method addNewUser Sudah tidak digunakan, karena add user sudah menggunakan signin & signup
     */
//    Users addNewUser(Users users);

    String verifyAccount(String email, String otp);

    String regenerateOtp(String email);

    CompletableFuture<Boolean> deleteUserByUsername(String userName);

    CompletableFuture<Boolean> updateUserByUsername(String oldUsername, Users users);

    CompletableFuture<List<UsersResponse>> getAllUser();

    CompletableFuture<UsersResponse> getUserDetailByUsername(String username);

    Optional<Users> findByUsername(String username);

    CompletableFuture<Optional<UUID>> findUserIdByUsername(String username);

    CompletableFuture<Optional<Users>> findByUserId(String userId);

}
