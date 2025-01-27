package com.deshyan.mandela;

import com.deshyan.mandela.abstractSyntaxTree.BinaryOperator;
import com.deshyan.mandela.abstractSyntaxTree.Num;

public class Interpreter extends NodeVisitor{
    private final Parser parser;


    public Interpreter(Parser parser) {
        this.parser = parser;
    }


    public int visitBinaryOperator(BinaryOperator binaryOperator) {
        if (binaryOperator.getOperator().getTokenType() == TokenType.PLUS) {
            return (int)visit(binaryOperator.getLeft()) + (int) visit(binaryOperator.getRight());
        } else if (binaryOperator.getOperator().getTokenType() == TokenType.MINUS) {
            return (int)visit(binaryOperator.getLeft()) - (int) visit(binaryOperator.getRight());
        } else if (binaryOperator.getOperator().getTokenType() == TokenType.MULTIPLY) {
            return (int)visit(binaryOperator.getLeft()) * (int) visit(binaryOperator.getRight());
        } else if (binaryOperator.getOperator().getTokenType() == TokenType.DIVIDE) {
            return (int)visit(binaryOperator.getLeft()) / (int) visit(binaryOperator.getRight());
        }else {
            throw new IllegalArgumentException("Invalid operator");
        }
    }
    public int visitNum(Num num) {
        return num.getValue();
    }

    public int interpret() {
        var tree = parser.parse();
        return (int) visit(tree);
    }
}

