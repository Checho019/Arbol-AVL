package avl.arbol;

import avl.ArbolGrafico;
import java.util.LinkedList;
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
        this.tam++;
        NodoAVL nuevo = new NodoAVL(dato);
        if (this.raiz == null) {
            this.raiz = nuevo;
        } else {
            agregar(nuevo, this.raiz);
        }
    }

    // Agregar nodo en nodo especifico
    public void agregar(NodoAVL nuevo, NodoAVL pivote) {
        if (nuevo.getDato() <= pivote.getDato()) {
            if (!pivote.tieneHijoIzq()) {
                pivote.setIzq(nuevo);
                nuevo.setPadre(pivote);
                actualizarFB(pivote);
            } else {
                agregar(nuevo, pivote.getIzq());
            }
        } else {
            if (!pivote.tieneHijoDer()) {
                pivote.setDer(nuevo);
                nuevo.setPadre(pivote);
                actualizarFB(pivote);
            } else {
                agregar(nuevo, pivote.getDer());
            }
        }
        actualizarFB(raiz);
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
        System.out.println("Actualizando nodo: " + n.getDato());
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
        System.out.println(n.getFb() + " " + n.getDato());

        // Rebalancear en caso de no estarlo
        if (n.getFb() < -1 || n.getFb() > 1) {
            balancear(n);
            System.out.println("Balancear nodo " + n.getDato());
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
    
    //Recorrido inorden, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
    public LinkedList inOrden() {
        LinkedList rec = new LinkedList();
        inorden(raiz, rec);
        return rec;
    }

    public void inorden(NodoAVL aux, LinkedList recorrido) {
        if (aux != null) {
            inorden(aux.getIzq(), recorrido);
            recorrido.add(aux.getDato());
            inorden(aux.getDer(), recorrido);
        }
    }

    //Recorrido postorden, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
    public LinkedList postOrden() {
        LinkedList rec = new LinkedList();
        postorden(raiz, rec);
        return rec;
    }

    public void postorden(NodoAVL aux, LinkedList recorrido) {
        if (aux != null) {
            postorden(aux.getIzq(), recorrido);
            postorden(aux.getDer(), recorrido);
            recorrido.add(aux.getDato());
        }
    }
    
    //Recorrido preorden, recibe el nodo a empezar (raiz) y una linkedlist para ir guardando el recorrido
    public LinkedList preOrden() {
        LinkedList rec = new LinkedList();
        preorden(raiz, rec);
        return rec;
    }

    public void preorden(NodoAVL aux, LinkedList recorrido) {
        if (aux != null) {
            recorrido.add(aux.getDato());
            preorden(aux.getIzq(), recorrido);
            preorden(aux.getDer(), recorrido);
        }
    }

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
