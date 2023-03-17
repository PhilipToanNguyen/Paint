package Figurer;

import com.example.paintfx.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Sirkel extends Circle {
    private Color fyllFarge;
    private Color linjeFarge;

    //Konstruktør
    public Sirkel (Color fyllFarge, Color linjeFarge){
        super();
        this.fyllFarge = fyllFarge;
        this.linjeFarge = linjeFarge;
        Main.valgtFigur = this;

        //Main har public static void som gjør at jeg kan hente metoden hit
        setOnMousePressed(e -> {       // Selektere figuren
            Main.settInfo("Sirkel", fyllFarge.toString(), linjeFarge.toString(), (Math.PI * getRadius() * getRadius()),0 );
            Main.valgtFigur = this;
        });


        // Flytte på figuren
        setOnMouseDragged(e -> {
            if (Main.kanDra) {
                setCenterX(e.getX());
                setCenterY(e.getY());
            }

        });
    }
    //Getters og setters
    public Color getFyllFarge() {return fyllFarge;}

    public void setFyllFarge(Color fyllFarge) {
        this.fyllFarge = fyllFarge;
    }

    public Color getLinjeFarge() {
        return linjeFarge;
    }

    public void setLinjeFarge(Color linjeFarge) {
        this.linjeFarge = linjeFarge;
    }
}
