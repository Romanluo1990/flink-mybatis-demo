package roman.flink.mybatis.flinkmybatis;

import roman.flink.mybatis.common.properties.PropertiesManager;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Properties;

/**
 * https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
 * dialect=com.github.pagehelper.Dialect
 * helperDialect=mysql
 * offsetAsPageNum=false
 * rowBoundsWithCount=false
 * pageSizeZero=false
 * reasonable=false
 * params=pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero
 * supportMethodsArguments=com.github.pagehelper.test.basic
 * autoRuntimeDialect=false
 * closeConn=true
 * aggregateFunctions
 */
public class PageProperties extends Properties {

    private static final String[] KEYS = {"dialect",
            "helperDialect",
            "offsetAsPageNum",
            "rowBoundsWithCount",
            "pageSizeZero",
            "reasonable",
            "params",
            "supportMethodsArguments",
            "autoRuntimeDialect",
            "closeConn",
            "aggregateFunctions"};

    public PageProperties() {
        initProperties();
    }

    private void initProperties() {
        Arrays.stream(KEYS).forEach(key -> {
            String value = PropertiesManager.getString(key);
            if(StringUtils.isNotEmpty(value)){
                setProperty(key, value);
            }
        });
    }
}
