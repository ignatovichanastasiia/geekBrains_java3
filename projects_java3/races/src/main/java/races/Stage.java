package races;

//АБСТРАКЦИЯ ДЛЯ ЧАСТЕЙ ТРАССЫ
public abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public int getLength(){
        return length;

    }

    public abstract long go(Car c);
}

