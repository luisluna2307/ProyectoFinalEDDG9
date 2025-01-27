package edu.estructurasdatos.proyectofinaleddg9;
import java.util.*;

/**
 *
 * @author miguel
 * @version 3.0
 * @param <V> el tipo de dato de los Vertices
 */
public class GrafoMatriz<V>{
    private int maxVertices;
    private float [][] matAd;
    private ArrayList<V> vertices;
    private ArrayList<Boolean> visitados; //para recorridos
    private boolean dirigido = false;
    
    public GrafoMatriz(){
        this(20);
    }
    public GrafoMatriz(int maxVertices){
        this.maxVertices = maxVertices;
        matAd = new float [maxVertices][maxVertices];
        vertices = new ArrayList<>();
        visitados = new ArrayList<>();
    }
    public GrafoMatriz(int maxVertices, boolean dirigido){
        this(maxVertices);
        this.dirigido =dirigido;
    }
    
    
    @Override
    public String toString() {
        return "GrafoMatriz{\n" +
                "vertices=" + vertices +
                "\nmatAd=\n" + Arrays.deepToString(matAd).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") +
                "\n}";
    }
    
    public int numVertice(V v){// devuelve indice del vertice
        return vertices.indexOf(v);
    }
    
    public void agregarVertice (V v)throws Exception{
        if(vertices.size()==maxVertices)
            throw new Exception ("Matriz ya tiene el Max de vertices: "+maxVertices);
        if(numVertice(v) == -1){
            vertices.add(v);
            visitados.add(false);
        }
        else{
            System.out.println("Vertice ya existe");
        }
    }
    
    public void agregarArco(V v1,V v2)throws Exception{
        agregarArco(v1, v2, 1); // Para grafo no valorados se va a usar 1 o 0
    }
    
    public void agregarArco(V v1, V v2, float peso)throws Exception{
        int i1 = numVertice(v1);
        int i2 = numVertice(v2);
        if (i1 < 0 || i2 < 0) throw new Exception ("Vértice no existe");
        matAd[i1][i2] = peso;
        if(!dirigido)
            matAd[i2][i1] = peso;
    }
    
    public void agregarArcoIndices(int i1, int i2)throws Exception{
        agregarArcoIndices(i1, i2, 1);
    }
    
    public void agregarArcoIndices(int i1, int i2, float peso)throws Exception{
        if (i1 < 0 || i2 < 0) throw new Exception ("Vértice no existe");
        matAd[i1][i2] = peso;
        if(!dirigido)
            matAd[i2][i1] = peso;
    }
    
    /**
     * Recorrido en anchura Breadth-First Search
     * 
     * @param inicio vertice de inicio del recorrido
     * @return lista de vertices recorridos
     * @throws Exception
     */
    public List<V> BFS(V inicio)throws Exception{
        List<V> retorno =new LinkedList<>();
        int i1 = numVertice(inicio);
        if (i1 < 0) throw new Exception ("Vértice no existe");
        for (int i = 0; i < visitados.size(); i++)
            visitados.set(i, false); 
        Queue <V> cola = new LinkedList<>();
        cola.add(inicio);
        visitados.set(i1, true);
        while (!cola.isEmpty()){
            V actual = cola.poll();
            i1 = numVertice(actual);
            retorno.add(actual);
            for (int i = 0; i < vertices.size(); i++){
                if (matAd[i1][i] >0 && !visitados.get(i)){
                    cola.add(vertices.get(i));
                    visitados.set(i, true);
                }
            }
        }
        for (int i = 0; i < visitados.size(); i++)
            visitados.set(i, false);
        return retorno;
    }
    
    /**
     * Recorrido en profundidad Depth-First Search
     * 
     * @param inicio vertice de inicio del recorrido
     * @return lista de vertices recorridos
     * @throws Exception
     */
    public List<V> DFS(V inicio)throws Exception{
        List<V> retorno =new LinkedList<>();
        int i1 = numVertice(inicio);
        if (i1 < 0) throw new Exception ("Vértice no existe");
        for (int i = 0; i < visitados.size(); i++)
            visitados.set(i, false);      
        ArrayDeque <V> pila = new ArrayDeque<>();
        pila.push(inicio);
        while (!pila.isEmpty()){
            V actual = pila.pop();
            i1 = numVertice(actual);
            if (!visitados.get(i1)) {
                visitados.set(i1, true);
                retorno.add(actual);
                for (int i = vertices.size() - 1; i >= 0; i--) 
                    if (matAd[i1][i] > 0 && !visitados.get(i))
                        pila.push(vertices.get(i));
            }
        }
        for (int i = 0; i < visitados.size(); i++)
            visitados.set(i, false);
        return retorno;
    }
    
    public ArrayList<V> getVertices() {
        return vertices;
    }
    
    public float getPeso(V v1, V v2) throws Exception {
        int i1 = numVertice(v1);
        int i2 = numVertice(v2);
        if (i1 < 0 || i2 < 0) {
            throw new Exception("Vértice no existe");
        }
        return matAd[i1][i2];
    }
}