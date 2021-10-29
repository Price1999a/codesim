package cn.nju.edu.shentianqi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jdk.nashorn.internal.ir.ObjectNode;

import java.io.File;
import java.io.IOException;

public class JsonToTree {
    /**
     * 用于尝试解析几个已经手动dump下来的json文件
     * 由于cpp编译特性，头文件也会混杂进这里的ast 所以我们首先就是裁剪这棵树
     */
    public static void main(String[] args) {
        ObjectMapper om = new ObjectMapper();
        JsonNode root1 = null, root2;
        try {
            root1 = om.readTree(new File("./tmp/f1.json"));
            root2 = om.readTree(new File("./tmp/union.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
