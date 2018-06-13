package com.zliteams.hot.web.model;

import com.zliteams.hot.core.generic.BaseModel;

/**
 * 角色模型
 * 
 **/
public class Role extends BaseModel{
	private static final long serialVersionUID = 1L;

    private String roleName;

    private String roleSign;

    private String description;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleSign() {
        return roleSign;
    }

    public void setRoleSign(String roleSign) {
        this.roleSign = roleSign == null ? null : roleSign.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", roleName=" + roleName + ", roleSign=" + roleSign + ", description=" + description + "]";
    }

}