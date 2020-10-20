import java.util.Map;

/**
 *
 * Class representing the client code of the MusicPlayer app.
 *
 */
public class HomeEntertainmentSystem {
    /** 4.2:
     * Write a mock-up client code to showcase the complete code you have designed and
     * implemented so far (from Questions 2-4) in the empty main method in the
     * HomeEntertainmentSystem class.
     */

    public static void main(String[] args) {
        Song SONG1 = new Song("Get You","Daniel Caesar", 4);
        Song SONG2 = new Song("White Ferrari", "Frank Ocean", 5);
        Song SONG3 = new Song("Young Dumb & Broke", "Khalid",3);
        Song SONG4 = new Song("Easy","Mac Eyres", 3);

        MusicPlayer m1 = new MusicPlayer();     //Our music player app.
        m1.addItem(SONG1);                      //Add the songs into the pool.
        m1.addItem(SONG2);
        m1.addItem(SONG3);
        m1.addItem(SONG4);


        /** 2.1:
         * We must show that we can traverse the songs in the pool
         * without knowing the underlying structure of how the songs are saved in the MusicPlayer
         */
        System.out.println("------------------2.1--------------------");
        System.out.println("Songs in m1's pool is: ");
        for(Song song: m1){                                 //This for each loop does not reveal the data structure
            System.out.println(song.description());         //that the songs in the pool are stored in.
        }

        /** 2.2:
         * The client class should be able to create and save playlists using the music app. The same song
         * can be added to the playlist multiple times. A playlist can also be nested
         */
        System.out.println("------------------2.2--------------------");
        m1.createPlayList("Inner PlayList");
        m1.createPlayList("Jeongwoo's PlayList");

        PlayList p1 = m1.getPlayList("Inner PlayList");
        PlayList p2 = m1.getPlayList("Jeongwoo's PlayList");         //"Jeongwoo's Playlist" is a nested playlist
                                                                            //it contains the Inner PlayList as one of its
        p1.addSource(SONG1);                                                //Elements
        p1.addSource(SONG2);

        p2.addSource(p1);       //Jeongwoo's PlayList: {Inner PlayList, SONG3, SONG4, SONG4}
        p2.addSource(SONG3);
        p2.addSource(SONG4);
        p2.addSource(SONG4);    //Can add the same song multiple times.
        System.out.println(p2.description());

        /** 2.3:
         * The client class can add all the songs from a playlist to the queue. When added, the order of the
         * songs in the playlist is maintained.
         */
        System.out.println("------------------2.3--------------------");
        m1.addPlayListToQueue("Jeongwoo's PlayList");
        System.out.print("Queue is: [");
        m1.printQueue();
        System.out.println("]");


        /** 3.1 & 3.2:
         * Support the two ways of client code can access the song in the queue, i.e. ordered play and
         * shuffled play. : The client code can specify using which order to playing queue at any time using the music app
         */
        System.out.println("------------------3.1 & 3.2 --------------------");
        System.out.println("Queue played in shuffled mode");
        m1.setPlayOrder(Order.SHUFFLED);        //can specify play order through this method, and inputting the appropriate Order.
        for(int i=0; i<5;i++) m1.playNext();    //Play five songs according to the chosen playback order.
        System.out.println("Queue played in normal ordered mode");
        m1.setPlayOrder(Order.NORMAL);
        for(int i=0; i<5;i++) m1.playNext();

        /** 3.3:
         * During your design, take consideration on how to avoid “null” when the queue is empty and
         * when it reaches its end.
         */
        System.out.println("------------------3.3--------------------");
        //This is where we reach the end of the queue! Just print out a message telling the client to
        //add/remove songs from the queue, which will reinitialize the play order.
        m1.playNext();

        //This is the case of an empty queue. Promptly tell the client to add songs to the queue in order
        //to play songs.
        MusicPlayer m2 = new MusicPlayer();
        m2.setPlayOrder(Order.SHUFFLED);
        m2.playNext();

        /** 3.4:
         * Whenever the content of the queue is changed (songs added or removed), the playing order is
         * reinitialized according to the latest status of the queue
         */
        System.out.println("------------------3.4--------------------");
        m1.setPlayOrder(Order.NORMAL);
        m1.removeItemFromQueue("Easy");
        //Queue is: {SONG1,SONG2,SONG3,SONG4}
        m1.playNext();
        m1.playNext();
        m1.playNext();
        //Currently, we are playing SONG3 using the ordered play mode. Then SONG4 is added to the end of the queue
        m1.addItemToQueue("Easy");     //must play: SONG1-->SONG2-->SONG3-->SONG4-->SONG4
        for(int i=0; i<5;i++) m1.playNext();

        /** 3.5:
         * Write the unit test for the class representing the shuffled play strategy and achieve the 100%
         * method coverage.
         */
        //Please refer to class ShuffledPlayOrderTest, and run it with coverage.

        /** 4.1:
         * Using the provided Controller interface, achieve the support for both remote controller
         * and voice controller for playing either the next song in the music player or the next radio station
         * in the radio receiver. For the voice controller, please simply print some messages using the Java
         * print stream to mimic generating speech for the sake of this exam.
         */
        System.out.println("------------------3.5--------------------");
        m1.setPlayOrder(Order.SHUFFLED);
        Controller remoteController = m1.createRemoteController();
        Controller voiceController = m1.createVoiceController();

        remoteController.next();
        remoteController.next();
        voiceController.next();     //This one should print an additional message before playing the song.
        voiceController.next();
        remoteController.next();

        //This is to show that the same interface works for playing the next station in the radio receiver
        RadioReceiver radioTest = new RadioReceiver();
        Controller radioRemote = radioTest.createRemoteController();
        Controller radioVoice = radioTest.createVoiceController();

        radioRemote.next();
        radioVoice.next();

    }
}
