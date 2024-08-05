package cpsc2150.MyDeque;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestArrayDeque {

    private final double EPSILON = .0001;

    private IDeque MakeADeque() {
        IDeque q = new ArrayDeque();
        return q;
    }

    @Test
    public void testEnqueue_in1_in2_in3() {
        double input1 = 1;
        double input2 = 2;
        double input3 = 3;
        String expected = "<1.0, 2.0, 3.0>";
        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testEnqueue_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        String expected = "<A, B, C>";
        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testEnqueue_inN1_inN2_inN3() {
        double input1 = -1.5;
        double input2 = -2.6;
        double input3 = -3.7;

        String expected = "<-1.5, -2.6, -3.7>";
        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testDequeue_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        Double expected = input1;
        String sExpected = "<>";

        Double actual = (Double) q.dequeue();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testDequeue_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        char expected = input1;
        char actual = (char) q.dequeue();

        assertEquals(expected, actual);
    }

    @Test
    public void testDequeue_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        Double expected = input1;
        String sExpected = "<-2.2, -3.3>";

        Double actual = (Double) q.dequeue();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testInject_in1() {
        double input1 = 1;

        IDeque q = MakeADeque();
        q.inject(input1);

        String expected = "<1.0>";
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testInject_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.inject(input1);
        q.inject(input2);
        q.inject(input3);

        String expected = "<C, B, A>";
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testInject_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.inject(input1);
        q.inject(input2);
        q.inject(input3);

        String expected = "<-3.3, -2.2, -1.1>";
        String actual = q.toString();

        assertEquals(expected, actual);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testRemoveLast_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        Double expected = input1;
        String sExpected = "<>";

        Double actual = (Double) q.removeLast();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testRemoveLast_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        char expected = input3;
        char actual = (char) q.removeLast();

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveLast_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        Double expected = input3;
        String sExpected = "<-1.1, -2.2>";

        Double actual = (Double) q.removeLast();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testClear_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        String expected = "<>";
        q.clear();
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testClear_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        String expected = "<>";
        q.clear();
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testClear_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        String expected = "<>";
        q.clear();
        String actual = q.toString();

        assertEquals(expected, actual);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testPeek_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        Double expected = input1;
        String sExpected = "<1.0>";

        Double actual = (Double) q.peek();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testPeek_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        char expected = input1;
        char actual = (char) q.peek();

        assertEquals(expected, actual);
    }

    @Test
    public void testPeek_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        Double expected = input1;
        String sExpected = "<-1.1, -2.2, -3.3>";

        Double actual = (Double) q.peek();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testEndOfDeque_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        Double expected = input1;
        String sExpected = "<1.0>";

        Double actual = (Double) q.endOfDeque();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testEndOfDeque_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        char expected = input3;
        char actual = (char) q.endOfDeque();

        assertEquals(expected, actual);
    }

    @Test
    public void testEndOfDeque_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        Double expected = input3;
        String sExpected = "<-1.1, -2.2, -3.3>";

        Double actual = (Double) q.endOfDeque();
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testInsert_in1() {
        double input1 = 1;

        IDeque q = MakeADeque();
        q.insert(input1, 1);

        String expected = "<1.0>";
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.insert(input3, 2);

        String expected = "<A, C, B>";
        String actual = q.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);

        q.insert(input3, 2);

        String expected = "<-1.1, -3.3, -2.2>";
        String actual = q.toString();

        assertEquals(expected, actual);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testRemove_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        Double expected = input1;
        String sExpected = "<>";

        Double actual = (Double) q.remove(1);
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);

    }

    @Test
    public void testRemove_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        char expected = input2;
        char actual = (char) q.remove(2);

        assertEquals(expected, actual);
    }

    @Test
    public void testRemove_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        Double expected = input3;
        String sExpected = "<-1.1, -2.2>";

        Double actual = (Double) q.remove(3);
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }
    /////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGet_in1() {
        double input1 = 1.0;

        IDeque q = MakeADeque();
        q.enqueue(input1);

        Double expected = input1;
        String sExpected = "<1.0>";

        Double actual = (Double) q.get(1);
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }

    @Test
    public void testGet_inA_inB_inC() {
        char input1 = 'A';
        char input2 = 'B';
        char input3 = 'C';

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        char expected = input2;
        char actual = (char) q.get(2);

        assertEquals(expected, actual);
    }
    @Test
    public void testGet_inN1_inN2_inN3() {
        double input1 = -1.1;
        double input2 = -2.2;
        double input3 = -3.3;

        IDeque q = MakeADeque();
        q.enqueue(input1);
        q.enqueue(input2);
        q.enqueue(input3);

        Double expected = input3;
        String sExpected =  "<-1.1, -2.2, -3.3>";

        Double actual = (Double) q.get(3);
        String sActual = q.toString();

        assertEquals(sExpected, sActual);
        assertEquals(expected, actual, EPSILON);
    }
}
