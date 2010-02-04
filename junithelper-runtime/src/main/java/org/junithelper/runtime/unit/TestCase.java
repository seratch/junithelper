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
package org.junithelper.runtime.unit;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * Subclass of {@link junit.framework.TestCase} <br>
 * which is customized by junithelper-runtime project.<br>
 * 
 * <pre>
 * final SomeClass obj = jmock2.mock(SomeClass.class);
 * jmock2.checking(new Expectations() {
 * 	{
 * 		one(obj).doSomething();
 * 	}
 * });
 * </pre>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public abstract class TestCase extends junit.framework.TestCase {

	/**
	 * JMock2 Mockey instance
	 */
	protected Mockery jmock2;

	/**
	 * Using JMock2 or not
	 */
	protected boolean usingJMock2 = true;

	/**
	 * return {@link Mockery} instance.
	 * 
	 * @return Mockery instance
	 */
	protected Mockery getNewJMock2Mockey() {
		return new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
	}

	@Override
	protected void setUp() throws Exception {
		if (usingJMock2) {
			jmock2 = getNewJMock2Mockey();
		}
		super.setUp();
	}

}
