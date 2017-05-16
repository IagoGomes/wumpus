/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Controle.Constantes;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JRootPane;

/**
 *
 * @author Matheus
 */
public class PainelOpcoes extends JPanel implements ChangeListener, ActionListener, KeyListener {

    private ImageIcon imPainelOpcoes, imBotaoVoltar, imBotaoMenu, imBotaoSairJogo, imBotaoJogar, imgBotaoDoisPocos, imgBotaoQuatroPocos, imgBotaoSeisPocos, imgBotaoOitoPocos;
    private ImageIcon imgBotaoDoisPocosM, imgBotaoQuatroPocosM, imgBotaoSeisPocosM, imgBotaoOitoPocosM;
    private JLabel labelimPainelOpcoes, labelBotaoMenu, labelBotaoSairJogo, m_labelVelocidadeDaAnimacao, m_labelVolumeDoSom, labelBotaoJogar;
    private JButton botaoNivel2, botaoNivel4, botaoNivel6, botaoNivel8;
    //CheckBox para ativar/desativar som ao receber uma nova mensagem 
    public static JCheckBox m_checkboxExecutarSomPrincipalDoJogo, m_checkboxJogoManual;
    // JTextField contendo o valor escolhido em uma barra deslizante. esse valor ira ser usado
    //  para controlar a velocidade da animacao
    private JSlider m_deslizeVelocidadeDaAnimacao, m_deslizeVolumeDoSom;
    
    JButton botaoSairJogo, botaoJogar, botaoVoltar;
    JPanel painel;
    JanelaWumpus jw;

