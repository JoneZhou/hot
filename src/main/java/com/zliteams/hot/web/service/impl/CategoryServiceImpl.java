package com.zliteams.hot.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.generic.GenericServiceImpl;
import com.zliteams.hot.web.dao.CategoryMapper;
import com.zliteams.hot.web.model.Category;
import com.zliteams.hot.web.service.CategoryService;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, Long> implements CategoryService {
	@Resource
	private CategoryMapper categoryMapper;
	
	@Override
	public GenericDao<Category, Long> getDao() {
		return categoryMapper;
	}

}
