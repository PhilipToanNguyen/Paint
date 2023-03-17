package Figurer;

import com.example.paintfx.Main;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Linje extends Line {
    private Color linjefarge;
    private double lengdeX;
    private double lengdeY;

    //Konstruktør
    public Linje(Color linjefarge, double x, double y, double x2, double y2, double lengdeX, double lengdeY) {
    super (x,y,x2,y2);
    this.linjefarge = linjefarge;
    this.lengdeX = lengdeX;
    this.lengdeY = lengdeY;
        Main.valgtFigur = this;

        //Main har public static void som gjør at jeg kan hente metoden hit
        // Selektere figuren
        setOnMousePressed(e -> {
            //Regne ut linjelengde med pytagoras
            double a = ( (getEndY() - getStartY()) * (getEndY() - getStartY()) );
            double b = ( (getEndX() - getStartX()) * (getEndX() - getStartX()) );
            double c = Math.sqrt(a + b);
            Main.settInfo("Linje", " Har ikke fyllfarge", linjefarge.toString(),0, c);
            Main.valgtFigur = this;
        });
        // Flytte på figuren
        setOnMouseDragged(e -> {
            if (Main.kanDra) {      //Main har public static void som gjør at jeg kan hente metoden hit
                double width = getEndX()-getStartX();
                double height = getEndY()-getStartY();
                setStartX(e.getX()-width/2);
                setStartY(e.getY()-height/2);
                setEndX(getStartX()+width);
                setEndY(getStartY()+height);

            }
        });
    }
    //Setters og getters
    public Color getLinjefarge() {
        return linjefarge;
    }

    public void setLinjefarge(Color linjefarge) {
        this.linjefarge = linjefarge;
    }

    public double getLengdeX() {
        return lengdeX;
    }

    public void setLengdeX(double lengdeX) {
        this.lengdeX = lengdeX;
    }

    public double getLengdeY() {
        return lengdeY;
    }

    public void setLengdeY(double lengdeY) {
        this.lengdeY = lengdeY;
    }
}


