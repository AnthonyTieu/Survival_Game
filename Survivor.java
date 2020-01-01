/*FUTURE NOTE TO SELF: Since the plants and water do not move, they have their own 2D array, initailized once. 
 *The deer and wolves move, hence they have their own 2D array that is initialized each day
 *(the 2D arrays are maps of the land)*/

import java.util.*;

public class Survivor
{
  public static void main(String [] args)
  {
    //DECLARATION AND INITIALIZATION
    Scanner input = new Scanner(System.in); //Keyboard scanner
    
    //LAND
    String [][] land = new String[8][8]; //2D Array that stores the position of unmoving objects (plants and water)
    String square; //The letter-number coordinate of the Square that player chooses
    int squareX; //X-Coordinate of square on the map
    int squareY; //Y-Coordinate of the square on the map
    String squareContentLand; //The contents of the square (plants and water)
    String squareContentAnimal; //The contents of the square (deers and wolves)
    
    //CONTENTS OF THE LAND
    int numBerries = (int)(Math.random() * 10) + 20; //Number of berry bushes
    int numDeer = (int)(Math.random() * 5) + 7; //The number of deer, used to spawn the same number of deer each day
    int numWater = (int)(Math.random() * 5) + 5; //Number of bodies of water
    int numWolf = (int)(Math.random() * 3) + 15; //The number of wolves, used to spawn the same number of wolves each day
    int totalEternal = numBerries + numWater; //Total number of berry bushes and water bodies (contents of the land that do not change position)
    int totalEphemeral = numDeer + numWolf; //Total number of animals (contents of the land that do change position)
    
    //STATS
    int food = 100; //Food bar
    int water = 100; //Water bar
    int berryStorage = 0; //Berries in stock
    int meatStorage = 0; //Meat in stock
    int waterStorage = 0; //Water in stock
    
    //PLAYER INPUT
    int choice; //Decisions (typically expressed as either 1 or 2)
    int speed; //Player running or walking
    int travelCount; //Number of squares that the player can visit, given their speed of travel
    boolean eat = false; //Stores whether the player ate that day
    boolean drink = false; //Stores whether the player drank that day
    
    //GAME SETUP
    land = gameSetup(totalEternal, numBerries, numWater); //Randomly places berries and water on the map
    //checkSetup(land, numBerries, numWater);
    
    //EXPOSITION
    exposition(); //Describes the premise and rules of the game
    
    //START GAME
    entire: for(int count = 1; count <= 15; count++) //For 15 days...
    {
      //DAILY VARIABLE RESET
      String [][] animals = new String[8][8]; //2D Array (map of the land with animals)
      eat = false; //Reset the eat detector
      drink = false; //Reset the drink detector
      
      //ANIMAL POSITION RESET
      animals = dailyReset(totalEphemeral, numDeer, numWolf); //Randomly place deer and wolves on the map (change positions each day)
      //checkReset(animals);
      
      //START OF DAY
      System.out.println("\nDAY " + count); //Indicate day number
      
      //BREAKFAST
      if((berryStorage + meatStorage + waterStorage) > 0) //If there is food to eat or water to drink...
      {
        System.out.println("Would you like to eat breakfast? Enter \"1\" for yes or \"2\" for no."); //Ask if player wants to eat
        choice = input.nextInt(); //Store their choice
        
        while(choice == 1 && (berryStorage + meatStorage + waterStorage) > 0) //Loop while their choice is "yes" and they still have food...
        {
          System.out.println("What would you like to eat/drink? Enter \"1\" for berries, \"2\" for meat, and \"3\" for water.");
          //Ask what the player would like to eat
          choice = input.nextInt(); //Store their choice
          
          if(choice == 1) //If they choose berries...
          {
            if(berryStorage > 0) //If there are berries in stock...
            {
              System.out.println("You have eaten some berries."); //Indicate that they have eaten some berries
              eat = true; //Set eat to true (they have eaten today)
              berryStorage--; //Subtract 1 from the berry storage
              food += 5; //Add 5 to food
              water += 5; //Add 5 to water
            }
            else //If there are no berries in stock...
            {
              System.out.println("Unfortunately, you do not have any berries in stock."); //Indicate that they do not have berries
            }
          }
          
          else if(choice == 2) //If they choose meat...
          {
            if(meatStorage > 0) //If there is meat in stock...
            {
              System.out.println("You have eaten some meat."); //Indicate that they have eaten some meat
              eat = true; //Set eat to true (they have eaten today)
              meatStorage--; //Subtract 1 from the meat storage 
              food += 20; //Add 20 to food
            }
            else //If there is no meat in stock...
            {
              System.out.println("Unfortunately, you do not have any meat in stock."); //Indicate that they do not have meat
            }
          }
          
          if(choice == 3) //If they choose water...
          {
            if(waterStorage > 0)
            {
              System.out.println("You have drank some water."); //Indicate that they have drank some water
              drink = true; //Set drink to true (they drank today)
              waterStorage--; //Subtract 1 from the water storage
              water += 20; //Add 20 to water
            }
            else //If there is no water in stock...
            {
              System.out.println("Unfortunately, you do not have any water in reserve."); //Indicate that they do not have water
            }
          }
          
          if((berryStorage + meatStorage + waterStorage) > 0) //If there is food to eat or water to drink...
          {
            System.out.println("Anything else? Enter \"1\" for yes or \"2\" for no."); //Ask if they want to eat or drink more
            choice = input.nextInt(); //Store their choice
          }
        }
      }
      
      //HUNTING AND SCAVENGING
      System.out.println("Will you walk or run? Enter \"1\" to walk or \"2\" to run."); //Prompt player to indicate speed of travel
      speed = input.nextInt(); //Store speed of travel
      
      while(speed != 1 && speed != 2) //While speed is invalid...
      {
        System.out.println("Please enter \"1\" or \"2.\""); //Prompt player to re-enter a valid speed
        speed = input.nextInt(); //Store the speed
      }
      
      travelCount = speed; //In this case, when running, the player can go to 2 places, when walking, they can only go to one
      
      while(travelCount > 0) //While the player still can still go to another place...
      {
        System.out.println("Please enter the square you wish to travel to."); //Ask where the would like to go
        square = input.next().toUpperCase(); //Store their choice
        squareX = ((int)(square.charAt(0)))-65; //Convert the letter into a number
        squareY = (Integer.parseInt(square.substring(1, 2))) - 1; //Get the other number
        squareContentLand = land[squareX][squareY]; //Uses these as coordinates on the map (plants and water)
        squareContentAnimal = animals[squareX][squareY]; //Uses these as coordinates on the other map (deer and wolves)
        
        if(squareContentLand == null && squareContentAnimal == null) //If there is nothing on the square...
        {
          System.out.println("Nothing Here."); //Say that nothing is there
        }
        if(squareContentLand != null) //If the square contains something...
        {
          //BERRIES
          if(squareContentLand.contains("Berries")) //If the square contains berries...
          {
            System.out.println("You have found some berries!"); //Say that the player has found berries
            System.out.println("Would you like to eat or store them? Enter \"1\" to eat or \"2\" to store."); //Ask whether they would like to eat or store them
            choice = input.nextInt(); //Store their choice
            
            if(choice == 1) //If they choose to eat...
            {
              System.out.println("You have chosen to eat the berries"); //Indicate that they have chosen to eat
              eat = true; //Set eat to true (they have eaten today)
              food += 5; //Add 5 to food
              water +=5; //Add 5 to water
            }
            else if(choice == 2) //If they choose to store...
            {
              System.out.println("You have chosen to store the berries"); //Indicate that they have chosen to store
              berryStorage += 1; //Add 1 to the berry storage
            }
          }
          //WATER
          if(squareContentLand.contains("Water")) //If the square contains water...
          {
            System.out.println("You have found water!"); //Sy that the player has found water
            System.out.println("Would you like to drink or store it? Enter \"1\" to drink or \"2\" to store."); //Ask whether they would like to drink or store it
            choice = input.nextInt(); //Store their choice
            
            if(choice == 1) //If they choose to drink...
            {
              System.out.println("You have chosen to drink the water"); //Indicate that they have chosen to drink
              drink = true; //Set drink to true (they have drunk today)
              water += 20; //Add 20 to water
            }
            else if(choice == 2) //If they choose to store...
            {
              System.out.println("You have chosen to store the water"); //Indicate that they have chosen to store 
              waterStorage += 1; //Add 1 to the water storage
            }
          }
        }
        if(squareContentAnimal != null) //If the square contains animals...
        {
          //DEER
          if(squareContentAnimal.contains("Deer")) //If the square contains a deer...
          {
            Deer d = new Deer(speed); //Create an instance of the deer
            boolean deerReaction = d.interact(); //Determine how the deer interacts
            
            System.out.println("You have found a deer!"); //Say that they have found a deer
            if(deerReaction == true) //If the deer reacts...
            {
              System.out.println("Unfortunately, you spooked the deer, and it ran away."); //Say that the deer has run off
            }
            else //Otherwise...
            {
              System.out.println("You have successfully caught the deer!"); //Say that they have caught the deer
              System.out.println("Would you like to eat or store the meat? Enter \"1\" to eat or \"2\" to store."); //Ask whether they would like to eat or store it
              choice = input.nextInt(); //Store their choice
              
              if(choice == 1) //If they choose to eat
              {
                System.out.println("You have chosen to eat the meat"); //Indicate that they have chosen to eat
                eat = true; //Set eat to true (they have eaten today)
                food += 20; //Add 20 to food
              }
              else if(choice == 2) //If the choose to store...
              {
                System.out.println("You have chosen to store the meat"); //Indicate that thay have chosen to store
                meatStorage += 1; //Add 1 to the meat storage
              }
            }
          }
          //WOLF
          if(squareContentAnimal.contains("Wolf")) //If the square contains a wolf...
          {
            Wolf w = new Wolf(speed); //Creat and instance of the wolf
            boolean wolfReaction = w.interact(); //Determine how the wolf reacts
            
            System.out.println("A wolf has found you!"); //Say that a wolf has found them
            if(wolfReaction == true) //If the wolf reacts...
            {
              System.out.println("The wolf has attacked!"); //Say that the wolf has attacked
              food -= 30; //Remove 30 from food
              water -= 30; //Remove 30 from water
            }
            else //Otherwise...
            {
              System.out.println("The wolf has ignored you! Today is your lucky day."); //Say that the wolf has ignored them
            }
          }  
        }
        travelCount--; //Indicate that the player has travelled to one square
      }
      //END OF THE DAY
      food = hunger(food, eat); //Set value for food bar
      water = thirst(water, drink); //Set value for water bar
      
      //Print the food and water bar
      System.out.println("Your food bar is at: " + food + "\tYour water bar is at: " + water);
      
      if(food == 0 || water == 0) //If food or water is equal to 0...
      {
        if(food == 0) //If food is equal to 0...
        {
          System.out.println("You have died from hunger!"); //Say that they have dies of hunger
        }
        if(water == 0) //If water is equal to 0...
        {
          System.out.println("You have died from thirst!"); //Say that they have died of thirst
        }
        input.close(); //Close input
        break entire; //Break the loop
      }
      else //If food and water is not zero...
      {
        //Print the stocked food
        System.out.println("Berries: " + berryStorage + "     Meat: " + meatStorage + "     Water: " + waterStorage);
      }
    }
    System.out.println("\nYou have successfully completed the reparations of your car! You have survived!");
    //Say that they have survived
  } 
  
