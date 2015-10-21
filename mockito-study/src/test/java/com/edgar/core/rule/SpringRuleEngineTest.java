package com.edgar.core.rule;//package com.edgar.core.rule;
//
//import junit.framework.TestCase;
//
//public class SpringRuleEngineTest extends TestCase {
//
//    public void testSuccessfulFlow() throws Exception {
//        SpringRuleEngine engine = LoanProcessRuleEngine
//                .getEngine("SharkysExpressLoansApplicationProcessor");
//        LoanApplication application = new LoanApplication();
//        application.setFirstName("John");
//        application.setLastName("Doe");
//        application.setStateCode("TX");
//        application.setExpences(4500);
//        application.setIncome(7000);
//        engine.processRequest(application);
//        assertEquals(LoanApplication.APPROVED, application.getStatus());
//    }
//
//    public void testInvalidState() throws Exception {
//        SpringRuleEngine engine = LoanProcessRuleEngine
//                .getEngine("SharkysExpressLoansApplicationProcessor");
//        LoanApplication application = new LoanApplication();
//        application.setFirstName("John");
//        application.setLastName("Doe");
//        application.setStateCode("OK");
//        application.setExpences(4500);
//        application.setIncome(7000);
//        engine.processRequest(application);
//        assertEquals(LoanApplication.INVALID_STATE, application.getStatus());
//    }
//
//    public void testInvalidRatio() throws Exception {
//        SpringRuleEngine engine = LoanProcessRuleEngine
//                .getEngine("SharkysExpressLoansApplicationProcessor");
//        LoanApplication application = new LoanApplication();
//        application.setFirstName("John");
//        application.setLastName("Doe");
//        application.setStateCode("MI");
//        application.setIncome(7000);
//        application.setExpences(0.80 * 7000); // too high
//        engine.processRequest(application);
//        assertEquals(LoanApplication.INVALID_INCOME_EXPENSE_RATIO,
//                application.getStatus());
//    }
//
//    public void testIncompleteApplication() throws Exception {
//        SpringRuleEngine engine = LoanProcessRuleEngine
//                .getEngine("SharkysExpressLoansApplicationProcessor");
//        LoanApplication application = new LoanApplication();
//        engine.processRequest(application);
//        assertEquals(LoanApplication.INSUFFICIENT_DATA, application.getStatus());
//    }
//
//}
