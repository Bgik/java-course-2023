package edu.homework6;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class task2 {

    public static void cloneFile(Path path) {
        String fileName = path.getFileName().toString();
        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));

        int copyNumber = 0;
        Path copyPath = path.resolveSibling(baseName + " — копия" + extension);

        while (Files.exists(copyPath)) {
            copyNumber++;
            copyPath = path.resolveSibling(baseName + " — копия (" + copyNumber + ")" + extension);
        }

        try {
            Files.copy(path, copyPath, StandardCopyOption.COPY_ATTRIBUTES);
        } catch (Exception e) {
            // Handle exception
        }
    }
}
