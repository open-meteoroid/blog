package cn.meteoroid.manager.controller;

import cn.meteoroid.common.entity.Test;
import cn.meteoroid.common.extend.lock.RepeatLock;
import cn.meteoroid.common.support.Messages;
import cn.meteoroid.manager.service.TestService;
import cn.meteoroid.common.support.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Kelvin Song
 * @date 2019-05-27 17:15
 */
@RepeatLock
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService service;

    @GetMapping
    public Result<List<Test>> test(){
        return new Result<>(service.list());
    }

    @GetMapping("/{id:\\d+}")
    public Result<Test> one(@PathVariable Long id){
        return new Result<>(service.one(id));
    }

    @PostMapping
    public Result<Test> insert(@Valid @RequestBody Test test){
        return new Result<>(service.insert(test), Messages.Insert);
    }

    @PutMapping("/{id:\\d+}")
    public Result<Test> update(@Valid @RequestBody Test test, @PathVariable Long id) {
        return new Result<>(service.update(test, id), Messages.Update);
    }

    @DeleteMapping("/{id:\\d+}")
    public Result<Boolean> delete(@PathVariable Long id){
        return new Result<>(service.delete(id));
    }
}
