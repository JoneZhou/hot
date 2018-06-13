package com.zliteams.hot.core.generic;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

public abstract class BaseModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
