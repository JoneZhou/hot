package com.zliteams.hot.web.service;

import java.util.List;

import com.zliteams.hot.core.feature.orm.mybatis.Page;
import com.zliteams.hot.core.generic.GenericService;
import com.zliteams.hot.core.query.Query;
import com.zliteams.hot.web.model.Bill;

public interface BillService extends GenericService<Bill, Long> {

	List<Bill> queryBills(Page<Bill> page, Query query);

}
