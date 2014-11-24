import com.edgar.db.DaoManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * Created by Administrator on 2014/11/18.
 */
public class SqlPropertiesTest {

    @Test
    public void testLoadFile() {
        DaoManager.getInstance().init("sql" + File.separator + "datasource.properties", "sql" + File.separator + "sql.properties");
        Properties properties = DaoManager.getInstance().getSqlProperties();
        Assert.assertEquals("insert into user values(?, ?, ?)", properties.getProperty("user.insert.sql"));
    }
}
