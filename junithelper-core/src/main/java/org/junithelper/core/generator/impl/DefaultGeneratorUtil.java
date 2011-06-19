package org.junithelper.core.generator.impl;

import org.junithelper.core.config.TestingTarget;
import org.junithelper.core.constant.RegExp;
import org.junithelper.core.constant.StringValue;
import org.junithelper.core.meta.AccessModifier;
import org.junithelper.core.meta.ClassMeta;
import org.junithelper.core.meta.MethodMeta;

final class DefaultGeneratorUtil {

	private DefaultGeneratorUtil() {
	}

	static boolean isPublicMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
		return methodMeta.accessModifier == AccessModifier.Public && target.isPublicMethodRequired;
	}

	static boolean isProtectedMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
		return methodMeta.accessModifier == AccessModifier.Protected && target.isProtectedMethodRequired;
	}

	static boolean isPackageLocalMethodAndTestingRequired(MethodMeta methodMeta, TestingTarget target) {
		return methodMeta.accessModifier == AccessModifier.PackageLocal && target.isPackageLocalMethodRequired;
	}

	static void appendIfNotExists(StringBuilder buf, String src, String importLine) {
		String oneline = src.replaceAll(RegExp.CRLF, StringValue.Space);
		importLine = importLine.replace(StringValue.CarriageReturn + StringValue.LineFeed, StringValue.Empty);
		String importLineRegExp = importLine.replaceAll("\\s+", "\\\\s+").replaceAll("\\.", "\\\\.")
				.replaceAll("\\*", "\\\\*");
		if (!oneline.matches(RegExp.Anything_ZeroOrMore_Min + importLineRegExp + RegExp.Anything_ZeroOrMore_Min)) {
			buf.append(importLine);
			buf.append(StringValue.CarriageReturn);
			buf.append(StringValue.LineFeed);
		}
	}

	static boolean isCanonicalClassNameUsed(String expectedCanonicalClassName, String usedClassName,
			ClassMeta targetClassMeta) {
		if (usedClassName.equals(expectedCanonicalClassName)
				|| usedClassName.equals(expectedCanonicalClassName.replace("java.lang.", ""))) {
			// canonical class name
			// e.g.
			// "com.example.ArgBean"
			return true;
		} else {
			// imported type
			// e.g.
			// (same package)
			// import com.example.*;
			// import com.example.ArgBean;
			// "ArgBean"
			String[] extSplitted = expectedCanonicalClassName.split("\\.");
			String extClassName = extSplitted[extSplitted.length - 1];
			if (usedClassName.equals(extClassName)) {
				String extInSamplePackage = targetClassMeta.packageName + "." + extClassName;
				if (extInSamplePackage.equals(expectedCanonicalClassName)) {
					return true;
				} else {
					for (String imported : targetClassMeta.importedList) {
						String target = expectedCanonicalClassName.replaceFirst(extClassName, "");
						if (imported.matches(expectedCanonicalClassName) || imported.matches(target + ".+")) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
