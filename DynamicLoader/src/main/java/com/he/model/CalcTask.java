package com.he.model;

import java.util.concurrent.Callable;

/**
 * 
 * @author Heyongcheng
 *
 */
public abstract class CalcTask<T> implements Callable<T>{

	//protected Logger LOG = LoggerFactory.getLogger(getClass());
	
	public T call() throws Exception {
		//if(LOG.isDebugEnabled())
			//LOG.debug("calc task {} is starting...", getName());
		T obj = calc();
		//if(LOG.isDebugEnabled())
			//LOG.debug("calc task {} is starting...", getName());
		return obj;
	}
	
	public abstract String getName();
	
	public abstract T calc();
}
