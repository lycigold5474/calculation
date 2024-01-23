package org.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class AppTest2 {

    public static void main(String[] args) {
        Map<String, Object> values = new HashMap<>();
        values.put("KKND", 3);
        values.put("KKNDA", 2);
        calculate("(3*2)+2*(3+2)+KKND/KKNDA",values);
    }

    private static void calculate(String expression, Object value) {
        if(expression.isEmpty()) expression = "2*3+(5+KKND)";
        if (isInvalidExpression(expression)) {
            throw new IllegalArgumentException("Invalid expression");
        }
        String replacedExpression = expression;

        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if(val instanceof Number) {
                    replacedExpression = replacedExpression.replaceAll("\\b" + key + "\\b", Number.class.cast(val).toString());
                } else if (val instanceof String) {
                    replacedExpression = replacedExpression.replaceAll("\\b" + key + "\\b", parseNumberFromString((String) val));
                }
            }
        }

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        Object result = null;
        try {
            result = engine.eval(replacedExpression);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Result: " + result);
    }

    private static boolean isInvalidExpression(String expression) {
        return !expression.matches("^[\\d+\\-\\*/()\\sA-Za-z]*$");
    }

    private static String parseNumberFromString(String str) {
        // 문자열에서 숫자를 파싱하는 로직을 구현
        return str.replaceAll("[^0-9]", "");
    }



}
