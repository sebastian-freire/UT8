package com.mycompany.mavenproject1;

import java.util.Collection;
import java.util.LinkedList;

public class TVertice<T> implements IVertice {

    private final Comparable etiqueta;
    private LinkedList<IAdyacencia> adyacentes;
    private boolean visitado;
    private T datos;

    public Comparable getEtiqueta() {
        return etiqueta;
    }

    public LinkedList<IAdyacencia> getAdyacentes() {
        return adyacentes;
    }

    public TVertice(Comparable unaEtiqueta) {
        this.etiqueta = unaEtiqueta;
        adyacentes = new LinkedList();
        visitado = false;
    }

    public void setVisitado(boolean valor) {
        this.visitado = valor;
    }

    public boolean getVisitado() {
        return this.visitado;
    }

    @Override
    public IAdyacencia buscarAdyacencia(IVertice verticeDestino) {
        if (verticeDestino != null) {
            return buscarAdyacencia(verticeDestino.getEtiqueta());
        }
        return null;
    }

    @Override
    public Double obtenerCostoAdyacencia(IVertice verticeDestino) {
        IAdyacencia ady = buscarAdyacencia(verticeDestino);
        if (ady != null) {
            return ady.getCosto();
        }
        return Double.MAX_VALUE;
    }

    @Override
    public boolean insertarAdyacencia(Double costo, IVertice verticeDestino) {
        if (buscarAdyacencia(verticeDestino) == null) {
            TAdyacencia ady = new TAdyacencia(costo, verticeDestino);
            return adyacentes.add(ady);
        }
        return false;
    }

    @Override
    public boolean eliminarAdyacencia(Comparable nomVerticeDestino) {
        IAdyacencia ady = buscarAdyacencia(nomVerticeDestino);
        if (ady != null) {
            adyacentes.remove(ady);
            return true;
        }
        return false;
    }

    @Override
    public IVertice primerAdyacente() {
        if (this.adyacentes.getFirst() != null) {
            return this.adyacentes.getFirst().getDestino();
        }
        return null;
    }

    public IVertice siguienteAdyacente(IVertice w) {
        IAdyacencia adyacente = buscarAdyacencia(w.getEtiqueta());
        int index = adyacentes.indexOf(adyacente);
        if (index + 1 < adyacentes.size()) {
            return adyacentes.get(index + 1).getDestino();
        }
        return null;
    }

    @Override
    public IAdyacencia buscarAdyacencia(Comparable etiquetaDestino) {
        for (IAdyacencia adyacencia : adyacentes) {
            if (adyacencia.getDestino().getEtiqueta().compareTo(etiquetaDestino) == 0) {
                return adyacencia;
            }
        }
        return null;
    }

    @Override
    public void bpf(Collection<TVertice> visitados) {
        if (!this.getVisitado()) {
            this.setVisitado(true);
            visitados.add(this);
            for (IAdyacencia adyacencia : this.adyacentes) {
                IVertice destino = adyacencia.getDestino();
                destino.bpf(visitados);
            }
        }
    }

    /**
     *
     * @return
     */
    public T getDatos() {
        return datos;
    }

    @Override
    public TCaminos todosLosCaminos(Comparable etVertDest, TCamino caminoPrevio, TCaminos todosLosCaminos) {
        this.setVisitado(true);
        for (IAdyacencia adyacencia : this.getAdyacentes()) {
            IVertice destino = adyacencia.getDestino();
            if (!destino.getVisitado()) {
                if (destino.getEtiqueta().compareTo(etVertDest) == 0) {
                    TCamino copia = caminoPrevio.copiar();
                    copia.agregarAdyacencia((TAdyacencia) adyacencia);
                    todosLosCaminos.getCaminos().add(copia);
                } else {
                    TCamino copia = caminoPrevio.copiar();
                    copia.agregarAdyacencia((TAdyacencia) adyacencia);
                    todosLosCaminos = destino.todosLosCaminos(etVertDest, copia, todosLosCaminos);
                }
            }
        }
        this.setVisitado(false);
        return todosLosCaminos;
    }

    public Boolean tieneCiclo(TCamino unCamino) {
        this.setVisitado(true);
        for (IAdyacencia adyacencia : this.getAdyacentes()) {
            TVertice destino = (TVertice) adyacencia.getDestino();

            if (unCamino.getOrigen().getEtiqueta().compareTo(destino.getEtiqueta()) == 0) {
                return true;
            }

            for (Comparable etiqueta : unCamino.getOtrosVertices()) {
                if (etiqueta.compareTo(destino.getEtiqueta()) == 0) {
                    return true;
                }
            }

            if (!destino.getVisitado()) {
                unCamino.agregarAdyacencia((TAdyacencia) adyacencia);
                if (destino.tieneCiclo(unCamino)) {
                    return true;
                }
                unCamino.eliminarAdyacencia((TAdyacencia) adyacencia);
            }
        }
        return false;

    }

    @Override
    public void bea(Collection<TVertice> visitados) {
        this.visitado = true;
        LinkedList<TVertice> lista = new LinkedList();
        lista.add(this);
        visitados.add(this);
        while (!lista.isEmpty()) {
            TVertice primero = lista.remove(0);
            LinkedList<TAdyacencia> ady = primero.getAdyacentes();
            for (TAdyacencia t : ady) {
                if (!t.getDestino().getVisitado()) {
                    t.getDestino().setVisitado(true);
                    lista.add((TVertice) t.getDestino());
                    visitados.add((TVertice) t.getDestino());
                }
            }
        }
    }

}
