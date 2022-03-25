import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
    //global variable
    private int WIDTH = 1240;
    private int HEIGHT = 600;
    private Image[] CARDIMAGES;
    private String[] savePlayerName;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Group 13 - Card Game Assignment 2");
        primaryStage.getIcons().add(new Image("assets/icon.png"));

        initCardImages();

        enterNameStage(primaryStage);
        primaryStage.show();
    }

    //Enter player names
    private void enterNameStage(Stage stage){
        savePlayerName = new String[3];
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.setVgap(20);
        layout.setHgap(20);
        layout.setAlignment(Pos.CENTER);

        Button btn = new Button("Start Game");

        Label p1Label = new Label("Player 1: ");
        Label p2Label = new Label("Player 2: ");
        Label p3Label = new Label("Player 3: ");

        TextField p1 = new TextField();
        p1.setPromptText("Player 1");
        TextField p2 = new TextField();
        p2.setPromptText("Player 2");
        TextField p3 = new TextField();
        p3.setPromptText("Player 3");
        
        GridPane.setConstraints(p1Label, 0, 0);
        GridPane.setConstraints(p2Label, 0, 1);
        GridPane.setConstraints(p3Label, 0, 2);
        GridPane.setConstraints(p1, 1, 0);
        GridPane.setConstraints(p2, 1, 1);
        GridPane.setConstraints(p3, 1, 2);
        GridPane.setConstraints(btn, 1, 3);

        btn.setOnAction(e -> {
            if(checkPlayerName(p1, p2, p3)){
                savePlayerName[0] = p1.getText();
                savePlayerName[1] = p2.getText();
                savePlayerName[2] = p3.getText();
                readyStage(stage, new Game(p1.getText(), p2.getText(), p3.getText()));
            }
        });

        layout.getChildren().addAll(p1Label, p2Label, p3Label, p1, p2, p3, btn);
        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        stage.setScene(scene);
    }

    //Ready stage for player to choose shuffle to start the game
    private void readyStage(Stage stage, Game game){
        Button shuffleBtn = new Button("Shuffle");
        Button startBtn = new Button("Start");

        //Layout properties
        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: #35654d;");
        layout.setPadding(new Insets(30,30,30,30));
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(20);

        GridPane playerCards = getAvailableCard(game.getPlayers());

        //Button functions
        shuffleBtn.setOnAction(e -> {
            game.reShuffle();
            readyStage(stage, game);
        });
        startBtn.setOnAction(e -> playStage(stage, game, 1));

        GridPane.setConstraints(playerCards, 0, 0);
        GridPane.setConstraints(shuffleBtn, 0, 1);
        GridPane.setConstraints(startBtn, 1, 1);
        layout.getChildren().addAll(playerCards, shuffleBtn, startBtn);
        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        stage.setScene(scene);
    }

    //play stage
    private void playStage(Stage stage, Game game, int roundCount){
        //if player cards is below 5 -> game is over(3 player phase), cards is below 6 (2 player phase)
        if(game.getPlayers().get(0).getPlayeCards().size() < 5 || (game.getPlayers().size() == 2 && game.getPlayers().get(0).getPlayeCards().size() <= 6)){
            if(game.twoLose()){//finish 3 player phase if 2 player get 0 point
                game.getPlayers().get(0).setTotalPointsEarn(game.getPlayers().get(0).getWinPoints());
                winnerPage(stage, game);
            }else{ //finish 3 player phase
                if(game.getPlayers().size() > 2){
                //display who is lose
                String losePlayer = game.removeLosePlayer().getName();
                Alert message = new Alert(AlertType.INFORMATION);
                message.setHeaderText("3-Player phase End");
                message.setContentText(game.getPlayers().get(0).getName() + " and " +
                                        game.getPlayers().get(1).getName() + " proceed to 2-Player phase. " +
                                        losePlayer + " is lose!");
                message.showAndWait();
                game.resetPlayerPoint();
                game.reShuffle();
                readyStage(stage, game);
                }else{ //finish 2 player phase
                    game.removeLosePlayer();
                    game.getPlayers().get(0).setTotalPointsEarn(game.getPlayers().get(0).getTotalPointEarn() + game.getPlayers().get(0).getWinPoints());
                    winnerPage(stage, game);
                }
            }
            return; //exit this function
        }

        Player pWin = game.play();//play the game and get the winner
        int count = roundCount;//count round number

        Button proceedBtn = new Button("Next Round");
        proceedBtn.setOnAction(e -> {
            game.removePlayerCards();
            showAvailableCard(stage, game, count + 1);
        });

        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: #35654d;");
        layout.setPadding(new Insets(30,30,30,30));
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(20);

        Label roundTitle = new Label("Round: " + count);
        roundTitle.setFont(new Font("Courier New", 20));
        roundTitle.setStyle("-fx-text-fill: white");

        GridPane playerCards = getHandCard(game.getPlayers());

        GridPane board = getPlayerBoard(game.getPlayers(), pWin);
        GridPane.setMargin(board, new Insets(30, 30, 30, 30));

        Label winner = new Label("Winner is " + pWin.getName());
        winner.setFont(new Font("Courier New", 30));
        winner.setStyle("-fx-text-fill: white");    

        GridPane.setConstraints(roundTitle, 0, 0);
        GridPane.setConstraints(board, 1, 1);
        GridPane.setConstraints(playerCards, 0, 1);
        GridPane.setConstraints(winner, 0, 2);
        GridPane.setConstraints(proceedBtn, 1, 3);

        layout.getChildren().addAll(roundTitle, board, playerCards,  winner, proceedBtn);
        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        stage.setScene(scene);
    }

    private void winnerPage(Stage stage, Game game){
        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: #35654d;");
        layout.setPadding(new Insets(30,30,30,30));
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(20);
        layout.setHgap(20);

        Button continueBtn = new Button("Continue With Same Players");
        Button newBtn = new Button("Continue With New Players");

        continueBtn.setOnAction(e -> readyStage(stage, game));

        Label title = new Label("Congratulation!");
        Label winner = new Label("Winner: " + game.getPlayers().get(0).getName());
        Label points = new Label("Total Points Earn: " + game.getPlayers().get(0).getTotalPointEarn());

        title.setFont(new Font("Courier New", 30));
        title.setStyle("-fx-text-fill: white");
        winner.setFont(new Font("Courier New", 20));
        winner.setStyle("-fx-text-fill: white");
        points.setFont(new Font("Courier New", 20));
        points.setStyle("-fx-text-fill: white");

        GridPane.setConstraints(title, 0, 0);
        GridPane.setConstraints(winner, 0, 1);
        GridPane.setConstraints(points, 0, 2);
        GridPane.setConstraints(continueBtn, 0, 3);
        GridPane.setConstraints(newBtn, 1, 3);

        continueBtn.setOnAction(e -> 
            readyStage(stage, new Game(savePlayerName[0], savePlayerName[1], savePlayerName[2])));

        newBtn.setOnAction(e -> enterNameStage(stage));

        layout.getChildren().addAll(title, winner, points, continueBtn, newBtn);

        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        stage.setScene(scene);
    }

    private void showAvailableCard(Stage stage, Game game, int nextRoundCount){
        GridPane layout = new GridPane();
        layout.setStyle("-fx-background-color: #35654d;");
        layout.setPadding(new Insets(30,30,30,30));
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(20);

        Button nextBtn = new Button("Next Round");
        nextBtn.setOnAction(e -> {
            playStage(stage, game, nextRoundCount);
        });

        GridPane playerCards = getAvailableCard(game.getPlayers());

        GridPane.setConstraints(playerCards, 0, 0);
        GridPane.setConstraints(nextBtn, 1, 2);

        layout.getChildren().addAll(playerCards, nextBtn);

        Scene scene = new Scene(layout, WIDTH, HEIGHT);
        stage.setScene(scene);
    }

    //player board
    private GridPane getPlayerBoard(ArrayList<Player> players, Player pWin){
        ArrayList<Player> p = new ArrayList<>(players);

        Collections.sort(p, Collections.reverseOrder());//sort desc so player hold the most points will be at the top

        GridPane layout = new GridPane();
        layout.setMaxWidth(300);
        layout.setStyle("-fx-background-color: #edebeb;");
        layout.setPadding(new Insets(30, 30, 30, 30));
        layout.setVgap(50);

        Label title = new Label("Score: ");
        title.setFont(new Font("Courier New", 20));

        Label[] player= new Label[players.size()];

        GridPane.setConstraints(title, 0, 0);
        layout.getChildren().add(title);

        for(int i = 0; i < p.size(); ++i){
            player[i] = new Label(p.get(i).getName() + " = " + p.get(i).getWinPoints());
            player[i].setFont(new Font("Courier New", 15));
            GridPane.setConstraints(player[i], 0, i + 1);
            layout.getChildren().add(player[i]);
        }
        return layout;
    }

    //get card on hand
    private GridPane getHandCard(ArrayList<Player> players){
        GridPane layout = new GridPane();
        layout.setVgap(20);
        layout.setHgap(30);
        Label title = new Label("Cards at Hand: ");
        title.setFont(new Font("Courier New", 20));
        title.setStyle("-fx-text-fill: white");

        GridPane.setConstraints(title, 0, 0);

        for(int i = 0; i < players.size(); ++i){
            Label playerName = new Label(players.get(i).getName() + ":");
            playerName.setFont(new Font("Courier New", 20));
            playerName.setStyle("-fx-text-fill: white");

            Label points = new Label("Points: " + players.get(i).getCurrentPoint());
            points.setFont(new Font("Courier New", 20));
            points.setStyle("-fx-text-fill: white");

            GridPane temp = getCardGroup(players.get(i).getCurrentCard(), 10);
            GridPane.setConstraints(playerName, 0, i + 1);
            GridPane.setConstraints(temp, 1, i + 1);
            GridPane.setConstraints(points, 2, i + 1);
            layout.getChildren().addAll(playerName, points, temp);
        }

        return layout;
    }

    //get players available cards
    private GridPane getAvailableCard(ArrayList<Player> players){
        GridPane layout = new GridPane();
        layout.setVgap(20);
        layout.setHgap(30);
        Label title = new Label("Available Cards: ");
        title.setFont(new Font("Courier New", 30));
        title.setStyle("-fx-text-fill: white");

        GridPane.setConstraints(title, 1, 0);
        layout.getChildren().add(title);

        Label[] playerName = new Label[players.size()];
        for(int i = 0; i < players.size(); ++i){
            playerName[i] = new Label(players.get(i).getName() + ":");
            playerName[i].setFont(new Font("Courier New", 20));
            playerName[i].setStyle("-fx-text-fill: white");
            GridPane temp = getCardGroup(players.get(i).getPlayeCards(), -55);
            GridPane.setConstraints(playerName[i], 0, i + 1);
            GridPane.setConstraints(temp, 1, i + 1);
            layout.getChildren().addAll(playerName[i], temp);
        }

        return layout;  
    }

    //get images
    public GridPane getCardGroup(Collection<Card> cards, int hGap){
        GridPane layout = new GridPane();
        int count = 0;
        for(Card card:(cards instanceof Queue ? (Queue<Card>)cards : (TreeSet<Card>)cards)){
            //ImageView temp = new ImageView(CARDIMAGES.get((card.getFace()*card.getPoint()) - 1));
            ImageView temp = new ImageView(CARDIMAGES[(((card.getFace()-1)*13)+card.getOriginalPoint())-1]);
            temp.setFitHeight(125);
            temp.setFitWidth(85);
            layout.getChildren().add(temp);
            GridPane.setConstraints(temp, count, 0);
            ++count;
        }
        layout.setHgap(hGap);
        return layout;
    }

    private boolean checkPlayerName(TextField p1, TextField p2, TextField p3){
        if(p1.getText().isEmpty() || p2.getText().isEmpty() || p3.getText().isEmpty()){// empty field
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("Empty Field");
            message.setContentText("Please complete the empty field");
            message.showAndWait();
            return false;
        }else if(p1.getText().trim().equals(p2.getText().trim()) || p1.getText().trim().equals(p3.getText().trim()) || p2.getText().trim().equals(p3.getText().trim())){
            Alert message = new Alert(AlertType.ERROR);
            message.setHeaderText("Same Player Name");
            message.setContentText("Same player name are not allowed");
            message.showAndWait();
            return false; 
        }else
            return true;
    }

    //Initialize, load card images
    private void initCardImages(){
        CARDIMAGES = new Image[52]; // fixed 52 cards
        String[] cardName = {
        "cA", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "cX", "cJ", "cQ", "cK",
        "dA", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "dX", "dJ", "dQ", "dK",
        "hA", "h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "hX", "hJ", "hQ", "hK",
        "sA", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "sX", "sJ", "sQ", "sK",
        }; 
        for(int i = 0; i < 52; ++i){
            CARDIMAGES[i] = new Image("assets/" + cardName[i] + ".png");
        }
    }
}