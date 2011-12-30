package org.junithelper.core.extractor;

import java.util.ArrayList;
import java.util.List;

import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.util.Assertion;

public class ArgExtractorHelper {

    private ArgExtractorHelper() {
    }

    static final String NESTED_GENERICS_MARK = "@NESTED_GENERICS@";

    static List<String> getArgListFromArgsDefAreaString(String argsDefAreaString) {

        Assertion.on("argsDefAreaString").mustNotBeNull(argsDefAreaString);

        // nested generics
        StringBuilder tmp = new StringBuilder();
        boolean isInsideOfGeneric = false;
        boolean isInsideOfNestedGeneric = false;
        int depthOfNestedGenerics = 0;
        int len = argsDefAreaString.length();
        for (int i = 0; i < len; i++) {
            char c = argsDefAreaString.charAt(i);
            if (isInsideOfNestedGeneric) {
                if (c == '<') {
                    depthOfNestedGenerics++;
                }
                if (c == '>') {
                    if (depthOfNestedGenerics == 1) {
                        depthOfNestedGenerics = 0;
                        tmp.append(NESTED_GENERICS_MARK);
                        isInsideOfNestedGeneric = false;
                    } else {
                        depthOfNestedGenerics--;
                    }
                }
                continue;
            } else if (isInsideOfGeneric) {
                if (c == '<') {
                    isInsideOfNestedGeneric = true;
                    depthOfNestedGenerics = 1;
                    continue;
                }
                if (c == '>') {
                    isInsideOfGeneric = false;
                }
            } else {
                if (c == '<') {
                    isInsideOfGeneric = true;
                }
                if (c == '>') {
                    isInsideOfGeneric = false;
                }
            }
            tmp.append(c);
        }
        argsDefAreaString = tmp.toString();

        String[] commaSplittedArray = argsDefAreaString.split(StringValue.Comma);
        int commaSplittedArrayLength = commaSplittedArray.length;
        List<String> args = new ArrayList<String>();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < commaSplittedArrayLength; i++) {
            String eachArea = commaSplittedArray[i].trim();
            eachArea = eachArea.replaceFirst("\\)\\s*.*\\s*\\{\\s*", StringValue.Empty);
            // ex. List<String>
            if (eachArea.matches(".+?<.+?>.+")) {
                eachArea = trimGenericsAreaIfNestedGenericsExists(eachArea);
                args.add(eachArea);
                continue;
            }
            // ex. Map<String
            if (eachArea.matches(".+?<.+")) {
                buf.append(eachArea);
                continue;
            }
            // ex. (Map<String,) Object>
            if (eachArea.matches(".+?>.+")) {
                String result = buf.toString() + StringValue.Comma + eachArea;
                result = trimGenericsAreaIfNestedGenericsExists(result);
                args.add(result);
                // re-initialize
                buf = new StringBuilder();
                continue;
            }
            if (!buf.toString().equals(StringValue.Empty)) {
                buf.append(StringValue.Comma);
                buf.append(eachArea);
                continue;
            }
            eachArea = trimGenericsAreaIfNestedGenericsExists(eachArea);
            args.add(eachArea);
        }
        return args;
    }

    static String trimGenericsAreaIfNestedGenericsExists(String target) {
        Assertion.on("target").mustNotBeNull(target);
        if (target.matches(RegExp.Anything_OneOrMore_Min + NESTED_GENERICS_MARK + RegExp.Anything_OneOrMore_Min)) {
            return target.replaceAll(RegExp.Generics, StringValue.Empty);
        }
        return target;
    }

}
