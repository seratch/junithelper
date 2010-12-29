/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.core.generator.impl;

import java.util.ArrayList;
import java.util.List;

import org.junithelper.core.config.Configulation;
import org.junithelper.core.config.JUnitVersion;
import org.junithelper.core.config.MessageValue;
import org.junithelper.core.config.MockObjectFramework;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.generator.ConstructorGenerator;
import org.junithelper.core.generator.TestMethodGenerator;
import org.junithelper.core.meta.ArgTypeMeta;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.ExceptionMeta;
import org.junithelper.core.meta.MethodMeta;
import org.junithelper.core.meta.TestMethodMeta;
import org.junithelper.core.parser.detect.AvailableTypeDetector;
import org.junithelper.core.util.PrimitiveTypeUtil;

public class DefaultTestMethodGenerator implements TestMethodGenerator {

	private Configulation config;
	private ClassMeta targetClassMeta;
	private MessageValue messageValue = new MessageValue();
	private ConstructorGenerator constructorGenerator = new DefaultConstructorGenerator();

	public DefaultTestMethodGenerator(Configulation config) {
		this.config = config;
	}

	@Override
	public void initialize(ClassMeta targetClassMeta) {
		this.targetClassMeta = targetClassMeta;
		messageValue.initialize(config.language);
	}

	@Override
	public TestMethodMeta getTestMethodMeta(MethodMeta targetMethodMeta) {
		return getTestMethodMeta(targetMethodMeta, null);
	}

	@Override
	public TestMethodMeta getTestMethodMeta(MethodMeta targetMethodMeta,
			ExceptionMeta exception) {
		if (targetClassMeta == null) {
			throw new IllegalStateException("Not initialized");
		}
		TestMethodMeta testMethodMeta = new TestMethodMeta();
		testMethodMeta.classMeta = targetClassMeta;
		testMethodMeta.methodMeta = targetMethodMeta;
		testMethodMeta.testingTargetException = exception;
		return testMethodMeta;
	}

	@Override
	public String getTestMethodNamePrefix(TestMethodMeta testMethodMeta) {
		return getTestMethodNamePrefix(testMethodMeta, null);
	}

	@Override
	public String getTestMethodNamePrefix(TestMethodMeta testMethodMeta,
			ExceptionMeta exception) {
		MethodMeta targetMethodMeta = testMethodMeta.methodMeta;
		// testing instantiation
		if (targetMethodMeta == null) {
			if (testMethodMeta.isTypeTest) {
				return "type";
			} else if (testMethodMeta.isInstantiationTest) {
				return "instantiation";
			}
		}
		StringBuilder buf = new StringBuilder();
		buf.append(targetMethodMeta.name);
		if (config.testMethodName.isArgsRequired) {
			buf.append(config.testMethodName.basicDelimiter);
			buf.append(config.testMethodName.argsAreaPrefix);
			if (targetMethodMeta.argTypes.size() == 0) {
				buf.append(config.testMethodName.argsAreaDelimiter);
			} else {
				for (ArgTypeMeta argType : targetMethodMeta.argTypes) {
					buf.append(config.testMethodName.argsAreaDelimiter);
					buf.append(argType.nameInMethodName);
				}
			}
		}
		if (config.testMethodName.isReturnRequired) {
			buf.append(config.testMethodName.basicDelimiter);
			buf.append(config.testMethodName.returnAreaPrefix);
			buf.append(config.testMethodName.returnAreaDelimiter);
			if (targetMethodMeta.returnType.nameInMethodName == null) {
				buf.append("void");
			} else {
				buf.append(targetMethodMeta.returnType.nameInMethodName);
			}
		}
		if (exception != null) {
			buf.append(config.testMethodName.basicDelimiter);
			buf.append(config.testMethodName.exceptionAreaPrefix);
			buf.append(config.testMethodName.exceptionAreaDelimiter);
			buf.append(exception.nameInMethodName);
		}
		return buf.toString();
	}

