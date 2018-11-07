package nio.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author sunpengwei
 * @创建时间 2018/10/27
 * @描述
 * @联系邮箱 sunpengwei@jd.com
 */
public class FilesTest {

    public static void main(String[] args) throws IOException {
        //验证给定的path路径是否合法
        System.out.println(Files.exists(Paths.get("E:\\merger4.xlsx")));
        // LinkOption.NOFOLLOW_LINKS这意味着该Files.exists() 方法不应该遵循文件系统中的符号链接来确定路径是否存在
        System.out.println(Files.exists(Paths.get("E:\\merger4.xlsx"), LinkOption.NOFOLLOW_LINKS));
        //创建给定path的一个目录 （如果path本身存在，则会报错）
        Files.createDirectory(Paths.get("E:\\merger1.xlsx"));


        //该Files.copy()方法将文件从一个路径复制到另一个路径。这是一个Java NIO Files.copy() 示例：
        Path sourcePath = Paths.get("/merger4.xlsx");
        Path destinationPath = Paths.get("/merger4_copy.xlsx");

        try {
            //如果 destinationPath已经存在，则会报错
            Files.copy(sourcePath,destinationPath);
            //如果 destinationPath已经存在，则会覆盖原有的文件
            Files.copy(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(Exception e){
            e.printStackTrace();
        }

        //Java NIO Files类还包含用于将文件从一个路径移动到另一个路径的功能。移动文件与重命名文件相同，除了移动文件可以将文件移动到不同的目录并在同一操作中更改其名称。是的，java.io.File该类也可以使用其renameTo() 方法执行此操作，但现在您也可以在java.nio.file.Files类中使用文件移动功能。
        try{
            Files.move(sourcePath,destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException e){
            //移动文件失败。
            e.printStackTrace();
        }
        //首先创建源路径和目标路径。源路径指向要移动的文件，目标路径指向文件应移动到的位置。然后Files.move()调用该方法。这会导致文件被移动。
        //注意传递给的第三个参数Files.move()。此参数指示Files.move()方法覆盖目标路径上的任何现有文件。该参数实际上是可选的。
        //如果移动文件失败， 该Files.move()方法可能会抛出IOException。例如，如果文件已经存在于目标路径中，并且您省略了该StandardCopyOption.REPLACE_EXISTING 选项，或者如果要移动的文件不存在等。



        Path path = Paths.get("data/subdir/logging-moved.properties");
        try {
            //删除一个path
            Files.delete(path);
        } catch (IOException e) {
            //deleting file failed
            e.printStackTrace();
        }

        //该Files.walkFileTree()方法包含用于递归遍历目录树的功能。该walkFileTree()方法采用Path实例和FileVisitoras参数。该Path实例指向要遍历的目录。该FileVisitor traversion时被调用。

        Files.walkFileTree(Paths.get("E:\\"), new FileVisitor<Path>() {
            //访问一个文件
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("visit file: " + file);
                return FileVisitResult.CONTINUE;
            }
            //开始一个文件目录
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("pre visit dir:" + dir);
                return FileVisitResult.CONTINUE;
            }
            // 访问文件失败
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("visit file failed: " + file);
                return FileVisitResult.CONTINUE;
            }
            //结束访问文件目录
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("post visit directory: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
