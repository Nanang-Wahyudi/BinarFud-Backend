package app.BinarFudBackend.config;

import app.BinarFudBackend.model.Roles;
import app.BinarFudBackend.model.enumeration.ERole;
import app.BinarFudBackend.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@Slf4j
@Configuration
public class Config {

    Config(RolesRepository rolesRepository) {
        log.info("Cheking roles presented");
        for (ERole c : ERole.values()) {
            try {
                Roles roles = rolesRepository.findByRoleName(c)
                        .orElseThrow(() -> new RuntimeException("Roles not found"));
                log.info("Role {} has been found!", roles.getRoleName());

            } catch (RuntimeException rte) {
                log.info("Role {} is not found, inserting to DB . . .", c.name());
                Roles roles = new Roles();
                roles.setRoleName(c);
                rolesRepository.save(roles);
            }
        }
    }

}
