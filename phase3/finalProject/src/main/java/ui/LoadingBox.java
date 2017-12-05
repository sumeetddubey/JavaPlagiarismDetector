package ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("restriction")
public class LoadingBox {

	public static void display(String title) {

        // Stage
        
		Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(150);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMaxX()/2 - window.getWidth()/2);
        window.setY(bounds.getMaxY()/2 - window.getHeight()/2);
        window.show();

        // ProgressBar
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(-1.0f);
        
        // Layout
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(progressBar);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.CENTER);

        // Scene
        Scene scene = new Scene(hBox, 200, 100);
        scene.getStylesheets().add("ui/plag.css");
        window.setScene(scene);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished( event -> window.close() );
        delay.play();
    }
}
