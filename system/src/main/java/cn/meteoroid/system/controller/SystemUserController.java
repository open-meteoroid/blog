package cn.meteoroid.system.controller;

import cn.meteoroid.common.support.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kelvin Song
 * @date 2019-05-27 17:10
 */
@RestController
@RequestMapping("/system/user")
public class SystemUserController {

    @GetMapping
    public Result<Object> list(){
        return new Result<>("test");
    }
}
