package com.example.paintfx;
import Figurer.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javafx.scene.paint.Color;
import static javax.swing.JOptionPane.showInputDialog;
public class Main extends Application {

    //VINDUSTØRRELSE
    public final int HØYDE_MAX = 800;
    public final int BREDDE_MAX = 1000;
    //Tegnebrett / Canvas
    Pane canvas;
    // NAVNELAPP FOR SLIDERS OG FARGEVALG
    Label Størrelse;
    Label Rotasjon;
    Label Fyllfarge;
    Label Linjefarge;
    Label Ekstra;
    Label FigurListe;
    Label Oppdatering;
    //RadioKnapper
    ToggleGroup ValgFigur;
    RadioButton linjeKnapp;
    RadioButton sirkelKnapp;
    RadioButton rektangelKnapp;
    RadioButton tekstKnapp;
    RadioButton ellipseKnapp;
    RadioButton draFigurKnapp;
    RadioButton tilfeldigKnapp;

    //Knapper
    Button fram;
    Button bak;
    Button fjern;

    //Slider
    Slider sizeSlider;
    Slider rotasjonSlider;

    //Colorpicker
    public static ColorPicker colorfill;
    public static ColorPicker coloroutline;


    public int teller = 0;

    public static Shape valgtFigur = null;
    public static boolean kanDra = false;
    public ArrayList<Shape> shapeList = new ArrayList<>();

    //Tekst infomeny
    public static Text figurInfo = new Text("");
    public static Text fyllFargeInfo = new Text("");
    public static Text linjeFargeInfo = new Text("");
    public static Text arealInfo = new Text("");
    public static Text linjeLengdeInfo = new Text("");
    public static Text textInfo = new Text("");
    public static Text textOpplysninger = new Text("");
    public static Text textListe = new Text("");
    public static Text figurTekst = new Text("");

    @Override
    public void start(Stage vindu) throws IOException {
        //Lager dette slik at jeg kan heller hente blackBorder for å lage rammer, istedet for å skrive dette hver gang.
        String blackBorder = "-fx-border-style: solid; -fx-border-width: 2px; -fx-border-color: black";
        String blackBorderv2 = "-fx-border-style: solid; -fx-border-width: 2px; -fx-border-color: black";
        textOpplysninger.setFont(Font.font("verdana", FontWeight.THIN, FontPosture.REGULAR, 10));

        //Oppretter borderPane og setter padding
        BorderPane borderpane = new BorderPane();
        borderpane.setPadding(new Insets(5));

        //--------------------ULIKE VERKTØY SOM BLIR OPPRETTET OG DEKLARERT
        //Oppretter Radioknapper
        linjeKnapp = new RadioButton("Linje");
        sirkelKnapp = new RadioButton("Sirkel");
        rektangelKnapp = new RadioButton("Rektangel");
        tekstKnapp = new RadioButton("Tekst");
        ellipseKnapp = new RadioButton("Ellipse");
        draFigurKnapp = new RadioButton("Dra figur");
        tilfeldigKnapp = new RadioButton("Lag tilfeldig figur");


        //Lager en togglegroup for figurtyper
        //Velge en om gangen av figurtype
        ValgFigur = new ToggleGroup();
        linjeKnapp.setToggleGroup(ValgFigur);
        sirkelKnapp.setToggleGroup(ValgFigur);
        rektangelKnapp.setToggleGroup(ValgFigur);
        tekstKnapp.setToggleGroup(ValgFigur);
        ellipseKnapp.setToggleGroup(ValgFigur);
        tilfeldigKnapp.setToggleGroup(ValgFigur);
        draFigurKnapp.setToggleGroup(ValgFigur);


        //Flytt en foran knapp
        fram = new Button("Flytt Frem");
        fram.setOnAction(e -> flyttFram(e, canvas));
        fram.setPrefSize(100, 15);

        //Flytt en bak knapp
        bak = new Button("Flytt Bak");
        bak.setOnAction(e -> flyttBak(e, canvas));
        bak.setPrefSize(100, 15);

        //Fjern alt knapp
        fjern = new Button("Fjern");
        fjern.setOnAction(e -> fjern(e, canvas));
        fjern.setPrefSize(100, 15);

        //Velg størrelse før oppretting av figurer
        Størrelse = new Label("Størrelse");
        sizeSlider = new Slider(10, 150, 1); //Min, Max, Start
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);

