##########################
New Classes
##########################
FlagGetter.java
Freer.java
Chaser.java
Team.java

My new class: FlagGetterWithoutRandomWalker
  // The normal FlagGetter class checks whether another team member has flag. If so, behaves as random walker 
  // and boundary bounce. FlagGetterWithoutRandomWalker does not implement this behavior. It follows the flag  
  // even if another team member had the flag.


##########################
modified classes
##########################

-------------------------
Entity.java: 
-------------------------
Add acccess method:
public int getId()

-------------------------
Player.java: 
-------------------------
Add data members:
  protected boolean inJail;  
  protected boolean hasFlag;

Add access methods:
public boolean isInJail ()
public void setInJail (boolean jail)   
public boolean hasFlag () 
public void setHasFlag (boolean flag)

public double getDistanceInArmLength(Player a, Player b, Field f)
public int getTeamId(Field f)    
protected void behaveAsDefaultRandomWalker(Field f)

-------------------------
Field.java: 
-------------------------
modified:
 public boolean catchOpponent(Player a, Player b)
 public boolean freeTeammate(Player a, Player b)
 public boolean pickUpFlag(Player a)
 public boolean winGame(Player a)

add new functions:
public boolean isInOppositeTerritory(Player player)
public Jail getTeamJail(Player player)
public Jail getOtherJail(Player player)
public Flag getOtherTeamFlag(Player player)

-------------------------
Jail.java
-------------------------
Add member variable:
   /** holds all captured opponent players*/
  ArrayList<Player> captured = new ArrayList<Player>();

Add new member function:
public ArrayList<Player> getCaptured()   

