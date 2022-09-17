package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Viewer extends Application {

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


    private final Group root = new Group();
    private final Group controls = new Group();

    private TextField turnIDTextField;
    private TextField aArboretumTextField;
    private TextField bArboretumTextField;
    private TextField aDiscardTextField;
    private TextField bDiscardTextField;
    private TextField deckTextField;
    private TextField aHandTextField;
    private TextField bHandTextField;

    /**
     * Through char to receive corresponding type of tree
     *
     * @param c tree type and number
     * @return a complete string of tree name with number
     */
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
        // implement the simple state viewer
        // Display Each Player's Discard and Hand area
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
              root.getChildren().add(HandCard);
            }
        }
        // Display Deck
        Label Deck = new Label("Deck:" + gameState[1][0].length()/2);
        Deck.setFont(Font.font("Time New Roman", 20));
        Deck.setLayoutX(WINDOW_XOFFSET + 465);
        Deck.setLayoutY(WINDOW_YOFFSET + 60);
        root.getChildren().add(Deck);
        String Deckimage = "assets/Deck.jpg";
        Image DeckImage = new Image(Viewer.class.getResource(Deckimage).toString());
        ImageView Decki = new ImageView(DeckImage);
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
    }
    /**
     * Create a basic text field for input and a refresh button.
     */
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
        Label bHand = new Label("Player B Discard:");
        bHandTextField = new TextField();
        bHandTextField = new TextField();
        bHandTextField.setPrefWidth(TEXTBOX_WIDTH);

        Button displayState = new Button("Display State");
        displayState.setOnAction(e -> {
            String[] sharedState = {turnIDTextField.getText(), aArboretumTextField.getText(),
                    aDiscardTextField.getText(), bArboretumTextField.getText(), bDiscardTextField.getText()};
            String[] hiddenState = {deckTextField.getText(), aHandTextField.getText(), bHandTextField.getText()};
            displayState(new String[][]{sharedState, hiddenState});
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Arboretum Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}

