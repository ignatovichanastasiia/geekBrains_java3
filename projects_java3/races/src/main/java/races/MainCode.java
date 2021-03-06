package races;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MainCode {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        //ТРАССА
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        //МАССИВ МАШИН
        Car[] cars = new Car[CARS_COUNT];
        //СОЗДАНИЕ МАШИН
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        //ЗАПУСК МАШИН - ТРЕДС + БАРЬЕР
        CyclicBarrier cb = new CyclicBarrier(CARS_COUNT+1);
        for (int i = 0; i < cars.length; i++) {
            final int w = i;
            new Thread(() -> {
                try {
                    System.out.println(cars[w].getName() + " готовится. ");
                    Thread.sleep(500 + (int) (Math.random() * 800));
                    cb.await();
                    System.out.println(cars[w].getName() + " стартовал. ");
                    for (int j = 0; j < race.getStages().size(); j++) {
                        long t = race.getStages().get(j).go(cars[w]);
                        cars[w].setT(cars[w].getT() + t);
                    }
                    cb.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            Car win = Car.winner(cars);
            System.out.println("Победитель: " + win.getName() + " с результатом " + win.getT());
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}





