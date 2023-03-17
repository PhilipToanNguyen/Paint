package Figurer;

import com.example.paintfx.Main;
import javafx.scene.paint.Color;


public class Ellipse extends javafx.scene.shape.Ellipse {
    private Color fyllFarge;
    private Color linjeFarge;
   //Konstruktør
    public Ellipse (Color fyllFarge, Color linjeFarge){
        super();
        this.fyllFarge = fyllFarge;
        this.linjeFarge = linjeFarge;
        Main.valgtFigur = this;

        // Selektere figuren
        //Main har public static void som gjør at jeg kan hente metoden hit
        setOnMousePressed(e -> {
            Main.settInfo("Ellipse", fyllFarge.toString(), linjeFarge.toString(), (Math.PI * getRadiusX() * getRadiusY()),0 );
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

    //Setters og getters

    public Color getFyllFarge() {
        return fyllFarge;
    }

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
