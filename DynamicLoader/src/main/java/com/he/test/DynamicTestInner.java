package com.he.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.he.dynamicLoader.DynamicLoader;
import com.he.dynamicLoader.JavaClassObject;
import com.he.dynamicLoader.MemoryClassLoader;

public class DynamicTestInner {
	public static void main(String[] args) {

		try {
			FileReader fr = new FileReader(new File("F:\\sumpay-risk-processor\\src\\main\\java\\DynamicLoader\\Key.java"));
			
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
			//JavaClassObject classObject = DynamicLoader.compile("Key",sb.toString());
			
			//System.out.println(classObject);
			
			/** classLoader  **/
			MemoryClassLoader mLoader = new MemoryClassLoader();
			
		//	Class<?> loadClass = mLoader.loadClass("Key",classObject.getBytes());
		//	System.out.println(loadClass.getClassLoader());
			
			FileReader fr2 = new FileReader(new File("F:\\sumpay-risk-processor\\src\\main\\java\\DynamicLoader\\KeyImpl.java"));
			
			BufferedReader br2 = new BufferedReader(fr2);
			
			String line2 = null;
			StringBuffer sb2 = new StringBuffer();
			while((line2 = br2.readLine()) != null){
				sb2.append(line2);
			}
			
			
			Map<String, JavaClassObject> compiles = DynamicLoader.compiles(new String[]{"DynamicLoader.Key","DynamicLoader.KeyImpl"},new String[]{sb.toString(),sb2.toString()});
			
			Set<Entry<String, JavaClassObject>> entrySet = compiles.entrySet();
			Iterator<Entry<String, JavaClassObject>> iterator = entrySet.iterator();
			Class<?> loadClass = null;
			while(iterator.hasNext()){
				Entry<String, JavaClassObject> next = iterator.next();
				Class<?> loadClass1 = mLoader.loadClass(next.getKey(),next.getValue().getBytes());
				if(next.getKey().equals("DynamicLoader.KeyImpl")){
					loadClass = loadClass1;
				}
			}
			
			/*Class<?> loadClass1 = mLoader.loadClass("DynamicLoader.Key",compiles.get("DynamicLoader.Key").getBytes());
			Class<?> loadClass2 = mLoader.loadClass("DynamicLoader.KeyImpl",compiles.get("DynamicLoader.KeyImpl").getBytes());
			Class<?> loadClass3 = mLoader.loadClass("DynamicLoader.KeyImpl$1",compiles.get("DynamicLoader.KeyImpl$1").getBytes());*/
			Object newInstance = loadClass.newInstance();
			
			Method method = loadClass.getMethod("call2");
			
			method.invoke(newInstance);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
