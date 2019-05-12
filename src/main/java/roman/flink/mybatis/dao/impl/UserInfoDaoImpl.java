package roman.flink.mybatis.dao.impl;

import com.github.pagehelper.PageHelper;
import roman.flink.mybatis.dao.UserInfoDao;
import roman.flink.mybatis.dao.model.UserInfo;
import roman.flink.mybatis.dao.repository.UserInfoRepository;

/**
 * @desc：用户信息Dao
 * @author romanluo
 * @date 2019/05/12
 */
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfoRepository, UserInfo> implements UserInfoDao {

    @Override
    public UserInfo getLast() {
        PageHelper.offsetPage(0,1, false);
        PageHelper.orderBy("id desc");
        return selectOne(new UserInfo());
    }
}