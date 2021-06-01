package simplemodel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {
    private final BorderPane root = new BorderPane(); // Application visuals

    private final FileManipulator fileManipulator = new FileManipulator();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NotePad");
        primaryStage.getIcons().add(new Image("file:src\\main\\resources\\icon.png")); // notepad icon
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // close operation request
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        // bottom status bar
        DocumentStatus documentStatus = new DocumentStatus();
        NotepadStatus notepadStatus = new NotepadStatus();

        documentStatus.setSaved();
        notepadStatus.setReady();

        Region statusBarRegion = new Region();
        HBox.setHgrow(statusBarRegion, Priority.ALWAYS);

        HBox statusBox = new HBox(notepadStatus.getTextElement(), statusBarRegion, documentStatus.getTextElement());
        statusBox.setPadding(new Insets(5, 12, 0, 12));
        statusBox.setAlignment(Pos.CENTER);
        root.setBottom(statusBox);

        // center textarea
        TextArea notepadArea = new TextArea();
        notepadArea.setStyle("-fx-focus-color: -fx-control-inner-background; -fx-faint-focus-color: -fx-control-inner-background;"); // no blue border
        notepadArea.textProperty().addListener((observable, oldVal, newVal) -> {
            documentStatus.setChanged(); // set changed on change in TextArea
        });
        root.setCenter(notepadArea);

        // menu
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createEditMenu(notepadArea, primaryStage, notepadStatus, documentStatus), createOtherMenu());

        root.setTop(menuBar);

        // other things
        root.setPadding(new Insets(0, 0, 5, 0));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Menu createEditMenu(TextArea notepadArea, Stage primaryStage, NotepadStatus notepadStatus, DocumentStatus documentStatus) {
        Menu menuFile = new Menu("File");

        MenuItem openFile = new MenuItem("Open File");
        openFile.setOnAction((ActionEvent t) -> {
            notepadStatus.setOpening();
            try {
                fileManipulator.openFile(primaryStage);
                notepadArea.setText(fileManipulator.getTextFromOpenedFile());
            } catch (IOException e) {
                ioAlert(e.getMessage() + "\n\n" + Arrays.toString(e.getStackTrace()));
            }
            notepadStatus.setReady();
            documentStatus.setSaved();
        });

        MenuItem saveFile = new MenuItem("Save File");
        saveFile.setOnAction((ActionEvent t) -> {
            notepadStatus.setSaving();
            try {
                fileManipulator.saveFile(notepadArea.getText());
            } catch (Exception e) {
               ioAlert("File can not be saved!" + "\n\n" + Arrays.toString(e.getStackTrace()));
            }
            documentStatus.setSaved();
            notepadStatus.setReady();
        });

        SeparatorMenuItem separator = new SeparatorMenuItem();

        MenuItem exitItem = new MenuItem("Exit"); // just in case
        exitItem.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        menuFile.getItems().addAll(openFile, saveFile, separator, exitItem);
        return menuFile;
    }

    private Menu createOtherMenu() { // credits
        Menu menuFile = new Menu("Help");

        MenuItem showCredits = new MenuItem("About the application");
        showCredits.setOnAction((ActionEvent t) -> creditsAlert());

        menuFile.getItems().addAll(showCredits);
        return menuFile;
    }

    private void ioAlert(String text) { // overload method
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("I/O Error");
        alert.setGraphic(null);
        alert.setContentText(text);
        alert.getDialogPane().setMinSize(200, 100);
        alert.showAndWait();
    }

    private void creditsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About the application");
        alert.setGraphic(null);
        alert.setContentText("Creator: Mikhail Lavrov");
        alert.getDialogPane().setMinSize(200, 100);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
