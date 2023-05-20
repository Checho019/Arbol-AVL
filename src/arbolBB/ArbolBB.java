package arbolBB;

import java.util.LinkedList;
import javax.swing.JPanel;

public class ArbolBB {

    private Nodo raiz;
    int alt, tam;

    public ArbolBB() {
        raiz = null;
        alt = 0;
        tam = 0;
    }

    // Agregar nodo por valor
    public void agregar(int dato) {
        this.tam++;
        Nodo nuevo = new Nodo(dato);
        if (this.raiz == null) {
            this.raiz = nuevo;
        } else {
            agregar(nuevo, this.raiz);
        }
    }

    // Agregar nodo en nodo especifico
    public void agregar(Nodo nuevo, Nodo pivote) {
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
    public Nodo buscarNodo(int dato) throws Exception{
        if (raiz != null){
            return buscarNodo(raiz,dato);
        }
        throw new Exception("El nodo que busca no existe");
    }
    
    // Buscar nodo a partir de una raiz
    public Nodo buscarNodo(Nodo n, int dato) throws Exception{
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
    
    // Actualizar factores de balance
    public void actualizarFB(Nodo n) {
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
    public void balancear(Nodo n) {
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
    private void rotarIzquierda(Nodo n){
        Nodo raizVieja = n;
        Nodo raizNueva = n.getDer();
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
    
    private void rotarDerecha(Nodo n){
        Nodo raizVieja = n;
        Nodo raizNueva = n.getIzq();
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

    public void inorden(Nodo aux, LinkedList recorrido) {
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

    public void postorden(Nodo aux, LinkedList recorrido) {
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

    public void preorden(Nodo aux, LinkedList recorrido) {
        if (aux != null) {
            recorrido.add(aux.getDato());
            preorden(aux.getIzq(), recorrido);
            preorden(aux.getDer(), recorrido);
        }
    }

    //Metodo para verificar si hay un nodo en el arbol
    public boolean existe(int dato) {
        Nodo aux = raiz;
        while (aux != null) {
            if (dato == aux.getDato()) {
                return true;
            } else if (dato > aux.getDato()) {
                aux = aux.getDer();
            } else {
                aux = aux.getIzq();
            }
        }
        return false;
    }

    private void altura(Nodo aux, int nivel) {
        if (aux != null) {
            altura(aux.getIzq(), nivel + 1);
            alt = nivel;
            altura(aux.getDer(), nivel + 1);
        }
    }

    //Devuleve la altura del arbol
    public int getAltura() {
        altura(raiz, 1);
        return alt;
    }

    public JPanel getdibujo() {
        return new ArbolExpresionGrafico(this);
    }
    
    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
}
