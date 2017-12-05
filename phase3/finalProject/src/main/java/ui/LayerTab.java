package ui;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

@SuppressWarnings("restriction")
public class LayerTab extends Tab{

    private TextArea txtScore, txtMessage;
    private TextArea txtLayerIntro;

    // Constructor

    public LayerTab(String text){
        this.setText(text);
        init();
    }

    // Initialize the LayerTab

    public void init(){

     

        Label lblScore = new Label("Score: ");
        lblScore.getStyleClass().add("lblScore1");
        lblScore.setPadding(new Insets(0,120,0,0));
        txtScore = new TextArea();
        txtScore.setEditable(false);
        txtScore.setPrefColumnCount(50);
        txtScore.setWrapText(true);
        txtScore.setPrefHeight(50);
        txtScore.getStyleClass().add("text-area-score");
        HBox hBox_child1 = new HBox(5);
        hBox_child1.setPadding(new Insets(10));
        hBox_child1.getChildren().addAll(lblScore, txtScore);
       

        Label lblMessage = new Label("Message: ");
        lblMessage.getStyleClass().add("lblMessage1");
        lblMessage.setPadding(new Insets(0,90,0,0));
        txtMessage = new TextArea();
        txtMessage.setEditable(false);
        txtMessage.setPrefColumnCount(50);
        txtMessage.setPrefRowCount(50);
        txtMessage.setWrapText(true);
        txtMessage.setPrefHeight(100);
        txtMessage.getStyleClass().add("text-area-message");
        HBox hBox_child2 = new HBox(5);
        hBox_child2.setPadding(new Insets(10));
        hBox_child2.getChildren().addAll(lblMessage, txtMessage);
       
        Label lblLayerIntro = new Label("Details: ");
        lblLayerIntro.getStyleClass().add("lblIntroduction1");
        lblLayerIntro.setPadding(new Insets(0,110,0,0));
        txtLayerIntro = new TextArea();
        txtLayerIntro.setEditable(false);
        txtLayerIntro.setPrefColumnCount(50);
        txtLayerIntro.setWrapText(true);
        txtLayerIntro.getStyleClass().add("text-area-intro");
        
        HBox hBox_child3 = new HBox(5);
        hBox_child3.setPadding(new Insets(10));
        hBox_child3.getChildren().addAll(lblLayerIntro, txtLayerIntro);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox_child1, hBox_child2, hBox_child3);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.setPadding(new Insets(0,0,0,450));  

        this.setContent(vBox);
    }

    // Getters

    public TextArea getTxtLayerIntro() {
        return txtLayerIntro;
    }

    public TextArea getTxtScore() {
        return txtScore;
    }

    public TextArea getTxtMessage() {
        return txtMessage;
    }


}
