package com.he.dynamicLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class DynamicLoader {

	private static final Pattern pattern = Pattern.compile("^\\s+public\\s+class\\s+(\\w+)");
    
	private static final String EXT = ".java";
	/**
	 * compile java source
	 * @param sourceCode
	 * @return
	 */
    public static JavaClassObject compile(String sourceCode) {
    	
        Matcher matcher = pattern.matcher(sourceCode);

        if (matcher.find())
            return compile(matcher.group(1) + EXT, sourceCode);
        
        return null;
    }
    
    /**
     * compile java source
     * @param className
     * @param sourceCode
     * @return
     */
    public static JavaClassObject compile(String className, String sourceCode) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MemoryJavaFileManager manager = null;
        try{
        	manager = new MemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
            JavaFileObject javaFileObject = manager.makeStringSource(className, sourceCode);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call())
                return manager.getJavaClassObject();
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
    
    /**
     * compile java source
     * @param className
     * @param sourceCode
     * @return
     */
    public static Map<String,JavaClassObject> compiles(String[] classNames, String[] sourceCodes) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        MemoryJavaFileManager manager = null;
        try{
        	manager = new MemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
        	List<JavaFileObject> javaFiles = new ArrayList<JavaFileObject>();
        	for(int i=0;i<sourceCodes.length;i++){
        		JavaFileObject javaFileObject = manager.makeStringSource(classNames[i], sourceCodes[i]);
        		javaFiles.add(javaFileObject);
        	}
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, javaFiles);
            if (task.call())
                return manager.getJavaClassObjectMap();
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
