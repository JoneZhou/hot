package com.zliteams.hot.core.entity;

import java.io.Serializable;

/**
 * 主键生成器
 * @author Ricky
 * @param <ID>
 */
public interface IdGenerator<ID extends Serializable> {

	public ID generate();
}
