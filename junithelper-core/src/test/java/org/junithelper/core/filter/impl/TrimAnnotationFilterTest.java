package org.junithelper.core.filter.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TrimAnnotationFilterTest {

	@Test
	public void trimAll_A$String_annotationClass() throws Exception {
		TrimAnnotationFilter target = new TrimAnnotationFilter();
		String src = "@Documented @Retention(RetentionPolicy.RUNTIME) @Target( { ElementType.TYPE, ElementType.METHOD }) public @interface AdminRoleRequired {";
		String actual = target.trimAll(src);
		String expected = "   public interface AdminRoleRequired {";
		assertEquals(expected, actual);
	}

	@Test
	public void trimAll_A$String_1() throws Exception {
		TrimAnnotationFilter target = new TrimAnnotationFilter();
		String src = "@Deprected\r\npublic void aaa(@NotNull String bbb) {";
		String actual = target.trimAll(src);
		String expected = " public void aaa( String bbb) {";
		assertEquals(expected, actual);
	}

	@Test
	public void trimAll_A$String_2() throws Exception {
		TrimAnnotationFilter target = new TrimAnnotationFilter();
		String src = "@Deprected\r\n@Target({ ElementType.TYPE, ElementType.METHOD }) \r\npublic void aaa(@NotNull String bbb) {";
		String actual = target.trimAll(src);
		String expected = "  public void aaa( String bbb) {";
		assertEquals(expected, actual);
	}

}
