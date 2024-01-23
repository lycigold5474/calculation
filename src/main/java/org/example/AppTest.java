package org.example;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

public class AppTest {

    public static void main(String[] args) {
        calculate("2*3+(5+VALUE)", 2);
    }

    private static void calculate(String expression, Object value) {
        if(expression.isEmpty()) expression = "2*3+(5+VALUE)";
        String strValue = "";

        if (value instanceof Number) {
            strValue = Number.class.cast(value).toString();
        } else if (value instanceof String) {
            strValue = parseNumberFromString((String) value);
        } else if (value instanceof Map) {
            strValue = parseNumberFromMap((Map) value);
        }

        String replacedExpression = expression.replace("VALUE", strValue);

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

    private static String parseNumberFromString(String str) {
        // 문자열에서 숫자를 파싱하는 로직을 구현
        // 아래는 간단한 예시입니다:
        return str.replaceAll("[^0-9]", "");
    }

    private static String parseNumberFromMap(Map map) {
        // 맵에서 숫자를 파싱하는 로직을 구현
        // 아래는 간단한 예시입니다:
        return map.get("num").toString();
    }




}
