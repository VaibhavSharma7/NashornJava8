package com.my.pack;

import javax.script.*;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

public class NashornDemo {

    public static void main(String arr[]) {
        String str = "This is a java global variable accessible in js file";
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        try {
            NashornDemo nashornDemo = new NashornDemo();
            //Initializing global variable.
            engine.put("javaGlobalVar", str);
            //Evaluating a Script File
            System.out.println("Before Script");
            engine = nashornDemo.evaluateScriptFile(engine);
            nashornDemo.invokeScriptTopLevelFunctions(engine);
            Object object = nashornDemo.invokeScriptObjectFunctions(engine);
            nashornDemo.invokeMultiThreads(engine, object);
            nashornDemo.modifyingScriptContext(engine);

            nashornDemo.dateJsMethod(engine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String staticFunction(String name) {
        System.out.println("Hi there from Java Static Function ," + name);
        return "greetings from java";
    }


    public String nonStaticFunction(String name) {
        System.out.println("Hi there from Java Non Static Function ," + name);
        return "greetings from java";
    }


    public void dateJsMethod(ScriptEngine engine) throws Exception {
        engine.eval(new FileReader("javascript-program-files/date.js"));
        Invocable invocable = (Invocable) engine;

        Object dateJs = engine.eval("Date");
        Object data = invocable.invokeMethod(dateJs, "parse","next thursday");
        System.out.println(" >>>  " + data);
    }

    public ScriptEngine evaluateScriptFile(ScriptEngine scriptEngine) throws Exception {
//        engine.eval("print('Hello World! directly from main() script code.')"); //Directly executing js code.
//        engine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/0.12.2/react.js')");
        scriptEngine.eval(new FileReader("javascript-program-files/script.js"));
        return scriptEngine;
    }

    public void invokeScriptTopLevelFunctions(ScriptEngine scriptEngine) throws Exception {
        Invocable invocable = (Invocable) scriptEngine;
        Object result = invocable.invokeFunction("fun1", "Vaibhav Sharma");
        System.out.println(result);
        System.out.println(result.getClass());
        invocable.invokeFunction("fun2", new Date());
        invocable.invokeFunction("fun2", LocalDateTime.now());
        invocable.invokeFunction("fun2", new Person("Vaibhav >>> Sharma"));
    }

    public Object invokeScriptObjectFunctions(ScriptEngine scriptEngine) throws Exception {
        Invocable invocable = (Invocable) scriptEngine;
        Object object = scriptEngine.get("user");
        Object returnedString = invocable.invokeMethod(object, "getFullName", "xyz", "abc");
        System.out.println("Response from js >> " + returnedString);
        return object;
    }

    public void invokeMultiThreads(ScriptEngine scriptEngine, Object object) throws Exception {
        Invocable invocable = (Invocable) scriptEngine;
        Object result = invocable.invokeFunction("fun1", "Vaibhav Sharma");
        System.out.println(result);
        System.out.println(result.getClass());
        //Implementing a Java Interface with the Script Top Level Method.
        // get Runnable interface object
        Runnable runnable = invocable.getInterface(Runnable.class);
        // start a new thread that runs the script
        Thread thread = new Thread(runnable);
        thread.start();

        //Implementing a Java Interface with the Script Object's Methods
        // get Runnable interface object
        Runnable r = invocable.getInterface(object, Runnable.class);
        // start a new thread that runs the script
        Thread th = new Thread(r);
        th.start();
    }

    public void modifyingScriptContext(ScriptEngine engine) throws Exception {
        // Define a different script context & use same global variable with different value.
        // define a different script context
        ScriptContext newContext = new SimpleScriptContext();
//            newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
        // set the variable to a different value in another scope
        engineScope.put("javaGlobalVar", "Same variable is initialized for different script.");
        // evaluate the same code(global variable) but in a different script context
        engine.eval(new FileReader("javascript-program-files/script2.js"), newContext);

    }

}

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

}

