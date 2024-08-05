package cpsc2150.MyDeque;

/**
 * This class is used to store information about a double-ended queue in an array implementation.
 *
 * @author Robert Vuong and Michael Elrod
 * @version 1.0
 *
 * @invariants 0 <= myQ.length() <= MAX_LENGTH
 *
 * @correspondence self = myQ[0...MAX_LENGTH-1]
 */
public class ArrayDeque<T> extends AbsDeque<T> implements IDeque<T>
{
    // where the data is stored. myQ[0] is the front of the deque
    private T[] myQ;
    // tracks how many items are in the deque
    // also used to find the end of the deque
    private int myLength;

    /**
     * Description: Constructor for ArrayQueue that initializes empty myQ as a
     * Double/character array that has a maximum length of 100
     *
     * @post myQ[0...MAX_LENGTH-1] is empty AND myLength = 0
     */
    public ArrayDeque(){
        myQ = (T[]) new Object[MAX_LENGTH];
        myLength = 0;
    }

    /**
     * Description: Adds x to the end of the deque
     *
     * @param x - Double/character value type that is inserted at the end of deque
     *
     * @pre myLength < MAX_LENGTH
     *
     * @post myQ[myLength-1] = x AND
     *       myLength = #myLength + 1
     */
    public void enqueue(T x){
        myQ[myLength] = x;
        myLength++;
    }

    /**
     * Description: removes and returns the Double/character at the top of the deque
     *
     * @return Double/character-type value removed at the top of deque
     *
     * @pre myLength > 0
     *
     * @post dequeue = myQ[0]
     *      AND myQ[0] removed from myQ
     *      AND myLength = #myLength - 1
     */
    public T dequeue(){
        T temp;
        temp = myQ[0];
        for (int i=0;i<myLength-1;i++)
            myQ[i] = myQ[i+1];

        // After moving elements forward 1, we delete the last space
        // Calling removeLast() decrements myLength for us
        removeLast();
        return temp;
    }


    /**
     * Description: Adds x to the front of the deque
     *
     * @param x - Double/character value type that is inserted at the end of deque
     *
     * @pre myLength < MAX_LENGTH
     *
     * @post myQ[0] = x AND myLength = #myLength + 1
     */
    public void inject(T x){
        for (int i=myLength-1;i>=0;i--){
            myQ[i+1] = myQ[i];
        }
        myQ[0] = x;
        myLength++;
    }

    /**
     * Description: removes and returns the Double/character at the end of the deque
     *
     * @return Double/character-value type removed at end of deque
     *
     * @pre myLength > 0
     *
     * @post removeLast = myQ[myLength-1]
     *       AND myQ[myLength-1] removed from myQ
     *       AND myLength = #myLength - 1
     */
    public T removeLast(){
        T temp;
        temp = myQ[myLength-1];
        myQ[myLength-1] = null;
        myLength--;
        return temp;
    }

    /**
     * Description: calculates number of variables in the deque
     *
     * @return integer of the length of deque
     *
     * @post length = myLength AND
     *       myLength = #myLength
     */
    public int length(){
        return myLength;
    }

    /**
     * Description: clears the entire deque
     *
     * @post myQ[0...MAX_LENGTH-1] is empty AND myLength = 0
     */
    public void clear(){
        myQ = (T[]) new Object[MAX_LENGTH];
        myLength = 0;
    }
}