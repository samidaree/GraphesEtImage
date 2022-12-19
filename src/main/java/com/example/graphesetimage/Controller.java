package com.example.graphesetimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 * Cette classe permet de gérer tous les évènements et interactions avec l'utilisateur
 */
public class Controller {
    @FXML
    private ImageView imageViewDepart;
    private BufferedImage bi ;
    private Image image;

    private WritableImage wr ;
    @FXML
    private ImageView imageViewResultat;
    @FXML
    private AnchorPane resultat ;
    private File fic ;
    private Stage stage;
    private Scene scene;
    private Desktop desktop ;
    private Graph g ;
    @FXML
    private ImageView settingsIcon, helpIcon, addImageIcon, changePixelsIcon;

    @FXML
    private ColorPicker choisirCouleur ;
    private Parent fxmlLoader;
    @FXML
    private Pane pane ;

    @FXML
    private TextField inputPixelDepartX, inputPixelDepartY;
    @FXML
    private TextField inputPixelArriveeX, inputPixelArriveeY;

    private int pixelDepart ;
    private int pixelArrive;

    @FXML
    private AnchorPane anchorPixel ;
    @FXML
    private AnchorPane settings;

    private Color couleur  ;
    private FileChooser fileChooser = new FileChooser();

    private List <List<Pixel>> listeAdj ;

    private List<Pixel> pixels;

    private Label label ;
    private Text textError;

