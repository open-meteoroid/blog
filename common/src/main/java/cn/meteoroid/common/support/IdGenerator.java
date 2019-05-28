package cn.meteoroid.common.support;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * 全局ID生成器
 * @author Kelvin Song
 * @date 2019-05-28 10:05
 */
public class IdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        TimeBasedGenerator generator = Generators.timeBasedGenerator();
        return generator.generate().timestamp();
    }
}
