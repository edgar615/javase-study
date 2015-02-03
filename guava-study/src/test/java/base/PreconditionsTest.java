package base;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

/**
 * Created by Administrator on 2015/2/3.
 */
public class PreconditionsTest {

    @Test
    public void testCheckArugment() {
        Preconditions.checkArgument(1 > 0);
//        Preconditions.checkArgument(1 > 1);

        Preconditions.checkArgument(1 > 1, ImmutableList.of(1, 2, 3));
    }

}
