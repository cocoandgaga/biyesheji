package io.renren.modules.sys.inf;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ktx-finalexam")//调用的远程调用服务名称
public interface ITelnetClient {

    @RequestMapping("/ktx-finalexam/telnet/insert/dept-user")
    void insertDept(@RequestParam("deptId") Long deptId, @RequestParam("userId") Long userId);
}
