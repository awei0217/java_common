package nio.path;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author sunpengwei
 * @创建时间 2018/10/27
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class PathTest {


    public static void main(String[] args) {
        //创建绝对路径  使用该Paths.get(path)方法创建相对路径
        Path path1 = Paths.get("E:\\merger4.xlsx");
        System.out.println(path1);

        //创建相对路径  使用该Paths.get(basePath, relativePath)方法创建相对路径
        Path path2 = Paths.get("E:\\","merger4.xlsx");
        System.out.println(path2);

        //创建当前目录
        Path currentDir = Paths.get(".");
        System.out.println(currentDir.toAbsolutePath());

    }
}
