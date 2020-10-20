import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RadioReceiver implements MediaPlayer{
    private List<RadioStation> aStations= new ArrayList<>();
    private PlayOrder aPlayOrder;

    public RadioReceiver(){}
    public void addStation(RadioStation pStation){
        aStations.add(pStation);
    }

    //I'm assuming that we don't actually need to implement a radio station class entirely, but
    //that my design needs to be able to accommodate these features.
    public void playNext(){
        System.out.println("Next radio station played");
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
                System.out.println("Voice command has been received. Will now play next radio Station");
                playNext();
            }
        };
    }
}
