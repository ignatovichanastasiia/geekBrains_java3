package testClases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestClassTest {
    private TestClass tc;
    private Integer[] arr1 = {3, 5, 6, 7, 8, 4, 1, 7};
    private Integer[] resArr1 = {1, 7};
    private Integer[] arr2 = {7, 8, 9, 1, 4, 4, 2, 2};
    private Integer[] resArr2 = {2, 2};
    private Integer[] arr3 = {4, 5, 6, 7, 4, 3, 2, 1};
    private Integer[] resArr3 = {3, 2, 1};
    private Integer[] arr4 = {4, 5, 6, 7, 4, 3, 2};
    private Integer[] arr5 = {5, 6, 7, 3, 2};


    @Before
    public void init(){
        TestClass tc = new TestClass();
    }

    @Test
    public void test1() {
        Assert.assertArrayEquals(resArr1, tc.arrCut1(arr1));
    }

    @Test
    public void test2() {
        Assert.assertArrayEquals(resArr2, tc.arrCut1(arr2));
    }

    @Test
    public void test3() {
        Assert.assertArrayEquals(resArr3, tc.arrCut1(arr3));
    }

    @Test
    public void test4() {
        Assert.assertThrows(RuntimeException.class, (() ->{tc.arrCut1(arr5);}));
    }

    @Test
    public void test5() {
        Assert.assertTrue(tc.arrWithNumbers(arr1));
    }

    @Test
    public void test6() {
        Assert.assertFalse(tc.arrWithNumbers(arr4));
    }
}