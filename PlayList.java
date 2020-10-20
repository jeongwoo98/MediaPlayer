import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class PlayList implements SongSource, Iterable<SongSource> {
    private List<SongSource> aSources = new ArrayList<>();
    private String aName;

    public PlayList(String pName){
        aName=pName;
    }

    public String getName(){
        return aName;
    }

    public String description(){
        StringJoiner description = new StringJoiner(",","[","]");
        for(SongSource source: aSources){
            if(source instanceof Song){
                description.add(source.getName());
            }else description.add(source.description());

        }
        return aName+": "+description.toString();
    }

    public void addSource(SongSource pSource){
        aSources.add(pSource);
    }

    @Override
    public Iterator<SongSource> iterator() {
        return aSources.iterator();
    }
}
