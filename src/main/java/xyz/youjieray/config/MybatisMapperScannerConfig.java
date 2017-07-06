package xyz.youjieray.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc
 *
 * @author leihz
 * @date 2017/7/5 17:57
 */
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
public class MybatisMapperScannerConfig {




    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("xyz.youjieray.task.mapper");
        return mapperScannerConfigurer;
    }

}