package com.example.graphesetimage;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe implémente les graphes en Java
 * @author Sami BOUFASSA
 */

public class Graph {
    private Map <Integer, ArrayList <Integer>> listeAdjacence ;
    private int nbSommets;

    /**
     * Constructeur vide
     */
    public Graph() {
        this.listeAdjacence = new HashMap<Integer, ArrayList<Integer>>();
    }


    /**
     * Modifie l'ordre du graphe
     * @param nbSommets nombre de sommets du graphe
     */
    public void setNbSommets(int nbLargeur, int nbHauteur) {
        this.nbSommets = nbLargeur*nbHauteur;
        for (int i =0 ;i<nbSommets; i++){
            listeAdjacence.put(i,new ArrayList<Integer>());
            listeAdjacence.get(i).add(i+1);
            listeAdjacence.get(i).add(i+nbLargeur);

            // Soit créer une classe pixel ?
            // Soit numéroter les pixels par ligne et parcourir l'image par ligne
        }
    }


}