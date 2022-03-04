package com.directa24.test.mostactiveauthors.way.too.simple.json.parser;

import com.directa24.test.mostactiveauthors.way.too.simple.json.parser.constants.Constants;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class JsonArray {

    private final char special;
    private final char comma;
    private List<String> jsonObjects;

    public JsonArray(String jsonStr) {
        this.special = Constants.SPECIAL.charValue();
        this.comma = Constants.COMMA.charValue();
        this.parseJson(jsonStr);
    }

    private void parseJson(String arg) {

        jsonObjects = new ArrayList<>();

        if (arg.startsWith(Constants.SQUARE_OPEN.toString()) && arg.endsWith(Constants.SQUARE_CLOSE.toString())) {

            StringBuilder jsonBuilder = new StringBuilder(arg);

            jsonBuilder.deleteCharAt(0);
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);

            replaceCommaWithinJsonObject(jsonBuilder);

            Collections.addAll(jsonObjects, jsonBuilder.toString().split(Constants.COMMA.toString()));
        }
    }


    private void replaceCommaWithinJsonObject(StringBuilder jsonBuilder) {

        boolean isJsonObject = false;
        int jsonBuilderLength = jsonBuilder.length();

        for (int i = 0; i < jsonBuilderLength; i++) {

            char ch = jsonBuilder.charAt(i);

            if (isJsonObject) {

                if (String.valueOf(ch).equals(Constants.COMMA.toString())) {
                    jsonBuilder.setCharAt(i, special);
                }
            }

            if (String.valueOf(ch).equals(Constants.CURLY_OPEN.toString())) {
                isJsonObject = true;
            }

            if (String.valueOf(ch).equals(Constants.CURLY_CLOSE.toString())) {
                isJsonObject = false;
            }
        }
    }

    public int size () {
        return jsonObjects.size();
    }

    public Optional<String> getObject(int index) {
            return jsonObjects != null && index < jsonObjects.size() ?
                    Optional.of(jsonObjects.get(index).replace(special, comma)) : Optional.empty();
    }

    public Optional<JsonObject> getJsonObject(int index) {
        return jsonObjects != null && index < jsonObjects.size() ? Optional.of(new JsonObject(jsonObjects.get(index)
                .replace(special, comma))) : Optional.empty();
    }

    public Stream<JsonObject> stream() {
        Iterator<JsonObject> it = jsonObjects.stream()
                .map(obj -> new JsonObject(obj.replace(special, comma)))
                .collect(Collectors.toList())
                .iterator();
        Spliterator<JsonObject> spliterator = Spliterators.spliterator(it, size(), 0);
        return StreamSupport.stream(spliterator, false);
    }
}
