跨域：  
https://developer.mozilla.org/zh-CN/docs/Web/HTTP/CORS#requests_with_credentials


登录--》
OAuth2Filter-->onAccessDenied-->createToken
AuthenticatingFilter-->subject.login(token);
OAuth2Ream-->doGetAuthenticationInfo

@Permission
OAuth2Ream-->doGetAuthorizationInfo


            