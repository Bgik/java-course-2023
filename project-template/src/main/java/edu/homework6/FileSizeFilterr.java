package edu.homework6;

import java.nio.file.Path;

public interface FileSizeFilterr {
    boolean accept(Path path);
}
