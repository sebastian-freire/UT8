package com.mycompany.mavenproject1;

import java.util.Map;

public class PruebaGrafo {

    public static void main(String[] args) {
        TGrafoDirigido gd = (TGrafoDirigido) UtilGrafos.cargarGrafo(
                "src/main/java/uy/edu/ucu/aed/tdas/aeropuertos.txt",
                "src/main/java/uy/edu/ucu/aed/tdas/conexiones.txt",
                false, TGrafoDirigido.class);

        Object[] etiquetasarray = gd.getEtiquetasOrdenado();

        Double[][] matriz = UtilGrafos.obtenerMatrizCostos(gd.getVertices());
        UtilGrafos.imprimirMatrizMejorado(matriz, gd.getVertices(), "Matriz");
        Double[][] mfloyd = gd.floyd();
        UtilGrafos.imprimirMatrizMejorado(mfloyd, gd.getVertices(), "Matriz luego de FLOYD");
        for (int i = 0; i < etiquetasarray.length; i++) {
            System.out.println("excentricidad de " + etiquetasarray[i] + " : "
                    + gd.obtenerExcentricidad((Comparable) etiquetasarray[i]));
        }

        Map<Comparable, IVertice> mapa = gd.getVertices();
        for (Comparable c : mapa.keySet()) {
            System.out.println(c + " " + mapa.get(c).getDatos());
        }

    }
}
