package ui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import utility.Report;

import java.io.*;
import java.util.*;

import driver.PlagiarismDetector;

@SuppressWarnings("restriction")
public class PlagiarismDetectorUI extends Application {

    private static final String LAYER_0_INTRO = 
            "Layer 0 shows the hash code comparison.\n" + 
            "If both the uploaded files are exactly same, the score will be 100, otherwise it will be 0.\n";
    private static final String LAYER_1_INTRO =
            "Layer 1 shows function signature comparison.\n" + 
            "If two functions have the same return type and arguments, they are concluded to have matching signatures.\n"+
            	"Score is calculated as number of functions in program x that match to a function in program y divided by \n"+
            "the total number of functions in program x. Here program x is the program with lesser number of functions";
    private static final String LAYER_2_INTRO =
            "Layer 2 shows the abstract syntax tree comparison.\n" + 
            "It constructs Abstract Syntax Trees of the two uploaded files and analyzes the similarity by using Greedy String Tiling algorithm.\n";

  
	private Stage window;
    private Scene mainScene, secondScene;
    private VBox vBox_main_center, vBox_main_left, vBox_main_center_file1, vBox_main_center_file2;
    private VBox vBox_second_left, vBox_second_left_child1, vBox_second_left_child2;
    private HBox hBox_main, hBox_main_center_file1, hBox_main_center_file2, hBox_table_view, hBox_main_center, hBox_second_layer2;
    private Button btnHelp, btnCheck, btnFile1, btnFile2;
    private Button btnReturn;
    private Label lblFile1, lblFile2, lblAlert, lblTitle, lblIntro, lblScore, lblLayer2Intro;
    private FileChooser fileChooser;
    private TextField txtFileName1, txtFileName2;
    private TextArea txtScore, txtFile1Show, txtFile2Show, txtLayer2Intro;
    private File[] files = new File[2];

    private TableView<CodeLine> table1, table2;
    private TableColumn<CodeLine, Integer> lineNumberCol1;
    private TableColumn<CodeLine, String> codeCol1;
    private TableColumn<CodeLine, Integer> lineNumberCol2;
    private TableColumn<CodeLine, String> codeCol2;

