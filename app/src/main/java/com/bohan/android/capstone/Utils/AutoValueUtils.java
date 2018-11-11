package com.bohan.android.capstone.Utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bo Han.
 * This utils class is for returning the method names of AutoValue classes
 */
public class AutoValueUtils {

    public static String autoVauleMethodsList(Class<?> type) {

        final String SEPARATOR = ",";
        final List<Method> methods = Arrays.asList(type.getDeclaredMethods());
        StringBuilder result = new StringBuilder();

        Collections.sort(methods, (m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));

        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if (Modifier.isAbstract(modifiers)) {
                result.append(method.getName()).append(SEPARATOR);
            }
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }
}
