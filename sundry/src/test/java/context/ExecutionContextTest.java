package context;

import exception.AppException;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2014/11/14.
 */
public class ExecutionContextTest {

    @Test
    public void test1() {
        ExecutionContext.clear();
        ExecutionContext.pre("ExecutionContextExample", "main", null);
        level1();
        ExecutionContext.post();
        log(ExecutionContext.root());
    }

    @Test
    public void test2() {
        ExecutionContext.clear();
        ExecutionContext.pre("ExecutionContextExample", "main", null);
        try {
            level4();
        } catch(Exception e){
            log(e);
        }
    }

    private static void level1() throws AppException {
        ExecutionContext.pre("ExecutionContextExample", "level1", null);
        level2();
        ExecutionContext.post();
    }

    private static void level2() throws AppException{
        ExecutionContext.pre("ExecutionContextExample", "level2-1",
                null);
        level3();
        ExecutionContext.post();
    }

    private static void level3() throws AppException {
        ExecutionContext.pre("ExecutionContextExample", "level3-1",
                null);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        throw new NullPointerException("null");
        ExecutionContext.post();
    }

    private static void level4() throws AppException {
        ExecutionContext.pre("ExecutionContextExample", "level1", null);
        level5();
        ExecutionContext.post();
    }

    private static void level5() throws AppException {
        ExecutionContext.pre("ExecutionContextExample", "level3-1",
                null);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("null");
    }

    private static void log(ExecutionContext.Node node) {
        for (int i = 0; i < node.getLevel(); i ++) {
            System.out.print(" ");
        }
        if (node.getParent() != null) {
            System.out.print("└── " );
        }
        System.out.println(node.getContextId() + " : " + node.getTarget() + " : " + node.getArgs() + ",耗时:" + node.getTotalTime());
        for (ExecutionContext.Node child : node.getChildren()) {
            log(child);
        }
    }

    private static void log(Exception e) {
        System.out.println(e.getMessage());
//        ExecutionContext.post();
        log(ExecutionContext.root());
    }
}