    private TabPane tabPane;
    private LayerTab tabLayer0;
    private LayerTab tabLayer1;
    private Tab tabLayer2;

    
    List<Integer> lineNumbers1 = new ArrayList<>();
    List<String> codes1 = new ArrayList<>();
    List<Integer> lineNumbers2 = new ArrayList<>();
    List<String> codes2 = new ArrayList<>();
    
    
    

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Stage
        window = primaryStage;
        window.setTitle("Java Plagiarism Detector");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        window.setResizable(false);
        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });
        window.show();

        /******************************* Main Scene ************************************/

        // TextField
        txtFileName1 = new TextField();
        txtFileName2 = new TextField();

        // Label
        lblFile1 = new Label("File 1: ");
        lblFile1.getStyleClass().add("lblFile11");
        lblFile1.setTextAlignment(TextAlignment.CENTER);
        lblFile2 = new Label("File 2: ");
        lblFile2.getStyleClass().add("lblFile12");
        lblFile2.setTextAlignment(TextAlignment.CENTER);

        lblAlert = new Label("Warning! Uploaded files have the same name.");
        lblAlert.setTextAlignment(TextAlignment.CENTER);
        lblAlert.setTextFill(Color.RED);
        lblAlert.setVisible(false);
        lblAlert.getStyleClass().add("lblAlert1");

        lblTitle = new Label("Plagiarism Detector For Java");
        lblTitle.getStyleClass().add("lblTitle1");

        lblIntro = new Label("Application for comparing 2 Java files");
        lblIntro.getStyleClass().add("lblIntro1");
        lblIntro.setPadding(new Insets(50));

        


        // FileChooser
        fileChooser = new FileChooser();

        // TextArea
        txtFile1Show = new TextArea();
        txtFile1Show.setEditable(false);
        txtFile1Show.setWrapText(true);
        txtFile1Show.setPrefRowCount(100);
        
        txtFile2Show = new TextArea();
        txtFile2Show.setEditable(false);
        txtFile2Show.setWrapText(true);
        txtFile2Show.setPrefRowCount(100);
        
        
        // Buttons
        btnHelp = new Button("Need Help?");
        btnHelp.getStyleClass().add("btnHelp1");
        btnHelp.setPrefWidth(250);
        btnCheck = new Button("Check Plagiarism");
        btnCheck.setPrefWidth(250);
        btnCheck.getStyleClass().add("btnCheck1");
        btnFile1 = new Button("Browse");
        btnFile1.getStyleClass().add("btnFile1");
        btnFile2 = new Button("Browse");
        btnFile2.getStyleClass().add("btnFile2");
        DropShadow shadow = new DropShadow();

        /******** Shadow Effect *********/
        btnHelp.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnHelp.setEffect(shadow));
        btnHelp.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnHelp.setEffect(null));

        btnCheck.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnCheck.setEffect(shadow));
        btnCheck.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnCheck.setEffect(null));

        btnFile1.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnFile1.setEffect(shadow));
        btnFile1.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnFile1.setEffect(null));

        btnFile2.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnFile2.setEffect(shadow));
        btnFile2.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnFile2.setEffect(null));

        /********** Action *************/
        btnHelp.setOnAction(event -> {
            HelpPage.display("Help");
        });

        btnCheck.setOnAction(event -> {
           
            if(files[0] != null & files[1] != null) {
            	
                LoadingBox.display("Loading...");


                /*********** render UI *******************/
                
                // setup second scene
                
                initSecondScene();
                
            	try {
					PlagiarismDetector plagiarismDetector = new PlagiarismDetector(files[0], files[1]);
					Report[] reports=plagiarismDetector.generateFinalReport();

	                // Layer 0
	                tabLayer0.getTxtLayerIntro().setText(LAYER_0_INTRO);
	                tabLayer0.getTxtScore().setText(String.valueOf(reports[0].getScore() + "%"));
	                tabLayer0.getTxtMessage().setText(reports[0].getMessage());

	                // Layer 1
	                
	                tabLayer1.getTxtLayerIntro().setText(LAYER_1_INTRO);
	                tabLayer1.getTxtScore().setText(String.format("%.1f", reports[1].getScore()) + "%");
	                tabLayer1.getTxtMessage().setText(reports[1].getMessage());

	                // Layer 2
	                
	                table1.setItems(setUpData(lineNumbers1, codes1));
	                table2.setItems(setUpData(lineNumbers2, codes2));
	                
	                Map<Integer, List<Integer>> map = new HashMap<>();
	                map = getPlagLineNumbers(reports[2].getMessage());
	                List<Integer> lines1 = map.get(1);
	                List<Integer> lines2 = map.get(2);

	                plagiarismShow(table1, lines1);
	                plagiarismShow(table2, lines2);
	                
	                
	                txtScore.setText(String.format("%.1f", reports[2].getScore()) + "%");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
            	
                secondScene.getStylesheets().add("ui/plag.css");
                window.setScene(secondScene);
            } else {
                AlertBox.display("ERROR", "Please upload two files!");
            }
        });

        btnFile1.setOnAction(event -> {
            configureFileChooser();
            File selectedFile1 = fileChooser.showOpenDialog(window);
            if (selectedFile1 != null) {
                files[0] = selectedFile1;
                txtFileName1.setText(files[0].getName());
                String file1Content = readUploadFile(selectedFile1.getAbsolutePath(), lineNumbers1, codes1);
                txtFile1Show.setText(file1Content);
                changeAlertVisibility();
            }
        });

        btnFile2.setOnAction(event -> {
            configureFileChooser();
            File selectedFile2 = fileChooser.showOpenDialog(window);
            if (selectedFile2 != null) {
                files[1] = selectedFile2;
                txtFileName2.setText(files[1].getName());
                String file2Content = readUploadFile(selectedFile2.getAbsolutePath(), lineNumbers2, codes2);
                txtFile2Show.setText(file2Content);
                changeAlertVisibility();
            }
        });



        // Layout

        hBox_main_center_file1 = new HBox(5);
        hBox_main_center_file1.getChildren().addAll(lblFile1, txtFileName1, btnFile1);
        hBox_main_center_file1.setPadding(new Insets(20));
        hBox_main_center_file1.setAlignment(Pos.CENTER);

        vBox_main_center_file1 = new VBox(5);
        vBox_main_center_file1.setAlignment(Pos.CENTER);
        vBox_main_center_file1.setPadding(new Insets(5));
        vBox_main_center_file1.getChildren().addAll(hBox_main_center_file1, txtFile1Show);
        
        
        hBox_main_center_file2 = new HBox(5);
        hBox_main_center_file2.getChildren().addAll(lblFile2, txtFileName2, btnFile2);
        hBox_main_center_file2.setPadding(new Insets(20));
        hBox_main_center_file2.setAlignment(Pos.CENTER);
        
        vBox_main_center_file2 = new VBox(5);
        vBox_main_center_file1.setAlignment(Pos.CENTER);
        vBox_main_center_file1.setPadding(new Insets(5));
        vBox_main_center_file2.getChildren().addAll(hBox_main_center_file2, txtFile2Show);
        
        hBox_main_center = new HBox(5);
        hBox_main_center.setAlignment(Pos.CENTER);
        hBox_main_center.getChildren().addAll(vBox_main_center_file1,vBox_main_center_file2);
        

        vBox_main_center = new VBox(5);
        vBox_main_center.setPadding(new Insets(5));
        vBox_main_center.setAlignment(Pos.CENTER);
        vBox_main_center.getChildren().addAll(hBox_main_center, btnCheck,btnHelp, lblAlert);
        
        vBox_main_left = new VBox(5);
        vBox_main_left.setAlignment(Pos.CENTER);;
        vBox_main_left.getChildren().addAll(lblTitle, lblIntro);
        
        
        hBox_main = new HBox(5);
        hBox_main.setPadding(new Insets(5));
        hBox_main.getChildren().addAll(vBox_main_left, vBox_main_center);
        
        


        // Scene
        mainScene = new Scene(hBox_main, window.getWidth(), window.getHeight());
        mainScene.getStylesheets().add("ui/plag.css");
        window.setScene(mainScene);


    }


    /****************** helper methods *****************/

    // Pop up confirm box to ensure the user wants to close the window
    private void closeProgram() {
        boolean toClose = ConfirmBox.display("Exit", "Do you want to exit?");
        if(toClose) {
            window.close();
        }
    }


    // limit start point to search files and the postfix of files
    private void configureFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("View Java Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Java Files", "*.java")
        );
    }
    
    
    // change the visibility of lblAlert according to two upload file names
    private void changeAlertVisibility() {
        if(txtFileName1.getText().equals(txtFileName2.getText()))
            lblAlert.setVisible(true);
        else
            lblAlert.setVisible(false);
    }

    // read two uploaded Java files
    private String readUploadFile(String filePath, List<Integer> lineNumbers, List<String> codes) {
        int count = 0;
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String fileContent = "";
        String temp = "temp";
        try {
            while (temp != null) {
                temp = br.readLine();
                if(temp != null)
                	fileContent += temp + "\n";
                if (temp == null)
                    break;
                lineNumbers.add(++count);
                codes.add(temp);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }


    // Initialize the data inside TableView controls
    public static ObservableList<CodeLine> setUpData(List<Integer> lineNumbers, List<String> codes){
        if(lineNumbers == null || codes == null || lineNumbers.size() != codes.size()) {
            return null;
        }
        ArrayList<CodeLine> codeLines = new ArrayList<>();
        for(int i = 0; i < lineNumbers.size(); i++) {
            System.out.println();
            codeLines.add(new CodeLine(lineNumbers.get(i), codes.get(i)));
        }

        ObservableList<CodeLine> data = FXCollections.observableArrayList(codeLines);
        return data;
    }

    // Render the tableView to show code lines which are plagiarism
    private void plagiarismShow(TableView<CodeLine> table, List<Integer> plagLineNums){

        table.setRowFactory(t -> {
            return new TableRow<CodeLine>() {
                @Override
                protected void updateItem(CodeLine item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item == null | empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        if(plagLineNums.contains(item.getLineNumber())) {
                            setStyle("-fx-background-color: yellow");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });
    }


    // Parse the report message got from back end
    public static Map<Integer, List<Integer>> getPlagLineNumbers(String reportMessage) {
    	
    	
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> plagLineNumbers;
        int mapKey = 1;

        String[] plagLineNumbersList = reportMessage.split("\n");

        for(String numberList: plagLineNumbersList) {
            plagLineNumbers = new ArrayList<>();
            int length = numberList.length();
            numberList = numberList.substring(1, length - 1);
            if(numberList.length() > 0) {
                String[] numbers = numberList.split(", ");
                for(String number: numbers) {
                    int num = Integer.parseInt(number.trim());
                    plagLineNumbers.add(num);
                }
            }
            map.put(mapKey, plagLineNumbers);
            mapKey++;
        }
        return map;
    }

    
    // setup second scene
    @SuppressWarnings("unchecked")
	private void initSecondScene() {
    	
    	// Two tableViews

        table1 = new TableView<>();
        table2 = new TableView<>();
        table1.setEditable(false);
        table2.setEditable(false);
        table1.setPrefWidth(800);
        table2.setPrefWidth(800);

        lineNumberCol1 = new TableColumn<>("Lines");
        lineNumberCol2 = new TableColumn<>("Lines");
        codeCol1 = new TableColumn<>("Codes");
        codeCol2 = new TableColumn<>("Codes");

        lineNumberCol1.setCellValueFactory(new PropertyValueFactory<CodeLine, Integer>("lineNumber"));
        codeCol1.setCellValueFactory(new PropertyValueFactory<CodeLine, String>("code"));
        lineNumberCol2.setCellValueFactory(new PropertyValueFactory<CodeLine, Integer>("lineNumber"));
        codeCol2.setCellValueFactory(new PropertyValueFactory<CodeLine, String>("code"));

        lineNumberCol1.setPrefWidth(50);
        codeCol1.setPrefWidth(750);
        lineNumberCol2.setPrefWidth(50);
        codeCol2.setPrefWidth(750);
        lineNumberCol1.setSortable(false);
        codeCol1.setSortable(false);
        lineNumberCol2.setSortable(false);
        codeCol2.setSortable(false);

        table1.getColumns().addAll(lineNumberCol1, codeCol1);
        table2.getColumns().addAll(lineNumberCol2, codeCol2);


        // Buttons

        btnReturn = new Button("Finish and Return");
        btnReturn.getStyleClass().addAll("btnReturn1");
        
        DropShadow shadow = new DropShadow();
        btnReturn.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> btnReturn.setEffect(shadow));
        btnReturn.addEventHandler(MouseEvent.MOUSE_EXITED, event -> btnReturn.setEffect(null));
        
        
        btnReturn.setOnAction(event -> {
            txtFileName1.setText("");
            txtFileName2.setText("");
            txtFile1Show.setText("");
            txtFile2Show.setText("");
            files[0] = null;
            files[1] = null;
            lineNumbers1 = new ArrayList<>();
            lineNumbers2 = new ArrayList<>();
            codes1 = new ArrayList<>();
            codes2 = new ArrayList<>();
            table1 = new TableView<>();
            table2 = new TableView<>();
            window.setScene(mainScene);
        });


        // Layouts

        tabPane = new TabPane();
        tabLayer0 = new LayerTab("Layer 0");
        tabLayer1 = new LayerTab("Layer 1");
        tabLayer2 = new Tab("Layer 2");

        hBox_table_view = new HBox(40);
        hBox_table_view.getChildren().addAll(table1, table2);
        hBox_table_view.setAlignment(Pos.CENTER_LEFT);
        hBox_table_view.setPadding(new Insets(40));

    
        lblScore = new Label("Score: ");
        lblScore.getStyleClass().add("lblScore1");
        txtScore = new TextArea();
        txtScore.setEditable(false);
        txtScore.setWrapText(true);
        txtScore.setPrefHeight(50);
        txtScore.getStyleClass().add("text-area-score");
        lblLayer2Intro = new Label("Details: ");
        lblLayer2Intro.getStyleClass().add("lblIntroduction1");
        txtLayer2Intro = new TextArea();
        txtLayer2Intro.getStyleClass().add("text-area-intro");
        txtLayer2Intro.setEditable(false);
        txtLayer2Intro.setPrefWidth(120);
        txtLayer2Intro.setWrapText(true);
        txtLayer2Intro.setText(LAYER_2_INTRO);

        vBox_second_left_child1 = new VBox(5);
        vBox_second_left_child1.setPadding(new Insets(20));
        vBox_second_left_child1.getChildren().addAll(lblScore, txtScore);
        
        vBox_second_left_child2 = new VBox(5);
        vBox_second_left_child2.setPadding(new Insets(15));
        vBox_second_left_child2.getChildren().addAll(lblLayer2Intro, txtLayer2Intro);

        vBox_second_left = new VBox(5);
        vBox_second_left.getChildren().addAll(vBox_second_left_child1, vBox_second_left_child2, btnReturn);
        vBox_second_left.setAlignment(Pos.CENTER);
        

        hBox_second_layer2 = new HBox(5);
        hBox_second_layer2.getChildren().addAll(vBox_second_left, hBox_table_view);
        hBox_second_layer2.setAlignment(Pos.CENTER);

        tabLayer2.setContent(hBox_second_layer2);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(tabLayer0, tabLayer1, tabLayer2);

        secondScene = new Scene(tabPane, window.getWidth(), window.getHeight());
    }


    /*********** main method ***********/
    public static void main(String[] args) {
        launch(args);
    }
}

