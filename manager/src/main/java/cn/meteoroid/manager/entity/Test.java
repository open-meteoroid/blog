package cn.meteoroid.manager.entity;

import cn.meteoroid.common.support.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author Kelvin Song
 * @date 2019-05-28 09:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "test")
@Where(clause = "deleted = 0")
@SQLDelete(sql = "update test set deleted = 1 where id = ?")
public class Test extends BaseEntity {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    private String address;

    private Integer age;

    private LocalDateTime updateTime;
}