	@Override
	public String getTestMethodSourceCode(TestMethodMeta testMethodMeta) {

		StringBuilder buf = new StringBuilder();

		// JMockit
		if (config.mockObjectFramework == MockObjectFramework.JMockit) {
			List<String> mockedFieldsForJMockit = getMockedFieldsForJMockit(testMethodMeta);
			for (String mocked : mockedFieldsForJMockit) {
				appendTabs(buf, 1);
				buf.append("@Mocked ");
				appendCRLF(buf);
				appendTabs(buf, 1);
				buf.append(mocked);
				buf.append(StringValue.Semicolon);
				appendCRLF(buf);
			}
			if (mockedFieldsForJMockit.size() > 0) {
				appendCRLF(buf);
			}
		}

		// test method signature
		if (config.junitVersion == JUnitVersion.version3) {
			appendTabs(buf, 1);
			buf.append("public void ");
			buf.append(StringValue.JUnit.TestMethodNamePrefixForJUnitVersion3);
			buf.append(config.testMethodName.basicDelimiter);
		} else {
			appendTabs(buf, 1);
			buf.append("@Test");
			appendCRLF(buf);
			appendTabs(buf, 1);
			buf.append("public void ");
		}
		buf.append(getTestMethodNamePrefix(testMethodMeta,
				testMethodMeta.testingTargetException));
		boolean isThrowableRequired = false;
		if (testMethodMeta.methodMeta != null
				&& testMethodMeta.methodMeta.throwsExceptions != null) {
			for (ExceptionMeta ex : testMethodMeta.methodMeta.throwsExceptions) {
				if (ex.name.equals("Throwable")) {
					isThrowableRequired = true;
					break;
				}
			}
		}
		buf.append("() throws ");
		buf.append(isThrowableRequired ? "Throwable" : "Exception");
		buf.append(" {");
		appendCRLF(buf);

		// auto generated todo message
		appendTabs(buf, 2);
		buf.append("// ");
		buf.append(messageValue.getAutoGeneratedTODOMessage());
		appendCRLF(buf);

		if (testMethodMeta.isTypeTest) {
			// testing type safe
			appendTabs(buf, 2);
			buf.append("assertNotNull(");
			buf.append(testMethodMeta.classMeta.name);
			buf.append(".class);");
			appendCRLF(buf);

		} else if (testMethodMeta.isInstantiationTest) {
			// testing instantiation
			String instantiation = constructorGenerator
					.getFirstInstantiationSourceCode(testMethodMeta.classMeta);
			buf.append(instantiation);
			appendTabs(buf, 2);
			buf.append("assertNotNull(target);");
			appendCRLF(buf);

		} else if (config.isTemplateImplementationRequired) {

			// prepare for Mock object framework
			if (config.mockObjectFramework == MockObjectFramework.JMock2) {
				appendTabs(buf, 2);
				buf.append("Mockery context = new Mockery(){{");
				appendCRLF(buf);
				appendTabs(buf, 3);
				buf.append("setImposteriser(ClassImposteriser.INSTANCE);");
				appendCRLF(buf);
				appendTabs(buf, 2);
				buf.append("}};");
				appendCRLF(buf);
			}
			if (config.mockObjectFramework == MockObjectFramework.EasyMock) {
				appendTabs(buf, 2);
				buf.append("IMocksControl mocks = EasyMock.createControl();");
				appendCRLF(buf);
			}
			// instantiation if testing an instance method
			if (!testMethodMeta.methodMeta.isStatic) {
				String instantiation = constructorGenerator
						.getFirstInstantiationSourceCode(testMethodMeta.classMeta);
				buf.append(instantiation);
			}
			// Mockito BDD
			appendBDDMockitoComment(buf, "given", 2);

			if (testMethodMeta.testingTargetException == null) {
				// --------------------------------
				// Normal pattern testing
				// --------------------------------
				// prepare args
				appendPreparingArgs(buf, testMethodMeta);
				// mock/stub checking
				appendMockChecking(buf, 2);
				// Mockito BDD
				appendBDDMockitoComment(buf, "when", 2);
				// return value
				if (testMethodMeta.methodMeta.returnType != null
						&& testMethodMeta.methodMeta.returnType.name != null) {
					appendTabs(buf, 2);
					buf.append(testMethodMeta.methodMeta.returnType.name);
					buf.append(" actual = ");
				} else {
					appendTabs(buf, 2);
				}
				// execute target method
				appendExecutingTargetMethod(buf, testMethodMeta);
				// Mockito BDD
				appendBDDMockitoComment(buf, "then", 2);
				appendMockVerifying(buf, 2);
				// check return value
				if (testMethodMeta.methodMeta.returnType != null
						&& testMethodMeta.methodMeta.returnType.name != null) {
					appendTabs(buf, 2);
					buf.append(testMethodMeta.methodMeta.returnType.name);
					buf.append(" expected = ");
					if (PrimitiveTypeUtil
							.isPrimitive(testMethodMeta.methodMeta.returnType.name)) {
						buf.append(PrimitiveTypeUtil
								.getTypeDefaultValue(testMethodMeta.methodMeta.returnType.name));
					} else {
						buf.append("null");
					}
					buf.append(StringValue.Semicolon);
					appendCRLF(buf);
					// assertion
					appendTabs(buf, 2);
					buf.append("assertEquals(expected, actual)");
					buf.append(StringValue.Semicolon);
					appendCRLF(buf);
				}
			} else {
				// --------------------------------
				// Exception pattern testing
				// --------------------------------
				// prepare args
				appendPreparingArgs(buf, testMethodMeta);
				// mock/stub checking
				appendMockChecking(buf, 2);
				// try
				appendTabs(buf, 2);
				buf.append("try {");
				appendCRLF(buf);
				// Mockito BDD
				appendBDDMockitoComment(buf, "when", 3);
				// execute target method
				appendTabs(buf, 3);
				appendExecutingTargetMethod(buf, testMethodMeta);
				// fail when no exception
				appendTabs(buf, 3);
				buf.append("fail(\"Expected exception was not thrown!\")");
				buf.append(StringValue.Semicolon);
				appendCRLF(buf);
				// catch
				appendTabs(buf, 2);
				buf.append("} catch (");
				buf.append(testMethodMeta.testingTargetException.name);
				buf.append(" e) {");
				appendCRLF(buf);
				// Mockito BDD
				appendBDDMockitoComment(buf, "then", 3);
				appendTabs(buf, 2);
				buf.append("}");
				appendCRLF(buf);
			}
		}
		appendTabs(buf, 1);
		buf.append("}");
		appendCRLF(buf);

		return buf.toString();
	}

