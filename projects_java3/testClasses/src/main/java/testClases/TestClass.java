package testClases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestClass {
    private static final Integer KEY = 4;
    private static final Integer[] KEYS = {1,4};
    private static int count;
    private static List<Integer> list2;

    public static Integer[] arrCut1(Integer[] arr) {
        List<Integer> list = new ArrayList(Arrays.asList(arr));
        if(!list.contains(KEY)) throw new RuntimeException("Cut1: Нет ключевого значения.");
        Collections.reverse(list);
        int i = list.size() - (list.indexOf(KEY));
        Collections.reverse(list);
        count = 0;
        List<Integer> list2 = new ArrayList<>();
        list.stream().forEach(a -> {
            count++;
            if(count>i)list2.add(a);
        });
        Integer[] arr2 = new Integer[list2.size()];
        list2.toArray(arr2);
        count = 0;
        return arr2;
    }

    public static boolean arrWithNumbers(Integer[] arr){
        List<Integer> list = new ArrayList<>(Arrays.asList(arr));
        List<Integer> keys = new ArrayList<>(Arrays.asList(KEYS));
        if(!list.containsAll(keys)) return false;
        return true;
    }



}