  //*****************
  //GAME SETUP METHOD
  //*****************
  public static String[][] gameSetup(int totalEternal, int numBerries, int numWater)
  {
    String [][] land = new String[8][8];
    for(int setupCount = 0; setupCount < totalEternal; setupCount++) //For the total number of berry bushes and water bodies...
    {
      int x = (int)(Math.random() * 8); //Generate a random x-value
      int y = (int)(Math.random() * 8); //Generate a random y-value
      
      if(numBerries > 0) //As long as there are still berry bushes to add to the map...
      {
        if(land[x][y] == null) //If the value of the coordinate is "null"
        {
          land[x][y] = "Berries "; //Add the berries to the coordinate (replace "null" value in array)
          numBerries--; //Subtract the from the number of berry bushes that need to be placed
        }
        else //Otherwise, if the square already has berries...
        {
          setupCount--; //Subtract 1 from the loop counter (this effectively pretends that the "failed" loop never occured)
          //By "failed," I mean that a duplicate was generated
        }
      }
      else if(numWater > 0) //As long as there are still water bodies to add to the map...
      {
        if(land[x][y] != null) //If the value of the coordinate is not "null"
        {
          if(!land[x][y].contains("Water")) //If the square does not already have water...
          {
            land[x][y] += "Water "; //Add water to the coordinate
            numWater--; //Subtract from the number of water bodies that need to be placed
          }
          else //Otherwise, if the square already has water...
          {
            setupCount--; //Subtract 1 from the loop counter (this effectively pretends that the "failed" loop never occured)
            //By "failed," I mean that a duplicate was generated
          }
        }
        else //Otherwise...
        {
          land[x][y] = "Water "; //Add water to the coordinate (replace "null" value in array)
          numWater--; //Subtract from the number of water bodies that need to be placed
        }
      }
    }
    return land; //Return 2d array
  }
  
