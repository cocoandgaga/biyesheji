package com.zxj.finalexam.inf;

import com.zxj.common.form.SysLoginForm;
import com.zxj.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ktx-admin")//调用的远程调用服务名称
public interface ITelnetLoginService {
    @PostMapping("/ktx-admin/telnet/sys/validate")
    String validate(@RequestBody SysLoginForm form);

    @GetMapping("/ktx-admin/telnet/sys/token")
    R createToken(@RequestParam Long userId);

    @GetMapping("/ktx-admin/telnet/sys/get")
    R queryByToken(@RequestParam String token);

    @GetMapping("/ktx-admin/telnet/sys/user/info")
    R getUserInfo(@RequestParam Long userId);


}
