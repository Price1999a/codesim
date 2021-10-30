package cn.nju.edu.shentianqi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 这个类的目标就是管理临时文件
 * 新建时创建临时文件目录
 * 并确保其在jvm退出时删除
 * 具体而言 程序在调用clang时采用命令行调用输出重定向的方法
 * 这个类只要管理目录就行
 */
public class TempFileManager {
    private Path tmpDir = null;

    /**
     * 参考输出
     * /var/folders/5f/_x9hzf5137lb52vvbfwrpxbr0000gn/T/cn.nju.edu.shentianqi.codesim3971598773784603607
     */
    public String getTmpDirString() {
        return tmpDir.toFile().getAbsolutePath();
    }

    public TempFileManager() {
        try {
            tmpDir = Files.createTempDirectory("cn.nju.edu.shentianqi.codesim");
            tmpDir.toFile().deleteOnExit();
            Log.out(getTmpDirString() + "  created");
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        TempFileManager t = new TempFileManager();
        System.out.println(t.getTmpDirString());
        try {
            Path tmpDir1 = Files.createTempDirectory("cn.nju.edu.shentianqi.codesim");
            tmpDir1.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
