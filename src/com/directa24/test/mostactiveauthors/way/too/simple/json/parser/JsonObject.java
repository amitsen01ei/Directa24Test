package com.directa24.test.mostactiveauthors.way.too.simple.json.parser;

import com.directa24.test.mostactiveauthors.way.too.simple.json.parser.constants.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class JsonObject {

    private final char special;
    private final char comma;
    private Map<String, String> jsonObjects;

    public JsonObject(String jsonStr) {
        this.special = Constants.SPECIAL.charValue();
        this.comma = Constants.COMMA.charValue();
        this.parseJson(jsonStr);
    }

    private void parseJson(String jsonStr) {

        jsonObjects = new HashMap<>();

        if (jsonStr.startsWith(Constants.CURLY_OPEN.toString()) && jsonStr.endsWith(Constants.CURLY_CLOSE.toString())) {

            StringBuilder jsonBuilder = new StringBuilder(jsonStr);
            jsonBuilder.deleteCharAt(0);
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);

            replaceCommaWithinJsonArray(jsonBuilder);

            for (String jsonObject : jsonBuilder.toString().split(Constants.COMMA.toString())) {

                String[] objectValue = jsonObject.split(Constants.COLON.toString(), 2);

                if (objectValue.length == 2)
                    this.jsonObjects.put(objectValue[0]
                                    .replace("'", "")
                                    .replace("\"", "").trim(),
                            objectValue[1]
                                    .replace("'", "")
                                    .replace("\"", "").trim());
            }
        }
    }

    /**This method replaces comma with a special character so that when {@link #parseJson(String)} calls
     *  {@code String.split(",")} is called, the JsonArray value is preserved.
     * @param jsonBuilder : StringBuilder - A StringBuilder with Json String without the opening and closing curly braces
     */
    public void replaceCommaWithinJsonArray(StringBuilder jsonBuilder) {

        boolean isJsonArray = false;
        boolean isInsideQuote = false;
        int jsonBuilderLength = jsonBuilder.length();

        for (int i = 0; i < jsonBuilderLength; i++) {

            char ch = jsonBuilder.charAt(i);

            if (isJsonArray) {

                if (String.valueOf(ch).equals(Constants.COMMA.toString())) {
                    jsonBuilder.setCharAt(i, special);
                }
            }

            if (ch == '"' || ch == '\'') {
                isInsideQuote = !isInsideQuote;
            }

            if (String.valueOf(ch).equals(Constants.SQUARE_OPEN.toString()) && !isInsideQuote) {
                isJsonArray = true;
            }

            if (String.valueOf(ch).equals(Constants.SQUARE_CLOSE.toString()) && !isInsideQuote) {
                isJsonArray = false;
            }
        }
    }

    public Optional<String> getValue(String key) {
        return jsonObjects != null && jsonObjects.containsKey(key) ?
                Optional.of(jsonObjects.get(key).replace(special, comma)) : Optional.empty();
    }

    public Optional<JsonArray> getJsonArray(String key) {
        return jsonObjects != null ? Optional.of(new JsonArray(jsonObjects.get(key).replace(special, comma))) :
                Optional.empty();
    }

    @Override
    public String toString() {
        return "JsonObject{" +
                "jsonObjects=" + jsonObjects +
                '}';
    }
}
