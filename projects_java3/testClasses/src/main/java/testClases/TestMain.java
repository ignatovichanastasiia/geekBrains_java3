package testClases;

import org.junit.Assert;
import org.junit.Test;

public class TestMain {
    private TestClass tc;
    private TestClass2 tc2;
    private Integer[] arr1 = {3, 5, 6, 7, 8, 4, 1, 7};
    private Integer[] resArr1 = {1, 7};
    private Integer[] arr2 = {7, 8, 9, 1, 4, 4, 2, 2};
    private Integer[] resArr2 = {2, 2};
    private Integer[] arr3 = {4, 5, 6, 7, 4, 3, 2, 1};
    private Integer[] resArr3 = {3, 2, 1};
    private Integer[] arr4 = {4, 5, 6, 7, 4, 3, 2};

    @Test
    public void test1() {
        TestClass tc = new TestClass();
        Assert.assertArrayEquals(resArr1, tc.arrCut1(arr1));
    }

    @Test
    public void test2() {
        TestClass tc = new TestClass();
        Assert.assertArrayEquals(resArr2, tc.arrCut1(arr2));
    }

    @Test
    public void test3() {
        TestClass tc = new TestClass();
        Assert.assertArrayEquals(resArr3, tc.arrCut1(arr3));
    }

    @Test
    public void test4() {
        TestClass2 tc2 = new TestClass2();
        Assert.assertArrayEquals(resArr1, tc2.arrCut2(arr1));
    }
    @Test
    public void test5() {
        TestClass2 tc2 = new TestClass2();
        Assert.assertArrayEquals(resArr2, tc2.arrCut2(arr2));
    }
    @Test
    public void test6() {
        TestClass2 tc2 = new TestClass2();
        Assert.assertArrayEquals(resArr3, tc2.arrCut2(arr3));
    }

    @Test
    public void test7() {
        TestClass tc = new TestClass();
        Assert.assertTrue(tc.arrWithNumbers(arr1));
    }

    @Test
    public void test8() {
        TestClass tc = new TestClass();
        Assert.assertFalse(tc.arrWithNumbers(arr4));
    }
}
