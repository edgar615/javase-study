package exception;

//public class ExceptionTest {
//
//    protected ExceptionHandler exceptionHandler = new ExceptionHandler() {
//        public void handle(String context, int code,
//                           String message, Throwable t) {
//
//            if (!(t instanceof AppException)) {
//                throw new AppException(
//                        context, AppErrorCode.NULL, message, t);
//            } else {
//                ((AppException) t).addInfo(
//                        context, AppErrorCode.NULL, message);
//            }
//        }
//
//        public void raise(String context, int code,
//                          String message) {
//            throw new AppException(
//                    context, AppErrorCode.NULL, message);
//        }
//    };
//
//    public static void main(String[] args) {
//        ExceptionTest test = new ExceptionTest();
//        try {
//            test.level1();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e);
//        }
//    }
//
//    public void level1() {
//        try {
//            level2();
//        } catch (AppException e) {
//            this.exceptionHandler.handle(
//                    "L1", 100, "Error in level 1, calling level 2", e);
//            throw e;
//        }
//    }
//
//    public void level2() {
//        try {
//            level3();
//        } catch (AppException e) {
//            this.exceptionHandler.handle(
//                    "L2", 101, "Error in level 2, calling level 3", e);
//            throw e;
//        }
//    }
//
//    public void level3() {
//        try {
//            level4();
//        } catch (Exception e) {
//            this.exceptionHandler.handle(
//                    "L3", 103, "Error at level 3", e);
//        }
//    }
//
//    public void level4() {
//        throw new IllegalArgumentException("incorrect argument passed");
//    }
//
//}