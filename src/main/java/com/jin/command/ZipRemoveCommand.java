package com.jin.command;

import com.jin.ConsoleHelper;
import com.jin.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Remove the file from the archive.");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the full path of the archive file:");
        Path sourcePath = Paths.get(ConsoleHelper.readString());
        zipFileManager.removeFile(sourcePath);
        ConsoleHelper.writeMessage("Removal from archive completed.");
    }
}
