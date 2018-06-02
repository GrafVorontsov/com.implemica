package tasks;

import java.math.BigInteger;

/**
 * Find the sum of the digits in the number 100! (i.e. 100 factorial)
 {Correct answer: 648}
 */

public class Task3 {
    private static BigInteger calculateFactorial(int n){
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; i ++){
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    static int  calculateSum(int f){
        char[] arrayNumbers = calculateFactorial(f).toString().toCharArray();
        int sum = 0;
        for (char num: arrayNumbers) {
            sum += Character.getNumericValue(num);
        }
        return sum;
    }

    public static void main(String[] args){
        System.out.println(calculateSum(100));
    }
}
