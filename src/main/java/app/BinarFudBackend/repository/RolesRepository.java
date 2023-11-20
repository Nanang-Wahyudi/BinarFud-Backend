package app.BinarFudBackend.repository;

import app.BinarFudBackend.model.Roles;
import app.BinarFudBackend.model.enumeration.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByRoleName(ERole name);

}
