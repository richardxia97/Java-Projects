
import java.util.ArrayList;

public abstract class Player extends Entity{
 
 
  /** this player's team name */
  protected String team;
  
  /** this player's name */
  protected String name;
  
  /** this player's number */
  protected int number;
  
  /** indicates whether the player's in jail */
  protected boolean inJail;
  
  /** indicates whether the player carries flag */
  protected boolean hasFlag;
  
   
  /** gets this player's team name
    * 
    * @return the team name that this player is on
    */
  public final String getTeam(){ return this.team; }
  
  
  /** gets this player's name
    * 
    * @return the name of this player
    */
  public final String getName(){ return this.name; }

  /** gets this player's number
    * 
    * @return the number of this player
    */
  public final int getNumber(){ return this.number; }

  
  /** creates a player with specified symbol at specified position 
    * 
    * @param f is the field the player will be playing on
    * @param side is the side of the field the player will play on
    * @param name is this name of the player
    * @param number is this player's number 
    * @param team is this player's team name
    * @param symbol is a character (char) representation of this player
    * @param x is the x-coordinate of this player
    * @param y is the y-coordinate of this player
    */
  public Player(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(symbol, x, y);
    this.name = name;
    this.number = number;
    this.team = team;
    this.inJail = false;
    this.hasFlag = false;
    f.registerPlayer(this, this.id, side);  // register the player on the field
  }
  
  /** attempt to catch an opponent player
    * 
    * @param opponent a player on the opponent's team that you are trying to catch
    * @param field is the field the game is being played on
    * @return true if this player successfully catches the opponent player, false otherwise
    */
  public final boolean catchOpponent(Player opponent, Field field){
    return field.catchOpponent(this, opponent);
  }
  


  /** Informs this player that they have been caught by another player. 
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param opponent is the player that caught this player  
    * @param id should be the id of the this player
    */
  public void beenCaught(Player opponent, int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call beenCaught ");
    }
    
  }
    
  /** attempt to free a teammate from jail
    * 
    * @param teammate is another player on this player's team
    * @param field is the field the game is being played on
    * @return true if the <code>teammate</code> is successfully freed from jail, false otherwise 
    */
  public final boolean freeTeammate(Player teammate, Field field){
    return field.freeTeammate(this, teammate);
  }
    
  /** Informs this player that they have been freed by a teammate 
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param teammate is the player that caught this player  
    * @param id should be the id of the this player
    */
  public void hasBeenFreed(Player teammate, int id){
    /* check if the caller knows this entity's id */
    if( this.id != id){
      throw new SecurityException("Unauthorized attempt to call hasBeenFreed ");
    }
    
  }
  
  
  
  /** attempt to pick up the opponent's flag
    * 
    * @param field is the field the game is being played on
    * @return true if this player successfully picked up the opponent's flag, false otherwise 
    */
  public final boolean pickUpFlag(Field field){
    return field.pickUpFlag(this);
  }
  
  
  /** Informs this player that they have picked up the flag
    * <p>
    * This method should only be called from with the Field class.  
    * 
    * @param id should be the id of the this player
    */
  public void hasPickedUpFlag(int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call hasPickedUpFlag ");
    }
    
  }
  
  /** Informs this player that they have dropped the flag
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param id should be the id of the this player
    */
  public void hasDroppedFlag(int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call hasDroppedFlag ");
    }
    
  }
  
  
  /** attempt to win the game
    * 
    * @param field is the field the game is being played on
    * @return true if this player successfully brings the opponent's flag back to this player's base, false otherwise 
    */
  public final boolean winGame(Field field){
    return field.winGame(this);
  }
  
   /** 
    * Gets whether the player is in jail 
    * 
    * @return true if the player is in jail, otherwise false 
    */
  public boolean isInJail (){
    return this.inJail;
  }

   /** 
    *  Sets whether the player is in jail
    * 
    * @param jail boolean
    * 
    * @return N/A
    */
  public void setInJail (boolean jail){
    this.inJail = jail;
  }

   /** 
    * Gets whether the player is carrying the flag
    * 
    * @return true if the player is carrying the flag, otherwise false 
    */

  public boolean hasFlag (){
    return this.hasFlag;
  }

   /** 
    * Set whether the player is carrying the flag
    * 
    * @param flag indicates whether the player is carrying the flag
    */
  public void setHasFlag (boolean flag){
    this.hasFlag = flag;
  }

   /** 
    *  Returns the distance between two players devided by ARMS_LENGTH, ie distance in term of ARMS_LENGTH.
    * 
    * @param a Player A 
    * @param b Player B 
    * 
    * @return the distance between two players devided by ARMS_LENGTH, ie distance in term of ARMS_LENGTH.
    */
  public double getDistanceInArmLength(Player a, Player b, Field f)
  {
    return (Math.hypot( a.getX() - b.getX(), a.getY() - b.getY() ) / f.ARMS_LENGTH);
  }

   /** 
    * Get team id (1 or 2) of this player from Field team 
    * 
    * <p>
    * Traverse over the teams of the field. If there is id matched to this player, then 
    * it belongs to the team.
    * 
    * @param field is the current playing field
    * 
    * @return 1 or 2 if team is found, -1 if not found.
    */
  public int getTeamId(Field f)    
  {
     int team = -1;     
     ArrayList<Entity> list = f.getTeam1();
     for (Entity en : list)
     {
        if (en.id == this.id)
        {
          team = 1;
          break;
        }
     }
      
     if (team == -1)
     {
        list = f.getTeam2();
        for (Entity en : list)
        {
           if (en.id == this.id)
           {
              team = 2;
              break;
            }
         }
     }    
     return team;
  }

   /** 
    * behaves as default random walker: 
    * reset as random walker speed in the region [-2, 2]
    * bounce when reaching the boundary
    * 
    * This should be used in play() method only.
    * 
    * 
    * @param field is the current playing field
    * 
    * @return N/A
    * 
    */
  protected void behaveAsDefaultRandomWalker(Field f)
  {
    
    // reset as random walker speed in the region [-2, 2]
    if (this.speedX == 0 || this.speedY == 0 || Math.abs(this.speedX) > 2 || Math.abs(this.speedY) > 2)
    {
      this.speedX = Math.random()*4-2;;
      this.speedY = Math.random()*4-2;;
    }
    
    // bounce behavior
    if (this.x < f.minX) {
      speedX *= -1;
    }
    if (this.x > f.maxX) {
      speedX *= -1;
    }
    if (this.y < f.minY) {
      speedY *= -1;
    }
    if (this.y > f.maxY) {
      speedY *= -1;
    }     
 }

}