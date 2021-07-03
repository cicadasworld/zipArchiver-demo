package com.jin.command;

import com.jin.ConsoleHelper;
import com.jin.FileProperties;
import com.jin.ZipFileManager;

import java.util.List;

public class ZipContentCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("View the contents of the archive.");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Archive content:");
        List<FileProperties> files = zipFileManager.getFilesList();
        for (FileProperties file : files) {
            ConsoleHelper.writeMessage(file.toString());
        }
        ConsoleHelper.writeMessage("Archive content read.");
    }
}
