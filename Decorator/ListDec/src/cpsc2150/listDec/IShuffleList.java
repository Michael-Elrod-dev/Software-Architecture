package cpsc2150.listDec;
import java.util.List;
import java.util.Random;

/**
 * IShuffleList is abstractly an extension of the List java object that allows
 * for a list's objects to be shuffled.
 *
 * @author Michael Elrod and Robert Vuong
 * @version 1.0
 *
 * Initialization ensures:
 *      IShuffleList contains a list that holds object of any type.
 *
 * Defines:
 *      self = Generic List
 */
public interface IShuffleList<T> extends List<T>
{
    /**
     * </Description: Shuffles a list of objects #swaps number of times>
     * @param swaps Number of times to shuffle
     * @pre 0 <= swaps
     * @post [swap() is called #swap number of times]
     */
    public default void shuffle(int swaps)
    {
        for (int i=0;i<swaps;i++)
        {
            Random rand = new Random();
            int rand_num = rand.nextInt(size());
            int rand_num2 = rand.nextInt(size());
            swap(rand_num, rand_num2);
        }
    }

    /**
     *
     * @param i position in list to swap
     * @param j position in list to swap
     * @pre 0 <= i < self.size() AND
     *      0 <= j < self.size()
     * @post self = [List is shuffled #swaps number of times]
     */
    public default void swap (int i, int j)
    {
        T temp1 = get(i);
        T temp2 = get(j);
        set(i, temp2);
        set(j, temp1);
    }
}
