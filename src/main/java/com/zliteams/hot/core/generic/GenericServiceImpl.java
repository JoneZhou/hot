package com.zliteams.hot.core.generic;

import java.util.List;

import javax.annotation.Resource;

import com.zliteams.hot.core.entity.IdGenerator;
import com.zliteams.hot.core.feature.orm.mybatis.Page;

/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * Long :代表对象的主键类型
 *
 */
public abstract class GenericServiceImpl<Model extends BaseModel, PK> implements GenericService<Model, PK> {
	@Resource
	protected IdGenerator<Long> idGenerator;

    /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericDao实现类
     */
    public abstract GenericDao<Model, PK> getDao();

    /**
     * 插入对象
     *
     * @param model 对象
     */
    public int insert(Model model) {
    	if(model.getId() == null){
    		model.setId(idGenerator.generate());
    	} 
        return getDao().insertSelective(model);
    }

    /**
     * 更新对象
     *
     * @param model 对象
     */
    public int update(Model model) {
        return getDao().updateByPrimaryKeySelective(model);
    }

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    public int delete(PK id) {
        return getDao().deleteByPrimaryKey(id);
    }

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    public Model selectById(PK id) {
        return getDao().selectByPrimaryKey(id);
    }


    @Override
    public Model selectOne() {
        return null;
    }

    @Override
    public List<Model> selectList(Model model) {
        return getDao().selectList(model);
    }
    
    @Override
    public List<Model> selectByPage(Page<Model> page, Model model) {
    	 return getDao().selectByPage(page, model);
    }
}
