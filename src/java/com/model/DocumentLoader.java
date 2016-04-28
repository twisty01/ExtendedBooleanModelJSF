package com.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class DocumentLoader {

    public static List<String> listf(String rootPath) {
        // simple but slow
        /*
         File directory = new File(directoryName);
         List<File> resultList = new ArrayList<>();

         File[] fList = directory.listFiles();
         resultList.addAll(Arrays.asList(fList));
         for (File file : fList) {
         if (!file.isFile()) {
         resultList.addAll(listf(file.getAbsolutePath()));
         }
         }
         return resultList;
         */
        LinkedList<String> strings = new LinkedList<>();
        try {
            Files.walkFileTree(Paths.get(rootPath), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    File f = file.toFile();
                    if (f.isFile()) {
                        strings.add(f.getPath());
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return strings;
    }

}
