/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Animacao.ExecutarSom;
import Controle.*;
import Main.Codigo;
//import static Controle.Game.somDaMensagem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import sun.awt.windows.ThemeReader;

/**
 *
 * @author Matheus
 */
public class JanelaWumpus extends JFrame implements ActionListener, MouseListener {

    private static JPanel painelInicial, painelFinal, painelVencer;
    public JButton botaoIniciar, botaoSobre, botaoOpcoes, botaoSair,
            botaoJogarNovamente, botaoSairPainelFinal, botaoVoltarPainelInicial;
    public static PainelJogo painelJogo;
    private PainelOpcoes painelOpcao;
    private PainelSobre painelSobre;
    private ImageIcon imgFundo, imgFim;
    private JLabel labelFundo, labelFim, labelVencer, labelScoreApresentacao;
    private final Point[][] pointMapa;
    private String[][] mapa;
    private Game game;
    private Thread threadUsuario;
    private Codigo codigoUsuario;

    // declara uma variavel do tipo ExecutarSom(classe responsavel pela execução dos sons do sitema)
    public static ExecutarSom somDaMensagem, somGameOver, somVitoria;

    /**
     * Método construtor da classe
     *
     * @param codigoUsuario código do usuário
     */
    public JanelaWumpus(Codigo codigoUsuario) {
        //inicializando a matriz com a localicação dos elementos do mapa
        this.threadUsuario = new Thread(codigoUsuario);
        this.codigoUsuario = codigoUsuario;
        pointMapa = new Point[Constantes.quantLinhas][Constantes.quantColunas];
        initMatrizComponentLocations();
    }

