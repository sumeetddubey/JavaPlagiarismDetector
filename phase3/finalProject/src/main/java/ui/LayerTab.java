package ui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LayerTab extends Tab{

    private TextField txtScore, txtMessage;
    private TextArea txtLayerIntro;

    // Constructor

    public LayerTab(String text){
        this.setText(text);
        init();
    }

    // Initialize the LayerTab

    public void init(){

        GridPane gridPane = new GridPane();

        Label lblScore = new Label("Score: ");
        txtScore = new TextField();
        txtScore.setEditable(false);
        gridPane.addRow(1, lblScore, txtScore);

        Label lblMessage = new Label("Message: ");
        txtMessage = new TextField();
        txtMessage.setEditable(false);
        gridPane.addRow(2, lblMessage, txtMessage);

        Label lblLayerIntro = new Label("Introduction: ");
        txtLayerIntro = new TextArea();
        txtLayerIntro.setEditable(false);
        txtLayerIntro.setPrefWidth(75);
        txtLayerIntro.setWrapText(true);
        gridPane.addRow(5, lblLayerIntro, txtLayerIntro);

        this.setContent(gridPane);
    }

    // Getters

    public TextArea getTxtLayerIntro() {
        return txtLayerIntro;
    }

    public TextField getTxtScore() {
        return txtScore;
    }

    public TextField getTxtMessage() {
        return txtMessage;
    }


}
