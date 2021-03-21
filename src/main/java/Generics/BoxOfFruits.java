package Generics;

import java.util.ArrayList;

//вместо родителя у фруктов имплементация Фрутабл
public class BoxOfFruits<F extends Fruitable> {
    private ArrayList<F> list;

    //конструктор коробки
    public BoxOfFruits() {
        this.list = new ArrayList<F>();
    }

    //геттер листа
    public ArrayList<F> getList() {
        return list;
    }

    //считаем вес коробки
    public float getBoxWeight(){
        if(this.getList().size()<1) {
            return 0f;
        }
        return (this.getList().get(0).getWeight() * this.getList().size());
    }

    //второй метод подсчета веса
//    public float getBoxWeight() {
//        float weight = 0;
//        for (F t : this.getList()) {
//            weight += t.getWeight();
//        }
//        return weight;
//    }

    //средний вес фрукта в коробке
    public float takeTypeWeight() {
        return (this.getBoxWeight() / this.getList().size());
    }

    //компаре по параметрам методички c учетом особеностей флотов
    public boolean compareTo(BoxOfFruits o2) {
        if((this.getBoxWeight() - o2.getBoxWeight()) < 0.01&&(this.getBoxWeight() - o2.getBoxWeight()) > -0.01) {
            return true;
        }
        return false;
    }

    //добавляет фрукт в коробку
    public void addFruit(F fr) {
        if (this.getList().size() > 0) {
            float type = this.takeTypeWeight();
            if ((fr.getWeight() - type) < 0.001&&(fr.getWeight() - type)>-0.001) {
                this.getList().add(fr);
                System.out.println("Фрукт добавлен");
            } else {
                System.out.println("В коробке другие фрукты. Этот фрукт добавить нельзя");
            }
        } else {
            this.getList().add(fr);
            System.out.println("Фрукт добавлен");
        }
    }

    //пересыпаем фрукты в указанную коробку из имеющейся
    public void TakeFruitsToOtherBox(BoxOfFruits b) {
        if (this.getList().size() > 0) {
            if (b.getList().size() > 0) {
                if ((this.takeTypeWeight() - b.takeTypeWeight()) < 0.001) {
                    b.getList().addAll(this.getList());
                    this.getList().clear();
                    System.out.println("Фрукты пересыпаны. Коробка пуста");
                } else {
                    System.out.println("В этой коробке другой тип фруктов. Пересыпать не удалось");
                }
            } else {
                b.getList().addAll(this.getList());
                this.getList().clear();
                System.out.println("Фрукты пересыпаны. Коробка пуста");
            }
        } else {
            System.out.println("Первая коробка пуста - пересыпать нечего");
        }
    }
}
