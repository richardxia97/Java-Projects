import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CaptureTheFlag{
  

  public static void main(String[] args){

    Field f = new Field();
    f.START_FROM_BASE = true;
    System.out.println(f.minX + "," + f.minY);


    /* --------------------------------------------- */
    /* create players in the game                    */

    Player p,q;
    
    int NUM_PLAYERS = 1;
    
    //    Team blues = new Team(f, 3, 1, 1, 0);
//    Team reds = new Team(f, 3, 1, 1, 0);


        Team blues = new Team(f, 3, 1, 1, 2);
        Team reds = new Team(f, 3, 1, 1, 2);

    
    /* ------------------------------------------- */
    /* play the game                               */

    boolean gameRunning = true;
    
    long iterations = 0;
    while( gameRunning ){
      iterations += 1;
   
      /* allow players to think about what to do and change directions */
      f.play();
      
      /* move all players */
      f.update();
      
      /* redraw all the players in their new positions */
      f.draw();
      
      
      /* give some message to display on the field */
      if(iterations < 2000){
        f.view.message("game on!");
      }else if(iterations < 4000){
        f.view.message("keeps on going...");
      }else{
        f.view.message("and going...");
      }
      
      // uncomment this if game is moving too fast , sleep for 10 ms
      try{ Thread.sleep(10); } catch(Exception e) {}
      
      gameRunning = f.gameStillRunning();
    }
    
    
  }
  
}