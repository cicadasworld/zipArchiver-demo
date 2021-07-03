package com.jin;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void zip(String folderToZip, String zipFileName) throws IOException {
        Path sourceDir = Paths.get(folderToZip);
        if (!Files.isDirectory(sourceDir)) {
            return;
        }

        Path zipFile = Paths.get(zipFileName);
        Path parent = zipFile.getParent();
        if (Files.notExists(parent)) {
            Files.createDirectories(parent);
        }

        List<Path> pathList = new ArrayList<>();
        listFiles(sourceDir, pathList);

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            for (Path path : pathList) {
                ZipEntry entry = new ZipEntry(sourceDir.relativize(path).toString());
                zos.putNextEntry(entry);
                Files.copy(path, zos);
                zos.closeEntry();
            }
        }
    }

    private static void listFiles(Path rootPath, List<Path> pathList) throws IOException {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(rootPath)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    listFiles(path, pathList);
                } else {
                    pathList.add(path);
                }
            }
        }
    }

    public static void unzip(String zipFileName, String folderToExtract) throws IOException {
        Path zipFile = Paths.get(zipFileName);
        if (!Files.isRegularFile(zipFile)) {
            return;
        }

        Path targetDir = Paths.get(folderToExtract);
        if (Files.notExists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry;
            while ( (zipEntry = zis.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                Path fileFullName = targetDir.resolve(fileName);
                Path parent = fileFullName.getParent();
                if (Files.notExists(parent)) {
                    Files.createDirectories(parent);
                }
                Files.copy(zis, fileFullName, StandardCopyOption.REPLACE_EXISTING);
            }
            zis.closeEntry();
        }
    }
}
