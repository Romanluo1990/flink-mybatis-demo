# flink-mybatis-demo
flink-mybatis+通用mapper集成

----
集成使用guice，参考：https://github.com/google/guice  
分页插件参考：https://github.com/pagehelper/Mybatis-PageHelper  
通用mapper参考：https://github.com/abel533/Mapper  

DataSourceModule：
```
public class DataSourceModule extends FlinkMyBatisModule {

    @Override
    protected void bindDao() {
        bind(UserInfoDao.class).to(UserInfoDaoImpl.class).in(Scopes.SINGLETON);
    }
}
```

获取dao使用：
```
public class UserInfoSaveSink extends RichSinkFunction<UserInfo> {

    private transient UserInfoDao userInfoDao;

    @Override
    public void invoke(UserInfo userInfo, Context context) throws Exception {
        userInfoDao.saveOrUpdate(userInfo);
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        userInfoDao = ApplicationContextManager.getInstance(UserInfoDao.class);
        super.open(parameters);
    }
}
```
