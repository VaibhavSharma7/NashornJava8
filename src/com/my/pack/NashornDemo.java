package com.my.pack;

import javax.script.*;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

public class NashornDemo {

    public static void main(String arr[]) {
        String str = "This is a java global variable accessible in js file";
        //Reference of Nashorn Script Engine :
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        try {

            //Initializing global variable.
            //This variable should be initialized before evaluating this variable in javascript.
            engine.put("javaGlobalVar", str);
            //Evaluating a Statement
            //In this example, the eval() method is called on the script engine instance to execute JavaScript code from a String object.
            engine.eval("print('Hello World! directly from main() script code.')"); //Directly executing js code.

            //Evaluating a Script File
            //In this example, the eval() method takes in a FileReader object that reads JavaScript code from a file named script.js. By wrapping various input stream objects as readers, it is possible to execute scripts from files, URLs, and other resources.
            engine.eval(new FileReader("javascript-program-files/script.js"));


            Invocable invocable = (Invocable) engine;
            Object result = invocable.invokeFunction("fun1", "Vaibhav Sharma");
            System.out.println(result);
            System.out.println(result.getClass());
            invocable.invokeFunction("fun2", new Date());
            invocable.invokeFunction("fun2", LocalDateTime.now());
            invocable.invokeFunction("fun2", new Person("Vaibhav >>> Sharma"));

            //Invoking script's object method
            Object object = engine.get("user");
            Object returnedString = invocable.invokeMethod(object, "getFullName", "xyz", "abc");
            System.out.println("Response from js >> " + returnedString);

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

            // Define a different script context & use same gloabl variable with different value.
            // define a different script context
            ScriptContext newContext = new SimpleScriptContext();
//            newContext.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
            Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
            // set the variable to a different value in another scope
            engineScope.put("javaGlobalVar", "Same variable is initialized for different script.");
            // evaluate the same code(global variable) but in a different script context
            engine.eval(new FileReader("javascript-program-files/script2.js"), newContext);

            new NashornDemo().mustacheMethod(engine);
            new NashornDemo().dateJsMethod(engine);

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

    public void mustacheMethod(ScriptEngine engine) throws Exception {
        /*Let’s now call a real-world JavaScript library from a Java application.
         To do so, let’s use the popular mustache.js template library, which is commonly used to render view fragments in HTML applications.
          Briefly, given a JSON data object {"name":"Bean"} and a template "Hello {{name}}", Mustache renders "Hello Bean".
         The template engine can do more than that, though, because it also supports conditions, collection iteration, and more.

         After getting a scripting engine reference for Oracle Nashorn, we evaluate the mustache.js code.
         We then define the Mustache template as a String. The data model needs to be a JSON object.
          In our case, we first have to define it as a String and call JSON.parse to have it as a JSON object. We can then call Mustache.render.
          Running this program yields the following output, calling mustache.js
         */
        engine.eval(new FileReader("javascript-program-files/mustache.js"));
        Invocable invocable = (Invocable) engine;

        String template = "Email addresses of {{contact.name}}:\n" +
                "{{#contact.emails}}\n" +
                "- {{.}}\n" +
                "{{/contact.emails}}";

        String contactJson = "{" +
                "\"contact\": {" +
                "\"name\": \"Vaibhav Sharma\", \"emails\": [" +
                "\"vaibhav@ttn.com\", \"vaibhav@gmail.com\"" +
                "]}}";


        Object json = engine.eval("JSON");
        Object data =
                invocable.invokeMethod(json, "parse", contactJson);


        Object mustache = engine.eval("Mustache");
        System.out.println(invocable.invokeMethod(
                mustache, "render", template, data));
    }

    public void dateJsMethod(ScriptEngine engine) throws Exception {
        engine.eval(new FileReader("javascript-program-files/date.js"));
        Invocable invocable = (Invocable) engine;

        Object dateJs = engine.eval("Date");
        Object data =
                invocable.invokeMethod(dateJs, "today");


        System.out.println(" >>> VVVVVVVVV " + data);
    }


}

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

}

