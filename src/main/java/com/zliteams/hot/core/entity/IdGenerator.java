package com.zliteams.hot.core.entity;

import java.io.Serializable;

/**
 * 主键生成器
 */
public interface IdGenerator<ID extends Serializable> {

	public ID generate();
}
