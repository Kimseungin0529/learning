package project.learning.java.string_문자열;

import java.util.*;
import java.util.stream.Collectors;

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

        HashMap<String, Nodes> map = new HashMap<>();
        List<Nodes> collect = map.values().stream().sorted()
                .collect(Collectors.toList());



    }

    static class Nodes{
        int total = 0;
        List<Node> list = new ArrayList<>();

        void add(Node node){
            list.add(node);
            total += node.count;
        }

        void sort(){
            list.sort((o1, o2) -> -o1.index + o2.index);
        }
    }

    static class Node{
        int index;
        int count;
        Node(int count, int index){
            this.count = count;
            this.index = index;
        }
    }

}
