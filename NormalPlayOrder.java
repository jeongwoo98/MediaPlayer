import java.util.ArrayList;
import java.util.List;

public class NormalPlayOrder implements PlayOrder, QueueObserver {
    private List<Song> aSongElements = new ArrayList<>();
    int currentIndex;

    public NormalPlayOrder(List<Song> pSongElements){
        for(Song song: pSongElements){
            aSongElements.add(song);
        }
        currentIndex=0;
    }

    @Override
    public int getNext() {
        return currentIndex++;
    }
    @Override
    public boolean hasNext() {
        return currentIndex<aSongElements.size()&& aSongElements.get(currentIndex)!=null;
    }

    //CALLBACK methods.
    @Override
    public void songAdded(Song pSong) {
        aSongElements.add(pSong);
        currentIndex=0;
    }

    @Override
    public void songRemoved(Song pSong) {
        aSongElements.remove(pSong);
        currentIndex=0;
    }
}
