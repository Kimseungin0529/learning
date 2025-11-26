package project.learning.jpa.공유값.임베디드타입;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {


    @DisplayName("주소를 변경합니다.")
    @Test
    void test() {
        // given
        Address address1 = new Address("서울", "마포");
        User user1 = new User("짱구", address1);

        // when
        Address addressByUser1 = user1.getAddress();
        addressByUser1.updateAddress("발산");
        // then

        assertThat(addressByUser1.getCity()).isEqualTo("서울");
        assertThat(addressByUser1.getStreet()).isEqualTo("발산");

    }

    @Test
    @DisplayName("값 객체를 공유하면 안 되는 경우 - 같은 메모리 주소를 가르키기에 의도치 않은 변경이 일어날 수 있다.")
    void test1() {
        // given
        Address address1 = new Address("서울", "마포");
        User user1 = new User("짱구", address1);
        User user2 = new User("훈이", address1);

        // when
        Address addressByUser1 = user1.getAddress();
        addressByUser1.updateAddress("발산"); // user1의 주소만 바꾸려했지만 user2의 주소값도 변경된다.

        // then
        user2.getAddress().getStreet();
        assertThat(user2.getAddress().getCity()).isEqualTo("서울");
        assertThat(user2.getAddress().getStreet()).isEqualTo("발산");

        assertThat(addressByUser1.getCity()).isEqualTo("서울");
        assertThat(addressByUser1.getStreet()).isEqualTo("발산");

        System.out.println(user1.getAddress());
        System.out.println(user2.getAddress());
    }

    @Test
    @DisplayName("값 객체를 공유를 방지하는 방법 - 생성자를 통해 객체의 메모리 주소를 다르게 하자.(값, 상태만 같게 / 동등성)")
    void test2() {
        // given
        Address address1 = new Address("서울", "마포");
        User user1 = new User("짱구", address1);
        User user2 = new User("훈이", new Address(address1.getCity(), address1.getStreet()));

        // when
        Address addressByUser1 = user1.getAddress();
        addressByUser1.updateAddress("발산"); // user1의 주소를 변경해도 user2의 주소는 변경되지 않는다!

        // then
        user2.getAddress().getStreet();
        assertThat(user2.getAddress().getCity()).isEqualTo("서울");
        assertThat(user2.getAddress().getStreet()).isEqualTo("마포");

        assertThat(addressByUser1.getCity()).isEqualTo("서울");
        assertThat(addressByUser1.getStreet()).isEqualTo("발산");

        System.out.println(user1.getAddress());
        System.out.println(user2.getAddress());
    }

}