  //******************
  //CHECK SETUP METHOD
  //******************
  public static void checkSetup(String[][] land, int numBerries, int numWater)
  {
    System.out.println(numBerries + " " + numWater);
    System.out.println(Arrays.toString(land[0]));
    System.out.println(Arrays.toString(land[1]));
    System.out.println(Arrays.toString(land[2]));
    System.out.println(Arrays.toString(land[3]));
    System.out.println(Arrays.toString(land[4]));
    System.out.println(Arrays.toString(land[5]));
    System.out.println(Arrays.toString(land[6]));
    System.out.println(Arrays.toString(land[7]));
  }
  
  //************************
  //CHECK DAILY RESET METHOD
  //************************
  public static void checkReset(String[][] animals)
  {
    System.out.println(Arrays.toString(animals[0]));
    System.out.println(Arrays.toString(animals[1]));
    System.out.println(Arrays.toString(animals[2]));
    System.out.println(Arrays.toString(animals[3]));
    System.out.println(Arrays.toString(animals[4]));
    System.out.println(Arrays.toString(animals[5]));
    System.out.println(Arrays.toString(animals[6]));
    System.out.println(Arrays.toString(animals[7]));
  }
  
  //*****************
  //EXPOSITION METHOD
  //*****************
  public static void exposition()
  {
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome Survivor to the Game of Survival, where your goal is to Survive!");
    System.out.println("\nLast night while driving through the forest, you car broke down. The damage is bad, and you are stranded.");
    System.out.println("You estimate that it will take around 15 days to fix, during this time,\nyou will need food and water (your car will be your shelter");
    System.out.println("Please press ANY KEY and ENTER when you are ready to continue.");
    input.next();
    
    System.out.println("\nIn this game, since the land is so vast, it is divided into an 8 by 8 board--like chess.");
    System.out.println("Every square of the board is designated a letter and a number (ex. E5, B7, A2)");
    System.out.println("Possible values span from A to H, and 1 to 8.");
    System.out.println("Please press ANY KEY and ENTER when you are ready to continue.");
    input.next();
    
    System.out.println("\nResources such as animals, plants and water are scatter across this board.");
    System.out.println("Naturally, the positions of plants and water do not change day by day, they are a reliable source once found.");
    System.out.println("However, animals do move. You must also be aware that not all animals you encounter will be your food, to some, you will be their food.");
    System.out.println("Please press ANY KEY and ENTER when you are ready to continue.");
    input.next();
    
    System.out.println("\nEach day, you will go hunting and scavenging at particular \"squares\" of the land (you will choose which ones).");
    System.out.println("Since you must also fix your car, you are limited on how many places you can search.");
    System.out.println("   -You may choose to walk, in which case, you may only vist one square that day");
    System.out.println("   -You may choose to run, in which case, you may visit two squares that day.");
    System.out.println("    However, if you do run, there is an increased chance that animals will hear you; prey may run away, and predators may be more aggressive.");
    System.out.println("Please press ANY KEY and ENTER when you are ready to continue.");
    input.next();
    
    System.out.println("\nYour hunger and thirst bars starts at 100.");
    System.out.println("Each day without food, the hunger bar will go down by 15.");
    System.out.println("Each day without water, the thirst bar will go down by 15.");
    System.out.println("If you are attacked by an animal, the both bars will go down 30.");
    System.out.println("Eating meat will restore the hunger bar by 20.");
    System.out.println("Eating berries will restore both bars by 5.");
    System.out.println("Drinking water will restore the thirst bar by 20.");
    System.out.println("Please press ANY KEY and ENTER when you are ready to continue.");
    input.next();
    
    System.out.println("\nGood Luck Survivor!");
    //input.close();
  }
  
