package com.example.snakenladder;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.HashMap;

public class Player {
    String name;
    int pos;
    Player(String s){
        name = s;
        pos = 1;
    }

    public void play(int score, ImageView view, HashMap<Integer, Cordi> map, HashMap<Integer, Integer> tunnels){
        if(pos+score <= 100){
            pos += score;
            TranslateTransition animate = new TranslateTransition(Duration.millis(120*score), view);
            animate.setToX(map.get(pos).x+15);
            animate.setToY(map.get(pos).y+17);
            animate.setAutoReverse(false);
            animate.play();
            animate.setOnFinished(e -> slide(score, view, map, tunnels));
        }
    }

    public void slide(int score, ImageView view, HashMap<Integer, Cordi> map, HashMap<Integer, Integer> tunnels){
        if(tunnels.getOrDefault(pos, 0) > 0){
            pos = tunnels.get(pos);
            TranslateTransition animate = new TranslateTransition(Duration.millis(800), view);
            animate.setToX(map.get(pos).x+15);
            animate.setToY(map.get(pos).y+17);
            animate.setAutoReverse(false);
            animate.play();
        }
    }
}
