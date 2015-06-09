package com.edgar.codegen;

import com.mulampaka.spring.data.jdbc.codegen.CodeGenerator;
import org.junit.Test;

/**
 * Created by Administrator on 2015/6/9.
 */
public class CodeGeneratorTest {
    private CodeGenerator generator;

    public CodeGeneratorTest() {

    }

    @Test
    public void generate() throws Exception {
        generator = new CodeGenerator();
        generator.setPropertiesFile("src/test/resources/codegenerator.properties");
        generator.generate();
    }
}
