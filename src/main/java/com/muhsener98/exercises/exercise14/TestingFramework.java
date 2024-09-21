package com.muhsener98.exercises.exercise14;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public class TestingFramework {

    public void runTestSuite(Class<?> testClass) throws Throwable {
        Constructor<?> constructor = testClass.getDeclaredConstructor();
        Object testObject = constructor.newInstance();

        Method[] methods = testClass.getDeclaredMethods();
        Method beforeClassMethod = findMethodByName(methods, "beforeClass");
        if (beforeClassMethod != null && beforeClassMethod.getParameterCount() == 0 && beforeClassMethod.getReturnType().equals(void.class))
            beforeClassMethod.invoke(null);

        Method setupTestMethod = findMethodByName(methods , "setupTest");
        List<Method> testMethods = findMethodByPrefix(methods , "test");
        for(Method testMethod : testMethods){
            if(setupTestMethod != null)
                setupTestMethod.invoke(testObject);
            testMethod.invoke(testObject);
        }

        Method afterClassMethod = findMethodByName(methods , "afterClass");
        if(afterClassMethod != null)
            afterClassMethod.invoke(null);


    }

    private Method findMethodByName(Method[] methods, String name) {
        return Arrays.stream(methods).filter(method -> method.getName().equals(name)).findFirst().orElse(null);
    }

    private List<Method> findMethodByPrefix(Method[] methods, String prefix) {
        return Arrays.stream(methods).filter(method -> method.getName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Throwable {
        TestingFramework testingFramework = new TestingFramework();
        testingFramework.runTestSuite(TestClass.class);

    }
}
