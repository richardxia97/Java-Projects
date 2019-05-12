import java.util.ArrayList;

public class Jail extends Entity{
  
   /** holds all captured opponent players*/
  ArrayList<Player> captured = new ArrayList<Player>();
  
  /* no logic for the jail yet */
  @Override
  public void play(Field field){}
  

  /* jails does not move yet */
  @Override
  public void update(Field field){}
  


  @Override
  public boolean equals(Object o){
    if(this==o){return true;}

    if(o == null) return false;
    
    /* jails are the same if they have the same symbol */
    if(o instanceof Jail && this.getSymbol() == ((Jail)o).getSymbol()){
      return true;
    }
    
    return false;
  }

  public Jail(char symbol, int x, int y){
    super(symbol, x, y);
    speedX = speedY = 0;
  }

 public Jail(Field f, int side, char symbol, int x, int y, String ref){
    super(f, side, symbol, x, y, ref);
    speedX = speedY = 0;
  }
 
 /**
   * Gets the list of captured opponent players
   * 
   * @return the list of captured opponent players
   */  
 public ArrayList<Player> getCaptured()   
 {
   return captured;
 }

}