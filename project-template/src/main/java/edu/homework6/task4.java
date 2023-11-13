package edu.homework6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class task4 {

    public static void main(String[] args) {
        try {
            Path filePath = Files.createTempFile("output", ".txt");

            try (OutputStream fileOutputStream = Files.newOutputStream(filePath);
                 CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new Adler32());
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
                 OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
                 PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {

                printWriter.println("Programming is learned by writing programs. ― Brian Kernighan");
            }

            System.out.println("Data written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
