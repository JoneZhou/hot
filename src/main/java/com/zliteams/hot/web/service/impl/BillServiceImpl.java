package com.zliteams.hot.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.generic.GenericServiceImpl;
import com.zliteams.hot.web.dao.BillMapper;
import com.zliteams.hot.web.model.Bill;
import com.zliteams.hot.web.service.BillService;

@Service
public class BillServiceImpl extends GenericServiceImpl<Bill,Long> implements BillService {
	@Resource
	private BillMapper billMapper;

	@Override
	public GenericDao<Bill, Long> getDao() {
		return billMapper;
	}

}
