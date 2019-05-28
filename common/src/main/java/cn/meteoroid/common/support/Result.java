package cn.meteoroid.common.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 全局接口返回结果集
 *
 * @param <T>
 * @author Kelvin Song
 */
@Getter
@Accessors(chain = true)
@ToString(exclude = "status")
@EqualsAndHashCode(exclude = "status")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String VERSION = "1.0.0";

    private int code;
    /**
     * 提示信息
     */
    private String[] message;
    /**
     * 版本信息
     */
    @Setter
    private String version;
    /**
     * 数据结果
     */
    @Setter
    private T data;
    /**
     * 时间戳
     */
    private long timestamp;

    @JsonIgnore
    private Messages status;

    @SuppressWarnings("unchecked")
    public Result() {
        this.data = (T) "{}";
        this.version = VERSION;
        this.status = Messages.Ok;
        this.code = this.status.code();
        this.message = new String[]{this.status.message()};
        this.timestamp = System.currentTimeMillis();
    }

    public Result(T data) {
        this();
        this.data = data;
    }

    public Result(T data, Messages status) {
        this(status);
        this.data = data;
    }

    public Result(Messages status) {
        this();
        this.status = status;
        this.code = this.status.code();
        this.message = new String[]{this.status.message()};
    }

    /**
     * 全局异常捕获时使用
     *
     * @param code
     * @param message
     */
    public Result(int code, String... message) {
        this();
        this.code = code;
        this.message = message;
    }
}
