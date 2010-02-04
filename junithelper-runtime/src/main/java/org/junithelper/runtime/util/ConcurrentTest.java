package org.junithelper.runtime.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Concurrent testing utility
 * 
 * @author Kazuhiro Sera <seratch@gmail.com>
 */
public class ConcurrentTest {

	/**
	 * Callable execute result
	 * 
	 * @author k-sera
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
