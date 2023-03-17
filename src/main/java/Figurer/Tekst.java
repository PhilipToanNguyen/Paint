package Figurer;

import com.example.paintfx.Main;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Tekst extends Text {
    private Color fyllFarge;
    private Color linjeFarge;

    //Konstruktør
    public Tekst ( Color fyllFarge, Color linjeFarge){
        super();
        this.fyllFarge = fyllFarge;
        this.linjeFarge = linjeFarge;
        Main.valgtFigur = this;
        // Selektere figuren
        //Main har public static void som gjør at jeg kan hente metoden hit
        setOnMouseClicked(e -> {
            Main.settInfo("Tekst", fyllFarge.toString(), linjeFarge.toString(),0,0);
            Main.valgtFigur = this;
        });

        // Flytte på eksisterende tekst
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
