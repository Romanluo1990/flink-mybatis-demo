package roman.flink.mybatis.dao.impl;

import com.google.inject.Inject;
import roman.flink.mybatis.dao.BaseDao;
import roman.flink.mybatis.dao.model.Identity;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public class BaseDaoImpl<M extends Mapper<E>, E extends Identity> implements BaseDao<E> {

	@Inject
	protected M mapper;
	private Class classCache;

	@Override
	public boolean save(final E e) {
		e.setId(null);
		e.setCreateTime(new Date());
		return mapper.insertSelective(e) == 1;
	}

	@Override
	public boolean deleteById(final long id) {
		final E e = createEntity();
		e.setId(id);
		e.setIsDeleted(true);
		return mapper.updateByPrimaryKeySelective(e) == 1;
	}

	@Override
	public int deleteByExample(final Example example){
		final E e = createEntity();
		e.setIsDeleted(true);
		return mapper.updateByExampleSelective(e, example);
	}

	@Override
	public boolean updateById(final E e) {
		if (e.getId() == null) {
			throw new IllegalArgumentException("updateById method id cannot be null");
		}
		e.setUpdateTime(null);
		return mapper.updateByPrimaryKey(e) == 1;
	}

	@Override
	public boolean updateByIdSelective(final E e) {
		if (e.getId() == null) {
			throw new IllegalArgumentException("updateById method id cannot be null");
		}
		e.setUpdateTime(null);
		return mapper.updateByPrimaryKeySelective(e) == 1;
	}

	@Override
	public boolean updateByExampleSelective(final E e, final Example example) {
		if (e.getId() == null) {
			throw new IllegalArgumentException("updateById method id cannot be null");
		}
		e.setUpdateTime(null);
		example.and().andEqualTo("isDeleted", false);
		return mapper.updateByExampleSelective(e, example) == 1;
	}


	@Override
	public E getById(final long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public E selectOne(final E e) {
		e.setIsDeleted(false);
		return mapper.selectOne(e);
	}

	@Override
	public List<E> selectAll() {
		final E e = createEntity();
		e.setIsDeleted(false);
		return mapper.select(e);
	}

	@Override
	public List<E> select(final E e) {
		e.setIsDeleted(false);
		return mapper.select(e);
	}

	@Override
	public List<E> selectByExample(final Example example) {
		example.and(example.createCriteria().andEqualTo("isDeleted", false));
		return mapper.selectByExample(example);
	}

	@Override
	public List<E> listByIds(final List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		final Example example = new Example(getEntityClass());
		example.createCriteria().andIn("id", ids);
		return mapper.selectByExample(example);
	}



	@Override
	public long count() {
		return selectCount(createEntity());
	}

	@Override
	public long selectCount(E e) {
		e.setIsDeleted(false);
		return mapper.selectCount(e);
	}

	@Override
	public long selectCountByExample(Example example) {
		example.and().andEqualTo("isDeleted", false);
		return mapper.selectCountByExample(example);
	}

	@Override
	public boolean saveOrUpdate(final E e) {
		if (e.getId() == null) {
			return save(e);
		} else {
			return updateByIdSelective(e);
		}
	}

	private E createEntity() {
		final Class clazz = getEntityClass();
		final E e;
		try {
			e = (E) (clazz.getConstructor().newInstance());
		} catch (final Exception ex) {
			log.error("没有无参构造", ex);
			throw new IllegalStateException(ex);
		}
		return e;
	}

	private Class<E> getEntityClass() {
		if (classCache == null) {
			final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
			classCache = (Class) parameterizedType.getActualTypeArguments()[1];
		}
		return classCache;
	}

}
