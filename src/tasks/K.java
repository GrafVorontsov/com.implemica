package tasks;

public class K {
    public static void main(String[] args) {
        int count = 20;
        char[] str = new char[count * 2];
        String s = String.copyValueOf(str);
        System.out.println(s);
    }
}
