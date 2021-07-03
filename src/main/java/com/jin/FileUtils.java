package com.jin;

import java.io.IOException;
import java.nio.file.*;

public class FileUtils {

    public static void createFile(String fileName) throws IOException {
        Path file = Paths.get(fileName);
        Path parent = file.getParent();
        if (Files.notExists(parent)) {
            Files.createDirectories(parent);
        }
        Files.createFile(file);
    }

    public static void copyFileToFile(String sourceFile, String targetFile) throws IOException {
        Path sourcePath = Paths.get(sourceFile);
        if (Files.notExists(sourcePath)) {
            // logging
            return;
        }

        Path targetPath = Paths.get(targetFile);
        Path parent = targetPath.getParent();
        if (Files.notExists(parent)) {
            Files.createDirectories(parent);
        }

        if (Files.notExists(targetPath)) {
            Files.copy(sourcePath, targetPath);
        } else {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void copyFileToFolder(String sourceFile, String targetFolder) throws IOException {
        Path targetFolderPath = Paths.get(targetFolder);
        if (Files.notExists(targetFolderPath)) {
            Files.createDirectories(targetFolderPath);
        }

        Path sourcePath = Paths.get(sourceFile);
        if (!Files.isDirectory(sourcePath)) {
            if (Files.notExists(sourcePath)) {
                // logging
                return;
            }

            Path targetPath = targetFolderPath.resolve(sourcePath.getFileName());
            if (Files.notExists(targetPath)) {
                Files.copy(sourcePath, targetPath);
            } else {
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            copyFileRecursively(sourcePath, targetFolderPath);
        }
    }

    private static void copyFileRecursively(Path rootPath, Path basePath) throws IOException {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(rootPath)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    copyFileRecursively(path, basePath);
                } else {
                    Path relativize = rootPath.relativize(path);
                    Path targetPath = basePath.resolve(relativize);
                    if (Files.notExists(targetPath)) {
                        Files.copy(path, targetPath);
                    } else {
                        Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }

    public static void moveFileToFile(String sourceFile, String targetFile) throws IOException {
        Path sourcePath = Paths.get(sourceFile);
        if (Files.notExists(sourcePath)) {
            // logging
            return;
        }

        Path targetPath = Paths.get(targetFile);
        Path parent = targetPath.getParent();
        if (Files.notExists(parent)) {
            Files.createDirectories(parent);
        }

        if (Files.notExists(targetPath)) {
            Files.move(sourcePath, targetPath);
        } else {
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void moveFileToFolder(String sourceFolder, String targetFolder) throws IOException {
        Path targetFolderPath = Paths.get(targetFolder);
        if (Files.notExists(targetFolderPath)) {
            Files.createDirectories(targetFolderPath);
        }

        Path sourcePath = Paths.get(sourceFolder);
        if (Files.isDirectory(sourcePath)) {
            if (Files.notExists(sourcePath)) {
                // logging
                return;
            }

            Path targetPath = targetFolderPath.resolve(sourcePath.getFileName());
            if (Files.notExists(targetPath)) {
                Files.move(sourcePath, targetPath);
            } else {
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } else {
            moveFileRecursively(sourcePath, targetFolderPath);
        }
    }

    private static void moveFileRecursively(Path rootPath, Path basePath) throws IOException {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(rootPath)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    moveFileRecursively(path, basePath);
                } else {
                    Path relativize = rootPath.relativize(path);
                    Path targetPath = basePath.resolve(relativize);
                    if (Files.notExists(targetPath)) {
                        Files.move(path, targetPath);
                    } else {
                        Files.move(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }

    public static void deleteFolder(String folderToDelete) throws IOException {
        Path path = Paths.get(folderToDelete);
        if (Files.notExists(path)) {
            // logging
            return;
        }

        if (Files.isDirectory(path)) {
            deleteRecursively(path);
        }
        Files.delete(path);
    }

    private static void deleteRecursively(Path rootPath) throws IOException {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(rootPath)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    deleteRecursively(path);
                }
                Files.delete(path);
            }
        }
    }
}
