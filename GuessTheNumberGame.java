import java.util.Random;
import java.util.Scanner;

public class Game {
    //The magic number that player is trying to guess
    int ans;

    //number of guesses a player takes (Custom mode)
    int guesses;

    //Upper bound number of the number range (1-bound)
    int bound;

    //Tells if game should keep being played or not
    boolean play = true;

    //tell whether to exit application or not
    boolean exit = false;

    //Players response stored as a string
    String resp;

    //Campaign mode string
    final String CAMPAIGN = "Campaign";

    //Custom mode string
    final String CUSTOM = "Custom";

    //Exit application string
    final String EXIT = "Exit";

    //Create instance of Random class to determine random numbers
    Random num = new Random();

    //Create instance of Scanner class to receive user input
    Scanner input = new Scanner(System.in);


    /**
     * Used to make sure the user input for a guess is valid
     * @param num the users input
     * @return true if it is valid, false if it is not
     */
    public boolean validateNum(String num){
        try {
            Integer.parseInt(num);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Used to make sure user input for mode is valid
     * @param mode the users input
     * @return true if valid, false if not
     */
    public boolean validateMode(String mode){
        return mode.equalsIgnoreCase("Campaign") || (mode.equalsIgnoreCase("Custom")) ||
                mode.equalsIgnoreCase(EXIT);
    }

    /**
     * Used to make sure user input for Yes/No questions is valid
     * @param ans the users input
     * @return true is valid, false if it is not
     */
    public boolean validateYesNo(String ans){
        return ans.equalsIgnoreCase("Y") || ans.equalsIgnoreCase("Yes") ||
                ans.equalsIgnoreCase("N") || ans.equalsIgnoreCase("No") ||
                ans.equalsIgnoreCase(EXIT);
    }

    /**
     * Used to calculate the score for campaign mode
     * @param guessesRemaining the number of remaining guesses the player has
     * @return the score that the player earned for a given level
     */
    public int calculateScore(int guessesRemaining){
        return  guessesRemaining*100;
    }

    /**
     * Used to determine if a guess is a correct or not
     * @param val the users guess
     * @return 1 if the guess is higher than the actual answer
     * 0 if the guess is the same as the answer
     * -1 if the guess is lower than the actual answer
     */
    public int checkGuess(int val){
        return Integer.compare(val, ans);
    }

    /**
     * The brain of the entire game
     */
    public void play() {
        //Player guess
        int guess;

        //The upper bound value of the range in which a player guesses from (1-bound)
        int bound;

        //While the user still wants to be in the application
        while (!exit) {
            play = true;
            System.out.print("\nType \"Exit\" to quit the game.\n" +
                    "Select Game Mode(Campaign/Custom): ");


            resp = input.next();

            //while the input is not valid
            while (!validateMode(resp)) {
                System.out.print("Error! Please enter a valid mode: (Campaign/Custom/Exit) ");
                resp = input.next();
            }

            //Play custom game mode
            if (resp.equalsIgnoreCase(CUSTOM)) {

                //while they still want to play
                while (play) {

                    System.out.print("\nSelect upper bound number: ");
                    resp = input.next();

                    //While the bound is not a valid number
                    while (!validateNum(resp)) {
                        System.out.print("Error! Enter a valid Number: ");
                        resp = input.next();
                    }

                    bound = Integer.parseInt(resp);

                    System.out.println("\nSuccess! Pick a number between 1 and " + bound + ".\n");

                    //Determine Target Number
                    ans = num.nextInt(bound) + 1;

                    //Enter initial guess
                    System.out.print("Enter your guess: ");

                    resp = input.next();

                    //Validate initial input
                    while (!validateNum(resp)) {
                        System.out.print("\nError! Please enter a valid Number: ");
                        resp = input.next();
                    }

                    //Set validated input as guess
                    guess = Integer.parseInt(resp);

                    //Increase number of guesses user has entered
                    guesses++;

                    //Loop till answer is found
                    while (checkGuess(guess) != 0) {

                        //Need to guess higher
                        if (checkGuess(guess) < 0) {
                            System.out.println("\nIncorrect, guess higher!");
                        } else
                            System.out.println("\nIncorrect, guess lower!");

                        guesses++;
                        System.out.print("Select new number: ");

                        //Next guess
                        resp = input.next();

                        //Validate additional guesses
                        while (!validateNum(resp)) {
                            System.out.print("Error! Please enter a valid Number: ");
                            resp = input.next();
                        }
                        guess = Integer.parseInt(resp);
                    }

                    //The guess was correct
                    System.out.print("\nCongratulations that's correct! It took you " + guesses +
                            " guesses to get the right answer.\nWould you like to play again?(Y/N) ");
                    resp = input.next();

                    //Check that their answer is valid
                    while (!validateYesNo(resp)) {
                        System.out.print("Error! Please enter valid response: (Yes/No)");
                        resp = input.next();
                    }

                    //If they don't want to play anymore, go back to mode screen
                    if (resp.equalsIgnoreCase("No") || resp.equalsIgnoreCase("N"))
                        play = false;

                    //Reset for next round
                    guesses = 0;
                }
            }

            //Campaign Game mode
            if(resp.equalsIgnoreCase(CAMPAIGN)) {
                //Score of the player
                int score = 0;

                //Number of guesses player has left
                int attempts = 0;

                //Level the player is on
                int level = 1;

                //Whether they have lost the game or not
                boolean lose = false;

                //While they still want to play campaign
                while (play) {

                    //Intro
                    System.out.print("\nWelcome to Campaign Mode!\nIn this mode you will go through a series of levels\n" +
                            "and only have a limited number of guesses for each level. \nThe less guesses it takes to " +
                            "find the number the more points you will score.\n\n");

                    //While they haven't lost
                    while(!lose){

                        //Set number of tries allowed
                        switch(level){
                            case 1:
                                attempts = 5;
                                break;
                            case 2:
                                attempts = 7;
                                break;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                attempts = 10;
                        }

                        System.out.print("Guess a number between 1 and " + level * 10 +": ");
                        this.bound = level * 10;

                        //Pick Answer via RNG
                        ans = num.nextInt(this.bound) + 1;
                        resp = input.next();

                        //Validate guess
                        while(!validateNum(resp)){
                            System.out.print("Error! Please enter a valid Number ");
                            resp = input.next();
                        }

                        //See if their guess is correct and keep going till correct or lose
                        while(checkGuess(Integer.parseInt(resp)) != 0 && attempts > 1){
                            attempts--;

                            if(checkGuess(Integer.parseInt(resp)) > 0)
                                System.out.print("\nIncorrect guess lower.\nYou have " + attempts + " guesses left: ");

                            if(checkGuess(Integer.parseInt(resp)) < 0 )
                                System.out.print("\nIncorrect guess higher.\nYou have " + attempts + " guesses left: ");

                            //Next guess
                            resp = input.next();

                            //validate guess
                            while(!validateNum(resp)){
                                System.out.print("\nError! Please enter a valid number. ");
                                resp = input.next();
                            }
                        }

                        //Correct guess
                        if(checkGuess(Integer.parseInt(resp)) == 0) {

                            //Calculate score
                            score += calculateScore(attempts);

                            //increase the level
                            level++;
                            System.out.println("\nCorrect! You are now on level " + level + "!");
                        }

                        //Out of attempts and the players guess is wrong
                        if(attempts == 1 && checkGuess(Integer.parseInt(resp)) != 0)
                            lose = true;
                    }

                    //They lost the game
                    System.out.print("\nGame over! You scored " + score + " points! Would you like to play again(Y/N)");
                    resp = input.next();

                    //validate their response
                    while(!validateYesNo(resp)){
                        System.out.print("Error! Please enter valid response(Y/N).");
                        resp = input.next();
                    }

                    //They don't want to play
                    if (resp.equalsIgnoreCase("No") || resp.equalsIgnoreCase("N"))
                        play = false;

                    //They want to play again
                    else {
                        lose = false;
                        score = 0;
                        level = 1;
                    }
                }
            }

            //They want to quit the application
            if(resp.equalsIgnoreCase(EXIT)){
                exit = true;
            }
        }
    }

    public static void main(String[] args){
        Game g1 = new Game();
        g1.play();
    }
}
