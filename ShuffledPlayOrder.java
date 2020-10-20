import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShuffledPlayOrder implements PlayOrder,QueueObserver{
    private List<Song> aSongElements = new ArrayList<>();
    private int currentIndex;
    private int count=0;
    private List<Integer> indexTraversed = new ArrayList<>();
    private Random random = new Random();

    public ShuffledPlayOrder(List<Song> pSongElements){
        for(Song song: pSongElements){
            aSongElements.add(song);
        }
        if(aSongElements.size()>1) currentIndex = random.nextInt(aSongElements.size());
        else currentIndex=0;
    }

    @Override
    public int getNext() {
        while(indexTraversed.contains(currentIndex)){
            currentIndex= random.nextInt(aSongElements.size());
        }
        count++;
        indexTraversed.add(currentIndex);
        return currentIndex;
    }

    @Override
    public boolean hasNext() {
        return count<aSongElements.size()&& aSongElements.get(currentIndex)!=null;
    }

    //CALLBACK methods.
    @Override
    public void songAdded(Song pSong) {
        count=0;        //must reinitialize the count
        indexTraversed.clear();
        currentIndex= random.nextInt(aSongElements.size());
        aSongElements.add(pSong);
    }

    @Override
    public void songRemoved(Song pSong) {
        count =0;
        indexTraversed.clear();
        aSongElements.remove(pSong);
        if(aSongElements.size()>1) currentIndex = random.nextInt(aSongElements.size());
        else currentIndex=0;    //accounting for case when there is one song in the queue. Must return index 0.
    }


}
