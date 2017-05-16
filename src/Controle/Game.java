/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Animacao.MarioAnimacao;
import Main.Codigo;
import View.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


/**
 * Classe que disponibiliza todas as interfaces para o usuário
 *
 * @author Iago
 */
public class Game {

    //mapa do jogo
    private Mapa mapa;
    //Janela da aplicação
    private JanelaWumpus janela;
    //posicao atual do mario
    private Point posicaoMario;
    //check wumpus morto
    private boolean wumpusMorreu;
    //Codigo do Usuário
    private Codigo codigoUsuario;

    /**
     * Método Construtor
     *
     * @return void
     */
    public Game(Codigo codigoUsuario) {
        //PainelOpcoes.setVelocidadeAnimacao(6);
        janela = new JanelaWumpus(codigoUsuario);
        this.codigoUsuario= codigoUsuario;
        initGame();

    }//fim construtor

    /*-------------------------------Inits------------------------------------*/
    /**
     * Método que inicia os componentes e torna visivel a janela
     *
     * @return void
     */
    public synchronized void init() {
        janela.initComponents();
        janela.setVisible(true);
       // controle.acquireUninterruptibly();
    }
    
    /**
     * Método que inicia e reinicia o jogo
     */
    public void initGame() {
        janela.setGame(this);
        mapa = new Mapa();
        posicaoMario = new Point(3, 0);
        wumpusMorreu = false;
        Constantes.score=0;
    }
    /*---------------------------Gets e Sets----------------------------------*/

    /**
     * Método que retorna janela do jogo
     *
     * @return JFrame
     */
    public JanelaWumpus getJanela() {
        return janela;
    }
    

    /**
     * Método que retorna o mapa armazenado em uma estrutura de matriz
     *
     * @return mapa String [][]
     */
    public String[][] getMatrizMapa() {
        return mapa.getMatriz();
    }//fim getMatrizMapa

    /**
     * Método que retorna o mapa armazenado em uma estrutura de grafo
     *
     * @return mapa ArrayList<No>
     */
    public ArrayList<No> getGrafoMapa() {
        return mapa.getGrafo();
    }//fim getGrafoMapa

    /**
     * Método que seta o objeto mapa
     *
     * @param mapa
     */
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    /**
     * Método que retorna o sentido de direcao do guerreiro
     *
     * @return direcao Contantes
     */
    public Constantes getDirecao() {
        return janela.painelJogo.getMarioAnimacao().getDirecao();
    }//fim getDirecao

    /**
     * Método que retorna a posicao atual do mario
     *
     * @return posicaoMario Point
     */
    public Point getPosicaoMario() {
        return posicaoMario;
    }//fim getPosicaoMario

    /**
     * Método altera o obejto posicaoMario
     *
     * @return void
     */
    public void setPosicaoMario(Point posicaoMario) {
        this.posicaoMario = posicaoMario;
    }

    public boolean isWumpusMorreu() {
        return wumpusMorreu;
    }


    /*-----------------------------ATUADORES----------------------------------*/
    /**
     * Método que permite ao guerreiro se deslocar de uma sala para outra
     *
     * @return void
     */
    public void guerreiroAndar() {
        MarioAnimacao mario = janela.painelJogo.getMarioAnimacao();
        mario.getWait().release();
        Constantes.score--;
        janela.getLabelScore().setText("x " + Constantes.score);
    }

    /**
     * Método que permite ao guerreiro rotacionar no sentido horario e modificar
     * assim seu sentido
     *
     * @return void
     */
    public void guerreiroGirarSentHorario() {
        MarioAnimacao mario = janela.painelJogo.getMarioAnimacao();
        Constantes dir = mario.getDirecao();
        Constantes.score--;
        janela.getLabelScore().setText("x " + Constantes.score);
        switch (dir) {
            case NORTE:
                mario.setDirecao(Constantes.LESTE);
                janela.painelJogo.setImageMario(22);//seta a imagem do leste
                break;
            case LESTE:
                mario.setDirecao(Constantes.SUL);
                janela.painelJogo.setImageMario(21);//seta a imagem do sul
                break;
            case SUL:
                mario.setDirecao(Constantes.OESTE);
                janela.painelJogo.setImageMario(23);//seta a imagem do oeste
                break;
            case OESTE:
                mario.setDirecao(Constantes.NORTE);
                janela.painelJogo.setImageMario(20);//seta a imagem do norte
                break;
        }
    }//fim guerreiroGirarSentHorario

    /**
     * Método que permite ao guerreiro rotacionar no sentido antihorario e
     * modificar assim seu sentido
     *
     * @return void
     */
    public void guerreiroGirarSentAntiHorario() {
        MarioAnimacao mario = janela.painelJogo.getMarioAnimacao();
        Constantes dir = mario.getDirecao();
        Constantes.score--;
        janela.getLabelScore().setText("x " + Constantes.score);
        switch (dir) {
            case NORTE:
                mario.setDirecao(Constantes.OESTE);
                janela.painelJogo.setImageMario(23);//seta a imagem do leste
                break;
            case LESTE:
                mario.setDirecao(Constantes.NORTE);
                janela.painelJogo.setImageMario(20);//seta a imagem do sul
                break;
            case SUL:
                mario.setDirecao(Constantes.LESTE);
                janela.painelJogo.setImageMario(22);//seta a imagem do oeste
                break;
            case OESTE:
                mario.setDirecao(Constantes.SUL);
                janela.painelJogo.setImageMario(21);//seta a imagem do norte
                break;
        }
    }//fim guerreiroGirarSentAntiHorario 

