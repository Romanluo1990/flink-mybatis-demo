package roman.flink.mybatis.dao;

import tk.mybatis.mapper.entity.Example;
import roman.flink.mybatis.dao.model.Identity;

import java.util.List;

public interface BaseDao<E extends Identity> {

	/**
	 * 保存
	 * @param e
	 * @return
	 */
	boolean save(E e);

	/**
	 * 按id删除
	 * @param id
	 * @return
	 */
	boolean deleteById(long id);

	/**
	 * 按Example删除
	 * @param example
	 * @return
	 */
	int deleteByExample(final Example example);

	/**
	 * 按id更新全字段
	 * @param e
	 * @return
	 */
	boolean updateById(E e);

	/**
	 * 按id更新非空字段
	 * @param e
	 * @return
	 */
	boolean updateByIdSelective(E e);

	public boolean updateByExampleSelective(final E e, Example example);

	long count();

	long selectCount(E e);

	long selectCountByExample(Example example);

	/**
	 * 保存或更新，按id判断
	 * @param e
	 * @return
	 */
	boolean saveOrUpdate(E e);

	/**
	 * 按id查数据
	 * @param id
	 * @return
	 */
	E getById(long id);


	E selectOne(E e);

	List<E> selectAll();

	List<E> select(E e);

	List<E> selectByExample(Example example);

	/**
	 * 按ids查数据
	 * @param ids
	 * @return
	 */
	List<E> listByIds(List<Long> ids);

}
