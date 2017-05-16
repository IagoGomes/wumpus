/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import static Controle.Constantes.*;
import java.awt.Point;
import java.util.ArrayList;
import javax.naming.Binding;
import javax.script.Bindings;
/**
 *
 * @author Iago
 */
public class Mapa {
     
//estrutura de dados em forma de matriz de strings    
private String mapa[][];
//estrutura de dados em forma de grafo
private ArrayList<No> grafo;  

/**
 * Método construtor da classe
 */
public Mapa(){
    mapa= makeRandomMapa();
    grafo=matrizToGrafo(mapa);
    
}//fim mapa

/**
 * -------------------------Gets e Sets----------------------------------------
 */
/**
 * Método que retorna o mapa na estrutura de matriz
 * @return mapa String [][]
 */
public String [][] getMatriz(){
    return mapa;
}//fim getMapa

/**
 * Método que retorna o mapa na estrutura de grafo
 * @return grafo ArrayList<No>
 */
public ArrayList<No> getGrafo(){
    return grafo;
}//fim getGrafo

/**
 * Método que cria uma mapa (matriz de strings) randômico e aleatório, considerando as seguintes regras:
 *   - O objetos no mapa são o ouro, guerreiro, poço e wumpul
 *   - Além dos objetos o mapa possui elementos que são associados aos objetos, sendo eles:
 *        * BRILHO - relaciona-se com o ouro e está localizado na mesma posição que o ouro
 *        * BRISA  - relaciona-se com o poço e está localizado na vizinhança dos poços
 *        * FEDOR  - relaciona-se com o wumpul e está localizado na vizinhança do wumpul
 *   - Um mapa é válido se as posições dos objetos não são coincidentes e existe um caminho entre o ouro 
 *        e o guerreiro que não está bloqueado por poços
 *  
 * @return 
 */
    public static String[][] makeRandomMapa() {
        String mapa[][] = new String[quantLinhas][quantColunas];
      
        //inicializa a matriz do mapa com (nada)
        for (String[] m : mapa) {
            for (int j = 0; j < quantColunas; j++) {
                m[j] = "";
            }//fim for interno
        }//fim for externo

        //setando o guerreiro no mapa
        int linhaGuerreiro = quantLinhas - 1, colunaGuerreiro = 0;
        mapa[linhaGuerreiro][colunaGuerreiro] += GUERREIRO+" ";//posiciona o guerreino na
        //posição inicial (canto inferior esquerdo)
        //setando o ouro no mapa
        int linhaOuro, colunaOuro;
        do {
            linhaOuro = randomNum(quantLinhas);
            colunaOuro = randomNum(quantColunas);
        } while (!validarPosicao(linhaOuro, colunaOuro, mapa));
        mapa[linhaOuro][colunaOuro] += OURO+" ";
        //setando o BRILHO no mapa
        mapa[linhaOuro][colunaOuro]+=BRILHO+" ";
  
        //setando o Wumpul no mapa
        int linhaWumpul, colunaWumpul;
        do {
            linhaWumpul = randomNum(quantLinhas);
            colunaWumpul = randomNum(quantColunas);
        } while (!validarPosicao(linhaWumpul, colunaWumpul, mapa));
        mapa[linhaWumpul][colunaWumpul] += WUMPUL+" "; //posiciona o wumpul na posição
                                                   //mapa[linhaWumpul][colunaWumpul], aleatório.
        //setando o FEDOR no mapa
        setVizinhos(FEDOR, mapa, linhaWumpul, colunaWumpul);
        //setando os poços no mapa
        for (int i = 0; i < getQuantPocos(); i++) {
            int linhaPoco, colunaPoco;
            int cont=0;
            do {
                linhaPoco = randomNum(quantLinhas);
                colunaPoco = randomNum(quantColunas);
                if(cont==100){
                   Constantes.quantPocos--;
                   break;
                }
                cont++;               
            } while (!(validarPosicao(linhaPoco, colunaPoco, mapa) && buscaSaida(mapa, linhaPoco, colunaPoco, linhaGuerreiro, colunaGuerreiro)));
            mapa[linhaPoco][colunaPoco] += POÇO+ " ";
            
            //setando a brisa no mapa
            setVizinhos(BRISA, mapa, linhaPoco, colunaPoco);
        }//fim for poços
//System.out.println("&");
        //inicializa a matriz do mapa com -1 (nada)
        for (String[] m : mapa) {
            for (int j = 0; j < quantColunas; j++) {
                if (m[j].isEmpty()) {
                    m[j] = "-";
                }
            }//fim for interno
        }//fim for externo
        return mapa;
    }//fim makeRandomMapa

