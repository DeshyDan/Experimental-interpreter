package com.deshyan.interpreter.interpreter;

import com.deshyan.interpreter.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.interpreter.abstractSyntaxTree.BinaryOperator;
import com.deshyan.interpreter.abstractSyntaxTree.FunctionCall;
import com.deshyan.interpreter.abstractSyntaxTree.FunctionDefinition;
import com.deshyan.interpreter.abstractSyntaxTree.Number;
import com.deshyan.interpreter.abstractSyntaxTree.Program;
import com.deshyan.interpreter.abstractSyntaxTree.Return;
import com.deshyan.interpreter.abstractSyntaxTree.Variable;
import com.deshyan.interpreter.abstractSyntaxTree.VariableDeclaration;

import java.util.HashMap;
import java.util.Map;

/**
 * Interprets an abstract syntax tree.
 */
public class Interpreter {
    private Map<String, Object> globalScope = new HashMap<>();
    private final Map<String, FunctionDefinition> functions = new HashMap<>();

    /**
     * Interprets the given abstract syntax tree.
     *
     * @param node The abstract syntax tree to interpret.
     * @return The result of the interpretation.
     */
    public Object interpret(AbstractSyntaxTree node) {
        if (node instanceof Program) {
            return interpretProgram((Program) node);
        } else if (node instanceof VariableDeclaration) {
            return interpretVariableDeclaration((VariableDeclaration) node);
        } else if (node instanceof Variable) {
            return interpretVariable((Variable) node);
        } else if (node instanceof FunctionDefinition) {
            return interpretFunctionDefinition((FunctionDefinition) node);
        } else if (node instanceof FunctionCall) {
            return interpretFunctionCall((FunctionCall) node);
        } else if (node instanceof BinaryOperator) {
            return interpretBinaryOp((BinaryOperator) node);
        } else if (node instanceof Number) {
            return ((Number) node).getValue();
        } else if (node instanceof Return) {
            return interpret(((Return) node).getExpression());
        }
        throw new RuntimeException("Unknown node type: " + node.getClass().getSimpleName());
    }

    /**
     * Interprets a program node
     *
     * @param node The program node to interpret
     * @return The result of the interpretation
     */
    private Object interpretProgram(Program node) {
        Object result = null;
        for (AbstractSyntaxTree statement : node.getStatements()) {
            result = interpret(statement);
        }
        return result;
    }

    /**
     * Interprets a variable declaration node
     *
     * @param node The variable declaration node to interpret
     * @return The result of the interpretation
     */
    private Object interpretVariableDeclaration(VariableDeclaration node) {
        Object value = interpret(node.getValue());
        globalScope.put(node.getName(), value);
        return value;
    }

    /**
     * Interprets a variable node
     *
     * @param node The variable node to interpret
     * @return The result of the interpretation
     */
    private Object interpretVariable(Variable node) {
        if (!globalScope.containsKey(node.getName())) {
            throw new RuntimeException("Undefined variable: " + node.getName());
        }
        return globalScope.get(node.getName());
    }

    /**
     * Interprets a function definition node
     *
     * @param node The function definition node to interpret
     * @return The result of the interpretation
     */
    private Object interpretFunctionDefinition(FunctionDefinition node) {
        functions.put(node.getName(), node);
        return null;
    }

    /**
     * Interprets a function call node
     *
     * @param node The function call node to interpret
     * @return The result of the interpretation
     */
    private Object interpretFunctionCall(FunctionCall node) {
        FunctionDefinition function = functions.get(node.getName());
        if (function == null) {
            throw new RuntimeException("Undefined function: " + node.getName());
        }
        Map<String, Object> localScope = new HashMap<>();
        for (int i = 0; i < function.getParameters().size(); i++) {
            localScope.put(function.getParameters().get(i), interpret(node.getArguments().get(i)));
        }
        Map<String, Object> originalScope = globalScope;
        globalScope = localScope;
        Object result = null;
        for (AbstractSyntaxTree bodyNode : function.getBody()) {
            result = interpret(bodyNode);
        }
        globalScope = originalScope;
        return result;
    }

    /**
     * Interprets a binary operator node
     *
     * @param node The binary operator node to interpret
     * @return The result of the interpretation
     */
    private Object interpretBinaryOp(BinaryOperator node) {
        Object left = interpret(node.getLeft());
        Object right = interpret(node.getRight());
        switch (node.getOperator().getText()) {
            case "+":
                return (int) left + (int) right;
            case "-":
                return (int) left - (int) right;
            case "*":
                return (int) left * (int) right;
            case "/":
                return (int) left / (int) right;
            default:
                throw new RuntimeException("Unknown operator: " + node.getOperator().getText());
        }
    }
}