	void appendCRLF(StringBuilder buf) {
		buf.append(StringValue.CarriageReturn);
		buf.append(StringValue.LineFeed);
	}

	void appendTabs(StringBuilder buf, int times) {
		for (int i = 0; i < times; i++) {
			buf.append(StringValue.Tab);
		}
	}

	void appendPreparingArgs(StringBuilder buf, TestMethodMeta testMethodMeta) {
		// prepare args
		int argsLen = testMethodMeta.methodMeta.argTypes.size();
		if (argsLen > 0) {
			for (int i = 0; i < argsLen; i++) {
				appendTabs(buf, 2);
				if (config.mockObjectFramework == MockObjectFramework.JMock2) {
					buf.append("final ");
				}
				ArgTypeMeta argTypeMeta = testMethodMeta.methodMeta.argTypes
						.get(i);
				String typeName = argTypeMeta.name;
				String argName = testMethodMeta.methodMeta.argNames.get(i);
				buf.append(typeName);
				buf.append(" ");
				buf.append(argName);
				buf.append(" = ");
				buf.append(getArgValue(testMethodMeta, argTypeMeta, argName));
				buf.append(StringValue.Semicolon);
				appendCRLF(buf);
			}
		}
	}

	void appendMockChecking(StringBuilder buf, int depth) {
		if (config.mockObjectFramework == MockObjectFramework.EasyMock) {
			appendTabs(buf, depth);
			buf.append("// ");
			buf.append(messageValue.getExempliGratia());
			buf.append(" : ");
			buf.append("EasyMock.expect(mocked.called()).andReturn(1);");
			appendCRLF(buf);
			appendTabs(buf, depth);
			buf.append("mocks.replay();");
			appendCRLF(buf);
		} else if (config.mockObjectFramework == MockObjectFramework.JMock2) {
			appendTabs(buf, depth);
			buf.append("context.checking(new Expectations(){{");
			appendCRLF(buf);
			appendTabs(buf, depth + 1);
			buf.append("// ");
			buf.append(messageValue.getExempliGratia());
			buf.append(" : ");
			buf.append("allowing(mocked).called(); will(returnValue(1));");
			appendCRLF(buf);
			appendTabs(buf, depth);
			buf.append("}});");
			appendCRLF(buf);
		} else if (config.mockObjectFramework == MockObjectFramework.JMockit) {
			appendTabs(buf, depth);
			buf.append("new Expectations(){{");
			appendCRLF(buf);
			appendTabs(buf, depth + 1);
			buf.append("// ");
			buf.append(messageValue.getExempliGratia());
			buf.append(" : ");
			buf.append("mocked.get(anyString); returns(200);");
			appendCRLF(buf);
			appendTabs(buf, depth);
			buf.append("}};");
			appendCRLF(buf);
		} else if (config.mockObjectFramework == MockObjectFramework.Mockito) {
			appendTabs(buf, depth);
			buf.append("// ");
			buf.append(messageValue.getExempliGratia());
			buf.append(" : ");
			buf.append("given(mocked.called()).willReturn(1);");
			appendCRLF(buf);
		}
	}

	void appendMockVerifying(StringBuilder buf, int depth) {
		// verfiy
		if (config.mockObjectFramework == MockObjectFramework.EasyMock) {
			appendTabs(buf, depth);
			buf.append("mocks.verify();");
			appendCRLF(buf);
		}
		if (config.mockObjectFramework == MockObjectFramework.Mockito) {
			appendTabs(buf, depth);
			buf.append("// ");
			buf.append(messageValue.getExempliGratia());
			buf.append(" : ");
			buf.append("verify(mocked).called();");
			appendCRLF(buf);
		}
	}

