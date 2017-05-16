/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacao;

import Controle.Constantes;
import View.PainelJogo;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import javax.swing.JLabel;

/**
 *
 * @author Matheus
 */
public class Foguinho extends Thread {

    private PainelJogo painel;
    private Constantes direcao;
    private int playS;

    public Foguinho(PainelJogo pj, Constantes c, int playS) {
        this.painel = pj;
        this.direcao = c;
        this.playS = playS;
    }//fim construtor

    @Override
    public void run() {
        painel.getLabelFoguinhoEstado().setVisible(false);
        Constantes.score -= 10;
        int x = painel.getMario().getX() + 50, y = painel.getMario().getY() + 50, xFinal = 0, yFinal = 0, proxImagem = 0;
        int varXControle = 0, varYControle = 0;
        painel.getLabelFoguinho().setVisible(true);
        painel.getLabelFoguinho().setLocation(x, y);
        switch (direcao) {
            case NORTE:
                xFinal = x;
                if (playS > 0) {
                    yFinal = y - (129 * playS);
                } else {
                    yFinal = 30;
                }
                varYControle = -1;
                painel.setImageMario(4);
                proxImagem = 20;
                break;
            case LESTE:
                if (playS > 0) {
                    xFinal = x + (165 * playS);
                } else {
                    xFinal = 680;
                }
                yFinal = y;
                varXControle = 1;
                painel.setImageMario(4);
                proxImagem = 22;
                break;
            case SUL:
                xFinal = x;
                if (playS > 0) {
                    yFinal = y + (129 * playS);
                } else {
                    yFinal = 480;
                }
                varYControle = 1;
                painel.setImageMario(5);
                proxImagem = 21;
                break;
            case OESTE:
                if (playS > 0) {
                    xFinal = x - (165 * playS);
                } else {
                    xFinal = 115;
                }
                yFinal = y;
                varXControle = -1;
                painel.setImageMario(5);
                proxImagem = 23;
        }

        do {
            x = x + varXControle;
            y = y + varYControle;
            painel.getLabelFoguinho().setLocation(x, y);
            esperar();
        } while (x != xFinal | y != yFinal);
        if (playS > 0) {
            grito();
            excluirWumpus();
        }
        painel.getLabelFoguinho().setVisible(false);
        painel.setImageMario(proxImagem);
    }//fim run

    public void esperar() {
        try {
            sleep(3);
        } catch (InterruptedException ex) {
            System.out.println("Erro: Sleep Class - MarioAnimacao");
        }
    }//fim esperar

    private void grito() {
        try {
            //ientificando arquivo de musicas
            AudioClip som = Applet.newAudioClip(new File("songs" + File.separator + "browserGrito.wav").toURL());
            som.play();
        } catch (Exception e) {
            System.out.println("erro com o audio do grito" + e);
        }
    }

    private void excluirWumpus() {
        painel.getComponentes()[0].setVisible(false);
        JLabel[] fedorWumpus =painel.getFedorWumpus();
        for (int k = 0; k < fedorWumpus.length; k++) {
            if (fedorWumpus[k] != null) {
                fedorWumpus[k].setVisible(false);
            }//fim if
        }//fim for
    }
}
