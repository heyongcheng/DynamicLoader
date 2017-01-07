package com.he.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

import com.he.dynamicLoader.DynamicLoader;
import com.he.dynamicLoader.JavaClassObject;
import com.he.dynamicLoader.MemoryClassLoader;

public class DynamicTest {
	
	public static void main(String[] args){
		try {
			FileReader fr = new FileReader(new File("F:\\sumpay-risk-processor\\src\\main\\java\\DynamicLoader\\TestClass.java"));
			
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
			JavaClassObject classObject = DynamicLoader.compile("TestClass",sb.toString());
			
			System.out.println(classObject);
			
			MemoryClassLoader mLoader = new MemoryClassLoader();
			
			Class<?> loadClass = mLoader.loadClass("TestClass",classObject.getBytes());
			
			Object newInstance = loadClass.newInstance();
			
			Method method = loadClass.getMethod("call");
			
			method.invoke(newInstance);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
