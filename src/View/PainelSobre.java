/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controle.Constantes;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JSlider;

/**
 *
 * @author Matheus
 */
public class PainelSobre extends JPanel implements ActionListener, KeyListener {

    private ImageIcon imPainelSobre, imBotaoMenu, imBotaoSairJogo, imBotaoJogar;
    private JLabel labelimPainelSobre, labelBotaoMenu, labelBotaoSairJogo, labelBotaoJogar, label;
    
    JButton botaoSairJogo, botaoJogar, botaoVoltar, botaoManual, botaoDocumentacao;
    JPanel painel, painelSobreDesenvolvedores, painelSobreOJogo; //jw
    JanelaWumpus jw;

    public PainelSobre(final JanelaWumpus JV) {
        this.requestFocus();
        this.jw = JV;
        this.setBackground(Color.blue);
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setFocusable(true);
        this.addKeyListener(this);
        //this.setBounds(0, 0, 800, 640);
        imBotaoJogar = new ImageIcon(getClass().getResource("Imagens" + File.separator + "jogar.png"));
        imBotaoMenu = new ImageIcon(getClass().getResource("Imagens" + File.separator + "menu.png"));
        imBotaoSairJogo = new ImageIcon(getClass().getResource("Imagens" + File.separator + "sairJogo.png"));
        imPainelSobre = new ImageIcon(getClass().getResource("Imagens" + File.separator + "painelSobre.png"));
        imPainelSobre.setImage(imPainelSobre.getImage().getScaledInstance(840, 640, 100));

        labelimPainelSobre = new JLabel(imPainelSobre);
        //labelimPainelSobre.setBounds(0, 0, 840, 640);


        painelSobreDesenvolvedores = new JPanel();
        //painelSobreDesenvolvedores.setLayout(null);
        painelSobreDesenvolvedores.setBounds(20, 130, 340, 285); //setBounds(coluna, linha, largura, altura) 
        painelSobreDesenvolvedores.setBorder(BorderFactory.createTitledBorder("Sobre Nós: "));
        painelSobreDesenvolvedores.setFocusable(true);
        painelSobreDesenvolvedores.requestFocus();
        painelSobreDesenvolvedores.addKeyListener(this);
        painelSobreDesenvolvedores.setOpaque(false);
        painelSobreDesenvolvedores.setForeground(Color.white);
        painelSobreDesenvolvedores.setFont(new Font("", Font.BOLD, 14));
        
        JLabel textoSobreNos = new JLabel("<html><body>Nome: Cleiton Galvão Santana <br> Matricula:200910563 <br> Email: cleitongalvao@uesb.edu.br<br><br>\n" +
                                          "Nome: Iago Pacheco Gomes<br> Matricula:201310456<br> Email: iago.pg00@gmail.com<br><br>\n" +
                                          "Nome: Matheus Lima Viana <br> Matricula:201310452<br>Email: matheuscond15@hotmail.com<br><br>\n" +
                                          "Nome: Matheus Thiago Marques Barbosa <br>Matricula:201310461<br>Email: matheus_thiago_mb@hotmail.com </body></html>", JLabel.LEFT);
        textoSobreNos.setForeground(Color.white);
        textoSobreNos.setFont(new Font("", Font.BOLD, 14));
        
        textoSobreNos.setBounds(10,10,300,100);
        painelSobreDesenvolvedores.add(textoSobreNos);

        JLabel textoSobreOJogo = new JLabel("<html><body>O Super Mario Wumpus é uma interface <br> \n"
                                            + "de manipulação e testes feita em JAVA, que <br> \n"
                                            + "possui como objetivo simular o resultado de <br>\n" 
                                            + "soluções inteligentes para resolver o problema <br>\n"
                                            + "computacional: mundo wumpus. <br>\n"
                                            + "Esta foi desenvolvida com base em um clássico<br>\n"
                                            + "dos videogames, o super mario bros.</body></html>", JLabel.LEFT);
        textoSobreOJogo.setForeground(Color.white);
        textoSobreOJogo.setFont(new Font("", Font.BOLD, 14));
        
        textoSobreOJogo.setBounds(25, 365, 400, 135); //setBounds(coluna, linha, largura, altura) 
        
        
        painelSobreOJogo = new JPanel();
        //painelSobreOJogo.setLayout(null);
        painelSobreOJogo.setBounds(365, 130, 400, 285); //setBounds(coluna, linha, largura, altura) 
        painelSobreOJogo.setForeground(Color.white);
        painelSobreOJogo.setFont(new Font("", Font.BOLD, 14));
        painelSobreOJogo.setBorder(BorderFactory.createTitledBorder("Sobre o Jogo: "));
        painelSobreOJogo.setFocusable(true);
        painelSobreOJogo.requestFocus();
        painelSobreOJogo.addKeyListener(this);
        painelSobreOJogo.setOpaque(false);
        painelSobreOJogo.add(textoSobreOJogo);

        painel = new JPanel();
        
        painel.setLayout(null);
        painel.add(labelimPainelSobre);
        painel.setBounds(0, 0, 800, 640);
        painel.add(painelSobreDesenvolvedores);
        painel.add(painelSobreOJogo);
        painel.setFocusable(true);
        painel.requestFocus();
        painel.addKeyListener(this);
        
        //painel.setToolTipText("dfksjfkasjfdkfjdskfjsdakf");

        labelBotaoJogar = new JLabel("  Jogar  ");
        labelBotaoJogar.setBounds(295, 560, 62, 45);//setBounds(coluna, linha, largura, altura) 
        botaoJogar = new JButton();
        botaoJogar.setBounds(290, 530, 62, 45);//setBounds(coluna, linha, largura, altura) 
        botaoJogar.setIcon(imBotaoJogar);
        botaoJogar.addActionListener(this);

        labelBotaoMenu = new JLabel("  Menu  ");
        labelBotaoMenu.setBounds(363, 560, 62, 45);//setBounds(coluna, linha, largura, altura) 
        botaoVoltar = new JButton();
        botaoVoltar.setBounds(360, 530, 62, 45);//setBounds(coluna, linha, largura, altura) 
        botaoVoltar.setIcon(imBotaoMenu);
        botaoVoltar.addActionListener(this);

        labelBotaoSairJogo = new JLabel(" Sair Jogo ");
        labelBotaoSairJogo.setBounds(430, 560, 80, 45);//setBounds(coluna, linha, largura, altura)         
        botaoSairJogo = new JButton();
        botaoSairJogo.setBounds(435, 530, 62, 45);//setBounds(coluna, linha, largura, altura)         
        botaoSairJogo.setIcon(imBotaoSairJogo);
        botaoSairJogo.addActionListener(this);

        botaoManual = new JButton(new ImageIcon(getClass().getResource("Imagens" + File.separator + "manualDoJogo.png")));
        botaoManual.setBounds(20, 425, 90, 100);//setBounds(coluna, linha, largura, altura)         
        botaoManual.addActionListener(this);
        botaoManual.setToolTipText("Manual da Interface deste Jogo");
        this.add(botaoManual);

        botaoDocumentacao = new JButton(new ImageIcon(getClass().getResource("Imagens" + File.separator + "documentacao.png")));
        botaoDocumentacao.setBounds(120, 425, 86, 100);//setBounds(coluna, linha, largura, altura)         
        botaoDocumentacao.addActionListener(this);
        botaoDocumentacao.setToolTipText("Documentação sobre como é o jogo Wumpus em si.");
        
        painel.add(labelimPainelSobre);
        this.add(botaoDocumentacao);
        this.add(botaoJogar);
        this.add(labelBotaoJogar);
        this.add(labelBotaoSairJogo);
        this.add(botaoSairJogo);
        this.add(labelBotaoMenu);
        this.add(botaoVoltar);
        //this.add(m_checkboxExecutarSomPrincipalDoJogo);
        this.add(painel);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == botaoSairJogo) 
        {
            try {
                System.exit(0);
            } catch (Throwable ex) {
                Logger.getLogger(PainelOpcoes.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro! no botão Sair do Jogo - painel opcoes");
            }
        } 
        else if (ae.getSource() == botaoJogar) 
        {
            try {
                this.setVisible(false);
                jw.iniciarJogo();

                if (!Constantes.jogoManual) {
                    jw.getThreadUsuario().start();
                }

            } catch (Throwable ex) {
                Logger.getLogger(PainelOpcoes.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro! no botao voltar - painel opcoes");
            }

        } else if (ae.getSource() == botaoVoltar) {
            try {
                PainelSobre.this.setVisible(false);
                PainelSobre.this.finalize();
                jw.getPainelInicial().setVisible(true);
            } catch (Throwable ex) {
                Logger.getLogger(PainelOpcoes.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro! no botao voltar - painel opcoes");
            }          

        } else if (ae.getSource() == botaoManual) {
            try {
                Desktop.getDesktop().open(new File(getClass().getResource("Imagens"+File.separator+"manual.pdf").getFile()));
            } catch (IOException ex) {
            }
        } else if (ae.getSource() == botaoDocumentacao) {
            try {
                Desktop.getDesktop().open(new File(getClass().getResource("Imagens"+File.separator+"documentacao.pdf").getFile()));
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("SSD");
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            botaoVoltar.doClick();
        } else if (ke.getKeyCode() == KeyEvent.VK_Q) {
            botaoSairJogo.doClick();
        } else if (ke.getKeyCode() == KeyEvent.VK_J) {
            botaoJogar.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
