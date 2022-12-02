package com.example.graphesetimage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Controller {
    private File fic ;
    private Stage stage;
    private Desktop desktop ;
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView settingsIcon, helpIcon, addImageIcon, changePixelsIcon;

    @FXML
    private Pane pane ;

    @FXML
    private TextField inputPixelDepart ;
    @FXML
    private TextField inputPixelArrivee;

    private int pixelDepart ;
    private int pixelArrive;
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


    public void parcourirFichier(){
        FileChooser fileChooser = new FileChooser();
        this.fic = fileChooser.showOpenDialog(stage);
    }

    public void openPixels() throws IOException{
        Parent fmxlLoader = FXMLLoader.load(getClass().getResource("pixels.fxml"));
        this.pane.getChildren().removeAll();
        this.pane.getChildren().setAll(fmxlLoader);
    }

    public void openFileMenu() throws IOException{
        Parent fmxlLoader = FXMLLoader.load(getClass().getResource("file.fxml"));
        this.pane.getChildren().removeAll();
        this.pane.getChildren().setAll(fmxlLoader);
    }

    public void setPixelDepart() throws NumberFormatException {
        this.pixelDepart=Integer.parseInt(inputPixelDepart.getText());
    }

    public void setPixelArrive() throws NumberFormatException{
        this.pixelArrive=Integer.parseInt(inputPixelArrivee.getText());
    }

    public void validerPixel(){
        try {
            setPixelDepart();
            setPixelArrive();
        }
        catch (NumberFormatException e){
            System.out.println("Veuillez saisir un nombre !");
        }
        finally {
            System.out.println(inputPixelDepart.getText());
            System.out.println(inputPixelArrivee.getText());

        }
    }

}