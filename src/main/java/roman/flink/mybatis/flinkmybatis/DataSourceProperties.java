package roman.flink.mybatis.flinkmybatis;

import com.google.inject.name.Named;
import roman.flink.mybatis.common.properties.PropertiesManager;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.guice.datasource.druid.DruidDataSourceProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

/**
 * JDBC.driverClassName=com.mysql.cj.jdbc.Driver
 * JDBC.url=jdbc:mysql://localhost:3306/test?autoReconnect=true&autoReconnectForPools=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf8&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8
 * JDBC.username=test
 * JDBC.password=test
 * JDBC.maxActive=60
 * JDBC.initialSize=5
 * JDBC.maxWait=60000
 * JDBC.minIdle=10
 * JDBC.timeBetweenEvictionRunsMillis=60000
 * JDBC.minEvictableIdleTimeMillis=300000
 * JDBC.validationQuery=SELECT 1
 * JDBC.testWhileIdle=true
 * JDBC.testOnBorrow=false
 * JDBC.testOnReturn=false
 * JDBC.maxOpenPreparedStatements=60
 * JDBC.removeAbandoned=true
 * JDBC.removeAbandonedTimeout=1800
 * JDBC.logAbandoned=true
 */
public class DataSourceProperties extends Properties {

    private static final String MYBATIS_ENVIRONMENT_ID = "mybatis.environment.id";

    public DataSourceProperties() {
        initProperties();
    }

    private void initProperties() {
        setProperty(MYBATIS_ENVIRONMENT_ID, PropertiesManager.getString(MYBATIS_ENVIRONMENT_ID));
        Method[] methods = DruidDataSourceProvider.class.getMethods();
        Arrays.stream(methods).forEach(method -> {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            Arrays.stream(parameterAnnotations).flatMap(Arrays::stream)
                    .filter(annotation -> annotation instanceof Named)
                    .map(annotation -> (Named)annotation)
            .map(Named::value)
            .forEach(key -> {
                String value = PropertiesManager.getString(key);
                if(StringUtils.isNotEmpty(value)){
                    setProperty(key, value);
                }
            });
        });
    }

}
