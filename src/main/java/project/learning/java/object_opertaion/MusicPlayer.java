package project.learning.java.object_opertaion;

public class MusicPlayer {
    private int volume;
    private boolean isOn;

    public void turnOn() {
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
    }

    public void volumeUp() {
        volume += 1;
    }

    public void volumeDown() {
        volume -= 1;
    }

    public void showStatus() {
        System.out.println("음악 플레이어 상태 확인");
        if (isOn) {
            System.out.println("음악 플레이어 ON" + ", 볼륨:" + volume);
        } else {
            System.out.println("음악 플레이어 OFF");
        }
    }

}