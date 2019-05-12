/******************************************************************************
  * 
  * COMP 1406, Assignment 9
  * Richard Xia, 101007519, 12/05/2016 
  *
  *  Compilation:  javac FlagGetter.java
  *  Execution:    java CaptureTheFlag
  * 
  *  Class FlagGetter implements the behavior of a FlagGetter.
  *  
  *  A FlagGetter will find the flag of the opposing team. The player should stop moving when it calls the pickUpFlag() 
  *  method andthat method returns true. 
  *
  */

import java.util.ArrayList;


public class FlagGetter extends Player {
  

  /**
   * @override
   */  
  public void play(Field f){
    
    if (!f.gameStillRunning())
    {
      return;
    }
    
    if (isInJail())
    {      
      return;
    }
    if (hasFlag() && f.winGame(this)){
      // set speed to zero for flag and FlagGetter
      updateSpeedForFlagGetterAndFlag(f, 0, 0);
      System.out.println("##winGame## team: " + this.getTeam() + "; id=" + this.id +"; name=" 
                           + this.getName() + ", number=" + this.getNumber());
      return;
    }
    
    // if other seeker in my team has flag, then this seeker behaves as a random walker
    // it will attemp to pick up flag only if no teammate has flag
    int team = getTeamId(f);
   if (!hasFlag() && team != -1)
   {
     boolean otherPlayerHasFlag = false;
     ArrayList<Entity> list;
     if (team == 1)
     {
       list = f.getTeam1();
     } else {
       list = f.getTeam2();
     }
     
     for (Entity en : list)
     {
        if (((Player) en).hasFlag() && en.id != this.id)
        {
          otherPlayerHasFlag = true;
          break;
        }
     }

     if (otherPlayerHasFlag)
     {
       behaveAsDefaultRandomWalker(f);
       return; 
     }     
   }
    
    //  The player should stop moving when it calls the pickUpFlag() 
    //  method and that method returns true. 
    if (f.pickUpFlag(this))
    {
      this.speedX = 0;
      this.speedY = 0;
      if (!hasFlag())
      {
        setHasFlag(true);
      }
      else 
      {
        setHasFlag(true);
      }
    }     
    
    int flagX = 0;
    int flagY = 0;    
    
    // find flag position of opponent team
    if (team == 1)
    {
      if (hasFlag())
      {
        flagX = f.getBase1Position()[0];
        flagY = f.getBase1Position()[1];
      }
      else
      {
        flagX = f.getFlag2Position()[0];
        flagY = f.getFlag2Position()[1];
      }
    }
    else if (team == 2)
    {
      if (hasFlag())
      {
        flagX = f.getBase2Position()[0];
        flagY = f.getBase2Position()[1];        
      } else {
        flagX = f.getFlag1Position()[0];
        flagY = f.getFlag1Position()[1];        
      }
    }
    
    if (team != -1)
    { 
      // The algorithm to chase the target player
      // 1) find delta X and delta Y to reach target from this player
      // 2) set speed X and Y of this player according to the ratio of delta X and Y
      int deltaX = flagX - getX();
      int deltaY = flagY - getY();
      double speedBase = Math.abs(Math.random()*4-2);  
      double newSpeedX = speedBase;
      if (deltaY != 0)
      {
        newSpeedX = speedBase * ((int) deltaX/deltaY);
      }                
      double newSpeedY = speedBase;
      
      // direction
      if (flagX < getX())
      {
        newSpeedX *= (-1);
      }        
      if (flagY < getY())
      {
        newSpeedY *= (-1);
      }     
            
      // update speed
      updateSpeedForFlagGetterAndFlag(f, newSpeedX, newSpeedY);
    }      
  }
  
  /**
   *  update speed for flag getter and flag
   * 
   * @param field is the current playing field
   * @param newSpeedX speedX
   * @param newSpeedY speedY
   */
  protected void updateSpeedForFlagGetterAndFlag(Field f, double newSpeedX, double newSpeedY)
  {
      double spX = newSpeedX;
      double spY = newSpeedY;
      
      // boundary bounce behavior
      if (this.x < f.minX) {
        spX = Math.abs(newSpeedX);
      }
      if (this.x > f.maxX) {
        spX = Math.abs(newSpeedX) * (-1);
      }
      if (this.y < f.minY) {
        spY = Math.abs(newSpeedY);
      }
      if (this.y > f.maxY) {
        spY = Math.abs(newSpeedY) * (-1);
      }     

      this.speedX = spX;
      this.speedY=  spY;
      
      // set flag to the seeker's speed
      if (hasFlag())
      {
        Flag otherFlag = f.getOtherTeamFlag(this);
        otherFlag.setSpeedX(spX, otherFlag.getId());
        otherFlag.setSpeedY(spY, otherFlag.getId());
      }       
  }   
  
  @Override
  public void update(Field field){}
  public FlagGetter(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = Math.random()*4-2;
    this.speedY = Math.random()*4-2;
  }
  
}