package roman.flink.mybatis.dao.model;

import java.util.Date;

public interface Identity {

	Long getId();

	void setId(Long id);

	Boolean getIsDeleted();

	void setIsDeleted(Boolean isDeleted);

	Date getUpdateTime();

	void setUpdateTime(Date updateTime);

	Date getCreateTime();

	void setCreateTime(Date createTime);
}
