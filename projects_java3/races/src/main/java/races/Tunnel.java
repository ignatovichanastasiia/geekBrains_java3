package races;

public class Tunnel extends Stage {
    long t;
    long waitTime;
    long startTime;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    //ПРОХОЖДЕНИЕ ТОННЕЛЯ(ЧАСТЬ ТРАССЫ)
    @Override
    public long go(Car c) {
        try {
            try {
                startTime = System.currentTimeMillis();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                //ВХОД В ТОННЕЛЬ ДОПИСАТЬ!
                waitTime = System.currentTimeMillis() - startTime;
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(t = time(c));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (t+waitTime);
    }

    public long time(Car c){
        return (length / c.getSpeed() * 1000);
    }
}
