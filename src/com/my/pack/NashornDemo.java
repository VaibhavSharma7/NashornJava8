package com.my.pack;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

public class NashornDemo {

    public static void main(String arr[]) {
        String str = "This is a java global variable accessible in js file";
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        try {
            NashornDemo nashornDemo = new NashornDemo();
            //Initializing global variable.
            engine.put("javaGlobalVar", str);

            System.out.println("*****************************Nashorn Demo Starts*****************************");
            engine = NashornDemo.evaluateScriptFile(engine);

            nashornDemo.invokeScriptTopLevelFunctions(engine);
            Object object = nashornDemo.invokeScriptObjectFunctions(engine);
            nashornDemo.invokeMultiThreads(engine, object);
            nashornDemo.modifyingScriptContext(engine);
            nashornDemo.dateJsMethod(engine);
            System.out.println("*****************************Nashorn Demo Ends*****************************");
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


    /**
     * This method uses external javascript file :
     *
     * @param engine The script to be executed by the script engine.
     * @return void
     * @throws Exception
     */
    public void dateJsMethod(ScriptEngine engine) throws Exception {
        engine.eval(new FileReader("javascript-program-files/date.js"));
        Invocable invocable = (Invocable) engine;
        Object dateJs = engine.eval("Date");
        Object data = invocable.invokeMethod(dateJs, "parse", "today");
        System.out.println(" >>>  " + data);
    }


    /**
     * This method evaluates a Script File
     *
     * @param scriptEngine The script to be executed by the script engine.
     * @return scriptEngine
     * @throws Exception
     */
    public static ScriptEngine evaluateScriptFile(ScriptEngine scriptEngine) throws Exception {
//        engine.eval("print('Hello World! directly from main() script code.')"); //Directly executing js code.
//        scriptEngine.eval("load('https://cdnjs.cloudflare.com/ajax/libs/react/0.12.2/react.js')");
        scriptEngine.eval(new FileReader("javascript-program-files/script.js"));

        return scriptEngine;
    }

    /*
    * This method invokes script top level functions :
    */
    public void invokeScriptTopLevelFunctions(ScriptEngine scriptEngine) throws Exception {
        Invocable invocable = (Invocable) scriptEngine;

        /*
        * invokeFunction() returns a ScriptObjectMirror instance which implements a following interfaces :- Map,Bindings,JSObject
        */
        ScriptObjectMirror result =(ScriptObjectMirror)invocable.invokeFunction("validateAge", "Vaibhav",17);
        System.out.println("Result Class :: "+result.getClass());

        Map map=(Map)result;
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            System.out.println("Key = " + entry.getKey() + " , Value = " + entry.getValue()+" , Value's Class = "+entry.getValue().getClass());
        }
    }

    /*
    * This method invokes script object's method :
    */
    public Object invokeScriptObjectFunctions(ScriptEngine scriptEngine) throws Exception {
        Invocable invocable = (Invocable) scriptEngine;
        Object object = scriptEngine.get("user");
        Object returnedString = invocable.invokeMethod(object, "getFullName", "Shit", "Fun");
        System.out.println("Response from js file  :: " + returnedString);
        return object;
    }

    /*
     * This method invokes script top level functions :
     */
    public void invokeMultiThreads(ScriptEngine scriptEngine, Object object) throws Exception {
        Invocable invocable = (Invocable) scriptEngine;

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


