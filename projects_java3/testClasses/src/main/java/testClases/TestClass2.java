package testClases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass2 {
    private static final int KEY = 4;
    private static int count = 0;
    private static int index;

    public static Integer[] arrCut2(Integer[] arr){
        if(rightArr(arr)) return cut(arr);
        throw new RuntimeException("Cut2: Нет ключевого значения.");
    }

    private static boolean rightArr(Integer[] arr){
        for(int a: arr){
            if(a == KEY){
                count++;
            }
        }
        if(count > 0) return true;
        return false;
    }

    private static Integer[] cut(Integer[] arr){
        for(int i =0; i<arr.length; i++){
            if(arr[i] == KEY) {
                count--;
                if(count==0){
                    index = i;
                }
            }
        }
        Integer[] arr2 = new Integer[arr.length-1 -(index)];
        int k = 0;
        for(int i = index+1;i< arr.length;i++){
            arr2[k] = arr[i];
            k++;
        }
        return arr2;
    }

    public static boolean arrOnlyNumbers(Integer[] arr){
        int count1=0;
        int count4=0;
        for (Integer a: arr) {
            if(a==1) count1++;
            if(a==4) count4++;
            if(a!=1&&a!=4) return false;
        }
        if(count1==0||count4==0)return false;
        return true;
    }
}
