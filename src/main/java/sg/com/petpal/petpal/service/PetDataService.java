package sg.com.petpal.petpal.service;

import java.util.ArrayList;

import sg.com.petpal.petpal.model.PetData;

public interface PetDataService {

    PetData createPetData(PetData petData);

    PetData getPetData(Long id);

    ArrayList<PetData> getAllPetData();

    PetData updatePetData(PetData petData);

    void deletePetData(Long id);
}
