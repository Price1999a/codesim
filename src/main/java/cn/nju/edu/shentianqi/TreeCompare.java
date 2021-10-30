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
    public static double computeSim(String apted1, String apted2) {
        String empty = "{}";//有趣之处就是假如有一个空的源文件 我们这一套之后生成的树就是只有一个翻译单元的ast树 对应到这里就是这个空树。
        double ret;
        BracketStringInputParser parser = new BracketStringInputParser();
        Node<StringNodeData> t1 = parser.fromString(apted1);
        Log.out("APTED String 1 parse stage done");
        Node<StringNodeData> t2 = parser.fromString(apted2);
        Log.out("APTED String 2 parse stage done");
        Node<StringNodeData> e = parser.fromString(empty);
        APTED<PerEditOperationStringNodeDataCostModel, StringNodeData> apted = new APTED<>
                (new PerEditOperationStringNodeDataCostModel(1.0f, 1.0f, 2.0f));
        ret = 1 - apted.computeEditDistance(t1, t2) /
                (apted.computeEditDistance(e, t1) + apted.computeEditDistance(e, t2));
        return ret;
    }

    public static void main(String[] args) {
        BracketStringInputParser parser = new BracketStringInputParser();
        Node<StringNodeData> t1 = parser.fromString("{A{B{X}{Y}{F}}{C}}");
        Node<StringNodeData> t2 = parser.fromString("{A{C}{B{F}{X}{Y}}}");
        Node<StringNodeData> tt = parser.fromString("{{kind{TranslationUnitDecl}}{inner{{kind{FunctionDecl}}{isReferenced{true}}{name{f}}{type{{qualType{int (int, int)}}}}{inner{{kind{ParmVarDecl}}{isUsed{true}}{name{a}}{type{{qualType{int}}}}}{{kind{ParmVarDecl}}{isUsed{true}}{name{b}}{type{{qualType{int}}}}}{{kind{CompoundStmt}}{inner{{kind{IfStmt}}{hasElse{true}}{inner{{kind{BinaryOperator}}{type{{qualType{int}}}}{opcode{==}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{b}}{type{{qualType{int}}}}}}}}}{{kind{IntegerLiteral}}{type{{qualType{int}}}}{value{0}}}}}{{kind{ReturnStmt}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{a}}{type{{qualType{int}}}}}}}}}}}{{kind{CompoundStmt}}{inner{{kind{ReturnStmt}}{inner{{kind{CallExpr}}{type{{qualType{int}}}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int (*)(int, int)}}}}{castKind{FunctionToPointerDecay}}{inner{{kind{DeclRefExpr}}{type{{qualType{int (int, int)}}}}{referencedDecl{{kind{FunctionDecl}}{name{f}}{type{{qualType{int (int, int)}}}}}}}}}{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{b}}{type{{qualType{int}}}}}}}}}{{kind{BinaryOperator}}{type{{qualType{int}}}}{opcode{%}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{a}}{type{{qualType{int}}}}}}}}}{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{b}}{type{{qualType{int}}}}}}}}}}}}}}}}}}}}}}}{{kind{FunctionDecl}}{isReferenced{true}}{name{f1}}{type{{qualType{int (int)}}}}{inner{{kind{ParmVarDecl}}{isUsed{true}}{name{aa}}{type{{qualType{int}}}}}{{kind{CompoundStmt}}{inner{{kind{IfStmt}}{inner{{kind{BinaryOperator}}{type{{qualType{int}}}}{opcode{==}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{aa}}{type{{qualType{int}}}}}}}}}{{kind{IntegerLiteral}}{type{{qualType{int}}}}{value{0}}}}}{{kind{ReturnStmt}}{inner{{kind{IntegerLiteral}}{type{{qualType{int}}}}{value{0}}}}}}}{{kind{ReturnStmt}}{inner{{kind{BinaryOperator}}{type{{qualType{int}}}}{opcode{+}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{aa}}{type{{qualType{int}}}}}}}}}{{kind{CallExpr}}{type{{qualType{int}}}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int (*)(int)}}}}{castKind{FunctionToPointerDecay}}{inner{{kind{DeclRefExpr}}{type{{qualType{int (int)}}}}{referencedDecl{{kind{FunctionDecl}}{name{f1}}{type{{qualType{int (int)}}}}}}}}}{{kind{BinaryOperator}}{type{{qualType{int}}}}{opcode{-}}{inner{{kind{ImplicitCastExpr}}{type{{qualType{int}}}}{castKind{LValueToRValue}}{inner{{kind{DeclRefExpr}}{type{{qualType{int}}}}{referencedDecl{{kind{ParmVarDecl}}{name{aa}}{type{{qualType{int}}}}}}}}}{{kind{IntegerLiteral}}{type{{qualType{int}}}}{value{1}}}}}}}}}}}}}}}}}");
        Node<StringNodeData> empty = parser.fromString("{}");
        APTED<PerEditOperationStringNodeDataCostModel, StringNodeData> apted = new APTED<>(new PerEditOperationStringNodeDataCostModel(1.0f, 1.0f, 2.0f));
        System.out.println(1 - apted.computeEditDistance(t1, t2) / (apted.computeEditDistance(t1, empty) + apted.computeEditDistance(t2, empty)));
        System.out.println(apted.computeEditDistance(tt, empty));
    }
}
