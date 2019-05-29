package cn.meteoroid.common.entity;

import cn.meteoroid.common.support.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Test extends BaseEntity {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    private String address;

    private Integer age;

    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updateTime;
}
