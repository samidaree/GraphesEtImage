package com.example.graphesetimage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Desktop desktop ;
    private Graph g ;
    @FXML
    private ImageView settingsIcon, helpIcon, addImageIcon, changePixelsIcon;

    private Parent fxmlLoader;
    @FXML
    private Pane pane ;

    @FXML
    private TextField inputPixelDepart ;
    @FXML
    private TextField inputPixelArrivee;

    private int pixelDepart ;
    private int pixelArrive;

    @FXML
    private AnchorPane anchorPixel ;
    private FileChooser fileChooser = new FileChooser();


    public void iconSettingsEntered(){
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.settingsIcon.setEffect(ca);
    }
    public void iconAddImageEntered() {
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.addImageIcon.setEffect(ca);
    }
    public void iconChangePixelsEntered() {
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.changePixelsIcon.setEffect(ca);
    }
    public void iconHelpEntered() {
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(1.0);
        this.helpIcon.setEffect(ca);
    }
    public void iconSettingsExited(){
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.settingsIcon.setEffect(ca);
    }
    public void iconAddImageExited(){
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.addImageIcon.setEffect(ca);
    }
    public void iconChangePixelsExited(){
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.changePixelsIcon.setEffect(ca);
    }
    public void iconHelpExited(){
        ColorAdjust ca = new ColorAdjust();
        ca.setBrightness(0);
        this.helpIcon.setEffect(ca);
    }

    public void openSettings(){

    }
    @FXML
    public void action(MouseEvent event){
        if (event.getTarget() == addImageIcon){
            pane.setVisible(true);
            anchorPixel.setVisible(false);
            resultat.setVisible(false);
        }

        else if (event.getTarget()==changePixelsIcon && this.fic.exists()==true){
            anchorPixel.setVisible(true);
            pane.setVisible(false);
            resultat.setVisible(false);
        }
    }

    public void parcourirFichier() throws IOException{
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        this.fic = fileChooser.showOpenDialog(stage);
        bi = ImageIO.read(fic);

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


        anchorPixel.setVisible(true);
        pane.setVisible(false);
        resultat.setVisible(false);


        setGraph();
    }


    public void setPixelDepart() throws NumberFormatException {
        this.pixelDepart=Integer.parseInt(inputPixelDepart.getText());
    }

    public void setPixelArrive() throws NumberFormatException{
        this.pixelArrive=Integer.parseInt(inputPixelArrivee.getText());
    }

    public void validerPixel() throws IOException{
        try {
            setPixelDepart();
            setPixelArrive();
            this.pane.setVisible(false);
            this.anchorPixel.setVisible(false);
            this.resultat.setVisible(true);
            imageViewResultat.setImage(wr);
        }
        catch (NumberFormatException e){
            Label label = new Label("Veuillez saisir un nombre !");
            Font font = new Font("B612 Mono Bold",16.0);
            label.setFont(font);
            label.setTextFill(Color.valueOf("#cc0000"));
            label.setLayoutX(80);
            label.setLayoutY(340);

            this.anchorPixel.getChildren().add(label);
            System.out.println("Veuillez saisir un nombre !");
        }
        finally {
            System.out.println(inputPixelDepart.getText());
            System.out.println(inputPixelArrivee.getText());

        }
    }

    public void setGraph() throws IOException{
        System.out.println(fic);
        BufferedImage inputImage = ImageIO.read(fic);
        BufferedImage outputImage = new BufferedImage(16,16, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, 16, 16, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = "jpg";

        // writes to output file
        ImageIO.write(outputImage, formatName, new File("src/main/resources/com/example/graphesetimage/images/image2.jpg"));
        int height = outputImage.getHeight();
        int width = outputImage.getWidth();
        g = new Graph();

        this.g.setNbSommets(height*width);
        System.out.println(height+ "x" + width);
    }
}