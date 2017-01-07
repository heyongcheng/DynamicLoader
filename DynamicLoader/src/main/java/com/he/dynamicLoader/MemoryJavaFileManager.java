package com.he.dynamicLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class MemoryJavaFileManager extends ForwardingJavaFileManager<JavaFileManager>{

	private final static String EXT = ".java";
	
	private Map<String, byte[]> classBytes;
	
	public Map<String, byte[]> getClassBytes() {
		return classBytes;
	}

	public void setClassBytes(Map<String, byte[]> classBytes) {
		this.classBytes = classBytes;
	}

	public MemoryJavaFileManager(JavaFileManager fileManager) {
		super(fileManager);
		classBytes = new ConcurrentHashMap<String, byte[]>();
	}
	/**
	 *  释放此文件管理器直接或间接打开的所有资源
	 */
	@Override
	public void close() throws IOException{
		try {
			super.close();
		} catch (IOException e) {
			throw new IOException(e);
		}finally{
			if(classBytes != null){
				classBytes = new ConcurrentHashMap<String, byte[]>();
			}
		}
	}
	/**
	 * 刷新此文件管理器直接或间接为输出打开的所有资源
	 */
	@Override
    public void flush() throws IOException{
    	super.flush();
    }

    /**
     * A file object used to represent Java source coming from a string.
     */
    private static class StringInputBuffer extends SimpleJavaFileObject {
    	
        final String code;

        StringInputBuffer(String name, String code) {
            super(toURI(name), Kind.SOURCE);
            this.code = code;
        }

        public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
            return CharBuffer.wrap(code);
        }
    }

    /**
     * A file object that stores Java bytecode into the classBytes map.
     */
    private class ClassOutputBuffer extends SimpleJavaFileObject {
    	
        private String name;
        
        ClassOutputBuffer(String name) {
            super(toURI(name), Kind.CLASS);
            this.name = name;
        }
        
        @Override
        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()) {
            	@Override
                public void close() throws IOException {
                	super.close();
					ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                    classBytes.put(name, bos.toByteArray());
                }
            };
        }
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
            return new ClassOutputBuffer(className);
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    public JavaFileObject makeStringSource(String name, String code) {
        return new StringInputBuffer(name, code);
    }

    private static URI toURI(String name) {
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
