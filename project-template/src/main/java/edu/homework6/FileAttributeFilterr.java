package edu.homework6;

import java.nio.file.Path;

public interface FileAttributeFilterr {
    boolean accept(Path path);
}
