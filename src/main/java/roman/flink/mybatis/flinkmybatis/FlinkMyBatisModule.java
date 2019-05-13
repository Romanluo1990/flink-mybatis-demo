package roman.flink.mybatis.flinkmybatis;

import com.github.pagehelper.PageInterceptor;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.inject.Key;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.configuration.ConfigurationProvider;
import org.mybatis.guice.configuration.settings.MapperConfigurationSetting;
import org.mybatis.guice.datasource.druid.DruidDataSourceProvider;
import org.mybatis.guice.provision.ConfigurationProviderProvisionListener;
import org.mybatis.guice.provision.KeyMatcher;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.reflect.ClassPath.ClassInfo;
import static com.google.common.reflect.ClassPath.from;
import static com.google.inject.util.Providers.guicify;
import static roman.flink.mybatis.common.util.Preconditions.checkArgument;
import static roman.flink.mybatis.flinkmybatis.DataSourceProperties.MAPPER_PACKAGE;

public abstract class FlinkMyBatisModule extends MyBatisModule {


    @Override
    protected void initialize() {
        bind(MapperHelper.class).in(Scopes.SINGLETON);
        bind(PageInterceptor.class).toProvider(PageProvider.class).in(Scopes.SINGLETON);

        bindDao();

        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        Names.bindProperties(binder(), dataSourceProperties);
        //绑定我们自定义的数据源provider，也可以使用guice已经编写好的
        bindDataSourceProviderType(DruidDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addInterceptorClass(PageInterceptor.class);

        //添加我们的mapper接口，可以按类注入（即通过类名注入），也可以指定整个包的路径
        String[] packages = dataSourceProperties.getProperty(MAPPER_PACKAGE).split(",");
        Arrays.stream(packages).forEach(this::addTkMapperClasses);

    }

    protected abstract void bindDao();

    protected void addTkMapperClasses(final String packageName){
        addTkMapperClasses(getClasses(packageName));
    }

    protected void addTkMapperClasses(Collection<Class<?>> mapperClasses) {
        checkArgument(mapperClasses != null, "Parameter 'mapperClasses' must not be null");

        for (Class<?> mapperClass : mapperClasses) {
            addTkMapperClass(mapperClass);
        }
    }

    protected final void addTkMapperClass(Class<?> mapperClass) {
        checkArgument(mapperClass != null, "Parameter 'mapperClass' must not be null");

        bindListener(KeyMatcher.create(Key.get(ConfigurationProvider.class)),
                ConfigurationProviderProvisionListener.create(new MapperConfigurationSetting(mapperClass)));
        bindTkMapper(mapperClass);
    }

    protected <T> void bindTkMapper(Class<T> mapperType) {
        bind(mapperType).toProvider(guicify(new MapperProvider<T>(mapperType))).in(Scopes.SINGLETON);
    }

    private Set<Class<?>> getClasses(String packageName){
        ClassPath classPath = null;
        try {
            classPath = from(getClass().getClassLoader());
        } catch (IOException e) {
            throw new IllegalStateException("mapper get error", e);
        }
        ImmutableSet<ClassInfo> classInfos = classPath.getTopLevelClasses(packageName);
        return classInfos.stream().map(ClassInfo::load).collect(Collectors.toSet());
    }
}
