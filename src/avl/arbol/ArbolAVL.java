package avl.arbol;

import avl.ArbolGrafico;
import javax.swing.JPanel;

public class ArbolAVL {

    private NodoAVL raiz;
    public int alt, tam;

    public ArbolAVL() {
        raiz = null;
        alt = 0;
        tam = 0;
    }

    // Agregar nodo por valor
    public void agregar(int dato) {
        try {
            if (buscarNodo(dato) != null){
                return;
            }
        } catch (Exception e){}
        tam++;
        NodoAVL nuevo = new NodoAVL(dato);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            agregar(nuevo, raiz);
        }
    }

    // Agregar nodo en nodo especifico
    public void agregar(NodoAVL nuevo, NodoAVL pivote) {
        // Agregar <= para admitir repetidos
        if (nuevo.getDato() < pivote.getDato()) {
            if (!pivote.tieneHijoIzq()) {
                pivote.setIzq(nuevo);
                nuevo.setPadre(pivote);
                actualizarFB(pivote);
            } else {
                agregar(nuevo, pivote.getIzq());
            }
        } else if (nuevo.getDato() > pivote.getDato()){
            if (!pivote.tieneHijoDer()) {
                pivote.setDer(nuevo);
                nuevo.setPadre(pivote);
                actualizarFB(pivote);
            } else {
                agregar(nuevo, pivote.getDer());
            }
        }
    }

    // Buscar nodo
    public NodoAVL buscarNodo(int dato) throws Exception{
        if (raiz != null){
            return buscarNodo(raiz,dato);
        }
        throw new Exception("El nodo que busca no existe");
    }
    
    // Buscar nodo a partir de una raiz
    public NodoAVL buscarNodo(NodoAVL n, int dato) throws Exception{
        if (n.getDato() > dato){
            if (n.tieneHijoIzq()){
                return buscarNodo(n.getIzq(), dato);
            } else {
                throw new Exception("El nodo que busca no existe");
            }
        } else if (n.getDato() < dato){
            if (n.tieneHijoDer()){
                return buscarNodo(n.getDer(), dato);
            } else {
                throw new Exception("El nodo que busca no existe");
            }
        } else {
            return n;
        }
    }
    
    // Eliminadción de nodo
    public void eliminar(int dato) throws Exception{
        NodoAVL x = buscarNodo(dato);
        // Caso en que la raiz es el dato a eliminar
        if (raiz == x){
            if (raiz.esHoja()){
                tam--;
                raiz = null;
            } else if (raiz.tieneAmbosHijos()){
                NodoAVL tmp = raiz.getDer().encontrarMinimo();
                eliminar(tmp.getDato());
                x.setDato(tmp.getDato());
            } else {
                tam--;
                raiz = raiz.tieneHijoIzq() ? raiz.getIzq() : raiz.getDer();
                raiz.setPadre(null);
            }
            
        // El nodo a eliminar es un nodo comun de nuestro arbol    
        } else {
            NodoAVL padrex = x.getPadre();
            if (x.esHoja()){
                tam--;
                if (x.esHijoIzq()){
                    x.getPadre().setIzq(null);
                } else {
                    x.getPadre().setDer(null);
                }
                actualizarFB(padrex);
            } else if (x.tieneAmbosHijos()){
                NodoAVL tmp = x.getDer().encontrarMinimo();
                eliminar(tmp.getDato());
                x.setDato(tmp.getDato());
            } else {
                tam--;
                if (x.tieneHijoIzq()){
                    if (x.esHijoIzq()){
                        x.getPadre().setIzq(x.getIzq());
                        x.getIzq().setPadre(x.getPadre());
                    } else {
                        x.getPadre().setDer(x.getIzq());
                        x.getIzq().setPadre(x.getPadre());
                    } 
                } else {
                    if (x.esHijoIzq()){
                        x.getPadre().setIzq(x.getDer());
                        x.getDer().setPadre(x.getPadre());
                    } else {
                        x.getPadre().setDer(x.getDer());
                        x.getDer().setPadre(x.getPadre());
                    } 
                }
                actualizarFB(padrex);
            }
        }
    }
    
    // Actualizar factores de balance
    public void actualizarFB(NodoAVL n) {
        int viejoBalance = n.getFb();

        // altura sub-arbol izquierdo
        if (n.tieneHijoIzq()) {
            n.setAltIzq(Math.max(n.getIzq().getAltIzq(), n.getIzq().getAltDer()) + 1);
        } else {
            n.setAltIzq(0);
        }

        // altura sub-arbol derecho
        if (n.tieneHijoDer()) {
            n.setAltDer(Math.max(n.getDer().getAltIzq(), n.getDer().getAltDer()) + 1);
        } else {
            n.setAltDer(0);
        }

        // Balance del nodo
        n.setFb(n.getAltIzq() - n.getAltDer());

        // Rebalancear en caso de no estarlo
        if (n.getFb() < -1 || n.getFb() > 1) {
            balancear(n);
            return;
        }

        // Calcular fb del padre
        if (viejoBalance != n.getFb() && n.tienePadre()) {
            actualizarFB(n.getPadre());
        }
    }

    // Balancear arbol en caso de no estarlo
    public void balancear(NodoAVL n) {
        if (n.getFb() < 0){
            if (n.getDer().getFb() > 0){
                rotarDerecha(n.getDer());
            } else {
                rotarIzquierda(n);
            }
        } else {
            if (n.getIzq().getFb() < 0){
                rotarIzquierda(n.getIzq());
            } else {
                rotarDerecha(n);
            }
        }
    }
    
    // Rotaciones
    private void rotarIzquierda(NodoAVL n){
        NodoAVL raizVieja = n;
        NodoAVL raizNueva = n.getDer();
        raizVieja.setDer(raizNueva.getIzq());
        if (raizNueva.tieneHijoIzq()){
            raizNueva.getIzq().setPadre(raizVieja);
        }
        raizNueva.setPadre(raizVieja.getPadre());
        if (raizVieja.tienePadre()){
            if (raizVieja.esHijoIzq()){
                raizVieja.getPadre().setIzq(raizNueva);
            } else {
                raizVieja.getPadre().setDer(raizNueva);
            }
        } else {
            raiz = raizNueva;
        }
        raizVieja.setPadre(raizNueva);
        raizNueva.setIzq(raizVieja);
        actualizarFB(raizVieja);
    }
    
    private void rotarDerecha(NodoAVL n){
        NodoAVL raizVieja = n;
        NodoAVL raizNueva = n.getIzq();
        raizVieja.setIzq(raizNueva.getDer());
        if (raizNueva.tieneHijoDer()){
            raizNueva.getDer().setPadre(raizVieja);
        }
        raizNueva.setPadre(raizVieja.getPadre());
        if (raizVieja.tienePadre()){
            if (raizVieja.esHijoIzq()){
                raizVieja.getPadre().setIzq(raizNueva);
            } else {
                raizVieja.getPadre().setDer(raizNueva);
            }
        } else {
            raiz = raizNueva;
        }
        raizVieja.setPadre(raizNueva);
        raizNueva.setDer(raizVieja);
        actualizarFB(raizVieja);
    }
    
    // Getters y Setters
    public JPanel getdibujo() {
        return new ArbolGrafico(this);
    }
    
    public NodoAVL getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoAVL raiz) {
        this.raiz = raiz;
    }
}
