package simplemodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

public class NotepadStatus {
    // javafx text element
    private final Text textElement = new Text();
    private StringProperty currentState; // current state for binding

    public NotepadStatus() {
        textElement.textProperty().bind(currentStateStringProperty()); // bind
    }

    public void setSaving() {setState("Saving...");}
    public void setOpening() {setState("Opening...");}
    public void setReady() {setState("Ready");}

    private void setState(String state) {
        currentStateStringProperty().set(state);
    }

    public Text getTextElement() {
        return textElement;
    }

    // property things
    public String getCurrentState() {
        return currentStateStringProperty().get();
    }

    public StringProperty currentStateStringProperty() {
        if (currentState == null) {
            currentState = new SimpleStringProperty();
        }
        return currentState;
    }
}
