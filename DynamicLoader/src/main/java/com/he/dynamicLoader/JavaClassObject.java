package com.he.dynamicLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.tools.SimpleJavaFileObject;

/**
 * ݔ��class �ļ���
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
     * ����һ�������������װ��JavaCompiler������Class�ļ�
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
     * ��дopenOutputStream�������������JavaCompiler������������õ�Classװ�ؽ���
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
