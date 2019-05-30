package cn.meteoroid.common.extend.jpa;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author Kelvin Song
 * @date 2019-05-30 10:30
 */
@SuppressWarnings("unchecked")
@NoRepositoryBean
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

//    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> S update(S entity) {

        ID id = (ID) entityInformation.getId(entity);

        if (ObjectUtils.isEmpty(id)) {
            throw new EmptyResultDataAccessException(1);
        }

        Optional<T> optional = findById(id);

        if (!optional.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        String[] nullProperties = getNullProperties(entity);

        S target = (S) optional.get();

        BeanUtils.copyProperties(entity, target, nullProperties);

        em.merge(target);

        return target;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> List<S> updateAll(Iterable<S> entities) {

        Assert.notNull(entities, "The given Iterable of entities not be null!");

        List<S> result = new ArrayList<>();

        for (S entity : entities) {
            result.add(update(entity));
        }

        return result;
    }

    private static String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper wrapper = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] descriptors = wrapper.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor descriptor : descriptors) {
            String name = descriptor.getName();
            Object value = wrapper.getPropertyValue(name);
            if (ObjectUtils.isEmpty(value)) {
                wrapper.setPropertyValue(name, null);
                properties.add(name);
            }
        }
        return properties.toArray(new String[0]);
    }
}
