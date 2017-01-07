package com.he.dynamicLoader;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class DynamicLoader {

    /**
     * auto fill in the java-name with code, return null if cannot find the public class
     * @param javaSrc source code string
     * @return return the Map, the KEY means ClassName, the VALUE means bytecode.
     */
    public static Map<String, byte[]> compile(String javaSrc) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");

        Matcher matcher = pattern.matcher(javaSrc);

        if (matcher.find())
            return compile(matcher.group(1) + ".java", javaSrc);
        return null;
    }
    
    /**
     * @param javaName the name of your public class,eg: <code>TestClass.java</code>
     * @param javaSrc source code string
     * @return return the Map, the KEY means ClassName, the VALUE means bytecode.
     */
    public static Map<String, byte[]> compile(String javaName, String javaSrc) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);

        try{
        	MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager);
            JavaFileObject javaFileObject = manager.makeStringSource(javaName, javaSrc);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call())
                return manager.getClassBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
