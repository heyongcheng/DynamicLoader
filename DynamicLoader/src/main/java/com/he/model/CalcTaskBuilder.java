package com.he.model;

import java.util.ArrayList;
import java.util.List;

import com.he.item.MemCachedItem;


/**
 * 
 * @author Heyongcheng
 *
 */
public class CalcTaskBuilder {
	
	private CalcTask<Void> calcTask;
	
	private String name;
	
	private Class<?> objectType;
	
	private Key objectKey;
	
	private String memKey;
	
	private String varKey;
	
	private List<Cond> counds = new ArrayList<Cond>();
	
	private boolean lazyGroup;
	
	private CalcTaskBuilder(String name){
		this.name = name;
	}
	
	public static CalcTaskBuilder getInstance(String name){
		return new CalcTaskBuilder(name);
	}
	
	public CalcTaskBuilder selectMemCachedItems(String tag,String bizCode,boolean flag){
		this.memKey = MemCachedItem.getMemCachedKey(null,tag,bizCode,flag);
		return this;
	}
	
	public CalcTaskBuilder selectObjectType(Class<?> clazz){
		this.objectType = clazz;
		return this;
	}
	
	public CalcTaskBuilder objectKey(Key objectKey){
		this.objectKey = objectKey;
		return this;
	}
	
	public CalcTaskBuilder objectCond(Cond cond){
		this.counds.add(cond);
		return this;
	}
	
	public CalcTaskBuilder lazyGroup(boolean lazyGroup){
		this.lazyGroup = lazyGroup;
		return this;
	}
	
	public CalcTaskBuilder varKey(String varKey){
		this.varKey = varKey;
		return this;
	}
	
	public CalcTaskBuilder build(){
		return this;
	}
	/**
	 * setter and getter
	 * @return
	 */

	public CalcTask<Void> getCalcTask() {
		return calcTask;
	}

	public void setCalcTask(CalcTask<Void> calcTask) {
		this.calcTask = calcTask;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getObjectType() {
		return objectType;
	}

	public void setObjectType(Class<?> objectType) {
		this.objectType = objectType;
	}

	public Key getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(Key objectKey) {
		this.objectKey = objectKey;
	}

	public String getMemKey() {
		return memKey;
	}

	public void setMemKey(String memKey) {
		this.memKey = memKey;
	}

	public List<Cond> getCounds() {
		return counds;
	}

	public void setCounds(List<Cond> counds) {
		this.counds = counds;
	}

	public boolean isLazyGroup() {
		return lazyGroup;
	}

	public void setLazyGroup(boolean lazyGroup) {
		this.lazyGroup = lazyGroup;
	}

	public String getVarKey() {
		return varKey;
	}

	public void setVarKey(String varKey) {
		this.varKey = varKey;
	}

	@Override
	public String toString() {
		return "CalcTaskBuilder [calcTask=" + calcTask + ", name=" + name + ", objectType=" + objectType
				+ ", objectKey=" + objectKey + ", memKey=" + memKey + ", varKey=" + varKey + ", counds=" + counds
				+ ", lazyGroup=" + lazyGroup + "]";
	}
	
}
