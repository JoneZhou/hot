package com.zliteams.hot.core.generic;

import java.util.List;
import java.util.Map;

import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.report.Report;
import com.zliteams.hot.web.model.User;

/**
 * 所有自定义Dao的顶级接口, 封装常用的增删查改操作,
 * 可以通过Mybatis Generator Maven 插件自动生成Dao,
 * 也可以手动编码,然后继承GenericDao 即可.
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 */
public interface GenericDao<Model, PK> {

    /**
     * 插入对象
     *
     * @param model 对象
     */
    int insertSelective(Model model);

    /**
     * 更新对象
     *
     * @param model 对象
     */
    int updateByPrimaryKeySelective(Model model);

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    Model selectByPrimaryKey(PK id);
    
    int insert(Model record);

    int updateByPrimaryKey(Model record);
    
    List<Model> selectList(Model model);
    
    /**
     * 分页条件查询
     */
    List<Model> selectByPage(Page<Model> page, Model model);
    
    List<Map<String, Object>> getReport(Report report);

}
