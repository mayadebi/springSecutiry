package com.fzy.springserctity.service;


import com.fzy.springserctity.handler.MyAuthenticationSuccessHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

// 自定义登录逻辑
// 实现UserDetailsService  重写loadUserByUsername
// 正常这里去数据库查询
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("执行自定义登录逻辑");
//        // 当前时间
//        long date = System.currentTimeMillis();
//        // 失效时间
//        long exp = date + 60 * 1000;
//        // 创建jwt
//        JwtBuilder jwtBuilder = Jwts.builder()
//                // 设置ID  随便给
//                .setId("1")
//                // 用户
//                .setSubject("admin")
//                // 创建日期
//                .setIssuedAt(new Date())
//                // 签名算法和盐  就是加密的秘钥  长度不能低于4位
//                .signWith(SignatureAlgorithm.HS256, "fzy123456")
//                // 设置失效时间
//                .setExpiration(new Date(exp))
//                // 自定义添加内容
//                .claim("username","admin")
//                // 或者添加一个map
////                .addClaims(map)
//                ;
//        // token
//        String compact = jwtBuilder.compact();
////        String compact = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE2MzE0NTI1MTQsImV4cCI6MTYzMTQ1MjU3NH0.igK1mpdW3-Dx8oyWNLoftmgxbny6W0tZdonL0FaN350";
//        System.out.println("token");
//        System.out.println(compact);
//        // 解析token
//        Claims claims = (Claims)Jwts.parser()
//                .setSigningKey("fzy123456")
//                .parse(compact)
//                .getBody();
//        System.out.println(claims.getId());
//        System.out.println(claims.getSubject());
//        System.out.println(claims.getIssuedAt());
//        System.out.println(claims.get("username"));
//        // 1，根据用户名去数据库查询，不存在就报UsernameNotFoundException
//        if(!"admin".equals(username)){
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//         2.比较密码(注册时已经加密，如果匹配成功返回UserDetails)
        String password = passwordEncoder.encode("123");

        return new User(username,password, AuthorityUtils
                // 添加权限  admin 和角色 fzy
                .commaSeparatedStringToAuthorityList("admin,ROLE_admin"));
//        return new User("admin", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
