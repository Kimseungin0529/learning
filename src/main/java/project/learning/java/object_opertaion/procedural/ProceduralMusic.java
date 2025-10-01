package project.learning.java.object_opertaion.procedural;

public class ProceduralMusic {
    /**
     * 이와 같은 형식이 절차지향 프로그래밍이다. 실제로 데이터와 기능을 외부에서 조절하고 동작시킨다. '무엇을' 보다 '어떻게'에 초점이 맞춰져 있다.
     * 유지보수와 생산성 측면에서는 객체지향이 유리하다. 변경이 있어도 객체마다 책임을 가지고 있기에 다른 코드에 영향을 끼치지 않는다.
     * 이를 사용하는 입장에서는 캡슐화된 객체만 알면 되지, 객체의 데이터와 같은 내부를 알 필요 없다.
     * 하지만 절차지향은 모든 것을 다 알아야 한다.
     * 절차지향은 작은 프로젝트나 단순한 작업에는 적합할 수 있지만, 규모가 커지고 복잡해질수록 유지보수와 확장성이 어려워진다.
     * 만약 음악 플레이어 볼륨을 출력하는 문장이 바뀐다면? 존재하는 모든 메소드를 전부 변경해야 한다.
     *
     * 조금 더 개선해서 isOn, volume 이란 필드값만 MusicPlayer 라는 객체에 담으면 괜찮을까? 그렇지 않다. 여전히 기능과 데이터를 외부에서 조작하고 있다.
     * 데이터 자체만 객체에 속하지 볼륨업, 볼륨다운, 상태 확인 등 기능에 대한 요소는 여전히 ProceduralMusic 에 있다.
     * 데이터(isOn, volume)과 기능(메소드)가 함께 묶인 객체들로 구성되어야 객체지향이라 할 수 있다.
     * 객체지향은 단순히 클래스를 만들고 객체를 생성하는 것이 아니다. 데이터와 기능이 함께 묶여 캡슐화되고 추상화되어야 한다.
     *
     * 객체지향을 적용한 예시는 MusicPlayerMain.java, MusicPlayer.java 를 참고하자.
     */
    public static void main(String[] args) {
        boolean isOn = false;
        int volume = 0;

        isOn = true;
        System.out.println("음악 플레이어를 시작합니다");

        volume += 1;
        System.out.println("음악 플레이어 볼륨:" + volume);

        volume += 1;
        System.out.println("음악 플레이어 볼륨:" + volume);

        volume -= 1;
        System.out.println("음악 플레이어 볼륨:" + volume);

        System.out.println("음악 플레이어 상태 확인");
        if(isOn) {
            System.out.println("음악 플레이어 ON" + ", 볼륨:" + volume);
        }else{
            System.out.println("음악 플레이어 OFF");
        }
        isOn = false;
        System.out.println("음악 플레이어를 종료합니다");
    }

}
