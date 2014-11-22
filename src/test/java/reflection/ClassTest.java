package reflection;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.List;


/**
 * Created by Edgar on 14-11-22.
 */
public class ClassTest {

    @Test
    public void testClass() throws ClassNotFoundException {
        Class<Integer> clazz = Integer.class;
        Class<Integer> clazz2 = (Class<Integer>) Class.forName("java.lang.Integer");

        //类名
        String className = clazz.getName();
        String simpleClassName = clazz.getSimpleName();
        //Modifiers
        int modifier = clazz.getModifiers();
        Modifier.isAbstract(modifier);
        Modifier.isFinal(modifier);
        Modifier.isInterface(modifier);
        Modifier.isNative(modifier);
        Modifier.isPrivate(modifier);
        Modifier.isProtected(modifier);
        Modifier.isPublic(modifier);
        Modifier.isStatic(modifier);
        Modifier.isStrict(modifier);
        Modifier.isSynchronized(modifier);
        Modifier.isTransient(modifier);
        Modifier.isVolatile(modifier);
        //包
        Package pack = clazz.getPackage();
        //父类
        Class superclass = clazz.getSuperclass();
        //接口
        Class[] interfaces = clazz.getInterfaces();
        //构造函数
        Constructor[] constructors = clazz.getConstructors();
        //方法
        Method[] methods = clazz.getMethods();
        //属性
        Field[] fields = clazz.getFields();
        //注解
        Annotation[] annotations = clazz.getAnnotations();
    }

    @Test
    public void testConstructor() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        //返回构造函数的数组
        Constructor[] constructor = String.class.getConstructors();
        //根据参数类型返回构造函数
        Constructor<String> constructor1 = String.class.getConstructor(String.class);

        //返回参数类型的数组
        Class[] types = constructor[0].getParameterTypes();
        //构造一个对象
        String str = constructor1.newInstance("aa");
    }

    @Test
    public void testField() throws NoSuchFieldException, IllegalAccessException {
        //public字段
        Field[] fields = SysUser.class.getFields();
        //返回具体的字段
        Field field = SysUser.class.getField("userId");
        //字段名
        field.getName();
        //类型
        field.getType();
        //get
        SysUser sysUser = new SysUser();
        Object usrId = field.get(sysUser);
        //set
        field.set(sysUser, usrId);

        //private
        Field privateField = SysUser.class.getDeclaredField("userId");
        privateField.setAccessible(true);

    }

    @Test
    public void testMethod() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        //public方法
        Method[] publicMethod = String.class.getMethods();
        //返回具体的方法
        Method method = String.class.getMethod("substring", Integer.class);
        String.class.getMethod("toLowerCase");
        //参数
        Class[] parameterTypes = method.getParameterTypes();
        //返回类型
        Class returnType = method.getReturnType();
        //方法调用
        Object returnValue = method.invoke("aa", 1);
        //静态方法
        Method method2 = String.class.getMethod("valueOf", Boolean.class);
        Object returnValue2 = method2.invoke(null, true);

        //private
        Method privateMethod = SysUser.class.getDeclaredMethod("getFullName");
        privateMethod.setAccessible(true);
    }

    @Test
    public void printGettersSetters() {
        Method[] methods = SysUser.class.getMethods();
        for (Method method : methods) {
            if (isGet(method)) {
                System.out.println(method.getName());
            }
            if (isGet(method)) {
                System.out.println(method.getName());
            }
        }
    }

    @Test
    public void testAnnotation() {
        //类
        Annotation[] annotations = SysUser.class.getAnnotations();
        for(Annotation annotation : annotations){
            if(annotation instanceof MyAnnotation){
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name: " + myAnnotation.name());
                System.out.println("value: " + myAnnotation.value());
            }
        }
        //指定注解类型
        Annotation annotation = SysUser.class.getAnnotation(MyAnnotation.class);

        //方法、字段都可以获得注解
    }

    @Test
    public void testGenericMethodReturn() throws NoSuchMethodException {
        Method method = MyClass.class.getMethod("getStringList");
        System.out.println(method.getReturnType());
        Type returnType = method.getGenericReturnType();
        System.out.println(returnType);
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type[] types = parameterizedType.getActualTypeArguments();
            for (Type type : types) {
                Class typeArgClass = (Class) type;
                System.out.println("typeArgClass = " + typeArgClass);
            }
        }
    }

    @Test
    public void testGenericMethodParameter() throws NoSuchMethodException {
        Method method = MyClass.class.getMethod("setStringList", List.class);
        Type[] parameterTypes = method.getGenericParameterTypes();
        for (Type pType : parameterTypes)
        if (pType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) pType;
            Type[] types = parameterizedType.getActualTypeArguments();
            for (Type type : types) {
                Class typeArgClass = (Class) type;
                System.out.println("typeArgClass = " + typeArgClass);
            }
        }
    }

    @Test
    public void testGenericFieldType() throws NoSuchMethodException, NoSuchFieldException {
        Field field = MyClass.class.getField("stringList");
        Type returnType = field.getGenericType();
        System.out.println(returnType);
        if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) returnType;
            Type[] types = parameterizedType.getActualTypeArguments();
            for (Type type : types) {
                Class typeArgClass = (Class) type;
                System.out.println("typeArgClass = " + typeArgClass);
            }
        }
    }

    @Test
    public void testArray() throws NoSuchMethodException, NoSuchFieldException, ClassNotFoundException {
        //创建数组
        int[] intArray = (int[]) Array.newInstance(int.class, 3);

        //访问数组
        Array.set(intArray, 0, 123);
        Array.set(intArray, 1, 456);
        Array.set(intArray, 2, 789);

        System.out.println("intArray[0] = " + Array.get(intArray, 0));
        System.out.println("intArray[1] = " + Array.get(intArray, 1));
        System.out.println("intArray[2] = " + Array.get(intArray, 2));

        //get Class
        Class stringArrayClass = String[].class;
        System.out.println(stringArrayClass);
        Class intArray2 = Class.forName("[I");
        Class stringArrayClass2 = Class.forName("[Ljava.lang.String;");

        Class stringArrayClass3 = Array.newInstance(String.class, 0).getClass();
        System.out.println("is array: " + stringArrayClass3.isArray());

        //Component Type
        String[] strings = new String[3];
        Class stringArrayClass4 = strings.getClass();
        System.out.println(stringArrayClass4.getComponentType());

    }

    private boolean isGet(Method method) {
        if (!method.getName().startsWith("get")) {
            return false;
        }
        if (method.getName().length() <= 3) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (method.getReturnType() == Void.class) {
            return false;
        }
        return true;
    }

    private boolean isSet(Method method) {
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getName().length() <= 3) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

}
