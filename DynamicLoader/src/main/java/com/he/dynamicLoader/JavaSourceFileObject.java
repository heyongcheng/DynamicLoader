package com.he.dynamicLoader;

import java.nio.CharBuffer;

import javax.tools.SimpleJavaFileObject;
/**
 * java source code
 * @author Heyongcheng
 *
 */
public class JavaSourceFileObject extends SimpleJavaFileObject{

	private String content;
	
	protected JavaSourceFileObject(String name,String code) {
		super(MemoryJavaFileManager.toURI(name), Kind.SOURCE);
		this.content = code;
	}
	/**
	 * ʵ��getCharContent��JavaCompiler���Դ�content��ȡjavaԴ��
	 */
    public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
        return CharBuffer.wrap(content);
    }
}
