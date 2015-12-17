package com.sxt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网络请求线程池
 * */

public class HttpThreadPool {

	private int Max_Size = 5;//核心线程数
	ExecutorService thread_pool = Executors.newFixedThreadPool(Max_Size);

	//启动线程方法
	public void execute(Runnable run){
		thread_pool.execute(run);
	}

}
