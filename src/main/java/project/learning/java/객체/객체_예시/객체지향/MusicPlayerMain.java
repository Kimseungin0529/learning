package project.learning.java.객체.객체_예시.객체지향;

public class MusicPlayerMain {
    /**
     * 이전 절차지향으로 구현한 점과 다른 점은 무엇일까?
     * - MusicPlayer 라는 클래스를 통해서 음악 플레이어를 추상화했다.
     * - MusicPlayerMain 클래스에서는 MusicPlayer 객체를 생성하고, 그 객체의 메서드를 호출하여 음악 플레이어를 조작한다.
     * - MusicPlayerMain 클래스는 음악 플레이어의 내부 상태(볼륨, 전원 상태 등)를 직접 다루지 않고, MusicPlayer 클래스가 제공하는 메서드를 통해서만 조작한다.
     * - MusicPlayerMain 클래스는 음악 플레이어의 구체적인 구현에 대해서는 알 필요가 없고, MusicPlayer 클래스가 제공하는 인터페이스만 알면 된다.
     * - MusicPlayerMain 클래스는 음악 플레이어의 상태를 출력할 때도 MusicPlayer 클래스의 showStatus 메서드를 호출한다.
     * - MusicPlayerMain 클래스는 음악 플레이어의 상태를 직접 출력하지 않고, MusicPlayer 클래스가 제공하는 메서드를 통해서만 출력한다.
     *
     * 위 복잡한 내용을 요약하면 다음과 같다.
     * MusicPlayer 객체가 데이터(정보)와 기능(메소드)을 담당한다. MusicPlayerMain 는 MusicPlayer 객체를 통해 요구만 하지, 구체적인 과정과 동작은 모른다.
     *
     * 이것이 객체지향의 힘이다. MusicPlayer 속에 데이터의 변경이 일어나도 외부에서는 몰라도 된다. 이를 캡슐화라고 하며 추상화라고도 할 수 있다.
     */
    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayer();
        // 음악 플레이어 켜기
        musicPlayer.turnOn();
        // 볼륨 증가
        musicPlayer.volumeUp();
        // 볼륨 증가
        musicPlayer.volumeUp();
        musicPlayer.volumeDown();
        musicPlayer.showStatus();
        musicPlayer.turnOff();
        musicPlayer.showStatus();
        //음악 플레이어 끄기
        musicPlayer.turnOff();
    }
}
