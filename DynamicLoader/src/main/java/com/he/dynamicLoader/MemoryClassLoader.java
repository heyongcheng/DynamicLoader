package com.he.dynamicLoader;

/**
 * 
 * @author Heyongcheng
 *
 */
public class MemoryClassLoader extends ClassLoader {
	
    public MemoryClassLoader() {
        super(MemoryClassLoader.class.getClassLoader());
    }
    
    public Class<?> loadClass(String name,byte[] classBytes) throws ClassNotFoundException {
    	return defineClass(name, classBytes, 0, classBytes.length);
    }
    
}