    /**
     * Método que permite o guerreiro atirar em uma direcao do mapa e analisar
     * com um sensor sonoro o resultado do seu disparo
     *
     * @return true=WumpusGritou false=naoGritou boolean
     */
    public boolean guerreiroAtirar() {
        if (isFlecha()) {
            int matou = matouWumpus();
            Constantes.score -= 10;
            janela.getLabelScore().setText("x " + Constantes.score);
            if (matou != 0) {
                janela.painelJogo.animacaoFogo(this.getDirecao(), matou);
                wumpusMorreu = true;
                
                return true;
            } else {
                janela.painelJogo.animacaoFogo(this.getDirecao(), 0);
            }
        }
        return false;
    }//fim guerreiroAtirar

    /**
     * Método que analisa e permite ao guerreiro vencer o desafio, coletando o
     * ouro do ambiente.
     *
     * @return true=coletouObjeto false=naoColetou boolean
     */
    public boolean guerreiroPegarObjeto() {
        Point pOuro = janela.painelJogo.getMarioAnimacao().getPontoOuro();
        if (pOuro != null) {
            if ((posicaoMario.x == pOuro.x)&&(posicaoMario.y == pOuro.y)) {
                Constantes.score += 1000;
                janela.getLabelScore().setText("x " + Constantes.score);
                System.out.println("pegou o ouro");
                return true;
            }
        }
        return false;
    }//fim guerreiroPegarObjeto

    /*------------------------------SENSORES----------------------------------*/
    /**
     * Método que simula um dos sensores do guerreiro o qual identifica fedor no
     * ambiente
     *
     * @return boleano
     */
    public boolean isFedor() {
        if (wumpusMorreu) {
            return false;
        }
        return (mapa.getMatriz()[posicaoMario.x][posicaoMario.y].contains(Constantes.FEDOR.toString()));
    }//fim isFedor

    /**
     * Método que simula um dos sensores do guerreiro o qual identifica vento ou
     * brisa no ambiente
     *
     * @return boleano
     */
    public boolean isBrisa() {
        return (mapa.getMatriz()[posicaoMario.x][posicaoMario.y].contains(Constantes.BRISA.toString()));
    }//fim isBrisa

    /**
     * Método que simula um dos sensores do guerreiro o qual identifica brilho
     * ou resplendor no ambiente
     *
     * @return boolean
     */
    public boolean isResplendor() {
        return (mapa.getMatriz()[posicaoMario.x][posicaoMario.y].contains(Constantes.BRILHO.toString()));
    }//fim isResplendor

    /**
     * Método que verifica se o guerreiro ainda pode disparar
     *
     * @return boolean
     */
    public boolean isFlecha() {
        return janela.painelJogo.isTemFlecha();
    }//fim isFlecha

    /*------------------------Metodos Auxiliares------------------------------*/
    /**
     * Método que indica se o wumpus foi derrotado
     *
     * @return matouWumpus int
     */
    private int matouWumpus() {
        int xMario = posicaoMario.x, yMario = posicaoMario.y;//variaveis locais de posicao do mario
        Point pWumpus = janela.painelJogo.getMarioAnimacao().getPontoWumpus(); //variaveis locais de posicao do wumpus
        Constantes direcao = getDirecao();//sentido direcional do tiro
        if (xMario == pWumpus.x) {//compara se a posicao x do mario eh a mesma do wumpus
            if ((yMario < pWumpus.y) & (direcao == Constantes.LESTE)) {//avalia se o tiro acerta o wumpus caso tenha atirado para o LESTE
                return pWumpus.y - yMario;//retorno verdadeiro
            } else if ((yMario > pWumpus.y) & (direcao == Constantes.OESTE)) {//avalia se o tiro acerta o wumpus caso tenha atirado para o OESTE
                return yMario - pWumpus.y;//retorno verdadeiro
            }
        } else if (yMario == pWumpus.y) {//compara se a posicao y do mario eh a mesma do wumpus
            if ((xMario < pWumpus.x) & (direcao == Constantes.SUL)) {//avalia se o tiro acerta o wumpus caso tenha atirado para o SUL
                return pWumpus.x - xMario;
            } else if ((xMario > pWumpus.x) & (direcao == Constantes.NORTE)) {//avalia se o tiro acerta o wumpus caso tenha atirado para o NORTE
                return xMario - pWumpus.x;
            }
        }
        return 0;
    }//fim matouWumpus

    private boolean marioPerdeu() {
        return false;
    }
}//fim class
