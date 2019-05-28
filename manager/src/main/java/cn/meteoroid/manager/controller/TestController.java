package cn.meteoroid.manager.controller;

import cn.meteoroid.manager.entity.Test;
import cn.meteoroid.manager.service.TestService;
import cn.meteoroid.common.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kelvin Song
 * @date 2019-05-27 17:15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping
    public Result<List<Test>> test(){
        return new Result<>(service.list());
    }

    @PostMapping
    public Result<Test> save(@RequestBody Test test){
        return new Result<>(service.save(test));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id){
        return new Result<>(service.delete(id));
    }
}
