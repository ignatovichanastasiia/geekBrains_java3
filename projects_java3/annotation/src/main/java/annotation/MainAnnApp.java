package annotation;

public class MainAnnApp {
    //предполагается, что сначала выполняются методы before, затем test, затем after, вне зависимости от приоритетов.
    // Приоритеты работают в пределах одного типа;
    public static void main(String[] args) {
        AnnotationApp.start(MainAnnApp.class);
    }

    @MyBeforeAnnotation (priority = 1)
    public static void beforeTest() {
        System.out.println("Some before testing prepared");
    }

    @MyTestAnnotation (priority = 1)
    public static void test1() {
        System.out.println("test1");
    }

    @MyTestAnnotation (priority = 1)
    public static void test2() {
        System.out.println("test2");
    }

    @MyTestAnnotation (priority = 2)
    public static void test3() {
        System.out.println("test3");
    }

    @MyTestAnnotation (priority = 4)
    public static void test4() {
        System.out.println("test4");
    }

    @MyAfterAnnotation (priority = 2)
    public static void afterTest1() {
        System.out.println("Some after testing closing");
    }

//    @MyAfterAnnotation (priority = 2)
//    public static void afterTest2() {
//        System.out.println("Some after testing closing");
//    }
}
