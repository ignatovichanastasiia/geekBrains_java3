package Generics;

import java.util.ArrayList;

class Generics{
    public static <T> T[] changeArr(T... tArr){
        if(tArr.length>2){
            T r = tArr[0];
            tArr[0]=tArr[1];
            tArr[1] = r;
        }
        return tArr;
    }

    public static <T> void printArrT(T[] tArr){
        for (T t:tArr) {
            System.out.print("["+t+"]");
        }
        System.out.println(" ");
    }

    public static <T> ArrayList<T> arrToList(T[] tArr){
        ArrayList<T> list = new ArrayList<>();
        for (T t:tArr) {
             list.add(t);
        }
        return list;
    }
}

public class GenericsApp{
    public static void main(String[] args) {
        System.out.println("Меняем 0ой и 1ый элементы");
        String[] arr1 = {"string1","string2","string3"};
        Generics.printArrT(Generics.changeArr(arr1));
        Integer[] arr2 = {1,2,3,4};
        Generics.printArrT(Generics.changeArr(arr2));

        System.out.println("Делаем массив Арайлистом");
        ArrayList<String> l1 = Generics.arrToList(arr1);
        System.out.println(l1.toString());
        ArrayList<Integer> l2 = Generics.arrToList(arr2);
        System.out.println(l2.toString());

        System.out.println("Создаем коробки");
        BoxOfFruits box1 = new BoxOfFruits();
        BoxOfFruits box2 = new BoxOfFruits();
        BoxOfFruits box3 = new BoxOfFruits();

        System.out.println("Cравниваем пустые коробки");
        System.out.println(box1.compareTo(box2));

        System.out.println("Добавляем 10 яблок");
        for(int i = 0;i<10;i++){
            box1.addFruit(new Apple());
        }

        System.out.println("Пытаемся пихнуть апельсин к яблокам");
        box1.addFruit(new Orange());

        System.out.println("Сравниваем коробку с яблоками с пустой");
        System.out.println(box1.compareTo(box2));

        System.out.println("Пересыпаем из первой коробки во вторую яблоки");
        box1.TakeFruitsToOtherBox(box2);
        System.out.println("Первая коробка: "+box1.getList().size()+"шт");
        System.out.println("Вторая коробка: "+box2.getList().size()+"шт");

        System.out.println("Добавляем в пустую первую коробку 10 яблок");
        for(int i = 0;i<10;i++){
            box1.addFruit(new Apple());
        }

        System.out.println("Сравниваем 2 коробки, в которых по 10 яблок");
        System.out.println(box1.compareTo(box2));
        System.out.println("Пересыпаем из первой коробки во вторую яблоки");
        box1.TakeFruitsToOtherBox(box2);
        System.out.println("Первая коробка: "+box1.getList().size()+"шт");
        System.out.println("Вторая коробка: "+box2.getList().size()+"шт");

        System.out.println("Добавляем в пустую первую коробку 10 апельсинов");
        for(int i = 0;i<10;i++){
            box1.addFruit(new Orange());
        }
        System.out.println("Пытаемся пересыпать из первой коробки во вторую апельсины");
        box1.TakeFruitsToOtherBox(box2);
        System.out.println("Первая коробка: "+box1.getList().size()+"шт");
        System.out.println("Вторая коробка: "+box2.getList().size()+"шт");

        System.out.println("Пересыпаем из первой коробки в третью апельсины");
        box1.TakeFruitsToOtherBox(box3);
        System.out.println("Первая коробка: "+box1.getList().size()+"шт");
        System.out.println("Третья коробка: "+box3.getList().size()+"шт");

        System.out.println("Добавляем в пустую первую коробку 10 апельсинов");
        for(int i = 0;i<10;i++){
            box1.addFruit(new Orange());
        }
        System.out.println("Сравниваем 2 коробки, в которых по 10 апельсинов");
        System.out.println(box1.compareTo(box2));

        System.out.println("Пытаемся пихнуть в апельсины яблоко");
        box1.addFruit(new Apple());

        System.out.println("Первая коробка: "+box1.getList().size()+"шт");
        System.out.println("Вторая коробка: "+box1.getList().size()+"шт");
        System.out.println("Третья коробка: "+box1.getList().size()+"шт");
        System.out.println("Сравниваем 2ую и 3юю коробки, в которых разные фрукты по 10 шт. Т.е. разный вес");
        System.out.println(box2.compareTo(box3));
    }
}
