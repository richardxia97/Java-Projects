/******************************************************************************
  * 
  * COMP 1406, Assignment 8
  * Richard Xia, 101007519, 12/06/2016 
  *
  *  Compilation:  javac Team.java
  *  Execution:    java CaptureTheFlag
  * 
  *  Create two team in CaptureTheFlag to start game. For example
  *  Team blues = new Team(f, 3, 1, 1, 0);
  *  Team reds = new Team(f, 3, 1, 1, 0);
  *
  *  
  *  Class Team create and register the players of the team.
  *  
  */

public class Team {
  
  /** static instanceNum which is incremented by 1 in class constructor   */
  private static int instanceNum = 0;
  
  /** team instance id*/
  private int instanceId;
  
  
   /** create and register the players of the team 
    * 
    * @param f game field
    * @param numFlagGetters  The number of FlagGetters
    * @param numCatchers  The number of Catchers
    * @param numFreeers  The number of Freers
    * @param numYourPlayer  The number of self defined class player
    * 
    * @return N/A
    */
  protected void init(Field f,  int numFlagGetters, int numCatchers,
              int numFreeers, int numYourPlayer)
  {
     int totalNum = 0;
     int id;
     String teamName = "TEAM " + instanceId;     
     
     // player start position
     double xPos, yPos;
     if (instanceId % 2 == 0)
     {           
       xPos = Math.random()*400+410;
       yPos = Math.random()*500+10;
     } else {
       xPos = Math.random()*400+10;
       yPos = Math.random()*500+10;
     }

     for (int i = 0; i < numFlagGetters; i++)
     {
         ++totalNum;
         id = instanceId*100 + totalNum; // each player has an unique id, for team 1: id = 101, 102 ...
         String playerName = teamName + ": FlagGetter " + (i+1);             
         // create and register the player           
         new FlagGetter(f, instanceId, playerName, id, teamName, ' ', xPos, yPos); 
     }
     
     for (int i = 0; i < numCatchers; i++)
     {
         ++totalNum;
         id = instanceId*100 + totalNum; // each player has an unique id, for team 1: id = 101, 102 ...
         String playerName = teamName + ": Catcher " + (i+1);             
         // create and register the player           
         new Catcher(f, instanceId, playerName, id, teamName, ' ', xPos, yPos); 
     }

     for (int i = 0; i < numFreeers; i++)
     {
         ++totalNum;
         id = instanceId*100 + totalNum; // each player has an unique id, for team 1: id = 101, 102 ...
         String playerName = teamName + ": Freer " + (i+1);             
         // create and register the player           
         new Freer(f, instanceId, playerName, id, teamName, ' ', xPos, yPos); 
     }

     for (int i = 0; i < numYourPlayer; i++)
     {
         ++totalNum;
         id = instanceId*100 + totalNum; // each player has an unique id, for team 1: id = 101, 102 ...
         String playerName = teamName + ": FlagGetterWithoutRandomWalker " + (i+1);             
         // create and register the player           
         new FlagGetterWithoutRandomWalker(f, instanceId, playerName, id, teamName, ' ', xPos, yPos); 
     }     
  }
  

  /**
   * Gets team instance id 
   * 
   * @return team instance id 
   */  
  public int getInstanceId()
  {
    return instanceId;
  }
  
  /**
   * Gets the number of team instance
   * 
   * @return the number of team instance
   */  
  public int getInstanceNum()
  {
    return instanceNum;
  }
  
  /**
   * Class Constructor
   * 
   * @param f game field
   * @param numFlagGetters  The number of FlagGetters
   * @param numCatchers  The number of Catchers
   * @param numFreeers  The number of Freers
   * @param numYourPlayer  The number of self defined class player
   * 
   */
  
  public Team(Field f,  int numFlagGetters, int numCatchers,
              int numFreeers, int numYourPlayer){
    instanceNum++;
    instanceId = instanceNum;
    init(f, numFlagGetters, numCatchers, numFreeers, numYourPlayer);  }
}