    public PainelOpcoes(final JanelaWumpus jw) {
    //public PainelOpcoes() {        
        this.requestFocus();
        this.jw = jw;
        this.setBackground(Color.blue);
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setFocusable(true);
        this.addKeyListener(this);
        imBotaoJogar = new ImageIcon(getClass().getResource("Imagens" + File.separator + "jogar.png"));
        imBotaoVoltar = new ImageIcon(getClass().getResource("Imagens" + File.separator + "menu.png"));
        imBotaoSairJogo = new ImageIcon(getClass().getResource("Imagens" + File.separator + "sairJogo.png"));
        imPainelOpcoes = new ImageIcon(getClass().getResource("Imagens" + File.separator + "painelOpcoes.png"));
        imPainelOpcoes.setImage(imPainelOpcoes.getImage().getScaledInstance(840, 640, 100));

        // Imagens Botões com quantidade de Poços, quando Está Desabilitado
        imgBotaoDoisPocos = new ImageIcon(getClass().getResource("Imagens" + File.separator + "2pa.png"));
        imgBotaoQuatroPocos = new ImageIcon(getClass().getResource("Imagens" + File.separator + "4pa.png"));
        imgBotaoSeisPocos = new ImageIcon(getClass().getResource("Imagens" + File.separator + "6pa.png"));
        imgBotaoOitoPocos = new ImageIcon(getClass().getResource("Imagens" + File.separator + "8pa.png"));

        // Imagens Botões com quantidade de Poços, quando Está Habilitado
        imgBotaoDoisPocosM = new ImageIcon(getClass().getResource("Imagens" + File.separator + "2pc.png"));
        imgBotaoQuatroPocosM = new ImageIcon(getClass().getResource("Imagens" + File.separator + "4pc.png"));
        imgBotaoSeisPocosM = new ImageIcon(getClass().getResource("Imagens" + File.separator + "6pc.png"));
        imgBotaoOitoPocosM = new ImageIcon(getClass().getResource("Imagens" + File.separator + "8pc.png"));

        labelimPainelOpcoes = new JLabel(imPainelOpcoes);

        painel = new JPanel();
        //painel.setLayout(null);
        painel.add(labelimPainelOpcoes);
        painel.setBounds(0, -10, 800, 640);//setBounds(coluna ,linha , largura, altura)
        painel.setFocusable(true);
        painel.requestFocus();
        painel.addKeyListener(this);
          

        JPanel painelNivel = new JPanel();
        painelNivel.setLayout(null);
        painelNivel.setPreferredSize(new Dimension(300, 200));
        painelNivel.setBorder(BorderFactory.createTitledBorder("Escolha Nível do Jogo"));
        painelNivel.setBounds(60, 250, 650, 200);//setBounds(coluna ,linha , largura, altura)
        painelNivel.setBackground(new Color(0, 0, 0, 10));

        //Botões do Nível 2
        botaoNivel2 = new JButton("  Nivel2  ");
        botaoNivel2.setBounds(10, 20, 152, 170);//setBounds(coluna ,linha , largura, altura)
        botaoNivel2.setIcon(imgBotaoDoisPocos);
        botaoNivel2.addActionListener(this);

        //Botões do Nível 4
        botaoNivel4 = new JButton("  Nivel4  ");
        botaoNivel4.setBounds(165, 20, 153, 170);//setBounds(coluna ,linha , largura, altura)
        botaoNivel4.setIcon(imgBotaoQuatroPocos);
        botaoNivel4.addActionListener(this);

        //Botões do Nível 6
        botaoNivel6 = new JButton("  Nivel6  ");
        botaoNivel6.setBounds(322, 20, 152, 170);//setBounds(coluna ,linha , largura, altura)
        botaoNivel6.setIcon(imgBotaoSeisPocos);
        botaoNivel6.addActionListener(this);
//        
        //Botões do Nível 8
        botaoNivel8 = new JButton("  Nivel6  ");
        botaoNivel8.setBounds(480, 20, 152, 170);//setBounds(coluna ,linha , largura, altura) 
        botaoNivel8.setIcon(imgBotaoOitoPocos);
        botaoNivel8.addActionListener(this);

        selecionaNivel();

        painelNivel.add(botaoNivel2);
        painelNivel.add(botaoNivel4);
        painelNivel.add(botaoNivel6);
        painelNivel.add(botaoNivel8);

        // Label que se posiciona acima do JSlider para Escolha da Velocidade na animação
        m_labelVelocidadeDaAnimacao = new JLabel("Velocidade da Animação:"); // 21 caracteres
        m_labelVelocidadeDaAnimacao.setBounds(59, 150, 210, 20);//setBounds(coluna ,linha , largura, altura)
        m_labelVelocidadeDaAnimacao.setForeground(Color.white);
        m_labelVelocidadeDaAnimacao.setFont(new Font("", Font.BOLD, 14));
        // JSlider para Selecionar o Intervalo na animação (intervalo esse em Segundos)
        m_deslizeVelocidadeDaAnimacao = new JSlider(JSlider.HORIZONTAL, 1, 10, Constantes.getVelocidadeAnimacao());
        m_deslizeVelocidadeDaAnimacao.setMajorTickSpacing(3);
        m_deslizeVelocidadeDaAnimacao.setMinorTickSpacing(1);
        m_deslizeVelocidadeDaAnimacao.setPaintTicks(true);
        m_deslizeVelocidadeDaAnimacao.setPaintLabels(true);
        m_deslizeVelocidadeDaAnimacao.setSnapToTicks(true);
        m_deslizeVelocidadeDaAnimacao.addChangeListener(this);
        m_deslizeVelocidadeDaAnimacao.setBounds(52, 175, 290, 35); //setBounds(coluna, linha, largura, altura) 
        m_deslizeVelocidadeDaAnimacao.setOpaque(false);
        m_deslizeVelocidadeDaAnimacao.setBackground(Color.WHITE);
        m_deslizeVelocidadeDaAnimacao.setValue(Constantes.getVelocidadeAnimacao());

        // Label que se posiciona acima do JSlider para Escolha do Volume do Som
        m_labelVolumeDoSom = new JLabel("Volume do Som:"); // 21 caracteres
        m_labelVolumeDoSom.setBounds(409, 150, 210, 20);//setBounds(coluna ,linha , largura, altura)
        m_labelVolumeDoSom.setForeground(Color.white);
        m_labelVolumeDoSom.setFont(new Font("", Font.BOLD, 14));
        // JSlider para Selecionar o volume do Som a Ser exibido (intervalo esse em Segundos)
        m_deslizeVolumeDoSom = new JSlider(JSlider.HORIZONTAL, 1, 34, Constantes.getVolumeDoSom());
        m_deslizeVolumeDoSom.setMajorTickSpacing(3);
        m_deslizeVolumeDoSom.setMinorTickSpacing(1);
        m_deslizeVolumeDoSom.setPaintTicks(true);
        m_deslizeVolumeDoSom.setPaintLabels(true);
        m_deslizeVolumeDoSom.setSnapToTicks(true);
        m_deslizeVolumeDoSom.addChangeListener(this);
        m_deslizeVolumeDoSom.setBounds(402, 175, 290, 35); //setBounds(coluna, linha, largura, altura) 
        m_deslizeVolumeDoSom.setOpaque(false);
        m_deslizeVolumeDoSom.setBackground(Color.WHITE);
        m_deslizeVolumeDoSom.setValue(Constantes.getVolumeDoSom());

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
        botaoVoltar.setIcon(imBotaoVoltar);
        botaoVoltar.addActionListener(this);

        labelBotaoSairJogo = new JLabel(" Sair Jogo ");
        labelBotaoSairJogo.setBounds(430, 560, 80, 45);//setBounds(coluna, linha, largura, altura)         
        botaoSairJogo = new JButton();
        botaoSairJogo.setBounds(435, 530, 62, 45);//setBounds(coluna, linha, largura, altura)         
        botaoSairJogo.setIcon(imBotaoSairJogo);
        botaoSairJogo.addActionListener(this);

        // CheckBox "Executar som ao Receber Mensagem"  
        m_checkboxExecutarSomPrincipalDoJogo = new JCheckBox("Som durante o Jogo", null, true);
        m_checkboxExecutarSomPrincipalDoJogo.setEnabled(true);
        m_checkboxExecutarSomPrincipalDoJogo.setBounds(400, 158, 400, 25);//setBounds(coluna ,linha , largura, altura)
        m_checkboxExecutarSomPrincipalDoJogo.setFont(new Font("", Font.BOLD, 14));
        m_checkboxExecutarSomPrincipalDoJogo.setOpaque(false);
        m_checkboxExecutarSomPrincipalDoJogo.setSelected(Constantes.getExecutarSom());
        m_checkboxExecutarSomPrincipalDoJogo.addActionListener(this);
        m_checkboxExecutarSomPrincipalDoJogo.setForeground(Color.WHITE);

        m_checkboxJogoManual = new JCheckBox("Jogar manualmente", null, true);
        m_checkboxJogoManual.setEnabled(true);
        m_checkboxJogoManual.setBounds(400, 198, 400, 25);//setBounds(coluna ,linha , largura, altura)
        m_checkboxJogoManual.setFont(new Font("", Font.BOLD, 14));
        m_checkboxJogoManual.setOpaque(false);
        m_checkboxJogoManual.setSelected(Constantes.getExecutarSom());
        m_checkboxJogoManual.addActionListener(this);
        m_checkboxJogoManual.setForeground(Color.WHITE);

        //this.add(titulo);
        this.add(painelNivel);
        this.add(m_labelVelocidadeDaAnimacao);
        this.add(m_deslizeVelocidadeDaAnimacao);
        // this.add(m_labelVolumeDoSom);
        // this.add(m_deslizeVolumeDoSom);

        this.add(botaoJogar);
        this.add(labelBotaoJogar);
        this.add(labelBotaoSairJogo);
        this.add(botaoSairJogo);
        this.add(labelBotaoMenu);
        this.add(botaoVoltar);

        this.add(m_checkboxExecutarSomPrincipalDoJogo);
        this.add(m_checkboxJogoManual);

        this.add(painel);
        this.setVisible(true);
    }

