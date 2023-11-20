package app.BinarFudBackend.repository;

import app.BinarFudBackend.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);

    @Query(nativeQuery = true, value = "select id_image from image_data where name = :imageName")
    Optional<Long> findIdImageByNameWithQuery(@Param("imageName") String imageName);

    Optional<ImageData> findByIdImage(Long idImage);

}
