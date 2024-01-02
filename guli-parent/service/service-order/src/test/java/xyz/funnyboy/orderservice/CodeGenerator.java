package xyz.funnyboy.orderservice;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

/**
 * @author VectorX
 * @version V1.0
 * @description 代码生成器
 * @date 23/12/2023
 */
public class CodeGenerator
{
    @Test
    public void generateCode() {
        // 1、创建代码生成器
        final AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        final GlobalConfig gc = new GlobalConfig();
        final String property = System.getProperty("user.dir");
        System.out.println(property);
        gc.setOutputDir(property + "/src/main/java");
        gc.setAuthor("VectorX");
        // 生成后是否打开资源管理器
        gc.setOpen(false);
        // 重新生成时文件是否覆盖FF
        gc.setFileOverride(false);
        // 去掉Service接口的首字母I
        gc.setServiceName("%sService");
        // 主键策略
        gc.setIdType(IdType.ID_WORKER_STR);
        // 定义生成的实体类中日期类型
        gc.setDateType(DateType.ONLY_DATE);
        // 开启 Swagger2 模式
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        final DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        final PackageConfig pc = new PackageConfig();
        pc.setModuleName("orderservice");
        pc.setParent("xyz.funnyboy");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        final StrategyConfig sc = new StrategyConfig();
        // sc.setInclude("t_order");
        sc.setInclude("t_pay_log");
        // 数据库表映射到实体的命名策略
        sc.setNaming(NamingStrategy.underline_to_camel);
        // 生成实体时去掉表前缀
        sc.setTablePrefix(pc.getModuleName() + "_");
        // 数据库表字段映射到实体的命名策略
        sc.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        sc.setEntityLombokModel(true);
        // restful api风格控制器
        sc.setRestControllerStyle(true);
        // url中驼峰转连字符
        sc.setControllerMappingHyphenStyle(true);
        // 逻辑删除字段
        sc.setLogicDeleteFieldName("isDeleted");
        mpg.setStrategy(sc);

        // 6、执行
        mpg.execute();
    }
}
