package cn.nju.edu.shentianqi;

import eu.mihosoft.ext.apted.costmodel.PerEditOperationStringNodeDataCostModel;
import eu.mihosoft.ext.apted.distance.APTED;
import eu.mihosoft.ext.apted.node.Node;
import eu.mihosoft.ext.apted.node.StringNodeData;
import eu.mihosoft.ext.apted.parser.BracketStringInputParser;

public class TreeCompare {
    /**
     * 是这样计算树相似度的
     */
    public static void main(String[] args) {
        BracketStringInputParser parser = new BracketStringInputParser();
        Node<StringNodeData> t1 = parser.fromString("{A{B{X}{Y}{F}}{C}}");
        Node<StringNodeData> t2 = parser.fromString("{A{C}{B{F}{X}{Y}}}");
        Node<StringNodeData> empty = parser.fromString("{}");
        APTED<PerEditOperationStringNodeDataCostModel, StringNodeData> apted = new APTED<>(new PerEditOperationStringNodeDataCostModel(1.0f, 1.0f, 2.0f));
        System.out.println(1 - apted.computeEditDistance(t1, t2) / (apted.computeEditDistance(t1, empty) + apted.computeEditDistance(t2, empty)));
    }
}
