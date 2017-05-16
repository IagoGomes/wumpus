/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Animacao.ExecutarSom;
import Animacao.Foguinho;
import Controle.Constantes;
import Animacao.MarioAnimacao;
import Controle.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

/**
 *
 * @author Matheus
 */
public class PainelJogo extends JPanel implements KeyListener, ActionListener {

    private JPanel painelScore;
    private JLabel labelBase, componentes[], pocos[], fedorWumpus[], brisaPoco[], labelBloco[][];
    private JLabel labelMario, labelScore, labelImagemCoin, labelPontuacao, labelFoguinho, labelFoguinhoEstado;
    private ImageIcon imPainel;
    private JLabel labelImagemPainel, labelEscuro;
    private boolean temFlecha;
    private JButton botaoVoltar, botaoSair;
    private JButton botaoAudio;
    private MarioAnimacao mario;
    private final int WUMPUS = 0,
            OURO = 1,
            BRILHO = 2,
            BRISA = 3,
            POCO = 5,
            GUERREIRO = 4,
            FEDOR = 6;
    private final String[] pathPersonagens = {"bowser.gif", "ouro.png", "brilho.gif", "vento.gif",
        "marioPDireita.png", "MarioPEsquerda.png", "MarioDireita.gif", "MarioEsquerda.gif",
        "pocoBase.png", "pocoTopo.png", "pocoCantoCimaDireita.png", "pocoCantoCimaEsquerda.png",
        "pocoCantoBaixoDireita.png", "pocoLateralDireita.png", "pocoLateralEsquerda.png", "pocoCentro.png",
        "fedor.png", "fedorVertical.png", "brisa.png", "ventoHorizontal.png",
        "marioNorte.png", "marioSul.png", "marioLeste.png", "marioOeste.png", "mario_fireBall.gif", "mario_fireBall.png"};
    private Point personagensLocation[][];
    private String[][] mapa;
    private Game game;
    private int indicePersonagens[];

    /**
     * Metodo construtor: e o metodo chamado quando a classe e instanciada,
     * inicia os componentes da aplicação
     */
    public PainelJogo(Game game) {
        this.game = game;

    }
    /*----------------------------------Inits---------------------------------*/

