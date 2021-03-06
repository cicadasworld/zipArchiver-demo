package com.jin;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

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

    public static void unzip(String zipFileName, String folderToExtract, Charset charset) throws IOException {
        Path source = Paths.get(zipFileName);
        Path target = Paths.get(folderToExtract);

        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(source), charset)) {
            ZipEntry zipEntry;
            while ( (zipEntry = zis.getNextEntry()) != null) {
                Path targetDirResolved = target.resolve(zipEntry.getName());
                // prevent zip slip vulnerability
                // make sure normalized file still has targetDir as its prefix else throw exception
                Path normalizedPath = targetDirResolved.normalize();
                if (!normalizedPath.startsWith(target)) {
                    throw new IOException("Bad zip entry: " + zipEntry.getName());
                }
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(normalizedPath);
                } else {
                    Path parent = normalizedPath.getParent();
                    if (parent != null) {
                        if (Files.notExists(parent)) {
                            Files.createDirectories(parent);
                        }
                    }
                    // file copy, nio
                    Files.copy(zis, normalizedPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            zis.closeEntry();
        }
    }
}
