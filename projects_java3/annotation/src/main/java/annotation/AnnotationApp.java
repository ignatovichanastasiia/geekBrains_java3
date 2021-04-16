package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

public class AnnotationApp {
    private static final int maxMethodAmount = 10;
    public static Map<Integer, Method> methodMap;

    public static void start(Class c) {
        methodMap = new TreeMap<>();
        Method[] methods = c.getDeclaredMethods();
        for (Method met : methods) {
            if (met.isAnnotationPresent(MyBeforeAnnotation.class)) {
                if ((Integer) met.getDeclaredAnnotation(MyBeforeAnnotation.class).priority() >= 1 && (Integer) met.getDeclaredAnnotation(MyBeforeAnnotation.class).priority() <= maxMethodAmount) {
                    mapPut((Integer) met.getDeclaredAnnotation(MyBeforeAnnotation.class).priority() * maxMethodAmount, met);
                } else {
                    ThrowExc();
                }
            }
            if (met.isAnnotationPresent(MyTestAnnotation.class)) {
                if ((Integer) met.getDeclaredAnnotation(MyTestAnnotation.class).priority() >= 1 && (Integer) met.getDeclaredAnnotation(MyTestAnnotation.class).priority() <= maxMethodAmount) {
                    mapPut((Integer) met.getDeclaredAnnotation(MyTestAnnotation.class).priority() * 10 * maxMethodAmount, met);
                } else {
                    ThrowExc();
                }
            }
            if (met.isAnnotationPresent(MyAfterAnnotation.class)) {
                if ((Integer) met.getDeclaredAnnotation(MyAfterAnnotation.class).priority() >= 1 && (Integer) met.getDeclaredAnnotation(MyAfterAnnotation.class).priority() <= maxMethodAmount) {
                    mapPut((Integer) met.getDeclaredAnnotation(MyAfterAnnotation.class).priority() * 100 * maxMethodAmount, met);
                } else {
                    ThrowExc();
                }
            }
        }
        methodMap.forEach((k, m) -> {
            System.out.println(m);
            m.setAccessible(true);
            try {
                m.invoke(c);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static void ThrowExc() {
        throw new RuntimeException("priority is not 1-" + maxMethodAmount);
    }

    private static void mapPut(int priority, Method m) {
        int pri = priority;
        while (methodMap.containsKey(pri)) {
            pri = pri+1;
        }
        methodMap.put(pri, m);
    }
}
