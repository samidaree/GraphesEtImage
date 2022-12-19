package com.example.graphesetimage;


import javafx.scene.layout.Priority;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;

/**
 * Cette classe implémente l'algorithme de dijkstra
 * @author Sami BOUFASSA
 */

public class Graph {
    public int dist[];
    private Set<Integer> settled;
    private PriorityQueue<Pixel> pq;
    // Nombre de sommets
    private int V;
    private List<List<Pixel> > adj;

    private List<Integer> path ;
    private Pixel src ;
    private Stack<Integer> pile;

    /**
     * Constructeur du graphe
     * @param V Taille du graphe / Nombre de sommets
     * @param source pixel de départ
     */
    public Graph(int V, Pixel source)
    {
        this.src= source;
        this.V = V;
        dist = new int[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Pixel>(V, new Pixel());
        path = new ArrayList<>();
        for (int i =0; i<V; i++)
            path.add(0);
        pile = new Stack<>();
    }

    /**
     * Implémente l'algorithme de dijkstra
     * @param adj Liste qui contient à l'indice i une liste des pixels adjacents au pixel i
     */
    public void dijkstra(List<List<Pixel> > adj)
    {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Ajouter le pixel de départ à la file
        pq.add(new Pixel(src.getId(), 0, src.getX(),src.getY()));

        // Distance du pixel du départ est 0
        dist[src.getId()] = 0;

        while (settled.size() != V) {

            if (pq.isEmpty())
                return;

            // Retirer le pixel de distance minimale de la file
            int u = pq.remove().getId();

            // Ajouter le pixel dont la distance finale a été trouvée
            if (settled.contains(u))

                // Si le pixel courant a déjà été traité, passé au suivant
                continue;

            settled.add(u);

            // Traiter les voisins du pixel courant
            e_Neighbours(u);
        }
    }


    /**
     * Traite tous les voisins du pixel courant u
     * @param u pixel courant
     */
    private void e_Neighbours(int u)
    {

        int edgeDistance = -1;
        int newDistance = -1;

        // Tous les voisins du pixel u
        for (int i = 0; i < adj.get(u).size(); i++) {
            Pixel v = adj.get(u).get(i);

            // Si le pixel courant n'a pas déjà été traité
            if (!settled.contains(v.getId())) {
                edgeDistance = v.getCost();
                newDistance = dist[u] + edgeDistance;

                // Si la nouvelle distance est plus petite que sa précédente
                if (newDistance < dist[v.getId()]){
                    dist[v.getId()] = newDistance;
                    path.set(v.getId(), u);
                }

                // Ajouter le pixel courant à la file pour traiter ses voisins par la suite
                pq.add(new Pixel(v.getId(), dist[v.getId()], v.getX(), v.getY()));
            }
        }
    }

    /**
     * Empiler tous les pixels (leur id) rencontrés sur le chemin optimal
     * en partant du pixel d'arrivé (d'où l'utilisation d'une pile et non pas un simple tableau)
     * @param v id du pixel d'arrivé
     */
    public void pathPixel(int v){

        int i = v;
        while (i!=src.getId()){
            //System.out.println(i);
            pile.add(i);
            i = path.get(i) ;

        }
        pile.add(src.getId());

    }

    /**
     *
     * @return la pile contenant les pixels rencontrés sur le chemin optimal
     */
    public Stack <Integer> getPile(){
        return this.pile;
    }
}



