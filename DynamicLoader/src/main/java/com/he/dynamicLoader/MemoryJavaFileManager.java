package com.he.dynamicLoader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager>{

	private JavaClassObject javaClassObject;
	
	public JavaClassObject getJavaClassObject() {
		return javaClassObject;
	}
	
	private Map<String,JavaClassObject> javaClassObjectMap = new ConcurrentHashMap<String,JavaClassObject>();
	
	public Map<String, JavaClassObject> getJavaClassObjectMap() {
		return javaClassObjectMap;
	}
	public MemoryJavaFileManager(JavaFileManager fileManager) {
		super(fileManager);
	}
	/**
	 *  释放此文件管理器直接或间接打开的所有资源
	 */
	@Override
	public void close() throws IOException{
		super.close();
	}
	/**
	 * 刷新此文件管理器直接或间接为输出打开的所有资源
	 */
	@Override
    public void flush() throws IOException{
    	super.flush();
    }

    /**
     * 获取输出的文件对象，它表示给定位置处指定包中的指定相对名称
     */
    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location,
                                               String className,
                                               JavaFileObject.Kind kind,
                                               FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            javaClassObject = new JavaClassObject(className);
            javaClassObjectMap.put(className, javaClassObject);
            return javaClassObject;
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }
    
    /**
     * 創建源代碼
     * @param name
     * @param code
     * @return
     */
    public JavaFileObject makeStringSource(String name, String code) {
        return new JavaSourceFileObject(name, code);
    }
}
