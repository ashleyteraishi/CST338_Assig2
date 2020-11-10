/* Ashley Teraishi
 * CST338 Software Design
 * November 10, 2020
 * CASINO PROGRAM
 */

import java.util.Scanner;
import java.lang.Math;

public class Assig2 {
	public static Scanner keyboard = new Scanner(System.in);
	
   public static int getBet() {
      int betAmount; 
      
      do {
         // prompt for user input
         System.out.println("Please enter your bet amount from 1 to 100. Press 0 to exit.");
         // check if user enters an int, then assign that to betAmount
         if(keyboard.hasNextInt()) {
            betAmount = keyboard.nextInt();
            keyboard.nextLine();
         }
         else   // else betAmount is zero and the program exits
            betAmount = 0;
      } while (betAmount > 100 || betAmount < 0);   // loop through until we get "legal" input
      return betAmount;
   }
   
   public static ThreeString pull() {
      // instantiate ThreeString object
      ThreeString thePull = new ThreeString();
      
      System.out.println("Whirrrrrr... and your pull is...");
      
      // fill object with three random strings
      thePull.setString1(randString());
      thePull.setString2(randString());
      thePull.setString3(randString());
      
      // return the object
      return thePull;
   }
   
   // helper method for pull()
   private static String randString() {
      // use Math.random to return a double between 0 and 1
      double randomNum = Math.random();
      // turn that to an int between 1 and 1000
      int randomInt = (int)(randomNum * 1000);
      // what each number triggers
      if (randomInt <= 500)   // 50% chance
         return "space";
      else if(randomInt <= 750)   // 25% chance
         return "cherries";
      else if(randomInt <= 875)   // 12.5% chance
         return "BAR";
      else   // 12.5% chance
         return "7";
   }
   
   public static int getPayMultiplier(ThreeString thePull) {
      // determine the pay multiplier
      // if "cherries", not cherries, any
      if (thePull.getString1() == "cherries" && thePull.getString2() != "cherries")
         return 5;
      // if "cherries", "cherries", not cherries
      else if (thePull.getString1() == "cherries" && thePull.getString2() == "cherries" && thePull.getString3() != "cherries")
         return 15;
      // if all "cherries"
      else if (thePull.getString1() == thePull.getString2() && thePull.getString2() == thePull.getString3() && thePull.getString1() == "cherries")
         return 30;
      // if all "BAR"
      else if (thePull.getString1() == thePull.getString2() && thePull.getString2() == thePull.getString3() && thePull.getString1() == "BAR")
         return 50;
      // if all "7"
      else if (thePull.getString1() == thePull.getString2() && thePull.getString2() == thePull.getString3() && thePull.getString1() == "7")
         return 100;
      else
         return 0;
   }
   
   public static void display(ThreeString thePull, int winnings) {
      // displays the three strings inside thePull
      System.out.println(thePull.getString1() + " " + thePull.getString2() + " " + thePull.getString3());
      // display if the user won or lost
      if (getPayMultiplier(thePull) == 0)
         System.out.println("Sorry, you lose.\n");
      else
         System.out.println("Congratulations, you win: " + winnings + "\n");
   }
   
   public static void main(String[] args) {
      ThreeString pull = new ThreeString();
      int bet, multiplier, winnings;
      
      // loop through 40 times
      for(int i = 0; i < 40; i++) {
         bet = getBet();   // get bet amount from user
         if (bet == 0) {   // if bet is 0, exit
            break;
         }
         
         pull = pull();   // pull, then get the pay multiplier
         multiplier = getPayMultiplier(pull);
               
         // calculate, save, and display winnings
         winnings = bet * multiplier;
         pull.saveWinnings(winnings);
         display(pull, winnings);
      }
      
      System.out.println("Thank you for playing at the Casino!");
      System.out.println(pull.toStringWinnings());   // display total winnings
      keyboard.close();
      System.exit(0);
   }
}

// ------------ ThreeString Class ------------
class ThreeString {
   // member variables
   private String string1, string2, string3;
   
   public static final int MAX_LEN = 20;
   public static final int MAX_PULLS = 40;
   private static int[] pullWinnings = new int[MAX_PULLS];
   
   private static int numPulls = -1;   // to keep track of index in array
   // first time calling the constructor will make the index 0
   
   // default constructor
   public ThreeString() {
      string1 = string2 = string3 = "";
      numPulls++;
   }
   
   // helper function do determine if string is legal
   private boolean validString(String str) {
      if((str != null) && (str.length() <= MAX_LEN))
         return true;
      else
         return false;
   }
   
   // accessor and mutator methods
   public String getString1() {
      return string1;
   }
   public void setString1(String str) {
      if(validString(str))
         string1 = str;
   }
   public String getString2() {
      return string2;
   }
   public void setString2(String str) {
      if(validString(str))
         string2 = str;
   }
   public String getString3() {
      return string3;
   }
   public void setString3(String str) {
      if(validString(str))
         string3 = str;
   }
   
   // toString method
   public String toString() {
      return string1 + string2 + string3;
   }
   
   // methods for the pullWinnings array
   public boolean saveWinnings(int winnings) {
      // save winnings to the pullWinnings array
      if(numPulls <= MAX_PULLS) {
         pullWinnings[numPulls - 1] = winnings;
         return true;
      }
      else   // if user is out of pulls, return false
         return false;
   }
   public String toStringWinnings() {
      int totalWinnings = 0;
      String stringWinnings = "Your individual winnings were:\n";
      
      // loop through array, add up total and add winnings to String
      for(int i = 0; i < numPulls; i++) {
         totalWinnings += pullWinnings[i];
         stringWinnings += "$" + pullWinnings[i] + " ";
      }
      
      stringWinnings += "\nYour total winnings were: $" + totalWinnings;
      return stringWinnings;
   }
}
   
