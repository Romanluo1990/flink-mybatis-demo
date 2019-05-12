package roman.flink.mybatis.common.properties;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class LocalPropertyHolder implements PropertyHolder  {

    private static final String APP_PROPERTIES = "app.properties";

    private String propertiesPath;

    private volatile Properties localProperties;

    public LocalPropertyHolder() {
        this(APP_PROPERTIES);
    }

    public LocalPropertyHolder(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    @Override
    public String getValue(String key) {
        Properties roperties = getLocalProperties();
        return (String)roperties.get(key);
    }

    private Properties getLocalProperties(){
        if(localProperties == null){
            synchronized (this){
                if(localProperties == null){
                    localProperties = new Properties();
                    InputStream inputStream = PropertiesManager.class.getClassLoader()
                            .getResourceAsStream(propertiesPath);
                    if (inputStream == null) {
                        throw new IllegalStateException("缺少:" + propertiesPath + "配置文件");
                    }
                    try {
                        localProperties.load(inputStream);
                    } catch (IOException e) {
                        log.warn("本地配置加载失败", e);
                    }
                }
            }
        }
        return localProperties;
    }

}
