package com.dp.demo.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.dp.demo.sys.pojo.User;
import com.dp.demo.sys.service.UserService;


public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;
	
	/*public String getName() {
        return this.getClass().getName();
    }*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username=(String) principals.getPrimaryPrincipal(); 
		User user=userService.getByUsername(username); 
		if(user == null){
			return null;
		}
        SimpleAuthorizationInfo  authorizationInfo=new SimpleAuthorizationInfo();  
        return authorizationInfo;  
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
     // 1. 把AuthenticationToken 转换为UsernamePasswordToken
        UsernamePasswordToken up = (UsernamePasswordToken) token;
        // 2. 从UsernamePasswordToken 中来获取username
        String username = up.getUsername();
        // 3. 调用数据库的方法，从数据库中查询username对应的用户记录
        User user=userService.getByUsername(username);  
        // 4. 若用户不存在，则可以抛出 UnknownAccoountException 异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }
        // 5. 根据用户信息的情况，决定是否需要抛出其他的AuthencationException 异常 假设用户被锁定
        /*if ("monster".equals(username)) {
            throw new LockedAccountException("用户被锁定");
        }*/
        // 6. 根据用户的情况，来构建AuthenticationInfo 对象并返回，通常使用的是
        // SimpleAuthenticationInfo
        // 以下信息是从数据库获取的.

        Object principal = username; // principal 认证的实体信息.
                                        // 可以是username，也可以是数据表对应的用户的实体类对象
        String credentials = user.getPassword(); // credentials：密码
        String realmName = getName();
        AuthenticationInfo info = null;/*new SimpleAuthenticationInfo(principal, credentials, realmName);*/
        
        
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);//这里的参数要给个唯一的;
        
        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);

        return info;
	}

}