        //Ikke fungerende, pga ikke lagt til funksjon.
        Ekstra = new Label("---Ekstra funksjoner---");
        Rotasjon = new Label("Rotasjon");
        rotasjonSlider = new Slider(1, 360, 1); //Min, Max, Start
        rotasjonSlider.setShowTickLabels(true);
        rotasjonSlider.setShowTickMarks(true);

        //-------------GUI----------------------------------------------- 1/2

        // (VENSTRE) --------------- 1/2
        //Alt legges til verktøyboksen aka VBoxen
        VBox verktøy = new VBox(linjeKnapp, sirkelKnapp, rektangelKnapp,
                                tekstKnapp,tilfeldigKnapp, draFigurKnapp,
                                fram, bak, fjern, Størrelse, sizeSlider,
                                Ekstra, ellipseKnapp, Rotasjon, rotasjonSlider);
        VBox leftCol = new VBox(verktøy); //Kaller denne leftCol, siden jeg skal sette det som setLeft.

        //Pynt med rammer og mellomrom
        leftCol.setStyle(blackBorder);
        leftCol.setPadding(new Insets(10, 10, 10, 10));
        verktøy.setSpacing(10);   //Mellomrom for ulike verktøy (VENSTRE BOKS)
        leftCol.getStyleClass().add("color-palette"); //Innvendig farge for Verktøy (VENSTRE)
        leftCol.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        // (VENSTRE) --------------- 2/2

        //(HØYRE) ------------------------------------------ 1/2
        //Oppretter ELEMENTER
        Fyllfarge = new Label("Fyllfarge");  // Navnelapp
        colorfill = new ColorPicker();
        Linjefarge = new Label("Linjefarge"); // Navnelapp
        coloroutline = new ColorPicker();
        FigurListe = new Label("Figurliste");

        //Info iforhold til hva man gjorde sist
        Oppdatering = new Label("Sist aktivitet: ");
        VBox logg = new VBox(Oppdatering, textInfo, figurTekst);

