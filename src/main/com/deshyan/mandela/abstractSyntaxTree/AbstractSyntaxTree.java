package com.deshyan.mandela.abstractSyntaxTree;

import com.deshyan.mandela.lexer.Token;
import lombok.Getter;

/**
 * This is a tree that represents the abstract syntactic structure of a language construct where each interior node and
 * the root node represents an operator, and the children of the node represent the operands of that operator
 */
public abstract class AbstractSyntaxTree {
    public static class UnaryOp extends AbstractSyntaxTree {
        private Token token;
        private AbstractSyntaxTree expr;

        public UnaryOp(Token token, AbstractSyntaxTree expr) {
            this.token = token;
            this.expr = expr;
        }
    }

    /**
     * Represents a variable in the AST
     */
    @Getter
    public static class Variable extends AbstractSyntaxTree {

        private Token token;
        private String value;

        public Variable(Token token) {
            this.token = token;
            this.value = token.getValue();
        }
    }
}