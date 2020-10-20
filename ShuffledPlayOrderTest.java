import com.sun.tools.corba.se.idl.constExpr.Or;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShuffledPlayOrderTest {
    private static Song SONG1 = new Song("a","artist A",1);
    private static Song SONG2 = new Song("b","artist B",1);
    private static Song SONG3 = new Song("c","artist C",1);
    private static Song SONG4 = new Song("d","artist D",1);
    private static MusicPlayer m1;
    private static ShuffledPlayOrder aPlayOrder;
    private static int count;


    @BeforeEach
    public void setup(){
        m1 = new MusicPlayer();
        m1.addItem(SONG1);
        m1.addItem(SONG2);
        m1.addItem(SONG3);
        m1.addItem(SONG4);
        count =0;
    }

    @Test
    public void emptyQueue_hasNext(){
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        assertFalse(aPlayOrder.hasNext());
    }

    @Test
    public void queueNotDoneTraversal_hasNext(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.addItemToQueue("c");
        m1.addItemToQueue("d");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        m1.playNext();
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
    }

    @Test
    public void queueDoneTraversal_hasNext(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.addItemToQueue("c");
        m1.addItemToQueue("d");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        m1.playNext();
        m1.playNext();
        m1.playNext();
        m1.playNext();
        assertFalse(aPlayOrder.hasNext());
    }

    @Test
    public void songAdded_hasNext(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.addItemToQueue("c");
        m1.addItemToQueue("d");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        m1.playNext();
        m1.playNext();
        m1.addItemToQueue("a");
        assertTrue(aPlayOrder.hasNext());
    }

    @Test
    public void queueNotEmpty_songRemoved_hasNext(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        m1.removeItemFromQueue("a");
        assertTrue(aPlayOrder.hasNext());
    }

    @Test
    public void queueEmpty_songRemoved_hasNext(){
        m1.addItemToQueue("a");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        m1.removeItemFromQueue("a");
        assertFalse(aPlayOrder.hasNext());
    }

    @Test
    public void emptyQueue_getNext(){
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        assertEquals(0,aPlayOrder.getNext());
        assertEquals(0,count);
    }

    @Test
    public void oneSongInQueue_getNext(){
        m1.addItemToQueue("a");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        assertEquals(0,aPlayOrder.getNext());
        assertEquals(0,count);
    }

    @Test
    public void multipleSongsInQueue_getNext(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.addItemToQueue("c");
        m1.addItemToQueue("d");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();
        //calling get next will return an integer index between 0-3 randomly, but
        //subsequent get next calls should not return the same index.
        //Calling it four times --> 0,1,2,3 should all be called, in random order.
        //Just check if their sum is equal !
        int count=0;
        for(int i=0; i<4;i++){
            count += aPlayOrder.getNext();
        }
        assertEquals(count,6);
    }

    @Test
    public void callback_songAdded(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.addItemToQueue("c");
        m1.addItemToQueue("d");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();

        m1.addItemToQueue("a"); //THIS CALLS METHOD songAdded(Song pSong) in ShuffledPlayOrder class!

        //Test to see if play order has in fact been re-initialized
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();

        assertFalse(aPlayOrder.hasNext());
    }

    @Test
    public void callback_songRemoved(){
        m1.addItemToQueue("a");
        m1.addItemToQueue("b");
        m1.addItemToQueue("c");
        m1.addItemToQueue("d");
        m1.setPlayOrder(Order.SHUFFLED);
        aPlayOrder= (ShuffledPlayOrder)m1.getPlayOrder();

        m1.removeItemFromQueue("a"); //THIS CALLS METHOD songRemoved(Song pSong) in ShuffledPlayOrder class!

        //Test to see if play order has in fact been re-initialized
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();
        assertTrue(aPlayOrder.hasNext());
        m1.playNext();

        assertFalse(aPlayOrder.hasNext());
    }

    









}