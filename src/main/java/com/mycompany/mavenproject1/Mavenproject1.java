package com.mycompany.mavenproject1;

import java.util.ArrayList;

/**
 *
 * @author javie
 */
public class Mavenproject1 {

    public static void main(String[] args) {
        IVertice v1 = new TVertice("A");
        IVertice v2 = new TVertice("B");
        IVertice v3 = new TVertice("C");

        IArista a1 = new TArista(v1.getEtiqueta(), v2.getEtiqueta(), 1.0);
        IArista a2 = new TArista(v2.getEtiqueta(), v3.getEtiqueta(), 2.0);
        IArista a3 = new TArista(v1.getEtiqueta(), v3.getEtiqueta(), 3.0);


        ArrayList vertices = new ArrayList();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        TAristas aristas = new TAristas();
        aristas.add(a1);
        aristas.add(a2);
        aristas.add(a3);

        TGrafoNoDirigido grafo = new TGrafoNoDirigido(vertices, aristas);

        TGrafoNoDirigido mst = grafo.Prim();

        System.out.println("Arbol abarcador minimo:");
        for (IArista arista : mst.getLasAristas()) {
            System.out.println(arista.getEtiquetaOrigen() + " - " + arista.getEtiquetaDestino() + " : " + arista.getCosto());
        }
    }
}
