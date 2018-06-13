package com.zliteams.hot.web.model;

import java.util.Date;

import com.zliteams.hot.core.generic.BaseModel;

public class Category extends BaseModel{
	private static final long serialVersionUID = 1L;

    private String name;

    private Date createTime;
    
    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}