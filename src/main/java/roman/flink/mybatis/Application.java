package roman.flink.mybatis;

import roman.flink.mybatis.flink.FlinkRunner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application  {

    public static void main(String[] args) throws Exception {
        FlinkRunner flinkRunner = new FlinkRunner(args, "mybatis-demo");
        flinkRunner.execute();
    }


}
