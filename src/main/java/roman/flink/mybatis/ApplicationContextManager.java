package roman.flink.mybatis;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import roman.flink.mybatis.dao.DataSourceModule;

import javax.annotation.concurrent.ThreadSafe;

@Slf4j
@ThreadSafe
public class ApplicationContextManager {

    private static class InjectorHolder {
        static Injector applicationContext = Guice.createInjector(new DataSourceModule());
    }

    private ApplicationContextManager() {
    }

    private static Injector getApplicationContext() {
        return InjectorHolder.applicationContext;
    }

    public static <T> T getInstance(Class<T> type) {
        return getApplicationContext().getInstance(type);
    }
}
