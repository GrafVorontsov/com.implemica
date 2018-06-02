package tasks;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        System.out.print("Please input integer value: ");
        Scanner scanner = new Scanner(System.in);
        int number;

        try {
            number = scanner.nextInt();  //get number from input
            Task1 task1 = new Task1();

            System.out.println(task1.generateParens(number));  //get correct parenthesis combination
            System.out.println(task1.generateParens(number).size());  //count correct parenthesis combination
        }catch (InputMismatchException i){  //catch the exception if input Wrong value
            System.out.println("Wrong value");
            main(args); //reload method
        }
    }

    public void addParen(ArrayList<String> list, int leftRem, int rightRem, char[] str, int count) {
        if (leftRem < 0 || rightRem < leftRem) return; // not corrected

        if (leftRem == 0 && rightRem == 0) { /* no more left brackets */
            String s = String.copyValueOf(str);
            list.add(s);
        } else {
            /* add left parenthesis, if left parenthesis exist */
            if (leftRem > 0) {
                str[count] = '(';
                addParen(list, leftRem - 1, rightRem, str, count + 1);
            }

            /* add right parenthesis, if the expression is true */
            if (rightRem > leftRem) {
                str[count] = ')';
                addParen(list, leftRem, rightRem - 1, str, count + 1);
            }
        }
    }
    public ArrayList<String> generateParens(int count) {
        char[] str = new char[count * 2];
        ArrayList<String> list = new ArrayList<String>();
        addParen(list, count, count, str, 0);
        return list;
    }
}
