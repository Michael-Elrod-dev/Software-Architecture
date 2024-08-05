package cpsc2150.MyDeque;
import java.util.*;

/**
 * This class is used to store information about a double-ended queue in an list implementation.
 *
 * @author Robert Vuong and Michael Elrod
 * @version 1.0
 *
 * @invariants 0 <= myQ.size() <= MAX_LENGTH AND
 *             0 <= myQ.length() <= MAX_LENGTH
 *
 * @correspondence self = myQ[0..myLength-1]
 */
public class ListDeque<T> extends AbsDeque<T> implements IDeque<T>
{
    // this time store the deque in a list
    // myQ.get(0) is the front of the deque
    private List<T> myQ;

    /**
     * Description: Constructor for ListQueue that initializes empty myQ as a
     * Double/character ArrayList
     *
     * @post myQ = [a list of Double/characters of MAX_LENGTH] AND
     *       myQ.size() = 0
     */
    public ListDeque(){
        myQ = new ArrayList<T>();
    }
    /**
     * Description: Adds x to the end of the deque
     *
     * @param x - Double/character-type value that is inserted at the end of deque
     *
     * @pre myQ.length() < MAX_LENGTH
     *
     * @post myQ = [x added to end of #myQ]
     */
    public void enqueue(T x){
        myQ.add(x);
    }

    /**
     * Description: removes and returns the Double/character at the top of the deque
     *
     * @return Double/character-type value removed at the top of deque
     *
     * @pre myQ.length() > 0
     *
     * @post dequeue = [dequeue() removed from top of #myQ]
     */
    public T dequeue(){
        T temp = myQ.get(0);
        myQ.remove(0);
        return temp;
    }

    /**
     * Description: Adds x to the front of the deque
     *
     * @param x - Double/character value type that is inserted at the end of deque
     *
     * @pre myQ.length() < MAX_LENGTH
     *
     * @post myQ = [x is added to from of #myQ]
     */
    public void inject(T x){myQ.add(0, x);}

    /**
     * Description: removes and returns the Double/character at the end of the deque
     *
     * @return Double/character-type value removed at end of deque
     *
     * @pre myQ.length() > 0
     *
     * @post myQ = [removeLast() removed from end of #myQ]
     */
    public T removeLast(){
        T temp = myQ.get(myQ.size()-1);
        myQ.remove(myQ.size()-1);
        return temp;
    }

    /**
     * Description: calculates number of variables in the deque
     *
     * @return integer of the length of deque
     *
     * @post length = myQ.size()
     */
    public int length(){
        return myQ.size();
    }

    /**
     * Description: clears the entire deque
     *
     * @post myQ is empty AND myQ.size() = 0
     */
    public void clear(){
        myQ = new ArrayList<T>();
    }
}