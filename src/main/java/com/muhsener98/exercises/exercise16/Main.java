package com.muhsener98.exercises.exercise16;

import com.muhsener98.App;
import com.muhsener98.exercises.exercise16.annotation.InitializerClass;
import com.muhsener98.exercises.exercise16.annotation.InitializerMethod;
import com.muhsener98.exercises.exercise16.annotation.RetryOperation;
import com.muhsener98.exercises.exercise16.annotation.ScanPackages;
import com.muhsener98.exercises.exercise16.databases.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@ScanPackages(values = {"com.muhsener98.exercises.exercise16.databases"})
public class Main {

    public static void main(String[] args) throws Throwable {

        initialize();

    }


    private static void initialize() throws Throwable {

        ScanPackages scanPackages = Main.class.getAnnotation(ScanPackages.class);

        if(scanPackages == null || scanPackages.values().length == 0)
            return;

        String[] packageNames = scanPackages.values();

        List<Class<?>> classes = getAllClasses(packageNames);

        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(InitializerClass.class))
                continue;


            Object instance = clazz.getDeclaredConstructor().newInstance();
            List<Method> initializerMethods = getAllInitializingMethods(clazz);

            for(Method initializerMethod : initializerMethods){
                callInitializingMethod(instance , initializerMethod);
            }
        }
    }

    private static void callInitializingMethod(Object instance, Method method) throws Throwable{
        RetryOperation retryOperation = method.getAnnotation(RetryOperation.class);

        int numberOfRetries = retryOperation == null ? 0 : retryOperation.numberOfRetries();
        while(true){
            try{
                method.invoke(instance);
                break;
            }catch (InvocationTargetException e){
                Throwable targetException = e.getTargetException();
                if(numberOfRetries > 0 && Set.of(retryOperation.retryExceptions()).contains(targetException.getClass())){
                    numberOfRetries --;
                    System.out.println("Retrying...");
                    Thread.sleep(retryOperation.durationBetweenRetriesMs());
                }else if(retryOperation != null){
                    throw new Exception(retryOperation.failureMessage() , targetException);
                }else{
                    throw targetException;
                }
            }
        }
    }

    private static List<Method> getAllInitializingMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitializerMethod.class))
                methods.add(method);
        }

        return methods;
    }

    private static List<Class<?>> getAllClasses(String... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        for(String packageName : packageNames){
            String packageRelativePath = "/" + packageName.replace('.' , '/');
            URI packageUri = Main.class.getResource(packageRelativePath).toURI();
            if(packageUri.getScheme().equals("file")){
                Path packageFullPath = Paths.get(packageUri);
                classes.addAll(getAllPackageClasses(packageFullPath , packageName));
            } else if (packageUri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(packageUri, Collections.emptyMap());
                Path packageFullPathInJar = fileSystem.getPath(packageRelativePath);
               classes.addAll(getAllPackageClasses(packageFullPathInJar , packageName));
               fileSystem.close();
            }
        }
        return classes;
    }



    private static List<Class<?>> getAllPackageClasses(Path packagePath , String packageName) throws IOException, ClassNotFoundException {

        if(!Files.exists(packagePath)){
            return Collections.emptyList();
        }


        List<Path> files = Files.list(packagePath)
                .filter(Files::isRegularFile)
                .toList();



        List<Class<?>> classes = new ArrayList<>();
        for(Path filePath : files){
            String fileName = filePath.getFileName().toString();

            if(fileName.endsWith(".class")){
                String classFullName = packageName + "." + fileName.replaceFirst("\\.class$" , "");
                Class<?> clazz = Class.forName(classFullName);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
