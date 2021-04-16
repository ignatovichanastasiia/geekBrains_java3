package testClases;

public class TestMain {
    public static void main(String[] args) {
        TestClass tc = new TestClass();
        TestClass2 tc2 = new TestClass2();
        Integer [] arr ={4,1,5};
        printArr(tc.arrCut1(arr));
        printArr(tc2.arrCut2(arr));
    }

    public static void printArr(Integer[] arr) {
        System.out.print("[");
        for (Integer i: arr) {
            System.out.printf(" %d, ",i);
        }
        System.out.print("]\n");
    }
}
