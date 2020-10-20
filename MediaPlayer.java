public interface MediaPlayer {
    void playNext();
    Controller createRemoteController();
    Controller createVoiceController();
}
