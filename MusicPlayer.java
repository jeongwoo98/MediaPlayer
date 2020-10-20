import javax.print.attribute.standard.Media;
import java.util.*;

/**
 * A class representing a Music Player App.
 * It contains a pool of songs that allow the user to traverse and add to the queue to play.
 */
public class MusicPlayer implements Iterable<Song>, MediaPlayer {
    private Map<String, Song> aItems = new LinkedHashMap<>(); // Make sure a predictable iteration order.
    private Queue aQueue= new Queue();
    private List<PlayList> aPlayLists = new ArrayList<>();
    private PlayOrder aPlayOrder;

    MusicPlayer(){}
    /**
     * Add a single song to the music pool if a song with the same name is not already in the pool.
     * @param pItem the song to be added in the pool
     * @pre pItem !=null
     */
    public void addItem(Song pItem) {
        assert pItem != null;
        aItems.putIfAbsent(pItem.getName(), pItem);
    }

    /**
     * Add a single song to the queue if that song can be found in the music pool.
     * @param pItemName the name of the song
     * @pre pItemName !=null
     */
    public void addItemToQueue(String pItemName) {
        assert pItemName != null;
        if (aItems.containsKey(pItemName)) {
            aQueue.add(aItems.get(pItemName));
        }
    }

    /**
     * Obtain the number of songs in the queue
     * @return the number of songs in the queue
     */
    public int getQueueSize() {
        return aQueue.size();
    }
////////////////////////////////////////////////////////////////////////////////////////////////

    public void createPlayList(String pName){
        aPlayLists.add(new PlayList(pName));
    }

    public PlayList getPlayList(String pName){
        for(PlayList pl: aPlayLists){
            if(pl.getName()==pName) return pl;
        }
        return null;
    }

    public void addToPlayList(String pPlayListName, SongSource pSource){
        PlayList pl = getPlayList(pPlayListName);
        if(pSource instanceof PlayList){
            pl.addSource(pSource);
        }else if(aItems.containsKey(pSource.getName())){
            pl.addSource(pSource);
        }
    }

    public void addPlayListToQueue(String pPlayListName){
        assert pPlayListName!=null;
        PlayList playListToAdd = getPlayList(pPlayListName);
        for(SongSource source: playListToAdd){
            if(source instanceof Song) aQueue.add((Song)source);
            else{
                addPlayListToQueue(source.getName());
            }
        }
    }

    public void setPlayOrder(Order pOrder) {
        aPlayOrder = aQueue.setQueueOrder(pOrder);
    }

    public void removeItemFromQueue(String pItemName) {
        assert pItemName != null;
        aQueue.remove(aItems.get(pItemName));
    }

    @Override
    public void playNext(){
        if(aPlayOrder.hasNext()){
            Song songPlayed = aQueue.get(aPlayOrder.getNext());
            System.out.println("Song playing: "+songPlayed.getName());
        }else if(aQueue.size()==0){
            System.out.println("Queue is empty. Add songs to the queue");
        }else{
            System.out.println("Reached the end of the Queue. Re-initialize the playback by adding/removing songs, or by resetting the play order");
        }
    }

    @Override
    public Controller createRemoteController() {
        return new Controller() {
            @Override
            public void next() {
                playNext();
            }
        };
    }

    @Override
    public Controller createVoiceController() {
        return new Controller() {
            @Override
            public void next() {
                System.out.println("Voice command has been received. Will now play next song");
                playNext();
            }
        };
    }

    @Override
    public Iterator<Song> iterator() {
        Iterator it = aItems.values().iterator();
        return it;
    }

    public void printQueue(){
        for(Song song: aQueue){
            System.out.print(song.getName()+", ");
        }
    }

    public PlayOrder getPlayOrder(){
        return aPlayOrder;
    }

}
