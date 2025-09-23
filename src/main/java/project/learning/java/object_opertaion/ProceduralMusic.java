package project.learning.java.object_opertaion;

public class ProceduralMusic {
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
