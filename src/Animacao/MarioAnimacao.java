/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacao;

import Controle.Constantes;
import Controle.Game;
import View.PainelJogo;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author MatheusThiago
 */
public class MarioAnimacao extends Thread {

    private PainelJogo painel;
    private Constantes direcao;
    private boolean permissao;
    private Semaphore wait;
    private Game game;
    private Point pontoWumpus, pontoOuro;
    private ArrayList<Point> poco;

    public MarioAnimacao(PainelJogo painel, Game game) {
        this.painel = painel;
        this.game = game;
        wait = new Semaphore(0);
        this.poco = new ArrayList<>();
    }//fim construtor

    @Override
    public void run() {
        while (permissao) {

            wait.acquireUninterruptibly();

            int x = painel.getMario().getX(), y = painel.getMario().getY(), b = 0;
            int posX = game.getPosicaoMario().x, posY = game.getPosicaoMario().y;
            switch (direcao) {

                case NORTE: // norte
                    if (posX != 0) {
                        painel.setImageMario(6);
                        b = y - 129;
                        game.setPosicaoMario(new Point(posX - 1, posY));
                        if (painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y] != null) {
                            //painel.setLabelsBloco(Game.getPosicaoMario().x, Game.getPosicaoMario().y, new ImageIcon(("Imagens" + File.separator + "blocos.gif")));
                            painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y].setVisible(false);
                            // painel.setLabelBloco(game.getPosicaoMario().x, game.getPosicaoMario().y);
                        }
                        while (painel.getMario().getY() != b) {
                            y--;
                            painel.getMario().setLocation(x, y);
                            esperar();
                        }
                        painel.setImageMario(20);
                    }
                    break;

                case LESTE: //leste
                    if (posY < 3) {
                        painel.setImageMario(6);
                        b = x + 165;
                        game.setPosicaoMario(new Point(posX, posY + 1));
                        if (painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y] != null) {
                            // painel.setLabelsBloco(Game.getPosicaoMario().x, Game.getPosicaoMario().y, new ImageIcon(("Imagens" + File.separator + "blocos.gif")));
                            painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y].setVisible(false);
                            //painel.setLabelBloco(game.getPosicaoMario().x, game.getPosicaoMario().y);
                        }

                        while (painel.getMario().getX() != b) {
                            x++;
                            painel.getMario().setLocation(x, y);
                            esperar();
                        }
                        painel.setImageMario(22);
                    }
                    break;
                case SUL: //sul
                    if (posX != 3) {
                        painel.setImageMario(7);
                        game.setPosicaoMario(new Point(posX + 1, posY));
                        if (painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y] != null) {
                            //painel.setLabelsBloco(Game.getPosicaoMario().x, Game.getPosicaoMario().y, new ImageIcon(("Imagens" + File.separator + "blocos.gif")));
                            painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y].setVisible(false);
                            // painel.setLabelBloco(game.getPosicaoMario().x, game.getPosicaoMario().y);
                        }
                        b = y + 129;
                        while (painel.getMario().getY() != b) {
                            y++;
                            painel.getMario().setLocation(x, y);
                            esperar();
                        }
                        painel.setImageMario(21);
                    }
                    break;
                case OESTE: //oeste
                    if (posY != 0) {
                        painel.setImageMario(7);
                        game.setPosicaoMario(new Point(posX, posY - 1));
                        if (painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y] != null) {
                            // painel.setLabelsBloco(Game.getPosicaoMario().x, Game.getPosicaoMario().y, new ImageIcon(("Imagens" + File.separator + "blocos.gif")));
                            painel.getLabelsBloco()[game.getPosicaoMario().x][game.getPosicaoMario().y].setVisible(false);
                            //painel.setLabelBloco(game.getPosicaoMario().x, game.getPosicaoMario().y);
                        }
                        b = x - 165;

                        while (painel.getMario().getX() != b) {
                            x--;
                            painel.getMario().setLocation(x, y);
                            esperar();
                        }
                        painel.setImageMario(23);
                    }
                    break;
            }//fim switch
            if (pontoWumpus != null) {
                if ((pontoWumpus.x == game.getPosicaoMario().x) && (pontoWumpus.y == game.getPosicaoMario().y) && (!game.isWumpusMorreu())) {
                    marioMorre(true);
                }
            }
            for (Point p : poco) {
                if ((p.x == game.getPosicaoMario().x) && (p.y == game.getPosicaoMario().y)) {
                    marioMorre(true);
                }
            }
            if (pontoOuro != null) {
                if (pontoOuro.equals(game.getPosicaoMario())) {
                    marioPegarOuro();
                }
            }

        }//fim while permissao
    }//fim run

    /**
     *
     */
    public void esperar() {
        try {
            sleep(14 - Constantes.velocidadeAnimacao);

        } catch (InterruptedException ex) {
            System.out.println("Erro: Sleep Class - MarioAnimacao");
        }
    }//fim esperar

    public void setDirecao(Constantes direcao) {
        this.direcao = direcao;
    }//fim setDirecao

    public Constantes getDirecao() {
        return direcao;
    }

    public void setPermissao(boolean permissao) {
        this.permissao = permissao;
    }

    public Semaphore getWait() {
        return wait;
    }

    public void setWumpus(Point Wumpus) {
        this.pontoWumpus = Wumpus;
    }

    public Point getPontoWumpus() {
        return pontoWumpus;
    }

    public void setPoco(Point p) {
        this.poco.add(p);
    }

    public Point getLocalizacaoWumpus() {
        return pontoWumpus;
    }

    private void marioMorre(boolean b) {

        if (b) {
            Constantes.score -= 1000;
            for (int i = 0; i < 20; i++) {
                painel.getMario().setVisible(false);
                try {
                    sleep(i * 2);
                    painel.getMario().setVisible(true);
                    sleep(i * 2);
                } catch (InterruptedException ex) {
                    System.out.println("Erro: Sleep Class - MarioAnimacao");
                }
            }
        }
        Constantes.score--;
        painel.getLabelScore().setText("x " + Constantes.score);
        painel.getMario().setVisible(false);
        permissao = false;
        painel.ocultarLabels();
        painel.getLabelEscuro().setVisible(true);
        game.getJanela().executarAudioGameOver();
        for (int i = 0; i < 50; i++) {
            painel.getLabelEscuro().setBackground(new Color(0, 0, 0, i));
            try {
                sleep(50);
            } catch (InterruptedException ex) {
            }

        }
        game.getJanela().setarFinal();
        painel.getLabelEscuro().setVisible(false);
    }

    private void marioPegarOuro() {
        painel.setLabelOuro();
        Constantes.score += 1000;
        painel.getLabelScore().setText("x " + Constantes.score);
        game.getJanela().executarAudioVitoria();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
        }
        painel.getMario().setVisible(false);
        permissao = false;
        painel.ocultarLabels();
        painel.getLabelEscuro().setVisible(true);

        for (int i = 0; i < 50; i++) {
            painel.getLabelEscuro().setBackground(new Color(0, 0, 0, i));
            try {
                sleep(50);
            } catch (InterruptedException ex) {
            }

        }
        game.getJanela().setarVitoria();
        painel.getLabelEscuro().setVisible(false);

    }

    public Point getPontoOuro() {
        return pontoOuro;
    }

    public void setPontoOuro(Point pontoOuro) {
        this.pontoOuro = pontoOuro;
    }

}//fim class
