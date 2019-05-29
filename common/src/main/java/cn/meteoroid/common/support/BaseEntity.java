package cn.meteoroid.common.support;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static cn.meteoroid.common.support.Constants.GENERIC_GENERATOR;

/**
 * @author Kelvin Song
 * @date 2019-05-28 10:16
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "timeBasedGenerator")
    @GenericGenerator(name = "timeBasedGenerator", strategy = GENERIC_GENERATOR)
    @Column(length = 20, unique = true, updatable = false, columnDefinition = "bigint comment '主键'")
    protected Long id;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false, columnDefinition = "bit default b'0' comment '0=不归档,1=归档'")
    protected Boolean deleted = false;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false, columnDefinition = "datetime default current_timestamp comment '创建时间'")
    protected LocalDateTime createTime;
}