	void appendExecutingTargetMethod(StringBuilder buf,
			TestMethodMeta testMethodMeta) {
		String actor = (testMethodMeta.methodMeta.isStatic) ? testMethodMeta.classMeta.name
				: "target";
		buf.append(actor);
		buf.append(".");
		buf.append(testMethodMeta.methodMeta.name);
		buf.append("(");
		int argsLen = testMethodMeta.methodMeta.argTypes.size();
		if (argsLen > 0) {
			buf.append(testMethodMeta.methodMeta.argNames.get(0));
		}
		if (argsLen > 1) {
			for (int i = 1; i < argsLen; i++) {
				buf.append(StringValue.Comma);
				buf.append(StringValue.Space);
				buf.append(testMethodMeta.methodMeta.argNames.get(i));
			}
		}
		buf.append(")");
		buf.append(StringValue.Semicolon);
		appendCRLF(buf);
	}

	void appendBDDMockitoComment(StringBuilder buf, String value, int depth) {
		if (config.mockObjectFramework == MockObjectFramework.Mockito) {
			appendTabs(buf, depth);
			buf.append("// ");
			buf.append(value);
			appendCRLF(buf);
		}
	}

	List<String> getMockedFieldsForJMockit(TestMethodMeta testMethodMeta) {
		List<String> dest = new ArrayList<String>();
		if (testMethodMeta.methodMeta != null) {
			int len = testMethodMeta.methodMeta.argTypes.size();
			for (int i = 0; i < len; i++) {
				String typeName = testMethodMeta.methodMeta.argTypes.get(i).name;
				if (PrimitiveTypeUtil.isPrimitive(typeName)) {
					continue;
				}
				if (!new AvailableTypeDetector(targetClassMeta)
						.isJMockitMockableType(typeName)) {
					continue;
				}
				ArgTypeMeta argTypeMeta = testMethodMeta.methodMeta.argTypes
						.get(i);
				String argName = testMethodMeta.methodMeta.argNames.get(i);
				String value = getArgValue(testMethodMeta, argTypeMeta, argName);
				if (value.equals("this."
						+ getTestMethodNamePrefix(testMethodMeta,
								testMethodMeta.testingTargetException) + "_"
						+ argName)) {
					dest.add(argTypeMeta.name + " "
							+ value.replace("this.", ""));
				}
			}
		}
		return dest;
	}

	String getArgValue(TestMethodMeta testMethodMeta, ArgTypeMeta argTypeMeta,
			String argName) {
		AvailableTypeDetector availableTypeDetector = new AvailableTypeDetector(
				targetClassMeta);
		if (availableTypeDetector.isJavaLangPackageType(argTypeMeta.name)) {
			return "null";
		} else if (PrimitiveTypeUtil.isPrimitive(argTypeMeta.name)) {
			return PrimitiveTypeUtil.getTypeDefaultValue(argTypeMeta.name);
		} else if (argTypeMeta.name.matches(".+?\\[\\]$")) {
			return "new " + argTypeMeta.name + " {}";
		} else if (argTypeMeta.name.matches("List(<[^>]+>)?")
				&& availableTypeDetector.isAvailableType("java.util.List",
						config)) {
			targetClassMeta.importedList.add("java.util.ArrayList");
			String genericsString = argTypeMeta.getGenericsAsString();
			if (genericsString.equals("<?>")) {
				genericsString = "";
			}
			return "new ArrayList" + genericsString + "()";
		} else if (argTypeMeta.name.matches("Map(<[^>]+>)?")
				&& availableTypeDetector.isAvailableType("java.util.Map",
						config)) {
			targetClassMeta.importedList.add("java.util.HashMap");
			String genericsString = argTypeMeta.getGenericsAsString();
			if (genericsString.matches("<.*\\?.*>")) {
				genericsString = "";
			}
			return "new HashMap" + genericsString + "()";
		} else if (config.mockObjectFramework == MockObjectFramework.EasyMock) {
			return "mocks.createMock("
					+ argTypeMeta.name.replaceAll(RegExp.Generics,
							StringValue.Empty) + ".class)";
		} else if (config.mockObjectFramework == MockObjectFramework.JMock2) {
			return "context.mock("
					+ argTypeMeta.name.replaceAll(RegExp.Generics,
							StringValue.Empty) + ".class)";
		} else if (config.mockObjectFramework == MockObjectFramework.JMockit) {
			if (new AvailableTypeDetector(targetClassMeta)
					.isJMockitMockableType(argTypeMeta.name)) {
				return "this."
						+ getTestMethodNamePrefix(testMethodMeta,
								testMethodMeta.testingTargetException) + "_"
						+ argName;
			} else {
				return "null";
			}
		} else if (config.mockObjectFramework == MockObjectFramework.Mockito) {
			return "mock("
					+ argTypeMeta.name.replaceAll(RegExp.Generics,
							StringValue.Empty) + ".class)";
		} else {
			return "null";
		}
	}

}