  //***********
  //DAILY RESET
  //***********
  public static String[][] dailyReset(int totalEphemeral, int numDeer, int numWolf)
  {
    //CHANGE THE POSITION OF THE DEER AND THE WOLVES
    String [][] animals = new String[8][8];
    for(int setupCountAnimals = 0; setupCountAnimals < totalEphemeral; setupCountAnimals++) //For the number of deer and wolves...
    {
      int x2 = (int)(Math.random() * 8); //Generate a random x-value
      int y2 = (int)(Math.random() * 8); //Generate a random y-value
      
      if(numDeer > 0) //As long as there are still deer to add...
      {
        animals[x2][y2] = "Deer "; //Add a deer on the map
        numDeer--; //Subtract a deer from the number of deer left to add
      }
      else if(numWolf > 0) //As long as there are still wolves to add...
      {
        if(animals[x2][y2] != null) //If the value of the coordinate is not "null"
        {
          animals[x2][y2] += "Wolf "; //Add a wolf to the map 
          numWolf--; //Subtract a wolf from the number of wolves left to add
        }
        else //Otherwise...
        {
          animals[x2][y2] = "Wolf "; //Add a wolf to the map (replace "null" value in array)
          numWolf--; //Subtract a wolf from the number of wolves left to add
        }
      }
    }
    return animals; //Return the 2d array
  }
  
  //*************
  //HUNGER METHOD
  //*************
  public static int hunger(int food, boolean eat)
  {
    if(eat == false) //If they have not eaten...
    {
      food -= 15; //Subtract 10 from food
    }
    if(food > 100) //If food is greater than 100...
    {
      food = 100; //Set it to 100
    }
    else if(food <= 0) //If food is less than or equal to 0...
    {
      food = 0; //Set food to 0
    }
    return food; //Return food
  }
  
  //*************
  //THIRST METHOD
  //*************
  public static int thirst(int water, boolean drink)
  {
    if(drink == false) //If they have not drank...
    {
      water -= 15; //Subtract 10 from water
    }
    if(water > 100) //If water is greater than 100...
    {
      water = 100; //Set it to 100
    }
    else if(water <= 0) //If water is less than or equal to 0...
    {
      water = 0; //Set it to 0
    }
    return water; //Return water
  }
}