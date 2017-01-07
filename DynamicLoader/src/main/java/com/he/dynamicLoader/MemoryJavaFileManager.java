package com.he.dynamicLoader;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager>{

	private final static String EXT = ".java";

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
            	return new JavaClassObject(className);
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

    /**
     * 
     * @param name
     * @return
     */
    public static URI toURI(String name) {
        File file = new File(name);
        if (file.exists()) {
            return file.toURI();
        } else {
            try {
                final StringBuilder newUri = new StringBuilder();
                newUri.append("mfm:///");
                newUri.append(name.replace('.', '/'));
                if (name.endsWith(EXT)) newUri.replace(newUri.length() - EXT.length(), newUri.length(), EXT);
                return URI.create(newUri.toString());
            } catch (Exception exp) {
                return URI.create("mfm:///com/sun/script/java/java_source");
            }
        }
    }
}
