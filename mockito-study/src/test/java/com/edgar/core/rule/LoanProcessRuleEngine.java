package com.edgar.core.rule;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoanProcessRuleEngine extends SpringRuleEngine {
    public static final SpringRuleEngine getEngine(String name) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring/SpringRuleEngineContext.xml");
        return (SpringRuleEngine) context.getBean(name);
    }
}