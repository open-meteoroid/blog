package cn.meteoroid.common.support;
/**
 * 全局基础消息集与状态码
 *
 * @author Kelvin Song
 */
public enum Messages {
    //全局基础消息
    Ok(200, "请求成功"),
    Error(500, "服务异常"),
    Insert(200, "新增成功"),
    Update(200, "修改成功"),
    Delete(200, "删除成功"),
    Upload(200, "上传成功");


    private int code;
    private String message;

    Messages(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
