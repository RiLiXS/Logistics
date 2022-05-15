package com.example.logistics.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : jyx
 * @description:
 * @date :12.10.21
 */
public class DataDTO implements Serializable {
    private SysUserDTO sysUser;
    private List<String> permissions;
    private List<String> roles;

    public SysUserDTO getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserDTO sysUser) {
        this.sysUser = sysUser;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
