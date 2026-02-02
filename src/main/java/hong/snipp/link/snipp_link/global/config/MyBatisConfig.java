package hong.snipp.link.snipp_link.global.config;

import hong.snipp.link.snipp_link.global.interceptor.MyBatisInterceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * packageName : hong.snipp.link.snipp_link.global.config
 * fileName : MyBatisConfig
 * author : home
 * date : 2025-04-13
 * description : MyBatisConfig
 * ===========================================================
 * DATE             AUTHOR      NOTE
 * -----------------------------------------------------------
 * 2025-04-13       home        최초 생성
 * 2026-01-17       work        h2-console 이용을 위해 allowMultiQuery 주석
 */

@Component
public class MyBatisConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    // @Value("${spring.datasource.allow-multi-query}")
    // private String allowMultiQuery;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${hong.mybatis-setting.mapper-locations}")
    private String mapperLocations;

    @Value("${hong.mybatis-setting.type-aliases-package}")
    private String typeAliasesPackage;

    /**
     * @method dataSource
     * @author home
     * @date 2025-04-13
     * @deacription 데이터베이스와 연결을 위한 DataSource 객체 생성
     **/
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        // dataSourceBuilder.url(datasourceUrl + "?allowMultiQueries=" +
        // allowMultiQuery);
        dataSourceBuilder.url(datasourceUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    /**
     * @method sqlSessionFactory
     * @author home
     * @date 2025-04-13
     * @deacription SQL 세션을 관리
     **/
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        // 1. DB 정보
        factoryBean.setDataSource(dataSource);

        // 2. mybatis-config.xml 위치
        // factoryBean.setConfigLocation(new
        // PathMatchingResourcePatternResolver().getResource(configLocation));

        // 3. mapper 위치
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        factoryBean.setMapperLocations(resources);

        // 4. dto, vo 위치
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);

        // 5. plugins: interceptor
        MyBatisInterceptor MyBatisInterceptor = new MyBatisInterceptor();
        factoryBean.setPlugins(MyBatisInterceptor);

        // 6. settings
        factoryBean.setConfiguration(setConfiguration());

        return factoryBean.getObject();
    }

    /**
     * @method setConfiguration
     * @author home
     * @date 2025-04-13
     * @deacription MyBatis 설정을 정의
     **/
    public Configuration setConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
        configuration.setMapUnderscoreToCamelCase(true); // DB 컬럼명이 snake_case 일 경우에 => camelCase 값으로 자동 변환
        configuration.getTypeAliasRegistry().registerAlias("Long", Long.class);
        return configuration;
    }

    /**
     * @method transactionManager
     * @author home
     * @date 2025-04-13
     * @deacription DB 연결을 사용하는 Tx 관리자 생성
     **/
    @Bean(name = "transactionManager")
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
