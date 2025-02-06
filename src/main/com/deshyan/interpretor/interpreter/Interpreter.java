package com.deshyan.interpretor.interpreter;

import com.deshyan.interpretor.abstractSyntaxTree.AbstractSyntaxTree;
import com.deshyan.interpretor.abstractSyntaxTree.BinaryOperator;
import com.deshyan.interpretor.abstractSyntaxTree.FunctionCall;
import com.deshyan.interpretor.abstractSyntaxTree.FunctionDefinition;
import com.deshyan.interpretor.abstractSyntaxTree.Number;
import com.deshyan.interpretor.abstractSyntaxTree.Program;
import com.deshyan.interpretor.abstractSyntaxTree.Variable;
import com.deshyan.interpretor.abstractSyntaxTree.VariableDeclaration;

import java.util.HashMap;
import java.util.Map;

public class Interpreter {
    private Map<String, Object> globalScope = new HashMap<>();
    private final Map<String, FunctionDefinition> functions = new HashMap<>();

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
        }
        throw new RuntimeException("Unknown node type: " + node.getClass().getSimpleName());
    }

    private Object interpretProgram(Program node) {
        Object result = null;
        for (AbstractSyntaxTree statement : node.getStatements()) {
            result = interpret(statement);
        }
        return result;
    }

    private Object interpretVariableDeclaration(VariableDeclaration node) {
        Object value = interpret(node.getValue());
        globalScope.put(node.getName(), value);
        return value;
    }

    private Object interpretVariable(Variable node) {
        if (!globalScope.containsKey(node.getName())) {
            throw new RuntimeException("Undefined variable: " + node.getName());
        }
        return globalScope.get(node.getName());
    }

    private Object interpretFunctionDefinition(FunctionDefinition node) {
        functions.put(node.getName(), node);
        return null;
    }

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