
package arbolBB;

public class Nodo {
    private int dato, altIzq, altDer, fb;
    private Nodo padre,izq,der;

    // Constructores
    public Nodo(int dato){
        this.dato = dato;
    }
    
    public Nodo(int dato, Nodo padre){
        this.dato = dato;
        this.padre = padre;
    }
    
    public Nodo(int dato, Nodo izq, Nodo der) {
        this.dato = dato;
        this.izq = izq;
        this.der = der;
    }

    // -- Metodos para conocer las relacones con los hijos
    public boolean tieneHijoIzq(){
        return this.izq != null;
    }
    
    public boolean tieneHijoDer(){
        return this.der != null;
    }
    
    public boolean esHoja(){
        return !tieneHijoIzq() && ! tieneHijoDer();
    }
    
    public boolean tieneAmbosHijos(){
        return tieneHijoIzq() && tieneHijoDer();
    }
    
    // -- Metodos para relacionar padre y hermanos --
    public boolean tienePadre(){
        return padre != null;
    }

    public boolean esHijoIzq(){
        return this.padre.getIzq() == this;
    }
    
    public boolean esHijoDer(){
        return this.padre.getDer() == this;
    }
    
    // -- Operaciones utiles para la eliminacion de nodos
    public Nodo encontrarMinimo(){
        if(this.tieneHijoIzq()){
            return this.izq.encontrarMinimo();
        } else {
            return this;
        }
    }
    
    public Nodo encontrarMaximo(){
        if(this.tieneHijoDer()){
            return this.der.encontrarMaximo();
        } else {
            return this;
        }
    }
    
    // -- Setters y getters
    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    
    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public Nodo getIzq() {
        return izq;
    }

    public void setIzq(Nodo izq) {
        this.izq = izq;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public int getAltIzq() {
        return altIzq;
    }

    public void setAltIzq(int altIzq) {
        this.altIzq = altIzq;
    }

    public int getAltDer() {
        return altDer;
    }

    public void setAltDer(int altDer) {
        this.altDer = altDer;
    }

    public int getFb() {
        return fb;
    }

    public void setFb(int fb) {
        this.fb = fb;
    }
 
    
    
}
