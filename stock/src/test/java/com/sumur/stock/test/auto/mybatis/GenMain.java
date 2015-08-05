package com.sumur.stock.test.auto.mybatis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis自动导入.
 * @author Li mao sen
 *
 */
public class GenMain {
	
	@Test
    public void testDemo() {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        String genCfg = "/com/sumur/stock/test/auto/mybatis/conf/mbgConfiguration.xml";
        File configFile = new File(GenMain.class.getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = null;
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
