package com.zliteams.hot.web.dao;

import java.util.List;

import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.generic.GenericDao;
import com.zliteams.hot.core.query.Query;
import com.zliteams.hot.web.model.Bill;

public interface BillMapper extends GenericDao<Bill, Long>{

	List<Bill> queryBills(Page<Bill> page, Query query);

}