package com.mioto.pms.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问权限控制
 * @author mioto-qinxj
 * @date 2020/7/1
 */
//@Component("basicAuthorityService")
public class BasicAuthorityService {
  /*  @Autowired
    private IFunctionService functionService;*/

    public boolean hasPermission(HttpServletRequest request) {
        return true;
       /* User user = BaseUtil.getLoginUser();
        //获取用户拥有的权限列表
        List<Function> functions = functionService.findFunctionByUserId(user.getId());

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (Function function : functions) {
            if (antPathMatcher.match(function.getPath(),request.getRequestURI()) && StrUtil.equalsIgnoreCase(function.getMethod(),request.getMethod())){
                return true;
            }
        }
        return false;*/
    }

}
