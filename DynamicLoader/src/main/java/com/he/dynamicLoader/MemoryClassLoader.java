package com.he.dynamicLoader;

import java.net.URL;
import java.net.URLClassLoader;
/**
 * 
 * @author Heyongcheng
 *
 */
public class MemoryClassLoader extends URLClassLoader {
	
    public MemoryClassLoader() {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
    }
    
    public Class<?> loadClass(String name,byte[] classBytes) throws ClassNotFoundException {
    	return defineClass(name, classBytes, 0, classBytes.length);
    }
    
}