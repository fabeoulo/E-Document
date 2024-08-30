/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.helper;

import javax.annotation.PostConstruct;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wei.Cheng
 */
@Component
public class SpringExpressionUtils {

    private ExpressionParser parser;

    private StandardEvaluationContext context;

    @PostConstruct
    protected void init() {
        parser = new SpelExpressionParser();
        context = new StandardEvaluationContext();
    }

    public Object getValueFromFormula(Object obj, String formula) {
        Expression exp = parser.parseExpression(formula);
        return exp.getValue(context, obj);
    }

    public Object setValueFromFormula(Object obj, String propName, Object value) {
        if (propName != null) {
            Expression exp = parser.parseExpression(propName);
            exp.setValue(context, obj, value);
        }
        return obj;
    }

    public void setVariable(String name, Object object) {
        context.setVariable(name, object);
    }
}
