package com.example.graphesetimage;

import java.util.*;
import java.util.stream.Stream;

public class Pixel implements Comparator<Pixel> {
    private int id ;
    private int x, y;
    private int cost ;

    /**
     * Classe représentant un pixel de l'image
     */
    public Pixel(){}
    public Pixel (int id, int cost, int x , int y){
        this.id = id ;
        this.cost = cost;
        // Coordonnées du pixel
        this.x = x;
        this.y = y ;
    }

    public int getId(){
        return id;
    }
    public int getX(){return x; }
    public int getY(){
        return y ;
    }
    public int getCost(){
        return this.cost ;
    }

    @Override
    public int compare(Pixel p1, Pixel p2){
        if (p1.getCost()<p2.getCost())
            return -1 ;
        if (p1.getCost()>p2.getCost())
            return 1;
        return 0;
    }

    public String toString (){
        return "("+this.getX()+","+this.getY()+")";
    }
}
