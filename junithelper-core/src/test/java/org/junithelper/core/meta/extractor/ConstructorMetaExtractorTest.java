package org.junithelper.core.meta.extractor;

import org.junit.Test;
import org.junithelper.core.config.Configulation;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ConstructorMeta;
import org.junithelper.core.util.IOUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ConstructorMetaExtractorTest {

	@Test
	public void type() throws Exception {
		assertNotNull(ConstructorMetaExtractor.class);
	}

	@Test
	public void instantiation() throws Exception {
		Configulation config = null;
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		assertNotNull(target);
	}

	@Test
	public void initialize_A$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String sourceCodeString = "package hoge; public class Sample { public void doSomething(String str) {} }";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ConstructorMetaExtractor actual = target.initialize(sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		ConstructorMetaExtractor expected = target;
		assertEquals(expected, actual);
	}

	@Test
	public void initialize_A$ClassMeta$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		ClassMeta classMeta = mock(ClassMeta.class);
		String sourceCodeString = "package hoge; public class Sample { public void doSomething(String str) {} }";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		ConstructorMetaExtractor actual = target.initialize(classMeta,
				sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		ConstructorMetaExtractor expected = target;
		assertEquals(expected, actual);
	}

	@Test
	public void extract_A$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String sourceCodeString = "package hoge; public class Sample { public void doSomething(String str) {} }";
		target.initialize(sourceCodeString);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		List<ConstructorMeta> actual = target.extract(sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		assertEquals(1, actual.size());
	}

	@Test
	public void extract_A$String_issue47() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String sourceCodeString = "import java.util.Map; public class HttpResponse {\r\n    private byte content[];\r\n    private int statusCode; \r\n    private Map headers;\r\n    public HttpResponse(byte content[], int statusCode, Map headers) {\r\n        this.content = content;\r\n        this.statusCode = statusCode;\r\n        this.headers = headers;\r\n    }\r\n\r\n";
		target.initialize(sourceCodeString);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		List<ConstructorMeta> actual = target.extract(sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		assertEquals(1, actual.size());
		assertEquals("byte[]", actual.get(0).argTypes.get(0).name);
		assertEquals("int", actual.get(0).argTypes.get(1).name);
		assertEquals("Map", actual.get(0).argTypes.get(2).name);
		assertEquals("content", actual.get(0).argNames.get(0));
		assertEquals("statusCode", actual.get(0).argNames.get(1));
		assertEquals("headers", actual.get(0).argNames.get(2));
	}

	@Test
	public void extract_A$String_Slim3_HtmlUtil() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String sourceCodeString = IOUtil.readAsString(
				IOUtil.getResourceAsStream("parser/impl/Slim3_HtmlUtil.txt"),
				"UTF-8");
		target.initialize(sourceCodeString);
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		List<ConstructorMeta> actual = target.extract(sourceCodeString);
		// then
		// e.g. : verify(mocked).called();
		assertEquals(1, actual.size());
		assertEquals(AccessModifier.Private, actual.get(0).accessModifier);
	}

	@Test
	public void getAccessModifier_A$String() throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String methodSignatureArea = "} public static void main(String[] args) {";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		AccessModifier actual = target.getAccessModifier(methodSignatureArea);
		// then
		// e.g. : verify(mocked).called();
		AccessModifier expected = AccessModifier.Public;
		assertEquals(expected, actual);
	}

	@Test
	public void trimAccessModifierFromMethodSignatureArea_A$String()
			throws Exception {
		Configulation config = new Configulation();
		ConstructorMetaExtractor target = new ConstructorMetaExtractor(config);
		// given
		String methodSignatureArea = "} public static void main(String[] args) {";
		// e.g. : given(mocked.called()).willReturn(1);
		// when
		String actual = target
				.trimAccessModifierFromMethodSignatureArea(methodSignatureArea);
		// then
		// e.g. : verify(mocked).called();
		String expected = " static void main(String[] args) {";
		assertEquals(expected, actual);
	}

}
