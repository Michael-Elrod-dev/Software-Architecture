package cpsc2150.MyDeque;
import java.util.*;
import java.lang.*;


/*
Names: Robert Vuong and Michael Elrod
Assignment: Lab 4 Interfaces
Class: CPSC 2151 - 001
Date: 10/4/2022

This program is designed to ask the user whether they want to do a double-ended queue in
an array implementation or list implementation. The program will use that implementation
and print out the variables out of the double-ended queue.
 */
public class CharacterDequeApp
{
    /**
     * Description: Prints heading to declare that it is about to print the deque
     *
     * @pre length of self, 0 <= |self| <= MAX_LENGTH
     *
     * @post [Prints that deque is ...]
     */
    public static void printDequeHead(int length){
        if (length == 0) // If empty
        {
            System.out.println("Deque is empty!");
            System.out.print("Deque is: \n");
        }
        else {
            System.out.print("Deque is:\n");
        }
    }

    // Main
    public static void main(String[] args) {
        //Double ended queue and scanner
        IDeque q;
        Scanner scnr = new Scanner(System.in);

        String userInput;
        char value;
        int choice;
        int menuChoice;

        /*
        Prompt the user to pick an implementation using the following
        message: "Enter 1 for array implementation or 2 for List
        implementation"
        Your code needs to make sure that they only enter either 1 or 2.
        Re-print the message to prompt the user to enter it again. Once
        you have gotten an answer, use it to initialize q appropriately.
        */

        //Asking user and preceding with implementation
        do {
            System.out.println("Enter 1 for array implementation or 2 for List implementation");
            userInput = scnr.next();
        } while (!(userInput.equals("1") || userInput.equals("2")));
        System.out.print("\n");

        //Array Implementation
        if (userInput.equals("1"))
            q = new ArrayDeque();

            //ArrayList Implementation
        else
            q = new ListDeque();

        do {
            System.out.print("Select an option: \n" +
                    "1. Add to the end of the Deque \n" +
                    "2. Add to the front of the Deque \n" +
                    "3. Remove from the front of the Deque \n" +
                    "4. Remove from the end of the Deque \n" +
                    "5. Peek from the front of the Deque \n" +
                    "6. Peek from the end of the Deque \n" +
                    "7. Insert to a position in the Deque \n" +
                    "8. Remove from a position in the Deque \n" +
                    "9. Get a position in the Deque \n" +
                    "10. Get the length of the Deque \n" +
                    "11. Clear the Deque \n" +
                    "12. Quit\n");
            menuChoice = scnr.nextInt();


            switch (menuChoice) {
                case 1:
                    // Add to the end of the Deque
                    System.out.println("What character to enqueue to the end of the Deque?");
                    value = scnr.next().charAt(0);
                    q.enqueue(value);
                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 2:
                    // Add to the front of the Deque
                    System.out.println("What character to inject to the front of the Deque?");
                    value = scnr.next().charAt(0);

                    q.inject(value);
                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 3:
                    // Remove from the front of the Deque
                    if (q.length() != 0) // If not empty
                        System.out.printf("Character at the front: %s\n", q.dequeue());

                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 4:
                    // Remove from the end of the Deque
                    if (q.length() != 0) // If not empty
                        System.out.printf("Character at the front: %s\n", q.removeLast());

                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 5:
                    // Peek from the front of the Deque
                    if (q.length() != 0) // If not empty
                        System.out.printf("Peek: %s\n", q.peek());

                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 6:
                    // Peek from the end of the Deque
                    if (q.length() != 0) // If not empty
                        System.out.printf("EndOfDeque: %s\n", q.endOfDeque());;

                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 7:
                    // Insert into a position in the Deque
                    if (q.length() != 0)// If not empty
                    {
                        System.out.println("What character to insert to the Deque?");
                        value = scnr.next().charAt(0);

                        do {
                            System.out.println("What position to insert in?");
                            userInput = scnr.next();
                            choice = Integer.parseInt(userInput);


                            // If invalid input
                            if (choice > q.length()+1 || choice < 1)
                                System.out.println("Not a valid position in the Deque!");

                            // While user input is a valid position
                        }while (choice < 1 || choice > q.length()+1);
                        q.insert(value, choice);// Insert #newValue at position #choice
                    }
                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 8:
                    // Remove from a position in the Deque
                    if (q.length() != 0) // If not empty
                    {
                        do {
                            System.out.println("What position to remove from the Deque?");
                            userInput = scnr.next();
                            choice = Integer.parseInt(userInput);

                            // If invalid input
                            if (choice > q.length() || choice < 1)
                                System.out.print("Not a valid position in the Deque!\n");

                            // While user input is a valid position
                        }while (choice > q.length() || choice < 1);
                        q.remove(choice);
                    }
                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 9:
                    // Get a position in the Deque
                    if (q.length() != 0) // If not empty
                    {
                        do {
                            System.out.print("What position to get from the Deque?\n");
                            userInput = scnr.next();
                            choice = Integer.parseInt(userInput);

                            // If invalid input
                            if (choice > q.length() || choice < 1)
                                System.out.print("Not a valid position in the Deque!\n");

                            // While user input is a valid position
                        }while (choice > q.length() || choice < 1);
                        System.out.printf("%s is at position %d in the Deque.\n",q.get(choice), choice);
                    }
                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 10:
                    // Get the length of the Deque
                    if (q.length() != 0) // If not empty
                        System.out.printf("Length of Deque: %d\n", q.length());

                    printDequeHead(q.length());
                    System.out.println(q.toString());
                    System.out.print("\n");
                    break;

                case 11:
                    // Clear the Deque
                    q.clear();
                    System.out.print("Deque is now empty!\n");
                    // Cannot call toString here or we double print wrong message
                    System.out.println("Deque is:\n" + "<>\n");
                    break;

                default: // case 12: Quit, do nothing
                    break;
            }
        } while (menuChoice >= 1 && menuChoice <= 11);
    }
}