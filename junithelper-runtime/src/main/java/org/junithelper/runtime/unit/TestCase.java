package org.junithelper.runtime.unit;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

/**
 * Subclass of {@link junit.framework.TestCase} <br>
 * which is customized by junithelper-runtime project.
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 */
public abstract class TestCase extends junit.framework.TestCase {

	/**
	 * JMock2 Mockey instance
	 */
	protected Mockery jmock2;

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
		super.setUp();
		jmock2 = getNewJMock2Mockey();
	}

}
