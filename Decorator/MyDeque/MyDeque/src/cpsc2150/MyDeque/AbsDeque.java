package cpsc2150.MyDeque;

/**
 * This abstract class contains an overridden implementation of
 * {@link Object#toString()}
 *
 * @author Robert Vuong and Michael Elrod
 * @version 1.0
 */
public abstract class AbsDeque<T> implements IDeque<T>{

    /**
     * Description: Creates a string that tells what the deque contains
     *
     * @return String of deque condition
     *
     * @pre length of self, 0 <= |self| <= MAX_LENGTH
     *
     * @post toString = [String representation of the deque] AND self = #self
     */
    @Override
    public String toString()
    {
        // The value of length() changes during certain function calls
        // So we lock it here
        int length = length();
        String myString = "";

        if (length == 0) // If empty
        {
            myString = myString.concat("<>");
        }
        else
        {
            myString = myString.concat("<");

            // Print all values
            for (int i=0;i<length;i++)
            {
                // Adds Doubles/characters from deque to string
                myString += get(i+1);

                // There are more variables in the deque
                if (i < length-1)
                    myString += ", ";
            }
            myString += ">";
        }
        return myString;
    }
}