    /*
     * Função que imprime uma matriz de inteiros
     * @param mapa String [][]
     */
    public static void imprimirMapa(String[][] mapa) {
        System.out.println("");
        for (String[] m : mapa) {
            for (String m1 : m) {
                System.out.print(String.format("[%10s", m1) + "] ");
            } //fim for interno
            System.out.println("");
        } //fim for externo
        System.out.println("");
    }//fim imprimirMapa

    /**
     * Função que gera um número aleatório no intervalo 0-max
     *
     * @param max int : limite máximo
     * @return num int: número aleatório que vai de 0-max
     */
    public static int randomNum(int max) {
        return (int) (Math.random() * max);
    }//fim randomIndice

    /**
     * Função que valida a posição do ouro, wumpul e poço no mapa. A posição é
     * válida se: 1) A posição não coincidir com a posição do guerreiro, do poço
     * ou do ouro que já está no mapa; 2) O mapa deve ser organizado de tal forma que
     * sempre permita que, partindo do guerreiro, seja possível encontrar o ouro
     *
     * @param linha int : posição em linha
     * @param coluna int : posição em coluna
     * @param mapa String [][] : mapa
     * @return true : se a posição for válida false: se a posição não for válida
     */
    public static boolean validarPosicao(int linha, int coluna, String[][] mapa) {    
        return !(mapa[linha][coluna].contains(GUERREIRO.toString())
                || mapa[linha][coluna].contains(POÇO.toString())
                || mapa[linha][coluna].contains(OURO.toString())
                || mapa[linha][coluna].contains(WUMPUL.toString()));
    }//fim validarPosicao


