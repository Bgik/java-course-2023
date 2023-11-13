/*package edu.homework6;

import org.apache.logging.log4j.core.filter.AbstractFilter;
import java.io.IOException;
import java.nio.file.*;

public interface task3 extends DirectoryStream.Filter<Path> {

    default AbstractFilter and(AbstractFilter other) {
        return path -> this.accept(path) && other.accept(path);
    }

    default AbstractFilter or(AbstractFilter other) {
        return path -> this.accept(path) || other.accept(path);
    }

    static AbstractFilter largerThan(long size) {
        return path -> {
            try {
                return Files.size(path) > size;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    static AbstractFilter magicNumber(int... magicBytes) {
        return path -> {
            try {
                byte[] bytes = Files.readAllBytes(path);
                if (bytes.length < magicBytes.length) {
                    return false;
                }
                for (int i = 0; i < magicBytes.length; i++) {
                    if ((bytes[i] & 0xFF) != magicBytes[i]) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    static AbstractFilter globMatches(String glob) {
        return path -> FileSystems.getDefault().getPathMatcher("glob:" + glob).matches(path.getFileName());
    }

    static AbstractFilter regexContains(String regex) {
        return path -> {
            String fileName = path.getFileName().toString();
            return fileName.matches(".*" + regex + ".*");
        };
    }

    static AbstractFilter readable = Files::isReadable;
    static AbstractFilter writable = Files::isWritable;
    static AbstractFilter regularFile = Files::isRegularFile;

    boolean accept(Path path);
}

class FileAttributeFilter implements AbstractFilter, FileAttributeFilterr {

    private final boolean isReadable;
    private final boolean isWritable;

    public FileAttributeFilter(boolean isReadable, boolean isWritable) {
        this.isReadable = isReadable;
        this.isWritable = isWritable;
    }

    @Override
    public boolean accept(Path path) {
        if (!Files.isRegularFile(path)) {
            return false;
        }

        if (isReadable && !Files.isReadable(path)) {
            return false;
        }

        return !isWritable || Files.isWritable(path);
    }
}

class FileSizeFilter extends AbstractFilter implements FileSizeFilterr {

    private final long size;

    public FileSizeFilter(long size) {
        this.size = size;
    }

    @Override
    public boolean accept(Path path) {
        try {
            return Files.size(path) > size;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
//создание фильтра
    Path dir = Paths.get("path_to_directory");
    DirectoryStream.Filter<Path> filter = AbstractFilter.regularFile
        .and(AbstractFilter.readable)
        .and(new FileSizeFilter(100_000))
        .and(AbstractFilter.magicNumber(0x89, 'P', 'N', 'G'))
        .and(AbstractFilter.globMatches("*.png"))
        .and(AbstractFilter.regexContains("[-]"));

try(
    DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter))

    {
        entries.forEach(System.out::println);
    } catch(
    IOException e)

    {
        e.printStackTrace();
    }
}
