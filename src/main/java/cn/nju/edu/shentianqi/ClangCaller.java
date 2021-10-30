package cn.nju.edu.shentianqi;

import java.io.File;

/**
 * 这个类用于调用clang
 * 测试用例中既有cpp 又有c
 * 当且仅当后缀名为 `.c`时，否则都是cpp
 * 将程序全部编译到临时目录里面去
 */
public class ClangCaller {
    public static String[] callClang(String[] srcFiles, TempFileManager t) {
        String[] ret = new String[srcFiles.length];// 记录json文件位置
        int i = 0;
        for (String src : srcFiles) {
            File f = new File(src);
            if (f.exists() && f.isFile()) {
                ret[i++] = callClang2(src, t);
            } else {
                Log.err("error: " + src + " is not existed or file");
                System.exit(1);
            }
        }
        return ret;
    }

    public static String callClang2(String src, TempFileManager t) {
        //这里的src是一个合规文件名
        // ./fdsfs/fdsf.cpp -> fdsf.cpp
        // /sdfsd/sfs.cc  -> sfs.cc
        // 每一个文件都放在独特的uuid文件夹里，防止sx的文件名
        String[] strings = src.split(File.pathSeparator);
        String srcName = strings[strings.length - 1];

        return "";
    }
}
