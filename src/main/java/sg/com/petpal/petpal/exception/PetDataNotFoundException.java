package sg.com.petpal.petpal.exception;

public class PetDataNotFoundException extends RuntimeException {
    public PetDataNotFoundException(Long id) {
        super("Could not find pet data with id: " + id);
    }
}
