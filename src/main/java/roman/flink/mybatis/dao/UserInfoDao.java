package roman.flink.mybatis.dao;


import roman.flink.mybatis.dao.model.UserInfo;

/**
 * @desc：用户信息Dao
 * @author romanluo
 * @date 2019/05/12
 */
public interface UserInfoDao extends BaseDao<UserInfo> {

    UserInfo getLast();
}