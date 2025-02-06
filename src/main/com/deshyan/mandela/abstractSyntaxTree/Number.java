package com.deshyan.mandela.abstractSyntaxTree;

import com.deshyan.mandela.lexer.Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a number/INTEGER in the AST
 */
@Getter
public class Number extends AbstractSyntaxTree {
    private Token token;
    private int value;

    public Number(Token token) {
        this.token = token;
        this.value = token.getValue();
    }

    /**
     * Represents an assignment in the AST
     */
    @Getter
    public static class Assignment extends AbstractSyntaxTree {


    }

    @Getter
    public static class Compound extends AbstractSyntaxTree {
        private final List<AbstractSyntaxTree> children;

        public Compound() {
            children = new ArrayList<>();
        }
    }
}
