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
package org.junithelper.runtime.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Utility class for concurrent testing<br>
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 * @version 1.0
 */
public class ConcurrentTest {

	/**
	 * Hiding default constructor
	 */
	private ConcurrentTest() {
	}

	/**
	 * Result object of callable execution
	 * 
	 * @author Kazuhiro Sera <seratch@gmail.com>
	 */
	public static class Result {
		/**
		 * returned value
		 */
		public Object returned;
		/**
		 * throwed exception
		 */
		public Exception throwed;
	}

	/**
	 * Start concurrent testing
	 * 
	 * <pre>
	 * ConcurrentTest.start(new Runnable() {
	 * 	public void run() {
	 * 		// do something
	 * 	}
	 * }, 3);
	 * </pre>
	 * 
	 * @param runnable
	 * @param maxThreads
	 */
	protected static void start(Runnable runnable, int maxThreads) {
		for (int i = 0; i < maxThreads; i++) {
			new Thread(runnable).start();
		}
	}

	/**
	 * Concurrent callable testing tasks
	 */
	private static List<FutureTask<Result>> futureTasks = new ArrayList<FutureTask<Result>>();

	/**
	 * Get concurrent callable testing result
	 * 
	 * <pre>
	 * Obejct returned = ConcurrentTest.getResult(0).returned;
	 * </pre>
	 * 
	 * @param index
	 * @return Result
	 */
	protected static Result getResult(int index) {
		try {
			return futureTasks.get(index).get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * Start concurrent testing
	 * 
	 * <pre>
	 * ConcurrentTest.start(new Callable&lt;Result&gt;() {
	 * 	public Result call() throws Exception {
	 * 		Result res = new Result();
	 * 		res.returned = &quot;res&quot;;
	 * 		return res;
	 * 	}
	 * }, 3);
	 * </pre>
	 * 
	 * @param callable
	 * @param maxThreads
	 * @throws Exception
	 */
	protected static void start(Callable<Result> callable, int maxThreads)
			throws Exception {
		for (int i = 0; i < maxThreads; i++) {
			FutureTask<Result> ft = new FutureTask<Result>(callable);
			futureTasks.add(ft);
		}
		int max = futureTasks.size();
		for (int i = 0; i < max; i++) {
			final FutureTask<Result> ft = futureTasks.get(i);
			Thread t = new Thread(ft);
			t.start();
		}
	}
}
