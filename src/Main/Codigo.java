/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controle.Game;

/**
 *
 * @author iago
 */
public class Codigo implements Runnable{
    Game game;
    
    public Codigo(){ 
        game = new Game(this);
        game.init();
    }

    @Override
    public void run() {
      
        game.initGame();
        game.guerreiroAndar();
        game.guerreiroAndar();
        game.guerreiroAndar();
        game.guerreiroAndar();
        game.guerreiroAndar();
        game.guerreiroAndar();
        
    }
    
}
