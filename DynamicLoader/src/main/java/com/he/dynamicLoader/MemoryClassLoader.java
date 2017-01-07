package com.he.dynamicLoader;

import java.net.URL;
import java.net.URLClassLoader;
/**
 * 
 * @author Heyongcheng
 *
 */
public class MemoryClassLoader extends URLClassLoader {

	private byte[] classBytes;
	
    public MemoryClassLoader(String className,byte[] classBytes) {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
        this.classBytes = classBytes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (classBytes == null) {
            return super.findClass(name);
        }
        return defineClass(name, classBytes, 0, classBytes.length);
    }
}