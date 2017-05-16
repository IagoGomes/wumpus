package Animacao;

/* *********************************************************************************
* Autor: Cleiton Galvao Santana / Ícaro Pacheco
* Inicio: 19-11-2011
* Ultima alteracao: 28-11-2011
* Nome: Cleiton Galvao Santana/200910563  Icaro Pacheco Lopes de Oliveira/200818323
* Funcao: Execucao de Som
*********************************************************************************** */

//import static View.JanelaWumpus.somDaMensagem;
import Controle.Constantes;
import java.io.File;   
import java.io.IOException;   
import java.net.URI;
import javax.sound.sampled.AudioFormat;   
import javax.sound.sampled.AudioInputStream;   
import javax.sound.sampled.AudioSystem;   
import javax.sound.sampled.DataLine;      
import javax.sound.sampled.LineUnavailableException;   
import javax.sound.sampled.SourceDataLine;   
import javax.sound.sampled.UnsupportedAudioFileException;
  
public class ExecutarSom extends Thread {   

    private String enderecoDoArquivo;
    private SourceDataLine auline = null;  
  
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    
    
    public ExecutarSom(String enderecoDoWav) 
    {   
        enderecoDoArquivo = enderecoDoWav;         
    }   

    public void run() 
    {   
        do
        {
            File arquivoDeSom = new File(enderecoDoArquivo);  
            if (!arquivoDeSom.exists()) {   
                System.err.println("Arquivo Wave não encontrado: " + enderecoDoArquivo);  
                return;  
            }   

            AudioInputStream audioInputStream = null;  
            try {   
                audioInputStream = AudioSystem.getAudioInputStream(arquivoDeSom);  
            } catch (UnsupportedAudioFileException e1) {   
                e1.printStackTrace();  
                return;  
            } catch (IOException e1) {   
                e1.printStackTrace();  
                return;  
            }   

            AudioFormat format = audioInputStream.getFormat();  
            //SourceDataLine auline = null;  
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);  

            try {   
                auline = (SourceDataLine) AudioSystem.getLine(info);  
                auline.open(format);  
            } catch (LineUnavailableException e) {   
                e.printStackTrace();  
                return;  
            } catch (Exception e) {   
                e.printStackTrace();  
                return;  
            }   

            
            auline.start();  
            int nBytesRead = 0;  
            byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];  

            try 
            {   
                while (nBytesRead != -1) 
                {   
                    nBytesRead = audioInputStream.read(abData, 0, abData.length);  
                    if (nBytesRead >= 0)   
                        auline.write(abData, 0, nBytesRead);  
                }   
            } catch (IOException e) {   
                e.printStackTrace();  
                return;  
            } finally {   
//                auline.drain();
//                auline.flush();
//                auline.close();            
            }   

            //interrupt();//Apos terminar o codigo interrompe a thread
        } while(Constantes.getTemSomParaTocar());
        auline.drain();
        auline.flush();
        auline.close();
        Constantes.setTemSomParaTocar(true);//Como já vai sair vai ficar habilitado para repetição, pq chega em música geral antes de chegar em música q n repete
        this.stop();//Se não tiver mais som para tocar é pq deve finalizar a Thread
        
    }//Fim 
}   

