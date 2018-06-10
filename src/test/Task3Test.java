package test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tasks.Task3;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class Task3Test {
    private int a, b;

    public Task3Test(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Parameterized.Parameters
    public static Collection numbers(){
        return Arrays.asList(new Object[][]{{3, 6},{100, 648}, {30, 117}, {0, 1}});
    }

    @org.junit.Test
    public void testCalculateSum() {
        int result = Task3.calculateSum(a);
        assertEquals(b, result);
    }
}
