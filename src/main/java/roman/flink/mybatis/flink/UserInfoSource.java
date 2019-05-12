package roman.flink.mybatis.flink;

import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import roman.flink.mybatis.ApplicationContextManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import roman.flink.mybatis.dao.UserInfoDao;
import roman.flink.mybatis.dao.model.UserInfo;


@Slf4j
public class UserInfoSource extends RichSourceFunction<UserInfo> {

    private transient UserInfoDao userInfoDao;

    private boolean running = true;

    private static final long SLEEP_MILLIS = 5*1000L;

    @Override
    public void run(SourceContext<UserInfo> ctx) {
        try{
            while (running){
                UserInfo last = userInfoDao.getLast();
                if(last == null){
                    last = new UserInfo();
                    last.setUserName("张三");
                    last.setAge(0);
                }
                ctx.collect(last);
                Thread.sleep(SLEEP_MILLIS);
            }
        }catch (Exception e){
            log.warn(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        userInfoDao = ApplicationContextManager.getInstance(UserInfoDao.class);
        super.open(parameters);
    }
}
