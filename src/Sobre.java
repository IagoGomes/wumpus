import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
/* ***************************************************************
 * Autores: Matheus Thiago Marques Barbosa(201310461)
 *          Yan Kaic Antunes da Silva(201311285)
 * Inicio: 17/07/2014
 * Ultima alteracao: 27/07/2014
 * Nome:Sobre
 * Funcao: abre uma tela de dialogo mostrando informações sobre o programa
 *************************************************************** */

public class Sobre extends JDialog {

  public JButton exitButton;

  /**
   * construtor: inicializa os compinentes
   *
   */
  public Sobre() {
    initComponents();
  }

  /**
   * init componentes: inicializa os componentes, setando as suas posições e
   * atributos
   *
   * @param sem parametros
   */
  private void initComponents() {
    setSize(465, 570);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setLayout(null);

    JTextArea textArea;

    textArea = new JTextArea(" ===================  SOBRE OS AUTORES  =====================\n"
            + "AUTORES: Matheus Thiago Marques Barbosa(201310461)\n"
            + "                    Yan Kaic Antunes da Silva \n"
            + "PERÍODO: 2013.1 \n"
            + "UNIVERSIDADE ESTADUAL DO SUDOESTE DA BAHIA \n"
            + "Campus Vitória da Conquista\n"
            + "Inicio: 17/07/2014\n"
            + "Ultima alteracao: 27/07/2014\n"
            + "Nome: Simulador do Problema dos Trens modelado ao Mario World\n"
            + "Funcao: Simular a concorrencia e os problemas entre processos dos trens em uma "
            + "malha compartilhada "
            + "===========================================================");
    textArea.setBounds(10, 10, 425, 450);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    add(textArea);

    exitButton = new JButton("Sair");
    //exitButton.addActionListener(new AcaoSobre(this));
    exitButton.setBounds(getWidth() - 130, getHeight() - 70, 120, 20);
    add(exitButton);

  }
}