    /**
     * Método que verifica se o mapa, em que deseja-se incluir um poço na posição (linha, coluna) é válido.
     *     -Para um mapa ser válido, deve ser possível, partindo do guerreiro, chegar no ouro;
     *     -Para fazer essa verificação foi usado um algoritmo de busca em largura que expande os nós diferentes de poço
     *      até encontrar o ouro, ou a lista de controle ficar vazia (indicando que o ouro não foi encontrado)
     * @param mapa     String [][] : mapa
     * @param linha    int : posição em linha do objeto
     * @param coluna   int : posição em coluna do objeto
     * @param linhaGuerreiro  int : posição em linha do guerreiro
     * @param colunaGuerreiro int : posição em coluna do guerreiro
     * @return true se o mapa é válido, false caso contrário 
     */
    private static boolean buscaSaida(String[][] mapa, int linha, int coluna, int linhaGuerreiro, int colunaGuerreiro) {
        String simulacaoMapa[][] = new String[quantLinhas][quantColunas];

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                simulacaoMapa[i][j] = mapa[i][j];
            }
        }
        simulacaoMapa[linha][coluna] += POÇO+" ";

        boolean encontrou = false;

        ArrayList<Integer> lista = new ArrayList<>();
        int visitados[][] = new int[quantLinhas][quantColunas];

        /**
         * i-1 j i j-1 i j i j+1 i+1 j
         *
         * vi2 j i vj2 i j i vj vi j
         */
        //adicionando o ponto de partida na lista
        lista.add(linhaGuerreiro);
        lista.add(colunaGuerreiro);
        //visitando os pontos adicionados na lista
        visitados[linhaGuerreiro][colunaGuerreiro] = 1;

        while (!encontrou && !lista.isEmpty()) {
            int i = lista.remove(0);
            int j = lista.remove(0);

            int vi = i + 1;
            int vi2 = i - 1;
            int vj = j + 1;
            int vj2 = j - 1;

            if (vi >= 0 && vi < quantLinhas) {
                if (j >= 0 && j < quantColunas) {
                    if (simulacaoMapa[vi][j].contains(OURO.toString())) {
                        encontrou = true;
                        break;
                    }//fim if mapa ouro
                    if (!simulacaoMapa[vi][j].contains(POÇO.toString())) {
                        if (visitados[vi][j] != 1) {
                            visitados[vi][j] = 1;
                            lista.add(vi);
                            lista.add(j);
                        }
                    }//fim if mapa poço
                }//fim if j
            }//fim if vi
            if (vi2 >= 0 && vi2 < quantLinhas) {
                if (j >= 0 && j < quantColunas) {
                    if (simulacaoMapa[vi2][j].contains(OURO.toString())) {
                        encontrou = true;
                        break;
                    }//fim if mapa ouro
                    if (!simulacaoMapa[vi2][j].contains(POÇO.toString())) {
                        if (visitados[vi2][j] != 1) {
                            visitados[vi2][j] = 1;
                            lista.add(vi2);
                            lista.add(j);
                        }
                    }//fim if mapa poço
                }//fim if j           
            }//fim if
            if (i >= 0 && i < quantLinhas) {
                if (vj >= 0 && vj < quantColunas) {
                    if (simulacaoMapa[i][vj].contains(OURO.toString())) {
                        encontrou = true;
                        break;
                    }//fim if mapa ouro
                    if (!simulacaoMapa[i][vj].contains(POÇO.toString())) {
                        if (visitados[i][vj] != 1) {
                            visitados[i][vj] = 1;
                            lista.add(i);
                            lista.add(vj);
                        }
                    }//fim if mapa poço
                }//fim if vj
                if (vj2 >= 0 && vj2 < quantColunas) {
                    if (simulacaoMapa[i][vj2].contains(OURO.toString())) {
                        encontrou = true;
                        break;
                    }//fim if mapa ouro
                    if (!simulacaoMapa[i][vj2].contains(POÇO.toString())) {
                        if (visitados[i][vj2] != 1) {
                            visitados[i][vj2] = 1;
                            lista.add(i);
                            lista.add(vj2);
                        }
                    }//fim if mapa poço
                }//fim if vj
            }//fim if
        }//fim while
        return encontrou;
    }//fim validar

    
    /**
     * Método que seta os elementos (BRISA e FEDOR) no mapa, de acordo com a posição dos objetos
     *        correspondentes (WUMPUL e POÇO)
     * @param tipo char : (FEDOR ou BRISA)
     * @param mapa String [][] : mapa 
     * @param linha posição em linha do objeto 
     * @param coluna posição em coluna do objeto
     */
    private static void setVizinhos(Constantes tipo, String[][] mapa, int linha, int coluna) {
        int vi=linha-1;
        int vi2=linha+1;
        int vj=coluna-1;
        int vj2=coluna+1;
        
        /**
         *                (vi   , coluna)
         * (linha, vj)    (linha, coluna)     (linha, vj2)  
         *                (vi2  , coluna)
         */
        
        if(vi>=0 && vi<quantLinhas && !mapa[vi][coluna].contains(tipo.toString()))
            mapa[vi][coluna]+=tipo+" ";
        if(vi2>=0 && vi2<quantLinhas && !mapa[vi2][coluna].contains(tipo.toString()))
            mapa[vi2][coluna]+=tipo+" ";
        if(vj>=0 && vj<quantColunas && !mapa[linha][vj].contains(tipo.toString()))
            mapa[linha][vj]+=tipo+" ";
        if(vj2>=0 && vj2<quantColunas && !mapa[linha][vj2].contains(tipo.toString()))
            mapa[linha][vj2]+=tipo+" ";
        
    }//fim setVizinhos

    /**
     * Método que cria um grafo com base e uma matriz de strings
     * @param mapa String [][]
     * @return grafo ArrayList<No>
     */
    public static ArrayList<No> matrizToGrafo(String [][] mapa){
        ArrayList<No> grafo = new ArrayList<>();
        
        //criando os nós do mapa
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                No no = new No(mapa[i][j]);
                no.setPoint(new Point(i,j));
                grafo.add(no);
            }//fim for
        }//fim for
        
        for (int i = 0; i < grafo.size(); i++) {
            int v1=i-1, v2=i+1, v3=i-4, v4=i+4;//posição dos vizinhos do no no grafo
            if(v1>=0 && v1<grafo.size() && (grafo.get(i).getPoint().x==grafo.get(v1).getPoint().x || grafo.get(i).getPoint().y==grafo.get(v1).getPoint().y ))
                grafo.get(i).getVizinhos().add(grafo.get(v1) );
            if(v2>=0 && v2<grafo.size() && (grafo.get(i).getPoint().x==grafo.get(v2).getPoint().x || grafo.get(i).getPoint().y==grafo.get(v2).getPoint().y ))
                grafo.get(i).getVizinhos().add(grafo.get(v2));
            if(v3>=0 && v3<grafo.size() && (grafo.get(i).getPoint().x==grafo.get(v3).getPoint().x || grafo.get(i).getPoint().y==grafo.get(v3).getPoint().y ))
                grafo.get(i).getVizinhos().add(grafo.get(v3));
            if(v4>=0 && v4<grafo.size() && (grafo.get(i).getPoint().x==grafo.get(v4).getPoint().x || grafo.get(i).getPoint().y==grafo.get(v4).getPoint().y ))
                grafo.get(i).getVizinhos().add(grafo.get(v4));
        }//fim for

        return grafo;
    }//fim matrizToGrafo
    
}//fim class
