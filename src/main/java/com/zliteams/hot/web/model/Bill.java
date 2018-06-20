package com.zliteams.hot.web.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import com.zliteams.hot.core.generic.BaseModel;

public class Bill extends BaseModel{
	private static final long serialVersionUID = 1L;

    private Long user;

    private Integer type;

    private Double money;

    private Long category;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    private String description;
    
    
    private Category categoryObj;

    @JsonSerialize(using = ToStringSerializer.class)
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public Category getCategoryObj() {
		return categoryObj;
	}

	public void setCategoryObj(Category categoryObj) {
		this.categoryObj = categoryObj;
	}
}