    public void initPainelJogo() {

        this.setSize(835, 640);//tamanho da janela = metade do tamanho da tela
        this.setFocusable(true);
        this.setLayout(null);

        imPainel = new ImageIcon(getClass().getResource("Imagens" + File.separator + "painel.png"));
        imPainel.setImage(imPainel.getImage().getScaledInstance(800, 600, 100));

        labelImagemPainel = new JLabel(imPainel);
        labelImagemPainel.setBounds(0, 0, 800, 600);
        this.add(labelImagemPainel);

        this.addKeyListener(this);;

        mario = new MarioAnimacao(this, game);
        mario.setPermissao(true);
        mario.setDirecao(Constantes.LESTE);
        mario.start();

        //criação do score
        painelScore = new JPanel();
        painelScore.setBounds(455, 5, 200, 30);
        painelScore.setLayout(null);
        painelScore.setOpaque(false);
        labelImagemPainel.add(painelScore);

        labelImagemCoin = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + "coin.gif")));
        labelImagemCoin.setBounds(90, 5, 20, 20);
        painelScore.add(labelImagemCoin);

        labelScore = new JLabel("Score: ");
        labelScore.setBounds(5, 5, 200, 20);
        labelScore.setForeground(Color.black);
        labelScore.setFont(new Font("Monospaced", Font.BOLD, 25));
        painelScore.add(labelScore);

        labelPontuacao = new JLabel("x 0");
        labelPontuacao.setBounds(110, 5, 170, 20);
        labelPontuacao.setForeground(Color.black);
        labelPontuacao.setFont(new Font("Arial", Font.BOLD, 25));
        painelScore.add(labelPontuacao);

        labelFoguinho = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[24])));
        labelFoguinho.setSize(50, 50);
        labelFoguinho.setVisible(false);
        labelImagemPainel.add(labelFoguinho);

        labelFoguinhoEstado = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[25])));
        labelFoguinhoEstado.setBounds(680, 5, 30, 30);
        labelImagemPainel.add(labelFoguinhoEstado);

        labelEscuro = new JLabel();
        labelEscuro.setBounds(0, 0, 800, 600);
        labelEscuro.setOpaque(true);
        labelEscuro.setVisible(false);
        labelImagemPainel.add(labelEscuro);
        temFlecha = true;

        botaoVoltar = new JButton();
        botaoVoltar.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "menu2.png")));
        botaoVoltar.setBounds(30, 5, 30, 30);
        botaoVoltar.addActionListener(this);
        labelImagemPainel.add(botaoVoltar);

        botaoSair = new JButton();
        botaoSair.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "sairJogo2.png")));
        botaoSair.setBounds(65, 5, 30, 30);
        botaoSair.addActionListener(this);
        labelImagemPainel.add(botaoSair);

        botaoAudio = new JButton();
        botaoAudio.setBounds(100, 5, 30, 30);
        botaoAudio.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "comAudio.png")));
        botaoAudio.setVisible(true);
        botaoAudio.addActionListener(this);
        labelImagemPainel.add(botaoAudio);

        indicePersonagens = new int[7];
        for (int i = 0; i < indicePersonagens.length; i++) {
            indicePersonagens[i] = -1;
        }

        initMapa();
        initBlocos();

    }

    private void initBlocos() {
        labelBloco = new JLabel[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i == 3 && j == 0)) {
                    continue;
                }
                labelBloco[i][j] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + "bloco.png")));
                labelBloco[i][j].setLocation(personagensLocation[i][j].x - 10, personagensLocation[i][j].y + 18);
                labelBloco[i][j].setSize(190, 150);
                labelImagemPainel.add(labelBloco[i][j], (indicePersonagens[GUERREIRO] - 1));
            }//for

        }//fim for 
    }//fim initBlocos

    /**
     * Método que inicializa o mapa do jogo, setando os personagens na tela
     */
    private void initMapa() {
        componentes = new JLabel[7];
        pocos = new JLabel[Constantes.getQuantPocos()];
        fedorWumpus = new JLabel[4];
        int kBrisa = 0;
        brisaPoco = new JLabel[15];
        int kPocos = 0;
        int kIndice = 0;
        for (int i = 0; i < Constantes.quantLinhas; i++) {
            for (int j = 0; j < Constantes.quantColunas; j++) {
                //setando o wumpuls
                if (mapa[i][j].contains(Constantes.WUMPUL.toString())) {
                    mario.setWumpus(new Point(i, j));
                    componentes[WUMPUS] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[WUMPUS])));
                    componentes[WUMPUS].setSize(80, 90);
                    componentes[WUMPUS].setLocation(personagensLocation[i][j].x + 40, personagensLocation[i][j].y + 20);
                    indicePersonagens[WUMPUS] = kIndice;
                    kIndice++;

                    fedorWumpus = setFedorTela(i, j);
                    for (int k = 0; k < fedorWumpus.length; k++) {
                        if (fedorWumpus[k] != null && !mapa[i][j].contains(Constantes.POÇO.toString())) {
                            labelImagemPainel.add(fedorWumpus[k]);
                        }//fim if
                    }//fim for
                }
                if (mapa[i][j].contains(Constantes.GUERREIRO.toString())) {
                    labelMario = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[22])));
                    labelMario.setSize(150, 150);
                    labelMario.setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y);

                    indicePersonagens[GUERREIRO] = kIndice;
                    kIndice++;

                }
                if (mapa[i][j].contains(Constantes.OURO.toString())) {
                    mario.setPontoOuro(new Point(i, j));
                    componentes[OURO] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[OURO])));
                    componentes[OURO].setSize(100, 100);
                    componentes[OURO].setLocation(personagensLocation[i][j].x + 30, personagensLocation[i][j].y + 25);

                    indicePersonagens[OURO] = kIndice;
                    kIndice++;

                    componentes[BRILHO] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[BRILHO])));
                    componentes[BRILHO].setSize(180, 180);
                    componentes[BRILHO].setLocation(personagensLocation[i][j].x + 15, personagensLocation[i][j].y + 5);
                    labelImagemPainel.add(componentes[BRILHO]);
                    indicePersonagens[BRILHO] = kIndice;
                }
                if (mapa[i][j].contains(Constantes.POÇO.toString())) {
                   
                    mario.setPoco(new Point(i, j));
                    pocos[kPocos] = (getImagePoco(i, j));
                    pocos[kPocos].setSize(150, 150);
                    kPocos++;
                    indicePersonagens[POCO] = kIndice;
                    kIndice++;
                }
                if (mapa[i][j].contains(Constantes.BRISA.toString()) && !mapa[i][j].contains(Constantes.POÇO.toString())) {
                    brisaPoco[kBrisa] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[18])));
                    brisaPoco[kBrisa].setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y);
                    brisaPoco[kBrisa].setSize(150, 150);
                    labelImagemPainel.add(brisaPoco[kBrisa]);
                    kBrisa++;

                }

            }//fim for interno
        }//fim for externo

        if (indicePersonagens[WUMPUS] < indicePersonagens[GUERREIRO]) {
            int k = indicePersonagens[GUERREIRO];
            indicePersonagens[GUERREIRO] = indicePersonagens[WUMPUS];
            indicePersonagens[WUMPUS] = k;
        }
        if (indicePersonagens[OURO] < indicePersonagens[GUERREIRO]) {
            int k = indicePersonagens[GUERREIRO];
            indicePersonagens[GUERREIRO] = indicePersonagens[OURO];
            indicePersonagens[OURO] = k;
        }
        if (indicePersonagens[GUERREIRO] < indicePersonagens[POCO]) {
            indicePersonagens[POCO] = indicePersonagens[GUERREIRO];
            indicePersonagens[GUERREIRO]++;
        }

        labelImagemPainel.add(componentes[WUMPUS], indicePersonagens[WUMPUS] + 1);
        labelImagemPainel.add(labelMario, indicePersonagens[GUERREIRO] + 1);
        labelImagemPainel.add(componentes[OURO], indicePersonagens[GUERREIRO] + 1);

      
        for (int i = 0; i < pocos.length; i++) {
           labelImagemPainel.add(pocos[i], indicePersonagens[POCO]);
        }

    }//fim initMapa

    private JLabel getImagePoco(int i, int j) {
        JLabel label = null;
        if (i == 0 && j == 0) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[11])));
            label.setLocation(personagensLocation[i][j].x + 8, personagensLocation[i][j].y + 5);
        } else if (i == 0 && j == 3) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[10])));
            label.setLocation(personagensLocation[i][j].x + 5, personagensLocation[i][j].y + 15);
        } else if (i == 0) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[9])));
            label.setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y + 10);
        } else if (i == 3 && j == 3) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[12])));
            label.setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y);
        } else if (i == 3) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[8])));
            label.setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y - 3);
        } else if (j == 3) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[13])));
            label.setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y);
        } else if (j == 0 && i != 3) {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[14])));
            label.setLocation(personagensLocation[i][j].x + 8, personagensLocation[i][j].y + 2);
        } else {
            label = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[15])));
            label.setLocation(personagensLocation[i][j].x, personagensLocation[i][j].y - 3);
        }
        return label;
    }

    /**
     * Inicia a thread de animação do fogo
     *
     * @param posicao Orientação que a animação vai ser ativada
     */
    public void animacaoFogo(Constantes posicao, int p) {
        Foguinho fogo = new Foguinho(this, posicao, p);
        fogo.start();
        temFlecha = false;
    }

    public JLabel getLabelFoguinho() {
        return labelFoguinho;
    }

    public boolean isTemFlecha() {
        return temFlecha;
    }

    /**
     * Método que retorna o personagem Mario (guerreiro) do jogo
     *
     * @return JLabel mario
     */
    public JLabel getMario() {
        return labelMario;
    }//fim getMario

    /**
     * Método que retorna o objeto responsável pela animação do personagem mario
     *
     * @return mario MarioAnimacao
     */
    public MarioAnimacao getMarioAnimacao() {
        return mario;
    }//fim getMarioAnimacao

    public JLabel[] getComponentes() {
        return componentes;
    }

    /**
     * Método que seta a matriz que representa o mapa do jogo
     *
     * @param mapa String [][]
     */
    public void setMapa(String[][] mapa) {
        this.mapa = mapa;
    }//fim setMapa

    /**
     * Método que seta a matriz com as posições relativas dos personagens
     *
     * @param localizacaoPersonagens Point [][]
     */
    public void setPersonagemLocations(Point[][] localizacaoPersonagens) {
        personagensLocation = new Point[Constantes.quantLinhas][Constantes.quantColunas];
        for (int i = 0; i < personagensLocation.length; i++) {
            for (int j = 0; j < personagensLocation[i].length; j++) {
                personagensLocation[i][j] = localizacaoPersonagens[i][j];
            }//fim for interno
        }//fim for externo
    }//fim setPersonagemLocations

    public void setImageMario(int posicao) {
        labelMario.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[posicao])));
    }

    public JLabel[][] getLabelsBloco() {
        return labelBloco;
    }

    public JLabel getLabelScore() {
        return labelPontuacao;
    }

    public JLabel getLabelFoguinhoEstado() {
        return labelFoguinhoEstado;
    }

    public JLabel[] getFedorWumpus() {
        return fedorWumpus;
    }

    public JLabel getPainel() {
        return (labelImagemPainel);
    }

    public JLabel getLabelEscuro() {
        return labelEscuro;
    }

    public JLabel getLabelOuro() {
        return componentes[OURO];
    }

    public void setLabelBloco(int x, int y) {

        labelBloco[x][y].setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "blocos.gif")));
        labelImagemPainel.repaint();
    }

    public void setLabelOuro() {
        componentes[OURO].setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ouro.gif")));
        labelImagemPainel.repaint();
    }

    public void setLabelsBloco(int x, int y, ImageIcon imageIcon) {
        labelBloco[x][y].setIcon(imageIcon);
        labelImagemPainel.repaint();
        labelBloco[x][y].repaint();
    }

    /**
     * Método que seta as imagens do fedor na janela
     *
     * @param i posição em x
     * @param j posição em y
     */
    private JLabel[] setFedorTela(int i, int j) {
        JLabel[] labels = new JLabel[4];
        int _x1 = i - 1, _y1 = j - 1, _x2 = i + 1, _y2 = j + 1, k = 0;

        /**
         * (_x1,j) (x,_y1) (x,j) (x, _y2) (_x2, j)
         */
        /**
         * posicionando as imagens verticais nas linhas
         */
        if (_x1 >= 0 && !mapa[_x1][j].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[16])));
            labels[k].setLocation(personagensLocation[_x1][j].x, personagensLocation[_x1][j].y + 10);
            labels[k].setSize(150, 150);
            k++;
        }
        if ((_x2 <= Constantes.quantLinhas - 1) && !mapa[_x2][j].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[16])));
            labels[k].setLocation(personagensLocation[_x2][j].x, personagensLocation[_x2][j].y + 10);
            labels[k].setSize(150, 150);
            k++;
        }
        if ((_y1 >= 0) && !mapa[i][_y1].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[16])));
            labels[k].setLocation(personagensLocation[i][_y1].x, personagensLocation[i][_y1].y + 10);
            labels[k].setSize(150, 150);
            k++;
        }
        if ((_y2 <= Constantes.quantColunas - 1) && !mapa[i][_y2].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[16])));
            labels[k].setLocation(personagensLocation[i][_y2].x, personagensLocation[i][_y2].y + 10);
            labels[k].setSize(150, 150);
            k++;
        }
        return labels;

    }//fim setFedorTela

    /**
     * Método que seta as imagens do fedor na janela
     *
     * @param i posição em x
     * @param j posição em y
     */
    private JLabel[] setBrisaTela(int i, int j) {
        JLabel labels[] = new JLabel[4];
        int _x1 = i - 1, _y1 = j - 1, _x2 = i + 1, _y2 = j + 1, k = 0;

        /**
         * (_x1,j) (x,_y1) (x,j) (x, _y2) (_x2, j)
         */
        /**
         * posicionando as imagens verticais nas linhas
         */
        if (_x1 >= 0 && !mapa[_x1][j].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[18])));
            labels[k].setLocation(personagensLocation[_x1][j].x, personagensLocation[_x1][j].y);
            labels[k].setSize(150, 150);
            k++;
        }
        if (_x2 <= Constantes.quantLinhas - 1 && !mapa[_x2][j].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[18])));
            labels[k].setLocation(personagensLocation[_x2][j].x, personagensLocation[_x2][j].y);
            labels[k].setSize(150, 150);
            k++;
        }
        if (_y1 >= 0 && !mapa[i][_y1].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[18])));
            labels[k].setLocation(personagensLocation[i][_y1].x, personagensLocation[i][_y1].y);
            labels[k].setSize(150, 150);
            k++;
        }
        if (_y2 <= Constantes.quantColunas - 1 && !mapa[i][_y2].contains(Constantes.POÇO.toString())) {
            labels[k] = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + pathPersonagens[18])));
            labels[k].setLocation(personagensLocation[i][_y2].x, personagensLocation[i][_y2].y);
            labels[k].setSize(150, 150);
            k++;
        }
        return labels;

    }//fim setOnScreem

    /*-----------------------------Key Listener ------------------------------*/
    @Override
    public void keyTyped(KeyEvent e) {
    }//fim keyTyped

    @Override
    public void keyPressed(KeyEvent e) {
        if (Constantes.jogoManual && e.getKeyCode() == KeyEvent.VK_ENTER) {
            game.guerreiroAndar();
        } else if (Constantes.jogoManual && e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.guerreiroGirarSentHorario();
        } else if (Constantes.jogoManual && e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.guerreiroGirarSentAntiHorario();
        } else if (Constantes.jogoManual && e.getKeyCode() == KeyEvent.VK_CONTROL) {
            game.guerreiroAtirar();
        } else if (Constantes.jogoManual && e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.guerreiroPegarObjeto();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            botaoVoltar.doClick();
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            botaoSair.doClick();
        }else if(e.getKeyCode() == KeyEvent.VK_M){
            botaoAudio.doClick();
        }
        //fim else if
    }//fim keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
    }//fim keyReleased

    public void ocultarLabels() {
        painelScore.setVisible(false);
        botaoAudio.setEnabled(false);
        botaoVoltar.setEnabled(false);
        botaoSair.setEnabled(false);

        for (JLabel label : componentes) {
            if (label != null) {
                label.setVisible(false);
            }
        }
        for (JLabel label : fedorWumpus) {
            if (label != null) {
                label.setVisible(false);
            }
        }
        for (JLabel label : brisaPoco) {
            if (label != null) {
                label.setVisible(false);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == botaoVoltar) {
            
            this.setVisible(false);
            
            game.getJanela().getPainelInicial().setVisible(true);
            if (JanelaWumpus.getSomDaMensagem() != null && JanelaWumpus.getSomDaMensagem().isAlive()) {
                JanelaWumpus.getSomDaMensagem().stop();
            }
        } else if (ae.getSource() == botaoSair) {
            game.getJanela().dispose();
            System.exit(0);
        } else if (ae.getSource() == botaoAudio) {
            Constantes.setExecutarSom(!Constantes.getExecutarSom());

            if (Constantes.getExecutarSom()) {
                if(JanelaWumpus.somDaMensagem!=null && JanelaWumpus.somDaMensagem.isAlive())
                    JanelaWumpus.somDaMensagem.stop();
               game.getJanela().executarAudio();
                botaoAudio.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "comAudio.png")));
            } else {
                if(JanelaWumpus.somDaMensagem!=null && JanelaWumpus.somDaMensagem.isAlive())
                    JanelaWumpus.somDaMensagem.stop();
                botaoAudio.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "semAudio.png")));
            }
            
           this.requestFocus();
        }
    }

}//fim class
