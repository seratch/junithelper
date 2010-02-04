package org.junithelper.runtime.concurrent;

import java.util.concurrent.Callable;

import junit.framework.TestCase;

import org.junithelper.runtime.concurrent.ConcurrentTest.Result;

public class ConcurrentTestTest extends TestCase {

	public void test_start_A$Runnable$int() throws Exception {
		ConcurrentTest.start(new Runnable() {
			public void run() {
				// do something
			}
		}, 3);
	}

	public void test_getResult_A$int() throws Exception {
		ConcurrentTest.start(new Callable<Result>() {
			public Result call() throws Exception {
				Result res = new Result();
				res.returned = "res";
				return res;
			}
		}, 3);
		assertNotNull(ConcurrentTest.getResult(0));
		assertEquals("res", ConcurrentTest.getResult(0).returned);
		assertNotNull(ConcurrentTest.getResult(1));
		assertEquals("res", ConcurrentTest.getResult(1).returned);
		assertNotNull(ConcurrentTest.getResult(2));
		assertEquals("res", ConcurrentTest.getResult(2).returned);
	}

	public void test_start_A$Callable$int() throws Exception {
		ConcurrentTest.start(new Callable<Result>() {
			public Result call() throws Exception {
				Result res = new Result();
				res.returned = "res";
				return res;
			}
		}, 3);
	}
}
