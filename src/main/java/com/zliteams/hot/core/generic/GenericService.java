package com.zliteams.hot.core.generic;

import java.util.List;
import java.util.Map;

import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.report.Report;
import com.zliteams.hot.web.model.Category;

/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 */
public interface GenericService<Model extends BaseModel, PK> {

    /**
     * 插入对象
     *
     * @param model 对象
     */
    int insert(Model model);

    /**
     * 更新对象
     *
     * @param model 对象
     */
    int update(Model model);

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    int delete(PK id);

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return model 对象
     */
    Model selectById(PK id);


    /**
     * 查询单个对象
     *
     * @return 对象
     */
    Model selectOne();


    /**
     * 查询多个对象
     * @param category 
     *
     * @return 对象集合
     */
    List<Model> selectList(Model model);
    
    /**
     * 分页条件查询
     */
    List<Model> selectByPage(Page<Model> page, Model model);
    
    List<Map<String, Object>> getReport(Report report);

}
