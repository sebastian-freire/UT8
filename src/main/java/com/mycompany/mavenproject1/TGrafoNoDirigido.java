package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class TGrafoNoDirigido extends TGrafoDirigido implements IGrafoNoDirigido {
    protected TAristas lasAristas = new TAristas();

    /**
     *
     * @param vertices
     * @param aristas
     */
    public TGrafoNoDirigido(Collection<IVertice> vertices, Collection<IArista> aristas) {
        super(vertices, aristas);
        lasAristas.insertarAmbosSentidos(aristas);

    }

    @Override
    public boolean insertarArista(IArista arista) {
        boolean tempbool = false;
        TArista arInv = new TArista(arista.getEtiquetaDestino(), arista.getEtiquetaOrigen(), arista.getCosto());
        tempbool = (super.insertarArista(arista) && super.insertarArista(arInv));
        return tempbool;
    }

    public TAristas getLasAristas() {
        return lasAristas;
    }

    @Override
    public TGrafoNoDirigido Prim() {
    ArrayList aristasTotal = new ArrayList();
    ArrayList verticesV = new ArrayList();
    ArrayList verticesU = new ArrayList();
    int costoPrim = 0;

    Map<Comparable, IVertice> verticesMap = this.getVertices();

    for (Map.Entry<Comparable, IVertice> entry : verticesMap.entrySet()) {
        IVertice vertice = entry.getValue();
        verticesV.add(vertice.getEtiqueta());
    }

    verticesU.add(this.getVertices().get(0).getEtiqueta());

    while (verticesV != null) {
        IArista aristaMin = this.getLasAristas().buscarMin(verticesU, verticesV);
        aristasTotal.add(aristaMin);
        verticesV.remove(aristaMin.getEtiquetaDestino());
        verticesU.add(aristaMin.getEtiquetaDestino());
        costoPrim += aristaMin.getCosto();
    }

    return new TGrafoNoDirigido(verticesU, aristasTotal);
    
    }

    @Override
    public TGrafoNoDirigido Kruskal() {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public Collection<TVertice> bea(Comparable etiquetaOrigen) {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
                                                                       // Tools | Templates.
    }

    @Override
    public Collection<TVertice> bea() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bea'");
    }
}
