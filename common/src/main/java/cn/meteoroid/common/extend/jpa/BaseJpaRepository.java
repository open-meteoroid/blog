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
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("unchecked")
@NoRepositoryBean
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BaseJpaRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

    private static final String ID_MUST_NOT_BE_NULL = "The given id must not be null!";

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public BaseJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public <S extends T> S update(S entity) {

        ID id = (ID) entityInformation.getId(entity);

        Assert.notNull(id, ID_MUST_NOT_BE_NULL);

        Optional<T> optional = findById(id);

        if (!optional.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        String[] nullProperties = getNullProperties(entity);

        T target = optional.get();

        BeanUtils.copyProperties(entity, target, nullProperties);

        em.merge(target);

        return entity;
    }

    public <S extends T> S save(S entity) {

        if (entityInformation.isNew(entity)) {
            em.persist(entity);
            return entity;
        } else {
            ID id = (ID) entityInformation.getId(entity);

            Assert.notNull(id, ID_MUST_NOT_BE_NULL);

            Optional<T> optional = findById(id);

            if (!optional.isPresent()) {
                em.persist(entity);
                return entity;
            }

            S target = (S) optional.get();

            String[] nullProperties = getNullProperties(entity);

            BeanUtils.copyProperties(entity, target, nullProperties);

            return em.merge(target);
        }

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
