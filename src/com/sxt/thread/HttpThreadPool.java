package com.sxt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���������̳߳�
 * */

public class HttpThreadPool {

	private int Max_Size = 5;//�����߳���
	ExecutorService thread_pool = Executors.newFixedThreadPool(Max_Size);

	//�����̷߳���
	public void execute(Runnable run){
		thread_pool.execute(run);
	}

}
