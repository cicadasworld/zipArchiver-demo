package com.jin.command;

import com.jin.ConsoleHelper;
import com.jin.ZipFileManager;
import com.jin.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Create an archive.");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter the full name of the file or directory to archive:");
            Path sourcePath = Paths.get(ConsoleHelper.readString());
            zipFileManager.createZip(sourcePath);
            ConsoleHelper.writeMessage("File created.");
        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("You did not specify the name of the file or directory correctly.");
        }
    }
}
