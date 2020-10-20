public class Song implements SongSource{
    private final String aName;
    private final String aArtist;
    private final int aLength;


    public Song(String pName, String pArtist, int pLength) {
        assert pName!=null && pArtist!=null && pLength >0;
        aName = pName;
        aArtist = pArtist;
        aLength = pLength;
    }

    public Song(Song pSong){
        aName=pSong.getName();
        aArtist=pSong.getArtist();
        aLength=pSong.getLength();
    }

    public String getName() {
        return aName;
    }

    public String getArtist() {
        return aArtist;
    }

    public int getLength() {
        return aLength;
    }

    @Override
    public String description() {
        return getName()+" by "+getArtist()+", "+getLength()+" minutes";
    }
}
