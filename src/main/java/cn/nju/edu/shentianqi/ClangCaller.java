package cn.nju.edu.shentianqi;

import java.io.File;

/**
 * 这个类用于调用clang
 * 测试用例中既有cpp 又有c
 * 当且仅当后缀名为 `.c`时，否则都是cpp
 * 将程序全部编译到临时目录里面去
 */
public class ClangCaller {
    /**
     * 返回json字符串数组
     * 这样之后就可以直接从这些字符串创建json树了
     */
    public static String[] callClang(String[] srcFiles) {
        String[] ret = new String[srcFiles.length];// 记录json文件位置
        int i = 0;
        for (String src : srcFiles) {
            File f = new File(src);
            if (f.exists() && f.isFile()) {
                ret[i++] = callClang2(src);
            } else {
                Log.err("error: " + src + " is not existed or file");
                System.exit(1);
            }
        }
        return ret;
    }

    public static String callClang2(String src) {
        //这里的src是一个合规文件名
        // clang -Xclang -ast-dump=json -fsyntax-only -pedantic ./testcase/f1.c
        // clang -Xclang -ast-dump=json -fsyntax-only -pedantic -std=c++17 ./testcase/demo.cpp
        if (src.endsWith(".c")) {
            //c source file

        } else {
            //cpp source file
        }
        return "";
    }
}
