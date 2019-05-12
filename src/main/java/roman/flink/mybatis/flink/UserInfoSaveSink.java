package roman.flink.mybatis.flink;

import roman.flink.mybatis.ApplicationContextManager;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import roman.flink.mybatis.dao.UserInfoDao;
import roman.flink.mybatis.dao.model.UserInfo;

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
