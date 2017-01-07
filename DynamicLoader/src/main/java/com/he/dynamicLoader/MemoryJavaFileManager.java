package com.he.dynamicLoader;

import java.io.IOException;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager>{

	private JavaClassObject javaClassObject;
	
	public JavaClassObject getJavaClassObject() {
		return javaClassObject;
	}
	
	public MemoryJavaFileManager(JavaFileManager fileManager) {
		super(fileManager);
	}
	/**
	 *  �ͷŴ��ļ�������ֱ�ӻ��Ӵ򿪵�������Դ
	 */
	@Override
	public void close() throws IOException{
		super.close();
	}
	/**
	 * ˢ�´��ļ�������ֱ�ӻ���Ϊ����򿪵�������Դ
	 */
	@Override
    public void flush() throws IOException{
    	super.flush();
    }

    /**
     * ��ȡ������ļ���������ʾ����λ�ô�ָ�����е�ָ���������
     */
    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location,
                                               String className,
                                               JavaFileObject.Kind kind,
                                               FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            if(javaClassObject == null){
            	javaClassObject = new JavaClassObject(className);
            }
            return javaClassObject;
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }
    
    /**
     * ����Դ���a
     * @param name
     * @param code
     * @return
     */
    public JavaFileObject makeStringSource(String name, String code) {
        return new JavaSourceFileObject(name, code);
    }
}
