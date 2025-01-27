package com.deshyan.mandela;


import com.deshyan.mandela.abstractSyntaxTree.AbstractSyntaxTree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NodeVisitor {

    public Object visit(AbstractSyntaxTree node) {
        String methodName = "visit" + node.getClass().getSimpleName();
        try {
            Method method = this.getClass().getDeclaredMethod(methodName, node.getClass());
            return method.invoke(this, node);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return genericVisit(node);
        }
    }

    public Object genericVisit(AbstractSyntaxTree node) {
        throw new RuntimeException("No visit" + node.getClass().getSimpleName() + " method");
    }
}

