package app.BinarFudBackend.controller;

import app.BinarFudBackend.model.Users;
import app.BinarFudBackend.model.response.ErrorResponse;
import app.BinarFudBackend.model.response.Response;
import app.BinarFudBackend.model.response.UsersResponse;
import app.BinarFudBackend.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
@RequestMapping(value = "/api/user")
public class UsersController {

    @Autowired
    public UsersService usersService;

    /*
     * Method addUser Sudah tidak digunakan, karena add user sudah menggunakan signin & signup
     */
//    @PostMapping(value = "/add", consumes = "application/json")
//    public String addUser(@RequestBody Users users) {
//        usersService.addNewUser(users);
//        return "Add New Product Successful";
//    }

    @DeleteMapping(value = "/delete/{username}")
    public ResponseEntity<Response<Object>> deleteUser(@PathVariable("username") String username) {

        Optional<Users> existingUser = usersService.findByUsername(username);

        if (!existingUser.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("User With Name " + username + " Not Found.")
                            .errorCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.NOT_FOUND);
        }

        CompletableFuture<Boolean> usersFuture = usersService.deleteUserByUsername(username);
        return usersFuture.thenApply(user -> ResponseEntity.ok(Response.builder()
                        .successMessage("Delete User With Name " + username + " Successful")
                        .data(usersFuture.isDone())
                        .isSuccess(Boolean.TRUE)
                        .build()))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Response.builder()
                                .errorResponse(ErrorResponse.builder()
                                        .errorMessage("Failed to delete merchant : " + ex.getMessage())
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .build())
                                .data(null)
                                .isSuccess(Boolean.FALSE)
                                .build())).join();
    }

    @PutMapping(value = "/update/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable("username") String oldUsername, @RequestBody Users users) {

        if (users.getUserName() == null || users.getUserName().isEmpty() ||
            users.getPassword() == null || users.getPassword().isEmpty() ||
            users.getEmail() == null || users.getEmail().isEmpty()) {

            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Unable to update data because there is empty or invalid data input.")
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Response.builder()
                .successMessage("Update User Successful.")
                .data(usersService.updateUserByUsername(oldUsername, users))
                .isSuccess(Boolean.TRUE)
                .build(), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getAllUser() throws ExecutionException, InterruptedException {
         CompletableFuture<List<UsersResponse>> userFuture = usersService.getAllUser();
         List<UsersResponse> usersResponses = userFuture.get();
         return ResponseEntity.ok()
                 .body(usersResponses);
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<Response<Object>> getUserDetail(@RequestParam("username") String username) {

        CompletableFuture<UsersResponse> usersResponse = usersService.getUserDetailByUsername(username);
        log.debug("User detail with name {} fetched with detail {}", username, usersResponse);

        try {
            UsersResponse users = usersResponse.get(10L, TimeUnit.SECONDS);

            return Optional.ofNullable(users)
                    .map(m -> ResponseEntity.ok().body(Response.builder()
                            .data(users)
                            .successMessage("User Details with name '" + username + "' successfully displayed")
                            .isSuccess(Boolean.TRUE)
                            .build()))
                    .orElseGet(() -> new ResponseEntity<>(Response.builder()
                            .errorResponse(ErrorResponse.builder()
                                    .errorMessage("User with name '" + username + "' not found")
                                    .errorCode(HttpStatus.NOT_FOUND.value())
                                    .build())
                            .data(null)
                            .isSuccess(Boolean.FALSE)
                            .build(), HttpStatus.NOT_FOUND));

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .errorResponse(ErrorResponse.builder()
                                    .errorMessage("Error Occurred : " + e.getMessage())
                                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .build())
                            .data(null)
                            .isSuccess(Boolean.FALSE)
                            .build());
        }
    }
}