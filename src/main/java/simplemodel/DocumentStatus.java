package simplemodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;

public class DocumentStatus {
    // javafx text element
    private final Text textElement = new Text();
    private StringProperty currentState; // current state for binding

    public DocumentStatus() {
        textElement.textProperty().bind(currentStateStringProperty()); // bind
    }

    public void setSaved() {setState("Saved");}
    public void setChanged() {setState("Changed");}

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
