package project.learning.java.string_literal_newstring;

public class StringPractice {

    public static void main(String[] args) {
        //  startsWith 메서드
        String url = "https://www.naver.com";
        boolean isContained = url.startsWith("https://");
        System.out.println("isContained = " + isContained);
        // length 메서드
        String[] arr = {"hello", "java", "jvm", "spring", "jpa"};
        int sum = 0;
        for(String s : arr) {
            System.out.println(s.length());
            sum += s.length();
        }
        System.out.println("sum = " + sum);
        // lastIndexOf 메서드
        String str = "hello.txt";
        int index = str.lastIndexOf(".txt");
        System.out.println("index = " + index);
        // substring 메서드
        String str1 = "hello.txt";
        String frountSubstring = str1.substring(0, 5);
        String backSubstring = str1.substring(5);
        System.out.println("first = " + frountSubstring);
        System.out.println("back = " + backSubstring);
        // index + substring 확장자 제거
        String str2 = "hello.txt";
        String ext = ".txt";
        int findIndex = str2.indexOf(ext);
        String result = str2.substring(0, findIndex);
        System.out.println("result = " + result);
    }

}
