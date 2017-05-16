/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Iago
 */
public class No {
    //posição do no no mapa em (x e y)
    private Point posicao;
    //nome do no
    private String estado;
    //visinhos do no
    private ArrayList<No> vizinhos;
    //sentido do no
    private Constantes sentido;
    
    /**
     * Construtor da classe 
     * @param estado String : estado do no;
     *   -O estado contém todos os elementos presente na posição do nó no mapa, como
     *      poços, ouro, brisa, etc.
     */
    public No(String estado){
        this.estado=estado;
        vizinhos=new ArrayList<>();
    }//fim construtor
    
    /**
     * Método que seta a localização do no na matriz (mapa)
     * @param ponto 
     */
    public void setPoint(Point ponto){
        this.posicao=ponto;
    }//fi setPoint
    
    /**
     * Método que converte a classe em String
     * @return str String: classe no formata do String,
     *   A string é formado pelos estados dos no no mapa e sua posição;
     */
   
    @Override
    public String toString(){
      return "["+posicao.x+","+posicao.y+"]=>"+estado;
    }//fim toString
    
    public String toString2(){
      String str= "["+posicao.x+","+posicao.y+"]=>";
        for (int v = 0; v < vizinhos.size(); v++) {
            str+="("+vizinhos.get(v).getPoint().x+","+vizinhos.get(v).getPoint().y+")";
        }//fim for
      return str;
    }//fim toString

    public String toString3(){
      String str= "["+posicao.x+","+posicao.y+"]=>\n";
        for (int v = 0; v < vizinhos.size(); v++) {
            str+="{"+vizinhos.get(v).toString2()+"}\n";
        }//fim for
      return str;
    }//fim toString
    
    public String toString4(){
      String str= "["+posicao.x+","+posicao.y+"]=>\n";
        for (int v = 0; v < vizinhos.size(); v++) {
            str+="{"+vizinhos.get(v).toString2()+"} := +"+ vizinhos.get(v).estado +"\n";
        }//fim for
      return str;
    }//fim toString
    
    public ArrayList<No> getVizinhos(){
        return vizinhos;
    }//fim getVizinhos
    
    public Point getPoint(){
        return posicao;
    }
    public String getEstados(){
        return estado;
    }
}//fim class
