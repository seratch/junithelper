package org.junithelper.core.constant;

import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.Test;

public class RegExpTest {

    @Test
    public void type() throws Exception {
        assertNotNull(RegExp.class);
    }

    @Test
    public void MethodSignatureArea_true() {
        String[] expectedTrue = new String[] { "} public String get(String name)		 { ", //
                "} public void run(String name){ ",//
                "; public void  run( String name )		 { ",//
                "} 	 public static void main(String[] args) { ",//
                "} public 		static final void main(String[] args) { ",//
                "} public static final void main(String[] args) { ",//
                "} static public final void main(String[]		 args) { ",//
                "} final public static void main(String[] args) { ",//
                "} private String run(String name) { ",//
                "} protected String 		run(String name) { ",//
                "} final String run(String name) 			{ ",//
                "} public boolean 		run(List<String> array, Map<String,Object> map){ ",//
                "} public			 boolean run(Map<String,Object> map) 		   {  ",//
                "} public static boolean 		run(Map<String,Object> map)  	  { ",//

        };
        for (int i = 0; i < expectedTrue.length; i++) {
            assertTrue(expectedTrue[i], expectedTrue[i].matches(RegExp.MethodSignatureArea));
        }
    }

    @Test
    public void MethodSignatureArea_false() {
        String[] expectedFalse = new String[] {
                "",//
                "public RegExp() { ",//
                "String get(String name) { ",//
                "String get(String name) { ",//
                "public static String matchesMethod",//
                "	static \\{",//
                "/\\*\\* public void run(IAction action, ISelection selection) \\*\\*/",
                "/\\*\\* public void run(List<String> list, Map<String,Object> values) \\*\\*/",//
                "public void run(String name)",//

        };
        for (int i = 0; i < expectedFalse.length; i++) {
            assertFalse(expectedFalse[i], expectedFalse[i].matches(RegExp.MethodSignatureArea));
        }
    }

    @Test
    public void StaticMethodSignature_true() {
        String[] expectedTrue = new String[] { "public static 		void main(String[] args) { ",//
                "public static final void 		main(String[] args)		 { ",//
                "public static final 	void main(String[] args)		 { ",//
                "static public final void main(String[] args) { ",//
                "final		 public static void main(String[] args) 			{ ",//
                "public static boolean 		run(Map<String,Object> map)  	  { } ",//

        };
        for (int i = 0; i < expectedTrue.length; i++) {
            assertTrue(expectedTrue[i], expectedTrue[i].matches(RegExp.StaticMethodSignature));
        }
    }

    public void StaticMethodSignature_false() {
        String[] expectedFalse = new String[] { "",//
                "public RegExp() { ",//
                "String get(String name) { ",//
                "public static String matchesMethod",//
                "	static \\{",//
                "/\\*\\* public void run(IAction action, ISelection selection) \\*\\*/",//
                "/\\*\\* public void run(List<String> list, Map<String,Object> values) \\*\\*/",//
                "public void run(String name)",//
                " String get(String name) { ",//
                "public void run(String name){ ",//
                "public void  run( String name ) { ",//
                "private String run(String name) { ",//
                "protected String run(String name) { ",//
                "final String run(String name) { ",//
                "public boolean run(List<String> array, Map<String,Object> map){ ",//
                "public boolean run(Map<String,Object> map)    { } ",//

        };
        for (int i = 0; i < expectedFalse.length; i++) {
            assertFalse(expectedFalse[i], expectedFalse[i].matches(RegExp.StaticMethodSignature));
        }
    }

    @Test
    public void MethodSignature_Group() {
        Matcher methodMatcher = RegExp.PatternObject.MethodSignatureWithoutAccessModifier_Group
                .matcher("public static boolean 		run(    Map<String,Object>  	   map  )  	  { }");
        if (methodMatcher.find()) {
            assertEquals("boolean", methodMatcher.group(1));
            assertEquals("run", methodMatcher.group(2));
            assertEquals("    Map<String,Object>  	   map  ", methodMatcher.group(3));
        } else {
            fail("not matched!");
        }
        methodMatcher = RegExp.PatternObject.MethodSignatureWithoutAccessModifier_Group
                .matcher("public static boolean 		run(  String hoge ,  Map<String,Object>  	   map  )  	  { }");
        if (methodMatcher.find()) {
            assertEquals("boolean", methodMatcher.group(1));
            assertEquals("run", methodMatcher.group(2));
            assertEquals("  String hoge ,  Map<String,Object>  	   map  ", methodMatcher.group(3));
        } else {
            fail("not matched!");
        }
    }

    @Test
    public void MethodArg_Group() {
        Matcher nameMatcher = RegExp.PatternObject.MethodArg_Group.matcher("boolean hoge");
        if (nameMatcher.find()) {
            assertEquals("hoge", nameMatcher.group(1));
        } else {
            fail("test ng");
        }
    }

}
