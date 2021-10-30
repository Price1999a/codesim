package cn.nju.edu.shentianqi;

public class Main {
    private final static String[] file = new String[2];

    public static void main(String[] args) {
        parseArgs(args);
        Log.out("main() start");
        String[] json = ClangCaller.callClang(file);
        Log.out("two files have been converted to Json Strings");
        String[] APTEDTreeStrings = JsonToTree.JsonStringToAPTEDString(json, file);
        Log.out("Json Strings have been converted to APTED tree Strings");
        Log.printRes(TreeCompare.computeSim(APTEDTreeStrings[0], APTEDTreeStrings[1]));
    }

    /**
     * false返回值仅在未监测到2个文件且无help选项时触发
     * 此时会导致程序认为参数有问题 返回1并打印usage
     * -h --help会短路掉正常流程 直接打印usage 但此时是正常退出
     */
    private static void parseArgs(String[] args) {
        int fileCount = 0;
        for (String s : args) {
            switch (s) {
                case "-v":
                case "--verbose":
                    Log.verbose = true;
                    Log.out("参数 -v|--verbose ");
                    break;
                case "-h":
                case "--help":
                    Log.out("参数 -h|--help ");
                    usage();
                    System.exit(0);
                default:
                    if (fileCount < 2) {
                        Log.out("参数待分析文件：" + s);
                        file[fileCount] = s;
                        fileCount++;
                    } else {
                        Log.err("检测到>2个文件参数！");
                        usage();
                        System.exit(1);
                    }
            }
        }
        if (fileCount != 2) {
            Log.err("仅检测到" + fileCount + "个文件参数！");
            usage();
            System.exit(1);
        }

    }

    private static void usage() {
        Log.verbose = true; //确保可输出
        Log.out("usage: codesim [-v|--verbose] [-h|--help] code1 code2");
    }
}
