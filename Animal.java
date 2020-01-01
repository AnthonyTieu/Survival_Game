public class Animal
{
  int survivorSpeed; //The speed of travel of the player (running or walking)
  int reactionChanceSpeed1 = 0; //Chance that the animal would react, given that the player is walking
  int reactionChanceSpeed2 = 0; //Chance that the animal would react, given that the player is running
  boolean reaction = false; //Stores whether animal reacts
  
  //Constructor
  public Animal (int survivorSpeed) //Takes in the speed of the player
  {
    this.survivorSpeed = survivorSpeed; //Store the speed of the player
  }
  
  //Interaction method
  public boolean interact()
  {
    int chance = (int)(Math.random() * 10) + 1; //Randomly generates a number from 1-10
    
    if(survivorSpeed == 1) //If the player is walking...
    {
      if(chance > reactionChanceSpeed1) //If the random number is greater that the number required for a reaction (given player is walking)...
      {
        reaction = false; //It does not react
      }
      else //Otherwise...
      {
        reaction = true; //It reacts
      }
    }
    else //If the player is running...
    {
      if(chance > reactionChanceSpeed2) //If the random number is greater that the number required for a reaction (given player is runnning)...
      {
        reaction = false; //It does not react
      }
      else //Otherwise...
      {
        reaction = true; //It reacts
      }
    }
    return reaction; //Return whether the animal reacts
  }
}
      