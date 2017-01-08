package com.he.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.he.dynamicLoader.DynamicLoader;
import com.he.dynamicLoader.JavaClassObject;
import com.he.dynamicLoader.MemoryClassLoader;

public class CalcTaskLoader {
	public static void main(String[] args) {
		
		try {
			FileReader fr = new FileReader(new File("F:\\sumpay-risk-processor\\src\\main\\java\\DynamicLoader\\CalcTaskCreater.java"));
			
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
			MemoryClassLoader mLoader = new MemoryClassLoader();
			
			Map<String, JavaClassObject> compiles = DynamicLoader.compiles(new String[]{"DynamicLoader.CalcTaskCreater"},new String[]{sb.toString()});
			
			Set<Entry<String, JavaClassObject>> entrySet = compiles.entrySet();
			Iterator<Entry<String, JavaClassObject>> iterator = entrySet.iterator();
			Class<?> loadClass = null;
			while(iterator.hasNext()){
				Entry<String, JavaClassObject> next = iterator.next();
				Class<?> loadClass1 = mLoader.loadClass(next.getKey(),next.getValue().getBytes());
				if(next.getKey().equals("DynamicLoader.CalcTaskCreater")){
					loadClass = loadClass1;
				}
			}
			Object newInstance = loadClass.newInstance();
			
			Method method = loadClass.getMethod("getCalcTask");
			
			Object object = method.invoke(newInstance);
			
			System.out.println(object);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