        //Pynt til logg
        logg.setStyle(blackBorderv2);
        logg.getStyleClass().add("color-palette");
        logg.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));

        //Alt legges til infoboksen aka VBoxen
        VBox rightCol = new VBox( //Kaller denne rightCol fordi jeg skal sette den til høyre
                    Fyllfarge, colorfill,
                     Linjefarge, coloroutline,
                figurInfo, fyllFargeInfo, linjeFargeInfo
                ,arealInfo, linjeLengdeInfo, textListe, logg);

        //Pynt med rammer og mellomrom
        rightCol.setStyle(blackBorder);
        rightCol.setPadding(new Insets(10, 10, 10, 10));
        rightCol.setSpacing(10); // Innvendig farge for Infoboks (HØYRE)
        rightCol.getStyleClass().add("color-palette");
        rightCol.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        //Ramme
        rightCol.setStyle(blackBorder);
        //(HØYRE) ------------------------------------------------2/2

        //CENTER / MIDTEN ------------------------------------------ 1/2
        //Tegnebrett, der man kan sette og lage figurer
        canvas = new Pane();
        canvas.setStyle(blackBorder);
        canvas.getStyleClass().add("color-palette");
        canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        //CENTER / MIDTEN ------------------------------------------ 2/2

        //BOTTOM --------------------------------------------------- 1/2
        //En slags sekundær "opplysning" Dette er kun for ekstra.
        //Denne teksten er kun ekstra, altså for å vise sist selektert figur.
        //draFigur() metoden vil påvirke denne.
        VBox botCol = new VBox(textOpplysninger);

        //Pynt til sistSelektert aka selektert opplysning
        botCol.setPadding(new Insets(5, 5, 5, 5));
        botCol.setStyle(blackBorderv2);
        botCol.getStyleClass().add("color-palette");
        botCol.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        //BOTTOM ---------------------------------------------------- 2/2

        // Setter posisjoner
        //Posisjon for verktøyboks, canvas og opplysninger
        borderpane.setCenter(canvas);
        borderpane.setRight(rightCol);
        borderpane.setLeft(leftCol);
        borderpane.setBottom(botCol);


        //TITTEL FOR VINDU/RESIZEABLE/ETC
        Scene scene = new Scene(borderpane, BREDDE_MAX, HØYDE_MAX);
        vindu.setTitle("PAINT FX");
        vindu.setResizable(true);
        vindu.setScene(scene);
        vindu.show();
        //-------------GUI---------------------------------------------------- 2/2

        /* MOUSE EVENTS */ //---------------------------------------------------- 1/2
        canvas.setOnMousePressed(e -> {
            kanDra = draFigurKnapp.isSelected(); //Uten denne kan man ikke dra figuren

            //Lage ulike former.
            // Trykker jeg på en av radioknappene, så skal jeg kunne
            // gjøre ulike handlinger.
            if (tilfeldigKnapp.isSelected()) {
                lagTilfeldig(e,canvas);
            }
            else if (sirkelKnapp.isSelected()) {
                    lagSirkel(e, canvas);
                } else if (rektangelKnapp.isSelected()) {
                    lagRektangel(e, canvas);
                } else if (linjeKnapp.isSelected()) {
                    lagLinje(e, canvas);
                } else if (tekstKnapp.isSelected()) {
                    lagTekst(e, canvas);
                } else if (ellipseKnapp.isSelected()) {
                    lagEllipse(e, canvas);
                }
            //Selektere og dra figur
            else if (draFigurKnapp.isSelected()) {
                draFigur(e, canvas); //setonmousedragged
            }
        });
        /* MOUSE EVENTS */ //---------------------------------------------------- 2/2
    }

    public static void main(String[] args) {
        launch();
    }

    //ULIKE Metoder for å lage figurer ------------------------- 1/2

    //Metode for å lage sirkel
    public void lagSirkel(MouseEvent e, Pane canvas) {
        Color fill = colorfill.getValue();
        Color outline = coloroutline.getValue();
        Sirkel sirkel = new Sirkel(fill, outline);
        sirkel.setRadius(sizeSlider.getValue());
        sirkel.setFill(fill);
        sirkel.setStroke(outline);
        sirkel.setCenterX(e.getX());
        sirkel.setCenterY(e.getY());
        canvas.setOnMouseDragged(event -> {
            sirkel.setRadius(event.getX() - e.getX());
            sirkel.setRadius(event.getY() - e.getY());
        });

        canvas.getChildren().add(sirkel);
        shapeList.add(sirkel);
        teller++;

        textInfo.setText("Du har laget en Sirkel!");
        figurTekst.setText("Du har: " + teller + " figur(er) på canvasen" + "\n" +
                "Selekter figuren for å sjekke " + "\n" + " opplysningene!");

    }

    //Metode for å lage Ellipse (Ekstra)
    public void lagEllipse(MouseEvent e, Pane canvas) {
        Color fill = colorfill.getValue();
        Color outline = coloroutline.getValue();
        Ellipse ellipse = new Ellipse(fill, outline);
        ellipse.setCenterX(sizeSlider.getValue());
        ellipse.setCenterY(sizeSlider.getValue());
        ellipse.setFill(fill);
        ellipse.setStroke(outline);
        ellipse.setCenterX(e.getX());
        ellipse.setCenterY(e.getY());

        canvas.setOnMouseDragged(event -> {
            ellipse.setRadiusX(event.getX() - e.getX());
            ellipse.setRadiusY(event.getY() - e.getY());
        });
        canvas.getChildren().add(ellipse);
        shapeList.add(ellipse);
        teller++;
        textInfo.setText("Du har laget en Ellipse!");
        figurTekst.setText("Du har: " + teller + " figur(er) på canvasen" + "\n" +
                "Selekter figuren for å sjekke " + "\n" + " opplysningene!");


    }

    //Metode for å lage Rektangel
    public void lagRektangel(MouseEvent e, Pane canvas) {
        Color fill = colorfill.getValue();
        Color outline = coloroutline.getValue();

        Rektangel rektangel = new Rektangel(e.getX(), e.getY(), sizeSlider.getValue(), sizeSlider.getValue(), fill, outline);
        rektangel.setFill(fill);
        rektangel.setStroke(outline);

        rektangel.setX(e.getX());
        rektangel.setY(e.getY());
        canvas.setOnMouseDragged(event -> {
            rektangel.setWidth(event.getX() - e.getX());
            rektangel.setHeight(event.getY() - e.getY());
        });
        canvas.getChildren().add(rektangel);
        shapeList.add(rektangel);
        teller++;
        textInfo.setText("Du har laget en Rektangel!");
        figurTekst.setText("Du har: " + teller + " figur(er) på canvasen" + "\n" +
                "Selekter figuren for å sjekke " + "\n" + " opplysningene!");


    }

    //Metode for å lage Linje
    public void lagLinje(MouseEvent e, Pane canvas) {
        Color outline = coloroutline.getValue();
        Linje linje = new Linje(outline, e.getX(), e.getY(), e.getX(), e.getY(), 0, 0);
        linje.setStroke(outline);
        canvas.setOnMouseDragged(event -> {
            linje.setEndX(event.getX());
            linje.setEndY(event.getY());
            linje.setStrokeWidth(sizeSlider.getValue());
        });
        canvas.getChildren().add(linje);
        shapeList.add(linje);
        teller++;
        textInfo.setText("Du har laget en Linje!");
        figurTekst.setText("Du har: " + teller + " figur(er) på canvasen" + "\n" +
                "Selekter figuren for å sjekke " + "\n" + " opplysningene!");

    }

    //Metode for å lage Tekst
    public void lagTekst(MouseEvent e, Pane canvas) {
        String melding = showInputDialog(null, "Hva vil du skrive?");
        Color fill = colorfill.getValue();
        Color outline = coloroutline.getValue();
        Tekst tekst = new Tekst(fill, outline);
        tekst.setFont(Font.font(null, sizeSlider.getValue()));
        tekst.setFill(fill);
        tekst.setStroke(outline);
        tekst.setX(e.getX());
        tekst.setY(e.getY());
        tekst.setText(melding);
        canvas.getChildren().add(tekst);
        shapeList.add(tekst);
        teller++;
        textInfo.setText("Du har skrevet en Tekst!");
        figurTekst.setText("Du har: " + teller + " figur(er) på canvasen" + "\n" +
                "Selekter teksten for å sjekke " + "\n" + " opplysningene!");
    }

    //Metode for å lage tilfeldig figur
    public void lagTilfeldig(MouseEvent e, Pane canvas) {
        //Kunne kanskje ha brukt switch?,
        // men fikk ikke til som jeg gjør med if og else if setninger
        // Har ikke fått til tilfeldig farge.
        Random bestemt = new Random();
        int valg;
        valg = bestemt.nextInt(5);
    if (valg == 0) {
        lagSirkel(e,canvas);
    }
    else if (valg == 1) {
        lagRektangel(e, canvas);
    }
    else if (valg == 2) {
        lagLinje(e,canvas);
    }
    else if (valg == 3) {
        lagEllipse(e,canvas);
    }
    else if (valg == 4) {
        lagTekst(e,canvas);
    }

    }
    //ULIKE Metoder for å lage figurer ------------------------- 2/2

    //ULIKE Metoder for å bevege figurer ------------------------------------------ 1/2
    /*BEVEGE FIGURER*///-------------------------------------------

    //Metode for drafigur
    public static void draFigur(MouseEvent e, Pane canvas) {
            canvas.setOnMouseDragged(event -> {
                    valgtFigur.setOpacity(0.6);
                    valgtFigur.setFill(colorfill.getValue());   // Gir muligheten til å bytte fyllfarge ved selektering.
                    valgtFigur.setStroke(coloroutline.getValue()); // Gir muligheten til å bytte linjefarge ved selektering.
                    textInfo.setText("Du har flyttet på en figur!");
                    figurTekst.setText("");
                 textOpplysninger.setText("Sist selektert:" + valgtFigur);
               // textOpplysninger.setText("Sist selektert:" + valgtFigur.getClass().getSimpleName()); // Haddde foretrukket dette, hvis jeg hadde hatt id.

            });
            //Selektert figur vil gi en opacity som gjør at det blir synlig på hvilke figur man drar i.
            //Til man slipper. Den man sist "rører", vil være den som er selektert.
            canvas.setOnMouseReleased(event -> {
                valgtFigur.setOpacity(1.0);

            });
    }
    //Metode for å flytte en figur foran en annen
    public void flyttFram(ActionEvent e, Pane canvas) {
        int index = canvas.getChildren().indexOf(valgtFigur);
   //Alternativ 1
    Collections.swap(shapeList, index , index+1);
    canvas.getChildren().clear();
    canvas.getChildren().addAll(shapeList);
        textInfo.setText("Selektert figur ble flyttet frem");
        figurTekst.setText("");

        //Denne ble brukt før jeg fant ut feilen.
        /* Alternativ 2
        if (canvas.getChildren().size() > 1) {
            int index = canvas.getChildren().indexOf(valgtFigur);
            if (index < canvas.getChildren().size()-1){
                Node temp = canvas.getChildren().get(index + 1); // gjør dette manuelt. pga feil ved collections.swap.
                canvas.getChildren().set(index + 1, new Line(0,0,0,0)); //new Line som placeholder, for å ikke miste indeksen
                canvas.getChildren().set(index, temp);
                canvas.getChildren().set(index + 1, valgtFigur);
            }
            textInfo.setText("Selektert figur ble flyttet frem");
            figurTekst.setText("");
        */

        }

    //Metode for å flytte en figur bak en annen
    public void flyttBak(ActionEvent e, Pane canvas) {
        int index = canvas.getChildren().indexOf(valgtFigur);

        //Alternativ 1
        Collections.swap(shapeList, index , index-1);
        canvas.getChildren().clear();
        canvas.getChildren().addAll(shapeList);
        textInfo.setText("Selektert figur ble flyttet bak"); //Sist aktivitet informasjon
        figurTekst.setText("");


        //Denne ble brukt før jeg fant ut feilen.
        /* Alternativ 2
       /* if (canvas.getChildren().size() > 1) {
            int index = canvas.getChildren().indexOf(valgtFigur);
            // System.out.println(test);
            if (index < canvas.getChildren().size()+1){
                Node temp = canvas.getChildren().get(index - 1); // gjør dette manuelt. pga feilmelding ved collections.swap();
                canvas.getChildren().set(index - 1, new Line(0,0,0,0)); //new Line som placeholder, for å ikke miste indeksen
                canvas.getChildren().set(index, temp);
                canvas.getChildren().set(index - 1, valgtFigur);
            }
            textInfo.setText("Selektert figur ble flyttet bak"); //Sist aktivitet informasjon
            figurTekst.setText("");
        */
        }

    //Metode for å fjerne alt aka blank ut canvasen
    public void fjern(ActionEvent e, Pane canvas) {
        if (e.getSource() == fjern) {
            canvas.getChildren().clear();
            textOpplysninger.setText("Alle figurer er blitt fjernet. Kan ikke hente opplysninger fra sist selektert figur");
        }
        else if (e.getSource() == fjern) {

        }
        textInfo.setText("Du har fjernet alt!");
        figurTekst.setText("Start å tegne på nytt!");
        teller = 0;

    }
    //Metode som brukes i ulike klasser for å få opplysninger om selektert figur
    public static void settInfo(String type, String farge1, String farge2, double verdi, double lengde) {

        figurInfo.setText("Figurtype: " + type );
        fyllFargeInfo.setText("Fyllfarge:" + farge1);
        linjeFargeInfo.setText("Linjefarge: " + farge2);
        arealInfo.setText("Areal: " + (verdi) + "px"); // Areal for sirkel, rektangel
        linjeLengdeInfo.setText("Linjelengde: " + (lengde) + "px"); //Lengde for linje



    }

    //ULIKE Metoder for å bevege figurer ------------------------------------------ 2/2
    }

