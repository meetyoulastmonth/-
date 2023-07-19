package com.mark.common;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {
    public static String scanner(String tip){
        Scanner scanner=new Scanner(System.in);
        StringBuilder help=new StringBuilder();
        help.append("请输入"+tip+"：");
        System.out.println(help.toString());
        if (scanner.hasNext()){
            String ipt=scanner.next();
            if (StringUtils.isNotEmpty(ipt)){
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的"+tip+"！");
    }

    public static void main(String[] args) {
        AutoGenerator mpg=new AutoGenerator();

        GlobalConfig gc=new GlobalConfig();
        String projectPath=System.getProperty("user.dir");
        gc.setOutputDir(projectPath+"/src/main/java");
        gc.setAuthor("jobob");
        gc.setOpen(false);

        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc=new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/my_blog?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("131321");
        mpg.setDataSource(dsc);

        PackageConfig pc=new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com");
        mpg.setPackageInfo(pc);
        InjectionConfig cfg= new InjectionConfig() {
            @Override
            public void initMap() {
                //to do nothing
            }
        };

        String templatPath="/templates/mapper.xml.ftl";

        List<FileOutConfig> foclist=new ArrayList<>();
        foclist.add(new FileOutConfig(templatPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath+"/src/main/resources/mapper/"+"/"+tableInfo.getEntityName()+"Mapper"+ StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(foclist);
        mpg.setCfg(cfg);

        TemplateConfig templateConfig=new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        StrategyConfig strategy=new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        //strategy.setInclude(scanner("user_record").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();


    }
}
