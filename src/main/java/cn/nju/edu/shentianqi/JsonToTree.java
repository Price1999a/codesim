package cn.nju.edu.shentianqi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonToTree {
    /**
     * 用于尝试解析几个已经手动dump下来的json文件
     * 由于cpp编译特性，头文件也会混杂进这里的ast 所以我们首先就是裁剪这棵树
     * 基本操作顺序
     * new -> file ok
     * createJsonNode -> jsonNode ok
     * jsonReduce -> simplified jsonNode ok
     * jsonNodeToAPTEDTree -> APTED Tree String ok
     */
    private final File existedFile;
    private JsonNode jsonNode = null;
    private final ObjectMapper om;
    private final Set<String> stopWords = new HashSet<>();

    public JsonToTree(File f) {
        existedFile = f;
        om = new ObjectMapper();
        initStopWords();
    }

    public JsonToTree() {
        existedFile = null;
        om = new ObjectMapper();
        initStopWords();
    }

    /**
     * {A{B{X}{Y}{F}}{C}}
     * A
     * / \
     * B   C
     * / | \
     * X  Y Z
     */
    public static String[] JsonStringToAPTEDString(String[] json, String[] file) {
        String[] ret = new String[json.length];
        for (int i = 0; i < json.length; i++) {
            JsonToTree jsonToTree = new JsonToTree();
            jsonToTree.createJsonNodeFromString(json[i]);
            jsonToTree.jsonReduce(file[i]);
            Log.out("Json String no." + (i + 1) + " reduce stage done");
            ret[i] = jsonToTree.jsonNodeToAPTEDTree();
            Log.out("Json String no." + (i + 1) + " done");
        }
        return ret;
    }

    private void initStopWords() {
        stopWords.add("id");
        stopWords.add("loc");
        stopWords.add("range");
        stopWords.add("mangledName");
        stopWords.add("valueCategory");
    }

    public static void main(String[] args) {
        Log.err("you shouldn't run JsonToTree.main() ");
        System.exit(1);
        JsonToTree jtt = new JsonToTree(new File("./tmp/f1.json"));
        jtt.createJsonNode();
        jtt.jsonReduce("./testcase/f1.c");
        String s = jtt.jsonNodeToAPTEDTree();

        System.out.println(s);

        ObjectMapper om = new ObjectMapper();
        JsonNode root1 = null;
        try {
            root1 = om.readTree(new File("./tmp/f1.json"));
            root1 = om.readTree(new File("./tmp/union.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert root1 != null;
        ArrayNode inner1 = (ArrayNode) root1.get("inner");
        if (inner1.isArray()) {
            for (int i = 0; i < inner1.size(); ) {
                JsonNode jj = inner1.get(i).get("loc").get("file");
                if (jj == null || !jj.asText().startsWith("./testcase/")) {
                    inner1.remove(i);
                } else {
                    break;
                }
            }
        }
        try {
            System.out.println(om.writeValueAsString(root1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public JsonNode getJsonNode() {
        return jsonNode;
    }

    public void jsonReduce(String srcCodeFileName) {
        if (jsonNode == null) {
            Log.err("jsonNode is null!");
        } else {
            ArrayNode inner = (ArrayNode) jsonNode.get("inner");
            if (inner.isArray()) {
                for (int i = 0; i < inner.size(); ) {
                    JsonNode tmp = inner.get(i).get("loc").get("file");
                    if (tmp == null || !tmp.asText().equals(srcCodeFileName)) {
                        inner.remove(i);
                    } else break;
                }
            } else {
                Log.err("inner is not array");
            }
        }
    }

    public String jsonNodeToAPTEDTree() {
        StringBuilder sb = new StringBuilder();
        jsonNodeToAPTEDTreeRecursion(jsonNode, sb);
        return sb.toString();
    }

    private void jsonNodeToAPTEDTreeRecursion(JsonNode root, StringBuilder sb) {
        if (root.isValueNode()) {
            sb.append("{");
            sb.append(root.asText().replaceAll("\"", ""));
            sb.append("}");
        } else if (root.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = root.fields();
            sb.append("{");
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                if (!stopWords.contains(entry.getKey())) {
                    sb.append("{");
                    sb.append(entry.getKey());
                    jsonNodeToAPTEDTreeRecursion(entry.getValue(), sb);
                    sb.append("}");
                }
            }
            sb.append("}");
        } else if (root.isArray()) {
            //sb.append("{");
            for (JsonNode node : root) {
                jsonNodeToAPTEDTreeRecursion(node, sb);
            }
            //sb.append("}");
        }
    }

    /**
     * 返回false说明建立失败
     * true建立成功
     */
    public void createJsonNode() {
        try {
            if (existedFile == null)
                throw new IOException("existedFile is null");
            jsonNode = om.readTree(existedFile);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * 返回false说明建立失败
     * true建立成功
     */
    public void createJsonNodeFromString(String s) {
        try {
            jsonNode = om.readTree(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void jsonLeaf(JsonNode node) {
        if (node.isValueNode()) {
            System.out.println(node);
            return;
        }

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                System.out.println("key: " + entry.getKey());
                jsonLeaf(entry.getValue());
            }
            return;
        }

        if (node.isArray()) {
            for (JsonNode value : node) {
                jsonLeaf(value);
            }
        }
    }
}
