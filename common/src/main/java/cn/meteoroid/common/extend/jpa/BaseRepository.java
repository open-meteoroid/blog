package cn.meteoroid.common.extend.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kelvin Song
 * @date 2019-05-30 10:30
 */
@NoRepositoryBean
@Transactional(readOnly = true, rollbackFor = Exception.class)
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    /**
     * 重写jpa save方法，使其支持传入空值时不作数据的修改
     *
     * @param entity bean
     * @param <S>    type
     * @return bean
     */
    public <S extends T> S update(S entity);

    /**
     * 重写jpa saveAll方法，使其支持传入空值时不作数据的修改
     * @param entities
     * @param <S>
     * @return
     */
    public <S extends T> List<S> updateAll(Iterable<S> entities);
}