    /**
     * -------------------------Métodos de Inicialização------------------------
     */
    public void initComponents() {
        this.setSize(800, 630);//tamanho da janela = metade do tamanho da tela
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//fechar a janela ao clicar no "x"
        //Matando o processo de animação do mario
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                if (painelJogo != null && painelJogo.getMarioAnimacao() != null && painelJogo.getMarioAnimacao().isAlive()) {
                    painelJogo.getMarioAnimacao().stop();
                }
                if (somDaMensagem != null && somDaMensagem.isAlive()) {
                    somDaMensagem.stop();
                }
                if (somGameOver != null && somGameOver.isAlive()) {
                    somGameOver.stop();
                }
                if (somVitoria != null && somVitoria.isAlive()) {
                    somVitoria.stop();
                }
                if (threadUsuario != null && threadUsuario.isAlive()) {
                    threadUsuario.stop();
                }

                System.exit(0);
            }
        });

        //imagens
        imgFundo = new ImageIcon(getClass().getResource(("Imagens" + File.separator + "fundo.png")));

        //painelInicial
        painelInicial = new JPanel();
        painelInicial.setSize(835, 640);
        //painelInicial.setBackground(Color.blue);
        painelInicial.setLayout(null);

        //inclusão do final
        
        imgFim = new ImageIcon(getClass().getResource(("Imagens" + File.separator + "gameOver.png")));

        painelFinal = new JPanel();
        painelFinal.setSize(800, 600);
        painelFinal.setLayout(null);
        labelFim = new JLabel(imgFim);
        labelFim.setBounds(0, 0, 800, 600);

        botaoJogarNovamente = new JButton("1  JOGAR NOVAMENTE");
        botaoJogarNovamente.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoJogarNovamente.setForeground(Color.WHITE);
        botaoJogarNovamente.setOpaque(false);
        botaoJogarNovamente.setContentAreaFilled(false);
        botaoJogarNovamente.setBorderPainted(false);
        botaoJogarNovamente.addMouseListener(this);
        botaoJogarNovamente.addActionListener(this);

        botaoVoltarPainelInicial = new JButton("2  JANELA PRINCIPAL");
        botaoVoltarPainelInicial.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoVoltarPainelInicial.setForeground(Color.white);
        botaoVoltarPainelInicial.setOpaque(false);
        botaoVoltarPainelInicial.setContentAreaFilled(false);
        botaoVoltarPainelInicial.setBorderPainted(false);
        botaoVoltarPainelInicial.addMouseListener(this);
        botaoVoltarPainelInicial.addActionListener(this);

        labelScoreApresentacao = new JLabel();
        labelScoreApresentacao.setFont(new Font("Arial", Font.PLAIN, 35));
        labelScoreApresentacao.setForeground(Color.WHITE);

        botaoSairPainelFinal = new JButton("3  SAIR");

        botaoSairPainelFinal.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoSairPainelFinal.setForeground(Color.white);
        botaoSairPainelFinal.setOpaque(false);
        botaoSairPainelFinal.setContentAreaFilled(false);
        botaoSairPainelFinal.setBorderPainted(false);
        botaoSairPainelFinal.addMouseListener(this);
        botaoSairPainelFinal.addActionListener(this);

        //painel fechar
        painelVencer = new JPanel();
        painelVencer.setSize(800, 600);
        painelVencer.setLayout(null);
        labelVencer = new JLabel(new ImageIcon(getClass().getResource("Imagens" + File.separator + "youWin.png")));
        labelVencer.setBounds(0, 0, 800, 600);

        //label fundo 
        labelFundo = new JLabel(imgFundo);
        labelFundo.setBounds(0, 0, 800, 600);

        //botões
        botaoIniciar = new JButton("1 COMEÇAR O JOGO");
        botaoIniciar.setBounds(110, 260, 600, 60);//define a localização e o a posição
        botaoIniciar.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoIniciar.setForeground(Color.white);
        botaoIniciar.setOpaque(false);
        botaoIniciar.setContentAreaFilled(false);
        botaoIniciar.setBorderPainted(false);
        botaoIniciar.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        botaoIniciar.addActionListener(this);
        botaoIniciar.addMouseListener(this);

        botaoOpcoes = new JButton("  2  OPÇÕES  ");
        botaoOpcoes.setBounds(110, 310, 600, 60);
        botaoOpcoes.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoOpcoes.setForeground(Color.WHITE);
        botaoOpcoes.setOpaque(false);
        botaoOpcoes.setContentAreaFilled(false);
        botaoOpcoes.setBorderPainted(false);
        botaoOpcoes.addMouseListener(this);
        botaoOpcoes.addActionListener(this);

        botaoSobre = new JButton("   3  SOBRE   ");
        botaoSobre.setBounds(110, 360, 600, 60);
        botaoSobre.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoSobre.setForeground(Color.white);
        botaoSobre.setOpaque(false);
        botaoSobre.setContentAreaFilled(false);
        botaoSobre.setBorderPainted(false);
        botaoSobre.addMouseListener(this);
        botaoSobre.addActionListener(this);

        botaoSair = new JButton(" 4  SAIR ");
        botaoSair.setBounds(110, 410, 600, 60);
        botaoSair.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoSair.setForeground(Color.WHITE);
        botaoSair.setOpaque(false);
        botaoSair.setContentAreaFilled(false);
        botaoSair.setBorderPainted(false);
        botaoSair.addMouseListener(this);
        botaoSair.addActionListener(this);

        painelInicial.add(botaoIniciar);
        painelInicial.add(botaoSobre);
        painelInicial.add(botaoOpcoes);
        painelInicial.add(botaoSair);
        painelInicial.add(labelFundo);

        painelInicial.setVisible(true);
        painelFinal.setVisible(false);

        this.add(painelInicial);

        painelOpcao = new PainelOpcoes(this);
        painelOpcao.setBounds(0, 0, 800, 600);
        painelOpcao.setVisible(false);
        this.add(painelOpcao);

        painelSobre = new PainelSobre(this);
        painelSobre.setBounds(0, 0, 800, 600);
        painelSobre.setVisible(false);
        this.add(painelSobre);
        
        
        this.setVisible(true);

    
    }//fim initComponents

    private void initMatrizComponentLocations() {
        for (int i = 0; i < (Constantes.quantLinhas); i++) {
            for (int j = 0; j < Constantes.quantColunas; j++) {
                pointMapa[i][j] = new Point(68 + j * 165, 18 + i * 129);
            }//fim for interno            
        }//fim for externo
    }

    /*----------------------------------Init Painel---------------------------*/
    /**
     * Método que inicia o painel Jogo na tela
     */
    public void iniciarJogo() {
        //Criando o painel do jogo

        painelJogo = new PainelJogo(game);
        this.add(painelJogo);
        game.initGame();
        mapa = game.getMatrizMapa();
        painelJogo.setPersonagemLocations(pointMapa);
        painelJogo.setMapa(mapa);
        painelJogo.setLocation(0, 0);
        painelJogo.initPainelJogo();
        painelJogo.setVisible(true);
        painelJogo.requestFocus();
        //Executa som ao iniciar o jogo caso esteja marcado nas opções (por default vem marcado para executar)

        if (Constantes.getExecutarSom()) {
            if (somDaMensagem != null && somDaMensagem.isAlive()) {
                somDaMensagem.stop();
            }
            String enderecoMusicaMario = (new File("songs" + File.separator + "MarioBos.wav").toURI()).toString();;
            enderecoMusicaMario = enderecoMusicaMario.substring(5, (enderecoMusicaMario.length()));
            somDaMensagem = new ExecutarSom(enderecoMusicaMario);

            executarAudio();
        }
    }//fim iniciarJogo

    public void setarFinal() {
        if (painelInicial != null) {
            painelInicial.setVisible(false);
        }
        if (painelJogo != null) {
            painelJogo.setVisible(false);
        }

        botaoSairPainelFinal.setBounds(90, 240, 600, 60);
        botaoJogarNovamente.setBounds(100, 150, 600, 60);
        botaoVoltarPainelInicial.setBounds(100, 200, 600, 60);
        labelScoreApresentacao.setText("SCORE: " + Constantes.score);
        labelScoreApresentacao.setBounds(300, 300, 600, 60);
        painelFinal.add(labelScoreApresentacao);
        painelFinal.add(botaoJogarNovamente);
        painelFinal.add(botaoVoltarPainelInicial);
        painelFinal.add(botaoSairPainelFinal);
        painelFinal.add(labelFim);
        painelFinal.setVisible(true);
        this.add(painelFinal);
    }

    public void setarVitoria() {
        if (painelInicial != null) {
            painelInicial.setVisible(false);
        }
        if (painelJogo != null) {
            painelJogo.setVisible(false);
        }

        botaoSairPainelFinal.setBounds(90, 270, 600, 60);
        botaoJogarNovamente.setBounds(100, 180, 600, 60);
        botaoVoltarPainelInicial.setBounds(100, 230, 600, 60);

        labelScoreApresentacao.setText("SCORE: " + Constantes.score);
        labelScoreApresentacao.setBounds(300, 350, 600, 60);
        painelVencer.add(labelScoreApresentacao);
        painelVencer.add(botaoJogarNovamente);
        painelVencer.add(botaoVoltarPainelInicial);
        painelVencer.add(botaoSairPainelFinal);
        painelVencer.add(labelVencer);
        painelVencer.setVisible(true);
        this.add(painelVencer);
    }
    /*----------------------------Métodos para o Aúdio-------------------------*/

    public void executarAudio() {
        if (somGameOver != null && somGameOver.isAlive()) {
            somGameOver.stop();
        }
        if (somDaMensagem != null && somDaMensagem.isAlive()) {
            somDaMensagem.stop();
        }
        if (somVitoria != null && somVitoria.isAlive()) {
            somVitoria.stop();
        }
        String enderecoMusicaMario = (new File("songs" + File.separator + "MarioBos.wav").toURI()).toString();;
        enderecoMusicaMario = enderecoMusicaMario.substring(5, (enderecoMusicaMario.length()));
        Constantes.setTemSomParaTocar(true);// Quando a classe ExecutarSom ver essa variavel true irá perceber que é para 
                                             // executar a música e no final deve voltar para repetir a mesma música, 
                                             // enquanto a Thread estiver viva vai executar essa música se n trocar de música
        somDaMensagem = new ExecutarSom(enderecoMusicaMario);
        somDaMensagem.start();
    }

    public void executarAudioGameOver() {
        if (somGameOver != null && somGameOver.isAlive()) {
            somGameOver.stop();            
        }
        if (somDaMensagem != null && somDaMensagem.isAlive()) {
            somDaMensagem.stop();
        }
        if (somVitoria != null && somVitoria.isAlive()) {
            somVitoria.stop();
        }
        String enderecoMusicaMario = (new File("songs" + File.separator + "gameOver.wav").toURI()).toString();;
        enderecoMusicaMario = enderecoMusicaMario.substring(5, (enderecoMusicaMario.length()));
        somGameOver = new ExecutarSom(enderecoMusicaMario);
        Constantes.setTemSomParaTocar(false);// Quando a classe ExecutarSom executar n vai ficar repetindo a música
                                             // Pois através dessa variavel sabe que deve repetir, ao final da música finaliza
        somGameOver.start();
    }

    public void executarAudioVitoria() {
        if (somGameOver != null && somGameOver.isAlive()) {
            somGameOver.stop();
        }
        if (somDaMensagem != null && somDaMensagem.isAlive()) {
            somDaMensagem.stop();
        }
        if (somVitoria != null && somVitoria.isAlive()) {
            somVitoria.stop();
        }
        String enderecoMusicaMario = (new File("songs" + File.separator + "youWin.wav").toURI()).toString();;
        enderecoMusicaMario = enderecoMusicaMario.substring(5, (enderecoMusicaMario.length()));
        somVitoria = new ExecutarSom(enderecoMusicaMario);
        Constantes.setTemSomParaTocar(false);// Quando a classe ExecutarSom executar n vai ficar repetindo a música
                                             // Pois através dessa variavel sabe que deve repetir, ao final da música finaliza
        somVitoria.start();
    }


    /*---------------------------------Gets e Sets ---------------------------*/
    public static ExecutarSom getSomDaMensagem() {
        return somDaMensagem;
    }

    /**
     * Método que seta o mapa do jogo
     *
     * @param mapa String [][]
     */
    public void setMapa(String[][] mapa) {
        this.mapa = mapa;
    }//fim setMapa

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Método que retorna o painelInicial da aplicação
     *
     * @return painelInicial
     */
    public JPanel getPainelInicial() {
        return painelInicial;
    }//fim getPainelInicial

    /**
     * Método que retorna o botaoIniciarJogo
     *
     * @return botaoIniciar JButton
     */
    public JButton getBotaoIniciarJogo() {
        return botaoIniciar;
    }//fim getBotaoIniciarJogo

    /**
     * Método que retorna o painelJogo
     *
     * @return painelJogo JPanel
     */
    public JPanel getPainelJogo() {
        return painelJogo;
    }//fim getPainelJogo

    /**
     * Método que retorna o labelScore
     *
     * @return JLabel score
     */
    public JLabel getLabelScore() {
        return painelJogo.getLabelScore();
    }//fim getLabelScore
    
    /**
     * Método que retorna a thread do usuario
     */
    public Thread getThreadUsuario(){
        if(threadUsuario.isAlive())
            threadUsuario.stop();
        
        threadUsuario = new Thread(codigoUsuario);
        return threadUsuario;
    }

    /**
     * --------------------------ActionListener---------------------------------
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoIniciar) {

            painelInicial.setVisible(false);
            painelJogo = null;
            iniciarJogo();
            if (!Constantes.jogoManual) {
             getThreadUsuario().start();
            }

        } else if (e.getSource() == botaoOpcoes) {
            painelOpcao.setVisible(true);
            this.transferFocus();
            if (painelJogo != null) {
                painelJogo.setVisible(false);
            }
            painelInicial.setVisible(false);

        } else if (e.getSource() == botaoSobre) {
            painelSobre.setVisible(true);
            this.transferFocus();
            if (painelJogo != null) {
                painelJogo.setVisible(false);
            }
            painelInicial.setVisible(false);
//            painelSobre = new PainelSobre(JanelaWumpus.this.painelInicial);
//            painelSobre.setBounds(0, 0, 800, 600);
//            this.add(painelSobre);
//            painelInicial.setVisible(false);
        } else if (e.getSource() == botaoSair || e.getSource() == botaoSairPainelFinal) {
            this.dispose();
            System.exit(0);
            //setarVitoria();
        } else if (e.getSource() == botaoJogarNovamente) {
            painelInicial.setVisible(false);
            painelFinal.setVisible(false);
            painelVencer.setVisible(false);
            painelJogo = null;

            iniciarJogo();
         
            if (!Constantes.jogoManual) {
               getThreadUsuario().start();
              
            }
           
        } else if (e.getSource() == botaoVoltarPainelInicial) {
            painelFinal.setVisible(false);
            painelVencer.setVisible(false);
            painelInicial.setVisible(true);
        }//fim if-else
    }//fim actionPerformed

    /**
     * ------------------------------MouseListener-------------------------------
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }//fim mouseClicked

    @Override
    public void mousePressed(MouseEvent e) {
    }//fim mousePressed

    @Override
    public void mouseReleased(MouseEvent e) {
    }//fim mouseReleased

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == botaoSobre) {
            botaoSobre.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        } else if (e.getSource() == botaoIniciar) {
            botaoIniciar.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        } else if (e.getSource() == botaoOpcoes) {
            botaoOpcoes.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        } else if (e.getSource() == botaoSair) {
            botaoSair.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        } else if (e.getSource() == botaoSairPainelFinal) {
            botaoSairPainelFinal.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        } else if (e.getSource() == botaoJogarNovamente) {
            botaoJogarNovamente.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        } else if (e.getSource() == botaoVoltarPainelInicial) {
            botaoVoltarPainelInicial.setIcon(new ImageIcon(getClass().getResource("Imagens" + File.separator + "ic.png")));
        }
    }//fim mouseEntered

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == botaoSobre) {
            botaoSobre.setIcon(null);
        } else if (e.getSource() == botaoIniciar) {
            botaoIniciar.setIcon(null);
        } else if (e.getSource() == botaoSair) {
            botaoSair.setIcon(null);
        } else if (e.getSource() == botaoOpcoes) {
            botaoOpcoes.setIcon(null);
        } else if (e.getSource() == botaoSairPainelFinal) {
            botaoSairPainelFinal.setIcon(null);
        } else if (e.getSource() == botaoJogarNovamente) {
            botaoJogarNovamente.setIcon(null);
        } else if (e.getSource() == botaoVoltarPainelInicial) {
            botaoVoltarPainelInicial.setIcon(null);
        }
    }//fim mouseExited
}//fim class
