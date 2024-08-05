package cpsc2150.MyDeque;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

/**
 * IDeque is abstractly a double-ended queue that supports insertion and removal
 * from both ends, and this data structure has a maximum length of 100.
 *
 * This interface is used to hold double - ended queue methods that classes can implement.
 *
 * @author Robert Vuong and Michael Elrod
 * @version 1.0
 *
 * Initialization ensures:
 *      IDeque contains empty elements that holds a deque of either empty characters OR Double-valued zeros.
 *
 * Defines:
 *      self = list or array
 *
 * Constraints:
 *      0 <= length of self <= MAX_LENGTH AND
 *      [indexes of self start at 1]
 */
public interface IDeque<T>
{
    /**
     * A deque containing floating-point numbers OR characters.
     * A deque is a data structure, a double-ended queue that allows you
     * to insert and remove from both ends.
     * This deque is bounded by MAX_LENGTH
     */
    public static final int MAX_LENGTH = 100;

    /**
     * Description: Adds x to the end of the deque
     *
     * @param x - Double/character type that is inserted at the end of deque
     *
     * @pre length of self, |self| < MAX_LENGTH
     *
     * @post self = [x added to end of #self]
     */
    public void enqueue(T x);

    /**
     * Description: removes and returns the Double/character at the top of the deque
     *
     * @return Double/character type value removed at the top of deque
     *
     * @pre length of self, |self| > 0
     *
     * @post self = [dequeue() removed from top of #self]
     */
    public T dequeue();

    /**
     * Description: Adds x to the front of the deque
     *
     * @param x - Double/character-type value that is inserted at the end of deque
     *
     * @pre length of self, |self| < MAX_LENGTH
     * @post self = [x added to front of #self]
     */
    public void inject(T x);

    /**
     * Description: removes and returns the Double/character at the end of the deque
     *
     * @return Double/character-type value removed at end of deque
     *
     * @pre length of self, |self| > 0
     *
     * @post self = [removeLast() removed from end of #self]
     */
    public T removeLast();

    /**
     * Description: calculates number of variables in the deque
     *
     * @return integer of length of deque
     *
     * @pre  length of self, 0 <= |self| <= MAX_LENGTH
     *
     * @post length = |self|
     */
    public int length();

    /**
     * Description: clears the entire deque
     *
     * @post self = <>
     */
    public void clear();

    /**
     * Description: Peeks the Double/character at the front of the deque but
     * does not remove it from the deque
     *
     * @return Double/character-type value in beginning of deque
     *
     * @pre length of self, 0 <= |self| <= MAX_LENGTH
     *
     * @post peek = [front of self]
     */
    public default T peek()
    {
        // Remove the variable, then place it back
        T temp = dequeue();
        inject(temp);
        return temp;
    }

    /**
     * Description: Peeks the Double/character at the end of the deque but
     * does not remove it from the deque
     *
     * @return Double/character-type value in end of deque
     *
     * @pre length of self, 0 <= |self| <= MAX_LENGTH
     *
     * @post endOfDeque = [end of self]
     */
    public default T endOfDeque()
    {
        // Remove the variable, then place it back
        T temp = removeLast();
        enqueue(temp);
        return temp;
    }

    /**
     * Description: Inserts Double/character at specific position in the deque
     *
     * @pre length of self, |self| < MAX_LENGTH AND
     *      [pos index starts at 1]
     *
     * @post self = [x added to pos in #self]
     */
    public default void insert(T x, int pos)
    {
        List<T> temp = new ArrayList<T>();

        // Add variables to temp list until (pos)
        for (int i=0;i<pos-1;i++)
            temp.add(dequeue());

        // Insert new variable
        inject(x);

        // Re-insert variables from temp list
        for (int i=pos-2;i>=0;i--)
            inject(temp.get(i));

    }

    /**
     * Description: Removes Double/character at specific position in the deque
     *
     * @return Double/character-type value that was removed at specific position in the deque
     *
     * @pre length of self, |self| > 0 AND
     *      [pos index starts at 1]
     *
     * @post self = [remove(pos) removed from pos of #self]
     */
    public default T remove(int pos)
    {
        List<T> temp = new ArrayList<T>();
        T result;

        // Add variables to temp list until (pos)
        for (int i=0;i<pos-1;i++)
            temp.add(dequeue());

        result = dequeue();

        // Re-insert variables from temp list
        for (int i=pos-2;i>=0;i--)
            inject(temp.get(i));

        return result;
    }

    /**
     * Description: Sees Double/character in specific position in the deque without removing it
     *
     * @return Double/character-type value at position of deque
     *
     * @pre  length of self, 0 <= |self| <= MAX_LENGTH AND
     *       [pos index starts at 1]
     *
     * @post get = [pos in self]
     */
    public default T get(int pos)
    {
        List<T> temp = new ArrayList<T>();
        T result;

        // Add variables to temp list until (pos)
        for (int i=0;i<pos;i++)
            temp.add(dequeue());

        result = temp.get(pos-1);

        // Re-insert variables from temp list
        for (int i=pos;i>0;i--)
            inject(temp.get(i-1));

        return result;
    }
}