package vladimir.gorin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class App {

    private static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (!isMap(object)) {
            final String[] LIST_PRIMITVES = new String[]{"byte", "short", "int", "long", "float", "double", "boolean", "char"};
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Set<String> nameFields=new HashSet<>();
            for (Field field : fields) {
                nameFields.add(field.getName());
            }

            for (String s : fieldsToCleanup) {
                if (!nameFields.contains(s)) {
                    throw new IllegalArgumentException();
                }
            }
            for (String s : fieldsToOutput) {
                if (!nameFields.contains(s)) {
                    throw new IllegalArgumentException();
                }
            }


            for (Field field : fields) {
                for (String s : fieldsToCleanup) {
                    if (field.getName().equals(s)) {
                        switch (field.getType().getName()) {
                            case "byte":
                                field.setByte(object, (byte) 0);
                                break;
                            case "short":
                                field.setShort(object, (short) 0);
                                break;
                            case "int":
                                field.setInt(object, 0);
                                break;
                            case "long":
                                field.setLong(object, 0);
                                break;
                            case "float":
                                field.setFloat(object, (float) 0);
                                break;
                            case "double":
                                field.setDouble(object, 0);
                                break;
                            case "boolean":
                                field.setBoolean(object, Boolean.FALSE);
                                break;
                            case "char":
                                field.setChar(object, (char) 0);
                                break;
                            default:
                                field.set(object, null);
                        }


                    }
                }


            }
            StringBuilder stringBuilder = new StringBuilder();
            boolean isPrimitive = false;

            for (Field field : fields) {
                for (String s : fieldsToOutput) {
                    if (field.getName().equals(s)) {
                        for (String list_primitve : LIST_PRIMITVES) {
                            if (list_primitve.equals(field.getType().getName())) {
                                isPrimitive = true;
                            }
                        }
                        if (isPrimitive) {
                            stringBuilder.append(String.valueOf(field.get(object)));
                        } else {
                            stringBuilder.append(field.get(object).toString());
                        }
                    }
                }
            }
        } else {

            Class<?> aClass = object.getClass();
            Class[] paramTypes = new Class[]{Object.class};
            Method remove = aClass.getMethod("remove", paramTypes);
            Method containsKey = aClass.getMethod("containsKey", paramTypes);
            Object[] args;
            for (String s : fieldsToCleanup) {
                args = new Object[]{s};
                if (!(boolean) containsKey.invoke(object,args)) {
                    throw new IllegalArgumentException("The key doesn't exist");
                }
            }
            for (String s : fieldsToOutput) {
                args = new Object[]{s};
                if (!(boolean) containsKey.invoke(object,args)) {
                    throw new IllegalArgumentException("The key doesn't exist");
                }
            }

            for (String s : fieldsToCleanup) {
                args = new Object[]{s};
                remove.invoke(object, args);
            }
            Method get = aClass.getMethod("get", paramTypes);
            for (String s : fieldsToOutput) {
                args = new Object[]{s};
                System.out.print(" " + get.invoke(object, s));
            }

        }
    }

    private static boolean isMap(Object object) {
        Class clazzMap = object.getClass();
        Class[] interfaces = clazzMap.getInterfaces();
        for (Class anInterface : interfaces) {
            if (anInterface.getSimpleName().equals("Map")) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Person person = new Person();
        Set<String> fieldsToCleanup = new HashSet<>();
        Set<String> fieldsToOutput = new HashSet<>();

        Class clazz = person.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            fieldsToCleanup.add(field.getName());
            fieldsToOutput.add(field.getName());
        }

        System.out.println(person);
        fieldsToCleanup.add("sdf");
//        App.cleanup(person, fieldsToCleanup, fieldsToOutput);

        System.out.println(person);

        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("1", 1);
        hashMap.put("2", 2);

        Set<String> strings = new HashSet<>();
        strings.add("1");
        strings.add("2");
//        strings.add("3");
        App.cleanup(hashMap, strings, strings);
        hashMap.containsKey("1");


    }
}
