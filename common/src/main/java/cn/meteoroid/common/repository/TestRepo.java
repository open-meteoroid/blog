package cn.meteoroid.common.repository;

import cn.meteoroid.common.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kelvin Song
 * @date 2019-05-28 12:36
 */
@Repository
public interface TestRepo extends JpaRepository<Test, Long> {
}
