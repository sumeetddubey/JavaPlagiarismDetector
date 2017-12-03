package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpPage {

    public static void display(String title){
        Stage window;
        Scene scene;
        Label lblCaption;
        TextArea textArea;
        VBox vBox;

        // Stage
        window = new Stage();
        // block input events or user interaction with other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(100);
        window.setMinHeight(100);

        // TextArea
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setText("Text of Help Page");

        // Label
        lblCaption = new Label("Help For Users");
        lblCaption.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        lblCaption.setLabelFor(textArea);

        // Layout
        vBox = new VBox();
        vBox.getChildren().addAll(lblCaption, textArea);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        // Scene
        scene = new Scene(vBox, 100, 100);
        window.setScene(scene);
        // display the window and before going back, it needs to be closed
        window.showAndWait();
    }
}
