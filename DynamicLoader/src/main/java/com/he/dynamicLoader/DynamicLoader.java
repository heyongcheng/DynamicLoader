package com.he.dynamicLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class DynamicLoader {

	private static final Pattern pattern = Pattern.compile("^\\s+public\\s+class\\s+(\\w+)");
    
	/**
	 * compile java source
	 * @param sourceCode
	 * @return
	 */
    public static Map<String, byte[]> compile(String sourceCode) {
    	
        Matcher matcher = pattern.matcher(sourceCode);

        if (matcher.find())
            return compile(matcher.group(1) + ".java", sourceCode);
        
        return null;
    }
    
    /**
     * compile java source
     * @param className
     * @param sourceCode
     * @return
     */
    public static Map<String, byte[]> compile(String className, String sourceCode) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MemoryJavaFileManager manager = null;
        try{
        	manager = new MemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
            JavaFileObject javaFileObject = manager.makeStringSource(className, sourceCode);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call())
                return manager.getClassBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(manager != null){
        		try {
					manager.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return null;
    }
}
