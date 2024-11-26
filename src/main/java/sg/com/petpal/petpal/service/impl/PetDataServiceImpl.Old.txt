package sg.com.petpal.petpal.service.impl;

import java.util.ArrayList;

import java.util.Optional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import sg.com.petpal.petpal.exception.PetDataNotFoundException;
import sg.com.petpal.petpal.model.PetData;
import sg.com.petpal.petpal.repository.PetDataRepository;
import sg.com.petpal.petpal.service.PetDataService;

@Primary
@Service
public class PetDataServiceImpl implements PetDataService {

    private PetDataRepository petDataRepository;

    public PetDataServiceImpl(PetDataRepository petDataRepository) {
        this.petDataRepository = petDataRepository;
    }

    @Override
    public PetData createPetData(PetData petData) {
        return petDataRepository.save(petData);
    }

    @Override
    public PetData getPetData(Long id) {
        Optional<PetData> petData = petDataRepository.findById(id);
        if (petData.isPresent()) {
            return petData.get();
        }

        throw new PetDataNotFoundException(id);
    }

    @Override
    public ArrayList<PetData> getAllPetData() {
        return (ArrayList<PetData>) petDataRepository.findAll();
    }

    @Override
    public PetData updatePetData(PetData petData) {
        return petDataRepository.save(petData);
    }

    @Override
    public void deletePetData(Long id) {
        petDataRepository.deleteById(id);
    }
}
