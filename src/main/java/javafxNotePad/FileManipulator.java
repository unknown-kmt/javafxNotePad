package javafxNotePad;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class FileManipulator {
    private File currentFile = null; // opened file

    public FileManipulator() {} // constructor

    public void openFile(Stage primaryStage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) { // if the FileChooser dialog was closed
            throw new IOException("No File selected!");
        }
        currentFile = selectedFile; // change current file
    }

    public void saveFile(String text) throws IOException { // write text into file
        if (isFileOpened()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
            writer.write(text);
            writer.flush();
            writer.close();
        }
        else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export to txt");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text File", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                writer.write(text);
                currentFile = selectedFile;
                writer.close();
            }
        }
    }

    public String getTextFromOpenedFile() throws IOException {
        if (isFileOpened()) {
            return Files.readString(currentFile.toPath(),  Charset.forName("windows-1251")); // to be able to load files with russian symbols in it
        }
        else throw new IOException("File was not opened yet!");
    }

    public boolean isFileOpened() {
        return currentFile != null;
    }
}
