package cn.meteoroid.common.extend.jpa;

import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author Kelvin Song
 * @date 2019-05-30 10:30
 */
@NoRepositoryBean
public class BaseRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID> {
    /**
     * Creates a new {@link JpaRepositoryFactoryBean} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    public BaseRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);
    }

    @NoRepositoryBean
    private static class BaseRepositoryFactory extends JpaRepositoryFactory {

        /**
         * Creates a new {@link JpaRepositoryFactory}.
         *
         * @param entityManager must not be {@literal null}
         */
        public BaseRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            if (information.getRepositoryBaseClass() == QuerydslJpaPredicateExecutor.class) {
                return getTargetRepositoryViaReflection(information, entityInformation, entityManager);
            } else {
                return getTargetRepositoryViaReflection(information, entityInformation, entityManager);
            }
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            Class<?> repositoryBaseClass = super.getRepositoryBaseClass(metadata);
            return (repositoryBaseClass == QuerydslJpaPredicateExecutor.class) ? repositoryBaseClass : BaseRepositoryImpl.class;
        }
    }
}