    public void iconSettingsEntered(){

        String css = getClass().getResource("style2.css").toExternalForm();

        settingsIcon.getScene().getStylesheets().add(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.settingsIcon.setEffect(ca);
    }
    public void iconAddImageEntered() {
        String css = getClass().getResource("style2.css").toExternalForm();

        addImageIcon.getScene().getStylesheets().add(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.addImageIcon.setEffect(ca);
    }
    public void iconChangePixelsEntered() {
        String css = getClass().getResource("style2.css").toExternalForm();

        changePixelsIcon.getScene().getStylesheets().add(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.changePixelsIcon.setEffect(ca);
    }
    public void iconHelpEntered() {
        String css = getClass().getResource("style2.css").toExternalForm();

        helpIcon.getScene().getStylesheets().add(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.helpIcon.setEffect(ca);
    }
    public void iconSettingsExited(){
        String css = getClass().getResource("style2.css").toExternalForm();
        settingsIcon.getScene().getStylesheets().remove(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.settingsIcon.setEffect(ca);
    }
    public void iconAddImageExited(){
        String css = getClass().getResource("style2.css").toExternalForm();
        addImageIcon.getScene().getStylesheets().remove(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.addImageIcon.setEffect(ca);
    }
    public void iconChangePixelsExited(){
        String css = getClass().getResource("style2.css").toExternalForm();
        changePixelsIcon.getScene().getStylesheets().remove(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.changePixelsIcon.setEffect(ca);
    }
    public void iconHelpExited(){
        String css = getClass().getResource("style2.css").toExternalForm();
        helpIcon.getScene().getStylesheets().remove(css);
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.helpIcon.setEffect(ca);
    }

    /**
     * Méthode permettant de switch entre les scènes lorsqu'un bouton du menu est cliqué
     * @param event
     */
    @FXML
    public void action(MouseEvent event) {
        if (event.getTarget() == addImageIcon){
            this.anchorPixel.getChildren().removeIf(Predicate.isEqual(textError));
            this.anchorPixel.getChildren().removeIf(Predicate.isEqual(label));

            pane.setVisible(true);
            settings.setVisible(false);
            anchorPixel.setVisible(false);
            resultat.setVisible(false);
        }

        try {
            if (event.getTarget()==changePixelsIcon && this.fic.exists()==true){
                anchorPixel.setVisible(true);

                if (bi != null) {
                    wr = new WritableImage(bi.getWidth(), bi.getHeight());
                    PixelWriter pw = wr.getPixelWriter();
                    for (int x = 0; x < bi.getWidth(); x++) {
                        for (int y = 0; y < bi.getHeight(); y++) {
                            pw.setArgb(x, y, bi.getRGB(x, y));
                        }
                    }
                }
                imageViewDepart.setImage(wr);
                centerImage(imageViewDepart);
                pane.setVisible(false);
                settings.setVisible(false);
                resultat.setVisible(false);
            }

        } catch (NullPointerException e){
            System.out.println("Vous devez d'abord choisir une image !");
            return ;
        }

        if (event.getTarget()==settingsIcon){
            settings.setVisible(true);
            pane.setVisible(false);
            anchorPixel.setVisible(false);
            resultat.setVisible(false);
        }
        if(event.getTarget()==helpIcon){
            if (anchorPixel.isVisible() || settings.isVisible() || pane.isVisible() || resultat.isVisible() ){
                settings.setVisible(false);
                pane.setVisible(false);
                anchorPixel.setVisible(false);
                resultat.setVisible(false);
            }
            else {
                pane.setVisible(true);

            }
        }

    }

    /**
     * Permet à l'utilisateur de séléctionner une image png ou jpg
     * @throws IOException si le fichier choisi est invalide
     */
    public void parcourirFichier() throws IOException{
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        this.fic = fileChooser.showOpenDialog(stage);
        try {
            bi = ImageIO.read(fic);
        } catch (IllegalArgumentException e){
            System.out.println("Veuillez choisir un fichier !") ;
            return ;
        }

        System.out.println(fic);
        System.out.println(bi);

        wr = null;
        if (bi != null) {
            wr = new WritableImage(bi.getWidth(), bi.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bi.getWidth(); x++) {
                for (int y = 0; y < bi.getHeight(); y++) {
                    pw.setArgb(x, y, bi.getRGB(x, y));
                }
            }
        }
        imageViewDepart.setImage(wr);
        imageViewDepart.fitWidthProperty();

        anchorPixel.setVisible(true);
        pane.setVisible(false);
        resultat.setVisible(false);
        centerImage(imageViewDepart);

        setGraph();
    }


    /**
     * Evenement lorsque l'utilisateur clique sur le button valider les pixels de départ et d'arrivé
     */
    public void validerPixel() {
        int xDepart = 0 ;
        int yDepart = 0 ;
        int xArrivee = 0;
        int yArrivee = 0 ;
        this.anchorPixel.getChildren().removeIf(Predicate.isEqual(textError));
        this.anchorPixel.getChildren().removeIf(Predicate.isEqual(label));

        Graph graph = null ;
        try {


             xDepart = Integer.parseInt(inputPixelDepartX.getText());
             yDepart = Integer.parseInt(inputPixelDepartY.getText());
             xArrivee = Integer.parseInt(inputPixelArriveeX.getText());
             yArrivee = Integer.parseInt(inputPixelArriveeY.getText());

            Pixel pArrive = null ;
            Pixel pDepart = null ;
            for (int i = 0; i<pixels.size(); i++) {
                if (pixels.get(i).getX()==xDepart && pixels.get(i).getY()==yDepart){
                    pDepart = pixels.get(i);
                    System.out.println("("+pDepart.getX()+","+pDepart.getY()+")");
                }

                if (pixels.get(i).getX()==xArrivee && pixels.get(i).getY()==yArrivee) {
                    pArrive=pixels.get(i);
                    System.out.println("("+pArrive.getX()+","+pArrive.getY()+")");

                }
            }


            graph = new Graph (bi.getWidth()* bi.getHeight(), pDepart);

            graph.dijkstra(this.listeAdj);

            graph.pathPixel(pArrive.getId());

            this.pane.setVisible(false);
            this.anchorPixel.setVisible(false);
            this.resultat.setVisible(true);
        }
        catch (NumberFormatException e){
            label = new Label("Veuillez saisir un nombre !");
            Font font = new Font("B612 Mono Bold",16.0);
            label.setFont(font);
            label.setTextFill(Color.valueOf("#cc0000"));
            label.setLayoutX(80);
            label.setLayoutY(350);
            label.setTextAlignment(TextAlignment.JUSTIFY);
            label.setMaxWidth(300);

            this.anchorPixel.getChildren().add(label);
            System.out.println("Veuillez saisir un nombre !");

            return ;
        } catch (NullPointerException e){
            Font font = new Font("B612 Mono Bold",11.0);
            textError = new Text("Veuillez entrer des pixels compris entre 0 et "  + bi.getWidth()+ " en abscisses et compris entre 0 et "+ bi.getHeight() + " en ordonnées !");
            textError.setWrappingWidth(390);
            textError.setFont(font);
            textError.setLayoutX(15);
            textError.setLayoutY(355);
            textError.setFill(Color.web("#cc0000"));
            textError.setTextAlignment(TextAlignment.CENTER);
            this.anchorPixel.getChildren().add(textError);


            System.out.println("Veuillez entrer des pixels compris entre 0 et"  + bi.getWidth()+ " en abscisses et compris entre 0 et "+ bi.getHeight() + " en ordonnées !");
            return ;
        }


        Stack<Integer> chemin = graph.getPile() ;
        //System.out.println(chemin );
        PixelWriter pw = wr.getPixelWriter();

        couleur = this.choisirCouleur.getValue();
        while(!chemin.isEmpty()){
            int id = chemin.pop();
            pw.setColor(pixels.get(id).getX(), this.pixels.get(id).getY(),  couleur);

        }

        imageViewResultat.setImage(wr);
        centerImage(imageViewResultat);

    }

    /**
     * Implémente le graph (V,E) avec V les pixels de l'image utilisateur et E la relation "se situe à l'un des 4 points cardinaux du pixel"
     */
    public void setGraph() {
        int height = bi.getHeight();
        int width = bi.getWidth();
        int V = height*width ;
        this.listeAdj = new ArrayList<>();
        this.pixels = new ArrayList<>();
        for (int i= 0; i<V; i++){
            List <Pixel> p= new ArrayList<>();
            listeAdj.add(p);
        }


        for (int i = 0 ; i<V; i++){
            if (i==0)
                pixels.add(new Pixel(0,0,0,0));
            if (i>0)
                pixels.add(new Pixel(i, bi.getRGB(i%width, i/width)-bi.getRGB((i-1)%width, (i-1)/width),(i)%width, i/width));
        }

        for (int i=0; i<pixels.size(); i++) {
            if ((i+1) %width !=0 ){
                listeAdj.get(i).add(pixels.get(i+1));
                listeAdj.get(i+1).add(pixels.get(i));

            }
            if (i<width*(height-1)) {
                listeAdj.get(i).add(pixels.get(i+width));
                listeAdj.get(i+width).add(pixels.get(i));

            }
        }

        //System.out.println(pixels);
        /*for (int i = 0 ; i<pixels.size(); i++) {
            System.out.println(pixels.get(i)+"\t"+pixels.get(i).getId());
        }*/
        //System.out.println(listeAdj);

    }

    /**
     * Permet de centrer une image dans une ImageView
     * @param imageView ImageView dont l'image fille doit être centrée
     */
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }


    /**
     * Changer la couleur du chemin lors de l'affichage du résultat
     * @param actionEvent
     */
    public void changerCouleur(ActionEvent actionEvent) {
        this.couleur = choisirCouleur.getValue();
    }
}