    @Override
    /* *********************************************************************************** 
     * Metodo stateChanged: Aguarda alguma alteração no estado de determinado componente
     * Parametros: um Objeto do tipo ChangeEvent
     * Retorno: void
     ************************************************************************************ */
    public void stateChanged(ChangeEvent e) {

        JSlider sliderParaComparacao = (JSlider) e.getSource();
        if (!sliderParaComparacao.getValueIsAdjusting()) {
            if (e.getSource() == m_deslizeVelocidadeDaAnimacao) //Se o Slider Alterado for Velocidade da Animaçao
            {
                Constantes.setVelocidadeAnimacao(sliderParaComparacao.getValue());
            } else if (e.getSource() == m_deslizeVelocidadeDaAnimacao) { //Se o Slider Alterado for Altura do Som
                Constantes.setVolumeDoSom(sliderParaComparacao.getValue());
            }

        }

    }//Fim do Metodo stateChanged

    private void selecionaNivel() {
        switch (Constantes.getQuantPocos()) {
            case 2:
                botaoNivel2.setIcon(imgBotaoDoisPocosM);
                botaoNivel4.setIcon(imgBotaoQuatroPocos);
                botaoNivel6.setIcon(imgBotaoSeisPocos);
                botaoNivel8.setIcon(imgBotaoOitoPocos);
                break;
            case 4:
                botaoNivel2.setIcon(imgBotaoDoisPocos);
                botaoNivel4.setIcon(imgBotaoQuatroPocosM);
                botaoNivel6.setIcon(imgBotaoSeisPocos);
                botaoNivel8.setIcon(imgBotaoOitoPocos);
                break;
            case 6:
                botaoNivel2.setIcon(imgBotaoDoisPocos);
                botaoNivel4.setIcon(imgBotaoQuatroPocos);
                botaoNivel6.setIcon(imgBotaoSeisPocosM);
                botaoNivel8.setIcon(imgBotaoOitoPocos);
                break;
            case 8:
                botaoNivel2.setIcon(imgBotaoDoisPocos);
                botaoNivel4.setIcon(imgBotaoQuatroPocos);
                botaoNivel6.setIcon(imgBotaoSeisPocos);
                botaoNivel8.setIcon(imgBotaoOitoPocosM);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == botaoSairJogo) {
            try {
                System.exit(0);
            } catch (Throwable ex) {
                Logger.getLogger(PainelOpcoes.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro! no botão Sair do Jogo - painel opcoes");
            }
        } else if (ae.getSource() == botaoJogar) {
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
                PainelOpcoes.this.setVisible(false);
                PainelOpcoes.this.finalize();
                jw.getPainelInicial().setVisible(true);
            } catch (Throwable ex) {
                Logger.getLogger(PainelOpcoes.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("erro! no botao voltar - painel opcoes");
            }

        } else if (ae.getSource() == botaoNivel2) {
            Constantes.setQuantPocos(2);
        } else if (ae.getSource() == botaoNivel4) {
            Constantes.setQuantPocos(4);
        } else if (ae.getSource() == botaoNivel6) {
            Constantes.setQuantPocos(6);
        } else if (ae.getSource() == botaoNivel8) {
            Constantes.setQuantPocos(8);
        } else if (ae.getSource() == m_checkboxExecutarSomPrincipalDoJogo) {
            Constantes.setExecutarSom(!Constantes.getExecutarSom());//Inverte Entre Executar Som ou Não
            Constantes.setTemSomParaTocar(!Constantes.getExecutarSom());//Se N puder executar som, n tem som para tocar, e vice-versa
        } else if (ae.getSource() == m_checkboxJogoManual) {
            Constantes.jogoManual = !Constantes.jogoManual;
        }

        selecionaNivel();

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
