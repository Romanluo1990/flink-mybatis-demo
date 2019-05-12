package roman.flink.mybatis.dao;

import com.google.inject.Scopes;
import roman.flink.mybatis.dao.impl.*;
import roman.flink.mybatis.flinkmybatis.FlinkMyBatisModule;

public class DataSourceModule extends FlinkMyBatisModule {

    @Override
    protected void bindDao() {
        bind(UserInfoDao.class).to(UserInfoDaoImpl.class).in(Scopes.SINGLETON);
    }
}
