
package sg.com.petpal.petpal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.com.petpal.petpal.model.Owner;
import sg.com.petpal.petpal.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p.owner FROM Pet p WHERE p.id = :petId")
    Owner findOwnerByPetId(@Param("petId") Long petId);

    @Query("SELECT COUNT(p) FROM Pet p WHERE p.petData.id = :petDataId")
    long countByPetDataId(@Param("petDataId") Long petDataId);

}
