package reflection;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Edgar on 14-11-22.
 */
public class JavaBeanTest {

    @Test
    public void testPropertyDescriptor() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("userId", SysUser.class);
        System.out.println(propertyDescriptor.getName());
        SysUser sysUser = new SysUser();
        sysUser.setUserId(1);
        //read
        Method readMethod = propertyDescriptor.getReadMethod();
        System.out.println(readMethod.invoke(sysUser));
        //set
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(sysUser, 2);
        System.out.println(readMethod.invoke(sysUser));
    }

    @Test
    public void testIntrospector() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(SysUser.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            System.out.println(propertyDescriptor.getName());
        }

        System.out.println(Introspector.decapitalize("SystemTime"));
    }
}
