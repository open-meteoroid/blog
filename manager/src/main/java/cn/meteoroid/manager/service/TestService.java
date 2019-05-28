package cn.meteoroid.manager.service;

import cn.meteoroid.manager.entity.Test;
import cn.meteoroid.manager.repository.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kelvin Song
 * @date 2019-05-28 12:37
 */
@Service
public class TestService {

    @Autowired
    private TestRepo testRepo;

    public List<Test> list(){
        return testRepo.findAll();
    }

    public Test save(Test test){
        return testRepo.save(test);
    }

    public Boolean delete(Long id) {
        testRepo.deleteById(id);
        return true;
    }
}
