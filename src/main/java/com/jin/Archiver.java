package com.jin;

import com.jin.exception.WrongZipFileException;

import java.io.IOException;

public class Archiver {

    public static void main(String[] args) {
        Operation operation = null;
        do {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
            } catch (WrongZipFileException e) {
                ConsoleHelper.writeMessage("You did not select an archive file or a valid file.");
            } catch (Exception e) {
                ConsoleHelper.writeMessage("An error has occurred. Check the input data.");
            }
        } while (operation != Operation.EXIT);
    }

    private static Operation askOperation() throws IOException {
        ConsoleHelper.writeMessage("");
        ConsoleHelper.writeMessage("Select operation:");
        ConsoleHelper.writeMessage(String.format("\t %d - Package and archive files", Operation.CREATE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Add file to archive", Operation.ADD.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Delete file from archive", Operation.REMOVE.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Unzip files", Operation.EXTRACT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - View archive content", Operation.CONTENT.ordinal()));
        ConsoleHelper.writeMessage(String.format("\t %d - Exit", Operation.EXIT.ordinal()));

        return Operation.values()[ConsoleHelper.readInt()];
    }
}
