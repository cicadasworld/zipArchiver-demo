package com.jin.command;

import com.jin.ConsoleHelper;
import com.jin.ZipFileManager;
import com.jin.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Unzip the archive.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter decompression path:");
            Path destinationPath = Paths.get(ConsoleHelper.readString());
            zipFileManager.extractAll(destinationPath);
            ConsoleHelper.writeMessage("The file was opened.");
        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("The decompression path is invalid.");
        }
    }
}
