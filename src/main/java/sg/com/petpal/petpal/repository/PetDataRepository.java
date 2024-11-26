package sg.com.petpal.petpal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.com.petpal.petpal.model.PetData;

public interface PetDataRepository extends JpaRepository<PetData, Long> {

    @Query("SELECT p FROM PetData p WHERE p.breed = :breed AND p.animalGroup = :animalGroup")
    Optional<PetData> findExistingPetData(@Param("breed") String breed,
            @Param("animalGroup") String animalGroup);
}
