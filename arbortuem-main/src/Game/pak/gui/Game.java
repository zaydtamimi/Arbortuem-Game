package comp1110.ass2.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Action;

import comp1110.ass2.Arboretum;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Game extends Application {
    /* board layout */

    private final Group root = new Group();
    private final Group controls = new Group();

    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private static final int GRID_SIZE = 50;
    private static final int GRID_DIMENSION = 10;
    private static final int WINDOW_XOFFSET = 10;
    private static final int WINDOW_YOFFSET = 30;
    private static final int TEXTBOX_WIDTH = 120;
    private static final int Card_WIDTH = 80;
    private static final int Card_HEIGHT = 112;
    private static final int ARBORETUM_YOFFSET = 345;
    private TextField turnIDTextField;
    private TextField aArboretumTextField;
    private TextField bArboretumTextField;
    private TextField aDiscardTextField;
    private TextField bDiscardTextField;
    private TextField deckTextField;
    private TextField aHandTextField;
    private TextField bHandTextField;
    private Stage primartStage;

    String [] locations = {"C00C00","N01C00","C00C00","C00E01"};
    

    String turn = "A";
    String aArbortuem = "A";
    String aDiscrad = "A";
    String bArbortuem = "B";
    String bDiscard = "B";
    String deck = "m6j8b8m2c2d5b7b5m7m5d3j1d7b3j6a1c6c3j2a6c1b1d2d8c7m4a3a5d6a7c5d1b4j5";
    String aHand = "Am1c4d1d4a5a2b1";
    String bHand = "Bj7d5m8m5b3a3c5";


    public String fromchar(String c){
        return switch (c.substring(0,1)) {
            case "a" ->  "Cassia_" + c.substring(1,2);
            case "b" -> "BlueSpruce_" + c.substring(1,2);
            case "c" -> "CherryBlossom_" + c.substring(1,2);
            case "d" -> "Dogwood_" + c.substring(1,2);
            case "j" -> "Jacaranda_" + c.substring(1,2);
            case "m" -> "Maple_" + c.substring(1,2);
            default -> null;
        };
    }
    /**
     * Through char to receive index of moving
     *
     * @param direction Direction String
     * @return move index
     */
    public int MoveArboretum(String direction){
        return switch (direction.substring(0,1)){
            case "N","W" ->  -Integer.parseInt(direction.substring(1,3));
            case "S","E" -> Integer.parseInt(direction.substring(1,3));
            case "C" -> 0;
            default -> 1;
        };
    }
    /**
     * Find the max length of Grid
     *
     * @param direction distance string
     * @return the maximum length of both x,y
     */
    public int FindPos(String direction){
        int board = 0;
        for (int i = 0; i < direction.length()/8; i ++)
            for (int j = 0; j < 2; j ++){
                int dis = Integer.parseInt(direction.substring(i*8+j*3+3,i*8+j*3+5));
                if (dis > board)
                    board = dis;
            }
        return board;
    }

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param gameState TASK 6 by Xiaodan Du
     */
    void displayState(String[][] gameState) {

        // FIXME Task 6: implement the simple state viewer
        // Display Each Player's Discard and Hand area
        
        if (gameState[1][0].length()/2==0) {
            showPopUp();
            return;
        }
        final Text target = new Text("DROP HERE");
        target.maxWidth(50);
        target.maxHeight(100);
        
        

        final Text target1 = new Text("DROP HERE");
        target1.maxWidth(50);
        target1.maxHeight(100);
        
        
        final Text target2 = new Text("DROP HERE");
        target2.maxWidth(50);
        target2.maxHeight(100);
        

        if (gameState[0][0].equals("B")) {
            
            target.setLayoutX(850);
            target.setLayoutY(260);

            target1.setLayoutX(560);
            target1.setLayoutY(60);

            target2.setLayoutX(770);
            target2.setLayoutY(330);
        }
        else{
           
            target.setLayoutX(470);
            target.setLayoutY(300);

            target1.setLayoutX(100);
            target1.setLayoutY(100);

            target2.setLayoutX(200);
            target2.setLayoutY(500);
        }

        root.getChildren().add(target);
        root.getChildren().add(target1);
        root.getChildren().add(target2);
        for (int i = 0; i < 2; i++) {
            Label Playt = new Label("Player " + gameState[0][i * 2 + 2].substring(0, 1) + " Discard");
            Playt.setFont(Font.font("Time New Roman", 10));
            Playt.setLayoutX(WINDOW_XOFFSET  + i * 550);
            Playt.setLayoutY(WINDOW_YOFFSET);
            root.getChildren().add(Playt);

            Label Playh = new Label("Player " + gameState[1][i + 1].substring(0, 1) + " Hand");
            Playh.setFont(Font.font("Time New Roman", 10));
            Playh.setLayoutX(WINDOW_XOFFSET + 360 + i * 550);
            Playh.setLayoutY(WINDOW_YOFFSET);
            root.getChildren().add(Playh);

            for (int j = 0; j < (gameState[0][i * 2 + 2].length() - 1) / 2; j++) {
                String Dis = "assets/" + fromchar(gameState[0][i * 2 + 2].substring(j * 2 + 1, j * 2 + 3)) + ".jpg";
                
                Image DisImage = new Image(Viewer.class.getResource(Dis).toString());
                ImageView Discard = new ImageView(DisImage);
                Discard.setFitWidth(Card_WIDTH);
                Discard.setFitHeight(Card_HEIGHT);
                int Discard_XOFFSET = WINDOW_XOFFSET;
                int Discard_YOFFSET = WINDOW_YOFFSET + 20 + (j*30);
                if (j > 6) {
                    Discard_XOFFSET += 20 + Card_WIDTH;
                    Discard_YOFFSET = WINDOW_YOFFSET + 20 + (j-7) * 30;
                }
                Discard.setLayoutX(Discard_XOFFSET + i * 550);
                Discard.setLayoutY(Discard_YOFFSET);
                final int tempi =i;
                final int tempj =j;
                Discard.setOnDragDetected(new EventHandler <MouseEvent>() {
                    public void handle(MouseEvent event) {
                        /* drag was detected, start drag-and-drop gesture*/
                        System.out.println("onDragDetected");
                        
                        /* allow any transfer mode */
                        Dragboard db = Discard.startDragAndDrop(TransferMode.ANY);
                        
                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(gameState[0][tempi * 2 + 2].substring(tempj * 2 + 1, tempj * 2 + 3));
                        db.setContent(content);
                        
                        event.consume();
                    }
                });
                target.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data is dragged over the target */
                        System.out.println("onDragOver");
                        
                        /* accept it only if it is  not dragged from the same node 
                         * and if it has a string data */
                        if (event.getGestureSource() != target &&
                                event.getDragboard().hasString()) {
                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        
                        event.consume();
                    }
                });
        
                target.setOnDragEntered(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */
                        System.out.println("onDragEntered");
                        /* show to the user that it is an actual gesture target */
                        if (event.getGestureSource() != target &&
                                event.getDragboard().hasString()) {
                            target.setFill(Color.GREEN);
                        }
                        
                        event.consume();
                    }
                });
        
                target.setOnDragExited(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
                        target.setFill(Color.BLACK);
                        
                        event.consume();
                    }
                });
                
                target.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            // target.setText(db.getString());
                            if (gameState[0][0].equals("B")) {
                                gameState[1][2] =gameState[1][2] + db.getString();
                                
                            }
                            else{
                                gameState[1][1] =gameState[1][1] + db.getString();
                                
                            }
                            
                            if (gameState[0][4].length() > 1 && db.getString().equals(gameState[0][4].substring(gameState[0][4].length()-2)))
                            {
                                gameState[0][4] = gameState[0][4].substring(0,gameState[0][4].length()-2);
                                
                            }else{
                                gameState[0][2] = gameState[0][2].substring(0,gameState[0][2].length()-2);
                                
                            }
                            
                            
                            
                            System.out.println(gameState[1][2]);
                            success = true;
                        }
                        /* let the source know whether the string was successfully 
                         * transferred and used */
                        event.setDropCompleted(success);
                        
                        event.consume();
                        
                    }
                });
        
                Discard.setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture ended */
                        System.out.println("onDragDone");
                        // root.getChildren().remove(tempImageView);
                        /* if the data was successfully moved, clear it */
                        
                        
                        event.consume();
                        root.getChildren().clear();
                        displayState(gameState);
                    }
                });
                root.getChildren().add(Discard);
            }
            
            for (int j = 0; j < (gameState[1][i + 1].length() - 1) / 2; j++) {
                String Hand = "assets/Deck.jpg";
                if (gameState[0][0].equals(gameState[1][i + 1].substring(0,1)))
                    Hand = "assets/" + fromchar(gameState[1][i + 1].substring(j * 2 + 1, j * 2 + 3)) + ".jpg";
                Image HandImage = new Image(Viewer.class.getResource(Hand).toString());
                ImageView HandCard = new ImageView(HandImage);
                HandCard.setFitWidth(Card_WIDTH);
                HandCard.setFitHeight(Card_HEIGHT);
                
                HandCard.setLayoutX(WINDOW_XOFFSET + 360 + i * 550);
                HandCard.setLayoutY(WINDOW_YOFFSET + 20 + j * 30);
                final int tempi =i;
                final int tempj =j;
                HandCard.setOnDragDetected(new EventHandler <MouseEvent>() {
                    public void handle(MouseEvent event) {
                        /* drag was detected, start drag-and-drop gesture*/
                        System.out.println("onDragDetected");
                        
                        /* allow any transfer mode */
                        Dragboard db = HandCard.startDragAndDrop(TransferMode.ANY);
                        
                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(gameState[1][tempi + 1].substring(tempj * 2 + 1, tempj * 2 + 3));
                        db.setContent(content);
                        
                        event.consume();
                    }
                });
                
                target1.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data is dragged over the target */
                        System.out.println("onDragOver");
                        
                        /* accept it only if it is  not dragged from the same node 
                         * and if it has a string data */
                        if (event.getGestureSource() != target1 &&
                                event.getDragboard().hasString()) {
                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        
                        event.consume();
                    }
                });
        
                target1.setOnDragEntered(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */
                        System.out.println("onDragEntered");
                        /* show to the user that it is an actual gesture target */
                        if (event.getGestureSource() != target1 &&
                                event.getDragboard().hasString()) {
                            target1.setFill(Color.GREEN);
                        }
                        
                        event.consume();
                    }
                });
        
                target1.setOnDragExited(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
                        target1.setFill(Color.BLACK);
                        
                        event.consume();
                    }
                });
                
                target1.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            // target1.setText(db.getString());
                            if (gameState[0][0].equals("B")) {
                                gameState[0][4] = gameState[0][4] + db.getString();
                                
                                gameState[0][0] = "A";
                                
                            }
                            else{
                                gameState[0][2] = gameState[0][2] + db.getString();
                                gameState[0][0] = "B";
                                
                            }
                            
                            
                            success = true;
                        }
                        /* let the source know whether the string was successfully 
                         * transferred and used */
                        event.setDropCompleted(success);
                        
                        event.consume();
                    }
                });
                
                
                target2.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data is dragged over the target */
                        System.out.println("onDragOver");
                        
                        /* accept it only if it is  not dragged from the same node 
                         * and if it has a string data */
                        if (event.getGestureSource() != target2 &&
                                event.getDragboard().hasString()) {
                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        
                        event.consume();
                    }
                });
        
                target2.setOnDragEntered(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */
                        System.out.println("onDragEntered");
                        /* show to the user that it is an actual gesture target */
                        if (event.getGestureSource() != target2 &&
                                event.getDragboard().hasString()) {
                            target1.setFill(Color.GREEN);
                        }
                        
                        event.consume();
                    }
                });
        
                target2.setOnDragExited(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
                        target1.setFill(Color.BLACK);
                        
                        event.consume();
                    }
                });
                
                target2.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            // target1.setText(db.getString());
                            if (gameState[0][0].equals("B")) {
                                Random rand = new Random();

                                // nextInt is normally exclusive of the top value,
                                // so add 1 to make it inclusive
                                int randomNum = rand.nextInt((3 - 0) + 1) + 0;
                                gameState[0][3] = gameState[0][3] + db.getString() + locations[randomNum];
                                
                            }
                            else{
                                Random rand = new Random();

                                // nextInt is normally exclusive of the top value,
                                // so add 1 to make it inclusive
                                int randomNum = rand.nextInt((3 - 0) + 1) + 0;
                                gameState[0][1] = gameState[0][1] + db.getString()+ locations[randomNum];
                                
                            }
                            
                            System.out.println(gameState[0][3]);
                            success = true;
                        }
                        /* let the source know whether the string was successfully 
                         * transferred and used */
                        event.setDropCompleted(success);
                        
                        event.consume();
                    }
                });
        
                
        
                HandCard.setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture ended */
                        System.out.println("onDragDone");
                        // root.getChildren().remove(HandCard);
                        if(gameState[0][0].equals("B")){
                            gameState[1][2] = gameState[1][2].substring(0,gameState[1][2].length()-2);
                        }
                        else{
                            gameState[1][1] = gameState[1][1].substring(0,gameState[1][1].length()-2);
                        }
                        event.consume();
                        root.getChildren().clear();
                        displayState(gameState);
                    }
                    });

                root.getChildren().add(HandCard);
            }
        }
       
        // Pane pane = new Pane();
        // pane.setLayoutX(tempone);
        // pane.setLayoutY(temptwo);
        // root.getChildren().add(pane);
        
        
        // Display Deck
        Label Deck = new Label("Deck:" + gameState[1][0].length()/2);
        Deck.setFont(Font.font("Time New Roman", 20));
        Deck.setLayoutX(WINDOW_XOFFSET + 465);
        Deck.setLayoutY(WINDOW_YOFFSET + 60);
        root.getChildren().add(Deck);
        String Deckimage = "assets/Deck.jpg";
        Image DeckImage = new Image(Viewer.class.getResource(Deckimage).toString());
        ImageView Decki = new ImageView(DeckImage);
        Decki.setOnMouseClicked(e ->
            {
                String contentString = gameState[1][0].substring(0, 2);
                String tempString = "assets/"+fromchar(gameState[1][0].substring(0, 2))+ ".jpg";
                gameState[1][0] = gameState[1][0].substring(2);
                Deck.setText("");
                Deck.setText("Deck:" + gameState[1][0].length()/2);
                Image tempImage = new Image(Viewer.class.getResource(tempString).toString());
                ImageView tempImageView = new ImageView(tempImage);
                tempImageView.setFitWidth(Card_WIDTH);
                tempImageView.setFitHeight(Card_HEIGHT);
                tempImageView.setLayoutX(WINDOW_XOFFSET + 460);
                tempImageView.setLayoutY(WINDOW_YOFFSET + 100);
                
                root.getChildren().add(tempImageView);
                // root.getChildren().forEach(this::makeDragable);
                tempImageView.setOnDragDetected(new EventHandler <MouseEvent>() {
                    public void handle(MouseEvent event) {
                        /* drag was detected, start drag-and-drop gesture*/
                        System.out.println("onDragDetected");
                        
                        /* allow any transfer mode */
                        Dragboard db = tempImageView.startDragAndDrop(TransferMode.ANY);
                        
                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(contentString);
                        db.setContent(content);
                        
                        event.consume();
                    }
                });
                
                target.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data is dragged over the target */
                        System.out.println("onDragOver");
                        
                        /* accept it only if it is  not dragged from the same node 
                         * and if it has a string data */
                        if (event.getGestureSource() != target &&
                                event.getDragboard().hasString()) {
                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        
                        event.consume();
                    }
                });
        
                target.setOnDragEntered(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */
                        System.out.println("onDragEntered");
                        /* show to the user that it is an actual gesture target */
                        if (event.getGestureSource() != target &&
                                event.getDragboard().hasString()) {
                            target.setFill(Color.GREEN);
                        }
                        
                        event.consume();
                    }
                });
        
                target.setOnDragExited(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
                        target.setFill(Color.BLACK);
                        
                        event.consume();
                    }
                });
                
                target.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {
                            // target.setText(db.getString());
                            if (gameState[0][0].equals("B")) {
                                gameState[1][2] =gameState[1][2] + db.getString();
                            }
                            else{
                                gameState[1][1] =gameState[1][1] + db.getString();
                            }
                            
                            System.out.println(gameState[1][2]);
                            success = true;
                        }
                        /* let the source know whether the string was successfully 
                         * transferred and used */
                        event.setDropCompleted(success);
                        
                        event.consume();
                    }
                });
        
                tempImageView.setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture ended */
                        System.out.println("onDragDone");
                        // root.getChildren().remove(tempImageView);
                        /* if the data was successfully moved, clear it */
                        
                        
                        event.consume();
                        root.getChildren().clear();
                        displayState(gameState);
                    }
                });
            }
           
        );  
        Decki.setFitWidth(Card_WIDTH);
        Decki.setFitHeight(Card_HEIGHT);
        Decki.setLayoutX(WINDOW_XOFFSET + 460);
        Decki.setLayoutY(WINDOW_YOFFSET + 100);
        root.getChildren().add(Decki);

        // Display Arboretum
        for (int i = 0; i < 2; i++) {
            Label Ara = new Label("Player " + gameState[0][i * 2 + 1].substring(0, 1) + " Arboretum");
            Ara.setFont(Font.font("Time New Roman", 10));
            Ara.setLayoutX(WINDOW_XOFFSET + 90 + i * 660);
            Ara.setLayoutY(ARBORETUM_YOFFSET);
            root.getChildren().add(Ara);
            GridPane Arboretum = new GridPane();
            
            for (int j = 0; j < (gameState[0][i * 2 + 1].length() - 1) / 8; j++) {
                String An = "assets/" + fromchar(gameState[0][i * 2 + 1].substring(j * 8 + 1, j * 8 + 3)) + ".jpg";
                Image AnImage = new Image(Viewer.class.getResource(An).toString());
                ImageView Ar = new ImageView(AnImage);
                
                Ar.setFitWidth(Card_WIDTH);
                Ar.setFitHeight(Card_HEIGHT);
                int CenPos = FindPos(gameState[0][i * 2 + 1].substring(1));
                Arboretum.add(Ar,CenPos+MoveArboretum(gameState[0][i * 2 +1].substring(j*8+6,j*8+9)),CenPos+MoveArboretum(gameState[0][i * 2 +1].substring(j*8+3,j*8+6)));
                Arboretum.setLayoutX(WINDOW_XOFFSET + i * 600);
                Arboretum.setLayoutY(ARBORETUM_YOFFSET+30);
            }
            root.getChildren().add(Arboretum);
        }
        root.getChildren().forEach(this::makeDragable);
    }
    
    private void showPopUp() {
        // create a button
        Button button = new Button("button");
   
        // create a tile pane
        TilePane tilepane = new TilePane();
   
        // create a label
        Label label = new Label("A is winner and get 6 score");
   
        // create a popup
        Popup popup = new Popup();
   
        // set background
        label.setStyle(" -fx-background-color: white;");
   
        // add the label
        popup.getContent().add(label);
   
        // set size of label
        label.setMinWidth(250);
        label.setMinHeight(250);
   
        popup.show(primartStage);
        // create a scene
        Scene scene = new Scene(tilepane, 200, 200);
   
        // set the scene
        primartStage.setScene(scene);
   
    }
    private double startX,startY;
    private void makeDragable(Node node){
        node.setOnMousePressed( e ->{
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
        });
        node.setOnMouseDragged(e -> {
            
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
        });
    }
    private void makeControls() {
        Label boardLabel = new Label("Player Turn ID");
        turnIDTextField = new TextField();
        turnIDTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aArboretum = new Label("Player A Arboretum:");
        aArboretumTextField = new TextField();
        aArboretumTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aDiscard = new Label("Player A Discard:");
        aDiscardTextField = new TextField();
        aDiscardTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bArboretum = new Label("Player B Arboretum:");
        bArboretumTextField = new TextField();
        bArboretumTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bDiscard = new Label("Player B Discard:");
        bDiscardTextField = new TextField();
        bDiscardTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label deck = new Label("Deck:");
        deckTextField = new TextField();
        deckTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aHand = new Label("Player A Hand:");
        aHandTextField = new TextField();
        aHandTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bHand = new Label("Player B Hand:");
        bHandTextField = new TextField();
        bHandTextField = new TextField();
        bHandTextField.setPrefWidth(TEXTBOX_WIDTH);

        Button displayState = new Button("Display State");
        displayState.setOnAction(e -> {
            String[] sharedState = {turnIDTextField.getText(), aArboretumTextField.getText(),
                    aDiscardTextField.getText(), bArboretumTextField.getText(), bDiscardTextField.getText()};
            String[] hiddenState = {deckTextField.getText(), aHandTextField.getText(), bHandTextField.getText()};
            displayState(new String[][]{sharedState, hiddenState});
            startGame();
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(boardLabel, turnIDTextField, aArboretum, aArboretumTextField, aDiscard,
                aDiscardTextField, bArboretum, bArboretumTextField, bDiscard, bDiscardTextField, deck, deckTextField,
                aHand, aHandTextField, bHand, bHandTextField, displayState);
        vbox.setSpacing(10);
        vbox.setLayoutX(10.4 * (GRID_SIZE) + (2 * WINDOW_XOFFSET) + (GRID_DIMENSION * GRID_SIZE) + (0.5 * GRID_SIZE));
        vbox.setLayoutY(WINDOW_YOFFSET);

        controls.getChildren().add(vbox);
    }

    
   
    @FXML
    Label numberOfPlayersLabel;
    
    

    public void modeScene() {
        Button vsPlayer = new Button("V/S Player");
        vsPlayer.setOnAction(
            e -> {
                numberOfPlayer();
            }
            
        );
        
        vsPlayer.setPrefSize(120, 50);
        Button vsComputer = new Button("V/S Computer");
        vsComputer.setOnAction(
            e -> {
                vsComputerLevel();
            } 
        );
        vsComputer.setPrefSize(120, 50);
        HBox hBox = new HBox(40,vsPlayer,vsComputer);
        hBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hBox,500,400);
        this.primartStage.setScene(scene);
    }
    public void vsComputerLevel() {
        Button simple = new Button("Simple");
        simple.setOnAction(
            e -> {
                numberOfPlayer();
            }
            
        );
        
        simple.setPrefSize(120, 50);
        Button expert = new Button("Expert");
        expert.setOnAction(
            e -> {
                numberOfPlayer();
            } 
        );
        expert.setPrefSize(120, 50);
        HBox hBox = new HBox(40,simple,expert);
        hBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hBox,500,400);
        this.primartStage.setScene(scene);
    }
    public void numberOfPlayer() {
        Label noOfPlayers = new Label("Enter the Number of Players");
        noOfPlayers.setPrefSize(200, 30);
        TextField noOfPlayersField = new TextField();
        noOfPlayersField.setPrefWidth(200);
        noOfPlayersField.setMaxWidth(200);
        Label maxvscomputer = new Label("Max no. of player vs computer: 3");
        noOfPlayers.setPrefSize(250, 30);
        Label maxvsplayer = new Label("Max no. of player vs player: 4");
        noOfPlayers.setPrefSize(250, 30);
        Button confirm = new Button("confirm");
        confirm.setOnAction(
            e -> {
                playerInfo(noOfPlayersField.getText(),1);
            } 
        );
        confirm.setPrefSize(120, 50);
        VBox hBox = new VBox(20,noOfPlayers,noOfPlayersField,maxvsplayer,maxvscomputer,confirm);
        hBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hBox,500,400);
        this.primartStage.setScene(scene);
    }
    ArrayList<String> names =  new ArrayList<>();
    public void playerInfo(String no,int playerno) {
        int number = Integer.parseInt(no);
        Label name = new Label("Enter player "+ playerno + " 's name");
            name.setPrefSize(200, 30);
            TextField nameField = new TextField();
            nameField.setPrefWidth(200);
            nameField.setMaxWidth(200);
            Button confirm = new Button("confirm");
            if (playerno==number) {
                confirm.setOnAction(
                e -> {
                    names.add(nameField.getText());
                    startGame();
                } 
            );
            } else {
                confirm.setOnAction(
                e -> {
                    names.add(nameField.getText());
                    int temp = playerno+1;
                    playerInfo(no, temp);
                } 
            );
            }
            
            confirm.setPrefSize(120, 50);
            VBox hBox = new VBox(20,name,nameField,confirm);
            hBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(hBox,500,400);
            this.primartStage.setScene(scene);
        
       
    }
    
    void startGame(){
        
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        String[] sharedState = {turn, aArbortuem,aDiscrad, bArbortuem, bDiscard};
        String[] hiddenState = {deck, aHand, bHand};
		displayState(new String[][]{sharedState, hiddenState});

        primartStage.setScene(scene);
        primartStage.centerOnScreen();
        primartStage.show();
    }
    @Override
    public void start(Stage stage) throws Exception {
        this.primartStage = stage;
        primartStage.setTitle("Arboretum");
        StackPane stackPane = new StackPane();
        Button button = new Button("Start");
        button.setOnAction(
            e -> modeScene()
        );
        button.setPrefSize(100, 50);
        stackPane.getChildren().add(button);
        Scene scene = new Scene(stackPane,500,400);
        primartStage.setScene(scene);
        primartStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
