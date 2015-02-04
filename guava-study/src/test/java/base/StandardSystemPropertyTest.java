package base;

import com.google.common.base.StandardSystemProperty;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/4.
 */
public class StandardSystemPropertyTest {

    @Test
    public void test() {
        System.out.println(StandardSystemProperty.JAVA_VERSION);
        System.out.println(StandardSystemProperty.JAVA_VENDOR);
        System.out.println(StandardSystemProperty.JAVA_VENDOR_URL);
        System.out.println(StandardSystemProperty.JAVA_HOME);
        System.out.println(StandardSystemProperty.JAVA_VM_SPECIFICATION_VERSION);
        System.out.println(StandardSystemProperty.JAVA_VM_SPECIFICATION_VENDOR);
        System.out.println(StandardSystemProperty.JAVA_VM_SPECIFICATION_NAME);

        System.out.println(StandardSystemProperty.JAVA_VM_VERSION);
        System.out.println(StandardSystemProperty.JAVA_VM_VENDOR);
        System.out.println(StandardSystemProperty.JAVA_SPECIFICATION_VERSION);
        System.out.println(StandardSystemProperty.JAVA_SPECIFICATION_VENDOR);
        System.out.println(StandardSystemProperty.JAVA_SPECIFICATION_NAME);
        System.out.println(StandardSystemProperty.JAVA_CLASS_VERSION);
        System.out.println(StandardSystemProperty.JAVA_CLASS_PATH);
        System.out.println(StandardSystemProperty.JAVA_LIBRARY_PATH);
        System.out.println(StandardSystemProperty.JAVA_IO_TMPDIR);
        System.out.println(StandardSystemProperty.JAVA_COMPILER);
        System.out.println(StandardSystemProperty.JAVA_EXT_DIRS);
        System.out.println(StandardSystemProperty.OS_NAME);
        System.out.println(StandardSystemProperty.OS_VERSION);
        System.out.println(StandardSystemProperty.OS_ARCH);
        System.out.println(StandardSystemProperty.FILE_SEPARATOR);
        System.out.println(StandardSystemProperty.PATH_SEPARATOR);
        System.out.println(StandardSystemProperty.LINE_SEPARATOR);
        System.out.println(StandardSystemProperty.USER_NAME);
        System.out.println(StandardSystemProperty.USER_HOME);
        System.out.println(StandardSystemProperty.USER_DIR);
    }
}
