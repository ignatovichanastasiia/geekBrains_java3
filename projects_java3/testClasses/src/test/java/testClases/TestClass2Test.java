package testClases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestClass2Test {
    private TestClass2 tc2;
    private Integer[] arr1 = {3, 5, 6, 7, 8, 4, 1, 7};
    private Integer[] resArr1 = {1, 7};
    private Integer[] arr2 = {7, 8, 9, 1, 4, 4, 2, 2};
    private Integer[] resArr2 = {2, 2};
    private Integer[] arr3 = {4, 5, 6, 7, 4, 3, 2, 1};
    private Integer[] resArr3 = {3, 2, 1};
    private Integer[] arr4 = {4, 4, 4, 4, 4, 4};
    private Integer[] arr5 = {4, 4, 4, 4, 4, 1};

    @Before
    public void init() {
        TestClass2 tc2 = new TestClass2();
    }

    @Test
    public void test1() {
        Assert.assertArrayEquals(resArr1, tc2.arrCut2(arr1));
    }

    @Test
    public void test2() {
        Assert.assertArrayEquals(resArr2, tc2.arrCut2(arr2));
    }

    @Test
    public void test3() {
        Assert.assertArrayEquals(resArr3, tc2.arrCut2(arr3));
    }

    @Test
    public void test4() {
        Assert.assertFalse(tc2.arrOnlyNumbers(arr3));
    }

    @Test
    public void test5() {
        Assert.assertFalse(tc2.arrOnlyNumbers(arr4));
    }

    @Test
    public void test6() {
        Assert.assertTrue(tc2.arrOnlyNumbers(arr5));
    }
}