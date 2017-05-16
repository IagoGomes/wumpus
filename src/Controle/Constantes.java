/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import sun.security.x509.OIDMap;

/**
 *
 * @author Iago
 */
public enum Constantes { 
  /*
    SENTIDOS DO GUERREIRO
    */
  NORTE,
  SUL,
  LESTE,
  OESTE,
  /*
    PERSONAGENS E ESTADOS DOS BLOCOS
  */
  GUERREIRO,
  WUMPUL,
  POÇO,
  BRISA,
  FEDOR,
  GRITO,
  BRILHO,
  OURO; 
  /*
   VARIÁVEIS DE CONTROLE DO MAPA:
    - QUANTIDADE DE LINHAS
    - QUANTIDADE DE COLUNAS
    - QUANTIDADE DE POÇOS
  */
  public static final int quantColunas = 4;
  public static final int quantLinhas = 4;
  public static  int quantPocos = 2;
  public static int velocidadeAnimacao = 10;
  private static int volumeDoSom = 6;
  private static boolean executarSom = true; //Caso seja marcado o checkbox p executar som
  private static boolean temSomParaTocar = true; //Para manter o som executando enquanto a Thread dele n for finalizada
  public static int score = 0;
  public static int dificuldade=2;
  public static boolean jogoManual = true;

    /**
     * @return the velocidadeAnimacao
     */
    public static int getVelocidadeAnimacao() {
        return velocidadeAnimacao;
    }

    /**
     * @param aVelocidadeAnimacao the velocidadeAnimacao to set
     */
    public static void setVelocidadeAnimacao(int aVelocidadeAnimacao) {
        velocidadeAnimacao = aVelocidadeAnimacao;
    }

    /**
     * @return the volumeDoSom
     */
    public static int getVolumeDoSom() {
        return volumeDoSom;
    }

    /**
     * @return the temSomParaTocar
     */
    public static boolean getTemSomParaTocar() {
        return temSomParaTocar;
    }

    /**
     * @param aTemSomParaTocar the temSomParaTocar to set
     */
    public static void setTemSomParaTocar(boolean aTemSomParaTocar) {
        temSomParaTocar = aTemSomParaTocar;
    }
    
    public boolean getSom(){
        return executarSom;
    }
    

    /**
     * @param aVolumeDoSom the volumeDoSom to set
     */
    public static void setVolumeDoSom(int aVolumeDoSom) {
        volumeDoSom = aVolumeDoSom;
    }

    /**
     * @return the quantPocos
     */
    public static int getQuantPocos() {
        return quantPocos;
    }

    /**
     * @param aQuantPocos the quantPocos to set
     */
    public static void setQuantPocos(int aQuantPocos) {
        quantPocos = aQuantPocos;
    }

    /**
     * @return the executarSom
     */
    public static boolean getExecutarSom() {
        return executarSom;
    }

    /**
     * @param aExecutarSom the executarSom to set
     */
    public static void setExecutarSom(boolean aExecutarSom) {
        executarSom = aExecutarSom;
    }
}//fim class enum Constantes
