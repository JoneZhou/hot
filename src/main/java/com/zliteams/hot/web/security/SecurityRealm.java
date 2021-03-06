package com.zliteams.hot.web.security;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zliteams.hot.core.util.ShiroHash;
import com.zliteams.hot.web.model.User;
import com.zliteams.hot.web.service.PermissionService;
import com.zliteams.hot.web.service.RoleService;
import com.zliteams.hot.web.service.UserService;

/**
 * 用户身份验证,授权 Realm 组件
 * 
 **/
//@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(SecurityRealm.class);

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;
   
    /**
     * 权限检查
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = String.valueOf(principals.getPrimaryPrincipal());

        final User user = userService.selectByUsername(username);
        final Set<String> roles = roleService.findRoles(user.getId());
        final Set<String> permissions = permissionService.findPermissions(user.getId());
        logger.info(roles.toString());
        logger.info(permissions.toString());
        authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
       
        return authorizationInfo;
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = String.valueOf(token.getPrincipal());
//        String password = new String((char[]) token.getCredentials());
//        //通过数据库进行验证
//        final User authentication = userService.authentication(new User(username, password));
        final User authentication = userService.selectByUsername(username);
        if (authentication == null) {
            throw new AuthenticationException("用户名错误.");
        }
        SimpleAuthenticationInfo authenticationInfo = 
        		new SimpleAuthenticationInfo(username, authentication.getPassword(),ByteSource.Util.bytes(username+ShiroHash.DEFAULT_SALT2), getName());
        return authenticationInfo;
    }

}
