import java.util.*;

/**
 * A class representing the Queue in the Music Player App.
 * It contains a list of songs that the Music Player can play
 * using different playing strategy.
 * It can contain repeated songs.
 */
public class Queue implements Iterable<Song>{
    private List<Song> aSongElements = new ArrayList<>();
    private List<QueueObserver> aObservers = new ArrayList<>();



    /**
     * Obtain the number of songs in the queue
     * @return the number of songs in the queue
     */
    public int size() {
        return aSongElements.size();
    }

    /**
     * Obtain the song using its index in the queue.
     * @param index the position of the song in the queue
     * @return the song in the queue at the position of the input index
     * @pre index >=0 && index < aSongElements.size()
     */
    public Song get(int index) {
        assert index >=0 && index < aSongElements.size();
        return aSongElements.get(index);
    }

    /**
     * Add a single song to the queue.
     * @param pItem the Song to be added to the queue
     * @pre pItem!=null
     */
    public void add(Song pItem) {
        assert pItem!=null;
        aSongElements.add(pItem);
        for(QueueObserver observer: aObservers){
            observer.songAdded(pItem);
        }
    }

    /**
     * Add a single song from the queue if it can be found.
     * If it appears more than once in the queue, the first occurrence will be removed.
     * @param pItem the Song to be removed to the queue if presented
     * @pre pItem!=null
     */
    public void remove(Song pItem) {
        assert pItem!=null;
        aSongElements.remove(pItem);
        for(QueueObserver observer: aObservers){
            observer.songRemoved(pItem);
        }
    }

    public boolean isQueueEmpty(){
        return size()<1;
    }

    public PlayOrder setQueueOrder(Order pOrder){
        if(pOrder.equals(Order.NORMAL)){
            NormalPlayOrder NOrder = new NormalPlayOrder(aSongElements);
            clearObservers();
            addObservers(NOrder);
            return NOrder;
        }
        else {
            ShuffledPlayOrder SOrder = new ShuffledPlayOrder(aSongElements);
            clearObservers();
            addObservers(SOrder);
            return SOrder;
        }
    }

    public void addObservers(QueueObserver pObserver){
        assert pObserver!=null;
        aObservers.add(pObserver);
    }
    public void clearObservers(){
        aObservers.clear();
    }

    @Override
    public Iterator<Song> iterator() {
        return aSongElements.iterator();
    }
}
