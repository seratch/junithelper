package org.junithelper.runtime.util;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

public final class JMock2Util {

	private JMock2Util() {
	}

	/**
	 * Get a new {@link Mockery} instance.
	 * 
	 * @return a new {@link Mockery} instance
	 */
	public static Mockery getNewInstance() {
		return new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
	}

}
