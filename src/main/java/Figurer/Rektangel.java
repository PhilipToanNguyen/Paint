package Figurer;

import com.example.paintfx.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rektangel extends Rectangle {
    private Color fyllFarge;
    private Color linjeFarge;

    public Rektangel(double x, double y, double w, double h,  Color fyllFarge, Color linjeFarge) {
        super(x,y,w,h);
        this.fyllFarge = fyllFarge;
        this.linjeFarge = linjeFarge;
        Main.valgtFigur = this;
        //Main har public static void som gjør at jeg kan hente metoden hit
        // Selektere figuren
        setOnMousePressed(e -> {
            Main.settInfo("Rektangel", fyllFarge.toString(), linjeFarge.toString(),(getHeight()*getWidth()),0);
            Main.valgtFigur = this;
        });

        // Flytte på figuren
        setOnMouseDragged(e -> {
            if (Main.kanDra) {
                setX(e.getX());
                setY(e.getY());
            }
        });

    }
    //Getters og setters
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
