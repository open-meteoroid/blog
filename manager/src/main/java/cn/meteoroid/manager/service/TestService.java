package cn.meteoroid.manager.service;

import cn.meteoroid.common.entity.Test;
import cn.meteoroid.common.repository.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kelvin Song
 * @date 2019-05-28 12:37
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class TestService {

    @Autowired
    private TestRepo repo;

    public List<Test> list() {
        return repo.findAll();
    }

    public Test one(Long id) {
        return repo.findById(id).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public Test insert(Test test) {
        return repo.save(test);
    }

    @Transactional(rollbackFor = Exception.class)
    public Test update(Test test, Long id) {
//        if (!repo.existsById(id)) {
//            throw new EmptyResultDataAccessException(1);
//        }
        test.setId(id);
        return repo.save(test);
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        repo.deleteById(id);
        return true;
    }
}
