
package avl.arbol;

public class NodoAVL {
    private int dato, altIzq, altDer, fb, altura = 0;
    private NodoAVL padre,izq,der;

    // Constructores
    public NodoAVL(int dato){
        this.dato = dato;
    }
    
    public NodoAVL(int dato, NodoAVL padre){
        this.dato = dato;
        this.padre = padre;
    }
    
    public NodoAVL(int dato, NodoAVL izq, NodoAVL der) {
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
    public NodoAVL encontrarMinimo(){
        if(this.tieneHijoIzq()){
            return this.izq.encontrarMinimo();
        } else {
            return this;
        }
    }
    
    public NodoAVL encontrarMaximo(){
        if(this.tieneHijoDer()){
            return this.der.encontrarMaximo();
        } else {
            return this;
        }
    }
    
    public int encontrarAltura(){
        if (this.esHoja()){
            return 1;
        } else {
            if (this.tieneAmbosHijos()){
                return Math.max(izq.encontrarAltura(), der.encontrarAltura()) + 1;
            } else {
                return this.tieneHijoIzq() ? this.getIzq().encontrarAltura() + 1 : this.getDer().encontrarAltura() + 1;
            }
        }
    }
    
    public String recorridoPre(){
        String cadena = "";
        cadena += " " + this.dato + " ";
        if (this.tieneHijoIzq()){
            cadena += " " + izq.recorridoPre() + " ";
        }
        if (this.tieneHijoDer()){
            cadena += " " + der.recorridoPre() + " ";
        }
        return cadena;
    }
    
    public String recorridoIn(){
        String cadena = "";
        if (this.tieneHijoIzq()){
            cadena += " " + izq.recorridoIn() + " ";
        }
        cadena += " " + this.dato + " ";
        if (this.tieneHijoDer()){
            cadena += " " + der.recorridoIn() + " ";
        }
        return cadena;
    }
    
    public String recorridoPos(){
        String cadena = "";
        if (this.tieneHijoIzq()){
            cadena += " " + izq.recorridoPos() + " ";
        }
        if (this.tieneHijoDer()){
            cadena += " " + der.recorridoPos() + " ";
        }
        cadena += " " + this.dato + " ";
        return cadena;
    }
    
    
    
    // -- Setters y getters
    public NodoAVL getPadre() {
        return padre;
    }

    public void setPadre(NodoAVL padre) {
        this.padre = padre;
    }
    
    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public NodoAVL getIzq() {
        return izq;
    }

    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    }

    public NodoAVL getDer() {
        return der;
    }

    public void setDer(NodoAVL der) {
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
