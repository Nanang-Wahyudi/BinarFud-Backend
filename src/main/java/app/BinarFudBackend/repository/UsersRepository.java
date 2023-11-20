package app.BinarFudBackend.repository;

import app.BinarFudBackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE users SET user_name = :newUsername, email = :newEmail, password = :newPassword WHERE user_name = :oldUsername")
    void updateUserByUsernameWithQuery(@Param("oldUsername") String oldUsername, @Param("newUsername") String newUsername, @Param("newEmail") String newEmail, @Param("newPassword") String newPassword);

    @Query(nativeQuery = true, value = "SELECT user_id FROM users WHERE user_name = :userName")
    UUID findUserIdByUsername(@Param("userName") String userName);

    Users findByUserId(String userId);

    Optional<Users> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
