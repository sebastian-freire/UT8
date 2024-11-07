package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TGrafoDirigido implements IGrafoDirigido {

    private Map<Comparable, IVertice> vertices; // vertices del grafo.-

    public TGrafoDirigido(Collection<IVertice> vertices, Collection<IArista> aristas) {
        this.vertices = new HashMap<>();
        for (IVertice vertice : vertices) {
            insertarVertice(vertice.getEtiqueta());
        }
        for (IArista arista : aristas) {
            insertarArista(arista);
        }
    }

    /**
     * Metodo encargado de eliminar una arista dada por un origen y destino. En
     * caso de no existir la adyacencia, retorna falso. En caso de que las
     * etiquetas sean invalidas, retorna falso.
     *
     */
    public boolean eliminarArista(Comparable nomVerticeOrigen, Comparable nomVerticeDestino) {
        if ((nomVerticeOrigen != null) && (nomVerticeDestino != null)) {
            IVertice vertOrigen = buscarVertice(nomVerticeOrigen);
            if (vertOrigen != null) {
                return vertOrigen.eliminarAdyacencia(nomVerticeDestino);
            }
        }
        return false;
    }

    /**
     * Metodo encargado de verificar la existencia de una arista. Las etiquetas
     * pasadas por par�metro deben ser v�lidas.
     *
     * @return True si existe la adyacencia, false en caso contrario
     */
    public boolean existeArista(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        IVertice vertOrigen = buscarVertice(etiquetaOrigen);
        IVertice vertDestino = buscarVertice(etiquetaDestino);
        if ((vertOrigen != null) && (vertDestino != null)) {
            return vertOrigen.buscarAdyacencia(vertDestino) != null;
        }
        return false;
    }

    /**
     * Metodo encargado de verificar la existencia de un vertice dentro del
     * grafo.-
     *
     * La etiqueta especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return True si existe el vertice con la etiqueta indicada, false en caso
     *         contrario
     */
    public boolean existeVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta) != null;
    }

    /**
     * Metodo encargado de verificar buscar un vertice dentro del grafo.-
     *
     * La etiqueta especificada como parametro debe ser valida.
     *
     * @param unaEtiqueta Etiqueta del vertice a buscar.-
     * @return El vertice encontrado. En caso de no existir, retorna nulo.
     */
    public IVertice buscarVertice(Comparable unaEtiqueta) {
        return getVertices().get(unaEtiqueta);
    }

    /**
     * Metodo encargado de insertar una arista en el grafo (con un cierto
     * costo), dado su vertice origen y destino.- Para que la arista sea valida,
     * se deben cumplir los siguientes casos: 1) Las etiquetas pasadas por
     * parametros son v�lidas.- 2) Los vertices (origen y destino) existen
     * dentro del grafo.- 3) No es posible ingresar una arista ya existente
     * (miso origen y mismo destino, aunque el costo sea diferente).- 4) El
     * costo debe ser mayor que 0.
     *
     * @return True si se pudo insertar la adyacencia, false en caso contrario
     */
    public boolean insertarArista(IArista arista) {
        if ((arista.getEtiquetaOrigen() != null) && (arista.getEtiquetaDestino() != null)) {
            IVertice vertOrigen = buscarVertice(arista.getEtiquetaOrigen());
            IVertice vertDestino = buscarVertice(arista.getEtiquetaDestino());
            if ((vertOrigen != null) && (vertDestino != null)) {
                return vertOrigen.insertarAdyacencia(arista.getCosto(), vertDestino);
            }
        }
        return false;
    }

    /**
     * Metodo encargado de insertar un vertice en el grafo.
     *
     * No pueden ingresarse vertices con la misma etiqueta. La etiqueta
     * especificada como par�metro debe ser v�lida.
     *
     * @param unaEtiqueta Etiqueta del vertice a ingresar.
     * @return True si se pudo insertar el vertice, false en caso contrario
     */
    public boolean insertarVertice(Comparable unaEtiqueta) {
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            IVertice vert = new TVertice(unaEtiqueta);
            getVertices().put(unaEtiqueta, vert);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    @Override
    public boolean insertarVertice(IVertice vertice) {
        Comparable unaEtiqueta = vertice.getEtiqueta();
        if ((unaEtiqueta != null) && (!existeVertice(unaEtiqueta))) {
            getVertices().put(unaEtiqueta, vertice);
            return getVertices().containsKey(unaEtiqueta);
        }
        return false;
    }

    public Object[] getEtiquetasOrdenado() {
        TreeMap<Comparable, IVertice> mapOrdenado = new TreeMap<>(this.getVertices());
        return mapOrdenado.keySet().toArray();
    }

    /**
     * @return the vertices
     */
    public Map<Comparable, IVertice> getVertices() {
        return vertices;
    }

    public Comparable centroDelGrafo() {
        Double[][] A = this.floyd();
        int i = 0;

        double valorTotal = Integer.MAX_VALUE;
        Comparable centro = "El grafo no tiene centro.";
        for (Comparable etiquetaVertice : vertices.keySet()) {
            double valorIndividual = Integer.MIN_VALUE;

            for (int j = 0; j < vertices.size(); j++) {
                if (A[j][i] > valorIndividual) {
                    valorIndividual = A[j][i];
                }
            }
            if (valorIndividual < valorTotal) {
                valorTotal = valorIndividual;
                centro = etiquetaVertice;
            }

            i++;

        }
        return centro;
    }

    public Double[][] floyd() {

        Double[][] A = UtilGrafos.obtenerMatrizCostos(this.vertices);

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (A[i][k] + A[k][j] < A[i][j]) {
                        A[i][j] = A[i][k] + A[k][j];
                    }
                }
            }
        }
        return A;
    }

    public Double[] dijkstra(IVertice vertice) {

        Double[][] A = UtilGrafos.obtenerMatrizCostos(this.vertices);
        Double[] D = new Double[A.length];

        int i = 0;
        for (Comparable ver : vertices.keySet()) {
            if (ver.compareTo(vertice.getEtiqueta()) == 0) {
                for (int j = 0; j < vertices.size(); j++) {
                    D[j] = A[i][j];
                }
            } else {
                i++;
            }
        }

        Boolean[] recorrido = new Boolean[D.length];
        recorrido[i] = true;

        for (int z = 0; z < recorrido.length; z++) {
            Double min = Double.MAX_VALUE;
            int index = 0;
            for (int x = 0; x < recorrido.length; x++) {
                if (recorrido[x] != true) {
                    Double dis = D[x];
                    if (dis < min) {
                        min = dis;
                        index = x;
                    }
                }
            }

            recorrido[index] = true;

            for (int x = 0; x < D.length; x++) {
                if (recorrido[x] != true) {
                    D[x] = Math.min(D[x], D[index] + A[index][x]);
                }
            }
        }

        return D;
    }

    public Comparable obtenerExcentricidad(Comparable etiquetaVertice) {
        Double[][] A = this.floyd();
        int i = 0;
        double valor = Integer.MIN_VALUE;
        for (Comparable vertice : vertices.keySet()) {
            if (vertice.compareTo(etiquetaVertice) == 0) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (A[j][i] > valor) {
                        valor = A[j][i];
                    }
                }
            } else {
                i++;
            }
        }
        Comparable devoler = valor;
        return devoler;
    }

    public boolean[][] warshall() {
        Double[][] A = UtilGrafos.obtenerMatrizCostos(this.vertices);
        boolean[][] W = new boolean[vertices.size()][vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (A[i][j] != Double.MAX_VALUE) {
                    W[i][j] = true;
                } else {
                    W[i][j] = false;
                }
            }
        }

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    if (!W[i][j]) {
                        W[i][j] = (W[i][k] && W[k][j]);
                    }
                }
            }
        }
        return W;
    }

    @Override
    public boolean eliminarVertice(Comparable nombreVertice) {
        if (existeVertice(nombreVertice)) {
            for (Comparable nombreVertice2 : vertices.keySet()) {
                if (existeArista(nombreVertice2, nombreVertice)) {
                    eliminarArista(nombreVertice2, nombreVertice);
                }
            }
            vertices.remove(nombreVertice);
            return true;

        }
        return false;
    }

    @Override
    public void desvisitarVertices() {
        for (IVertice vertice : this.getVertices().values()) {
            vertice.setVisitado(false);
        }
    }

    @Override
    public Collection<TVertice> bpf() {
        this.desvisitarVertices();
        Collection<TVertice> lista = new LinkedList<>();
        for (IVertice vertice : this.getVertices().values()) {
            if (!vertice.getVisitado()) {
                vertice.bpf(lista);
            }
        }
        return lista;
    }

    @Override
    public Collection<TVertice> bpf(TVertice vertice) {
        this.desvisitarVertices();
        Collection<TVertice> lista = new LinkedList<>();
        if (vertice != null && vertices.get(vertice.getEtiqueta()) != null) {
            vertice.bpf(lista);
        }
        return lista;
    }

    @Override
    public Collection<TVertice> bpf(Comparable etiquetaOrigen) {
        this.desvisitarVertices();
        Collection<TVertice> lista = new LinkedList<>();
        IVertice vertice = this.getVertices().get(etiquetaOrigen);
        if (vertice != null) {
            vertice.bpf(lista);
        }
        return lista;
    }

    @Override
    public TCaminos todosLosCaminos(Comparable etiquetaOrigen, Comparable etiquetaDestino) {
        IVertice origen = buscarVertice(etiquetaOrigen);
        IVertice destino = buscarVertice(etiquetaDestino);
        TCaminos caminos = new TCaminos();
        if (origen != null && destino != null) {
            TCamino caminoPrevio = new TCamino((TVertice) origen);
            origen.todosLosCaminos(etiquetaDestino, caminoPrevio, caminos);
        } else {
            return null;
        }
        return caminos;
    }

    public Boolean tieneCiclo() {
        this.desvisitarVertices();
        for (IVertice vertice : this.getVertices().values()) {
            TCamino camino = new TCamino((TVertice) vertice);
            if (!vertice.getVisitado()) {
                if (((TVertice) vertice).tieneCiclo(camino)) {
                    return true;
                }
            }
        }
        return false;
    }
}
