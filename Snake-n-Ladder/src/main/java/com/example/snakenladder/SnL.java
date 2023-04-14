package com.example.snakenladder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SnL extends Application {

    public static final int tile_size = 60, rows = 10, cols = 10;
    public boolean p1turn = true;
    public Player p1 = new Player("Shubham");
    public Player p2 = new Player("Rohan");
    public Label score;
    public ImageView p1_img;
    public ImageView p2_img;
    public ImageView turn_view;
    public Image turn_blue;
    public Image turn_red;
    public HashMap<Integer, Cordi> map = new HashMap<>();
    public HashMap<Integer, Integer> tunnels = new HashMap<>();

    private Pane main_panel(){
        Pane root = new Pane();
        root.setPrefSize(tile_size*cols, tile_size*rows+100);

        //Making Tile grid of 10x10
        int c = 100;
        for(int i=0; i<rows; i++){
            if(i%2 == 0){
                for(int j=0; j<cols; j++){
                    map.put(c--, new Cordi(j*tile_size, i*tile_size));
                }
            } else{
                for(int j=cols-1; j>=0; j--){
                    map.put(c--, new Cordi(j*tile_size, i*tile_size));
                }
            }
        }
        System.out.println(map.get(1).x+" "+map.get(1).y);

        //Importing image
        Image img = new Image("C:\\Users\\shubh\\IdeaProjects\\Snake-n-Ladder\\src\\main\\java\\com\\example\\snakenladder\\board.png");
        ImageView board = new ImageView();
        board.setImage(img);
        board.setFitHeight(tile_size*rows);
        board.setFitWidth(tile_size*cols);
        root.getChildren().add(board);

        //Making Controles n buttons

        //Dice button
        Image dice_img = new Image("C:\\Users\\shubh\\IdeaProjects\\Snake-n-Ladder\\src\\main\\java\\com\\example\\snakenladder\\dice.png");
        ImageView dice_view = new ImageView();
        dice_view.setImage(dice_img);
        dice_view.setFitWidth(70);
        dice_view.setFitHeight(70);
        Button dice_button = new Button();
        dice_button.setGraphic(dice_view);
        dice_button.setPrefSize(70, 70);
        dice_button.setTranslateX(10);
        dice_button.setTranslateY(tile_size*rows + 10);
        dice_button.setOnAction(actionEvent -> roll());
        root.getChildren().add(dice_button);

        //Roll score
        score = new Label();
        score.setText("~");
        score.setStyle("-fx-font-size : 40pt; -fx-font-weight : bold;");
        score.setPrefSize(70, 70);
        score.setTranslateX(140);
        score.setTranslateY(tile_size*rows + 10);
        root.getChildren().add(score);

        //Turns display
        Label turn_text = new Label("Turn :");
        turn_text.setStyle("-fx-font-size : 20pt; -fx-font-weight : bold;");
        turn_text.setTranslateX(400);
        turn_text.setTranslateY(tile_size*rows + 30);
        root.getChildren().add(turn_text);

        turn_blue = new Image("C:\\Users\\shubh\\IdeaProjects\\Snake-n-Ladder\\src\\main\\java\\com\\example\\snakenladder\\blue.png");
        turn_red = new Image("C:\\Users\\shubh\\IdeaProjects\\Snake-n-Ladder\\src\\main\\java\\com\\example\\snakenladder\\red.png");
        turn_view = new ImageView();
        turn_view.setImage(turn_blue);
        turn_view.setFitHeight(60);
        turn_view.setFitWidth(50);
        turn_view.setTranslateX(500);
        turn_view.setTranslateY(tile_size*rows + 15);
        root.getChildren().add(turn_view);

        //Displaying players on board
        p1_img = new ImageView();
        p2_img = new ImageView();
        p1_img.setImage(turn_blue);
        p2_img.setImage(turn_red);

        p1_img.setFitWidth(30);
        p1_img.setFitHeight(40);
        p1_img.setTranslateX(map.get(p1.pos).x+5);
        p1_img.setTranslateY(map.get(p1.pos).y+5);

        p2_img.setFitWidth(30);
        p2_img.setFitHeight(40);
        p2_img.setTranslateX(map.get(p2.pos).x+5);
        p2_img.setTranslateY(map.get(p2.pos).y+5);

        //Adding Snakes and Ladders data to map
        tunnels.put(5, 35);
        tunnels.put(9, 51);
        tunnels.put(23, 42);
        tunnels.put(36, 5);
        tunnels.put(48, 86);
        tunnels.put(49, 7);
        tunnels.put(56, 8);
        tunnels.put(62, 83);
        tunnels.put(69, 91);
        tunnels.put(82, 20);
        tunnels.put(87, 66);
        tunnels.put(95, 38);

        root.getChildren().addAll(p1_img, p2_img);

        return root;
    }

    public void roll(){
        int scr = (int)(Math.random()*6) + 1;
        score.setText(Integer.toString(scr));
        if(p1turn){
            p1.play(scr, p1_img, map, tunnels);
            p1turn = !p1turn;
            turn_view.setImage(turn_red);
            if(p1.pos == 100){
                Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                alrt.setContentText(p1.name+" Wins !!");
                alrt.showAndWait();
                reset();
            }
        } else{
            p2.play(scr, p2_img, map, tunnels);
            p1turn = !p1turn;
            turn_view.setImage(turn_blue);
            if(p2.pos == 100){
                Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                alrt.setContentText(p2.name+" Wins !!");
                alrt.showAndWait();
                reset();
            }
        }
    }

    public void reset(){
        p1turn = true;
        score.setText("~");
        p1.pos = 1;
        p2.pos = 1;
        p1_img.setTranslateX(map.get(p1.pos).x+15);
        p1_img.setTranslateY(map.get(p1.pos).y+17);
        p2_img.setTranslateX(map.get(p2.pos).x+15);
        p2_img.setTranslateY(map.get(p2.pos).y+17);
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(main_panel());
        stage.setTitle("Snakes n Ladders !");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}