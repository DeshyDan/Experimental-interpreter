package com.deshyan.mandela.interpreter;

import com.deshyan.mandela.abstractSyntaxTree.BinaryOperator;
import com.deshyan.mandela.abstractSyntaxTree.Number;
import com.deshyan.mandela.abstractSyntaxTree.UnaryOperator;
import com.deshyan.mandela.lexer.TokenType;
import com.deshyan.mandela.parser.Parser;

public class Interpreter extends NodeVisitor {
    private final Parser parser;


    public Interpreter(Parser parser) {
        this.parser = parser;
    }


    public int visitBinaryOperator(BinaryOperator binaryOperator) {
        switch (binaryOperator.getOperator().getTokenType()) {
            case PLUS:
                return (int) visit(binaryOperator.getLeft()) + (int) visit(binaryOperator.getRight());
            case MINUS:
                return (int) visit(binaryOperator.getLeft()) - (int) visit(binaryOperator.getRight());
            case MULTIPLY:
                return (int) visit(binaryOperator.getLeft()) * (int) visit(binaryOperator.getRight());
            case DIVIDE:
                return (int) visit(binaryOperator.getLeft()) / (int) visit(binaryOperator.getRight());
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    public void visitNoOp() {
        // Do nothing
    }

    public void visitCompound(Number.Compound node) {
        for (var child : node.getChildren()) {
            visit(child);
        }
    }

    public int visitUnaryOperator(UnaryOperator node) {
        var operation = node.getToken().getTokenType();

        if (operation == TokenType.PLUS) {
            return (int) visit(node.getExpr());
        } else if (operation == TokenType.MINUS) {
            return -1 * (int) visit(node.getExpr());
        } else {
            throw new IllegalArgumentException("Invalid operator");
        }
    }

    public int visitNum(Number number) {
        return number.getValue();
    }

    public int interpret() {
        var tree = parser.parse();
        return (int) visit(tree);
    }
}

