package com.he.dynamicLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.tools.SimpleJavaFileObject;

/**
 * 輸出class 文件流
 * @author Heyongcheng
 *
 */
public class JavaClassObject extends SimpleJavaFileObject{

	private List<String> names = new ArrayList<String>();

	public List<String> getNames() {
		return names;
	}

	public byte[] getBytes() {
        return bos.toByteArray();
    }
	
	/**
     * 定义一个输出流，用于装载JavaCompiler编译后的Class文件
     */
    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	/**
	 * 
	 * @param className
	 */
	protected JavaClassObject(String className) {
		super(URI.create("string:///" + className.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
		this.names.add(className);
	}

	/**
     * 重写openOutputStream，将输出流交给JavaCompiler，让它将编译好的Class装载进来
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        bos.close();
    }
    
}
