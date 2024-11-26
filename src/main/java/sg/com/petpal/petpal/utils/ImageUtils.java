package sg.com.petpal.petpal.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ImageUtils {

    private final String UPLOAD_DIR = "uploads/";

    public List<String> saveImages(List<String> base64Images) throws IOException {
        return base64Images.stream().map(this::decodeAndSaveImage).collect(Collectors.toList());
    }

    private String decodeAndSaveImage(String base64Image) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            String fileName = UUID.randomUUID() + ".jpg"; // Unique file name
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            Files.createDirectories(filePath.getParent());
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(imageBytes);
            }

            return filePath.toString(); // Store file path (or URL if using cloud storage)
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }
}
