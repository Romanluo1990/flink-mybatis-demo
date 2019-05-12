package roman.flink.mybatis.dao.model;

import java.util.Date;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @desc：用户信息模型
 * @author romanluo
 * @date 2019/05/12
 */
@Data
@Table(name="user_info")
public class UserInfo implements Identity {

    /**
     *
    */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     *
    */
    @Column(name = "`user_name`")
    private String userName;

    /**
     *
     */
    @Column(name = "`age`")
    private Integer age;

    /**
     *
    */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     *
    */
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     *
    */
    @Column(name = "`is_deleted`")
    private Boolean isDeleted;

}