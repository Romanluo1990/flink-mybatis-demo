package roman.flink.mybatis.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import roman.flink.mybatis.dao.model.UserInfo;

@Slf4j
public class FlinkRunner {

    private String[] arg;

    private String jobName;

    public FlinkRunner(String[] arg, String jobName) {
        this.arg = arg;
        this.jobName = jobName;
    }

    public JobExecutionResult execute() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<UserInfo> userInfoDataStream = env.addSource(new UserInfoSource());
        DataStream<UserInfo> updatedDataStream  = userInfoDataStream.map((MapFunction<UserInfo, UserInfo>) value -> {
            value.setAge(value.getAge() + 1);
            return value;
        });
        updatedDataStream.print();
        updatedDataStream.addSink(new UserInfoSaveSink());
        return env.execute(jobName);
    }

}
