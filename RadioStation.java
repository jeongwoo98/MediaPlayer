public class RadioStation {
    private int aStationNb;
    private String aStationName;

    public RadioStation(int pStationNb, String pStationName){
        aStationNb=pStationNb;
        aStationName=pStationName;
    }

    public int getStationNb(){
        return aStationNb;
    }
    public String getStationName(){
        return aStationName;
    }
}
