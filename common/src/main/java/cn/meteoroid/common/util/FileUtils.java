package cn.meteoroid.common.util;

import org.springframework.util.StringUtils;

import java.io.File;

/**
 * 文件操作工具类
 * @author Kelvin Song
 */
public class FileUtils {

    /**
     * 判断路径是否存在，如果不存在则创建
     */
    public static void mkdirs(String dir) {
        if (StringUtils.isEmpty(dir)) {
            return;
        }

        File file = new File(dir);
        if (file.isDirectory()) {
            return;
        } else {
            file.mkdirs();
        }
    }
}
