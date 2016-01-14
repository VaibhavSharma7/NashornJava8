print("readJs started :: Current TmeStamp ==" + java.lang.System.currentTimeMillis());

java.lang.System.out.println("Hello through System class of java.");


var javaPersonObject = new com.my.pack.User("Vaibhav Sharma");
print("Invoking getter of User class  " + javaPersonObject.getName());


var fun1 = function (name) {
    print('Hi there from Javascript, ' + name);
    return "greetings from javascript";
};

var fun2 = function (object) {
    print("JS Class Definition: " + Object.prototype.toString.call(object));
};

function run() {
    print("Executing Top level run() in js");
}


var MyJavaClass = Java.type('com.my.pack.NashornDemo');

//Calling static method of a class :
var result = MyJavaClass.staticFunction('Vaibhav Sharma');
print(result + " but printed through js file");

/*In most cases, calling Java APIs from Oracle Nashorn is straightforward, with the resulting code being Java written in JavaScript.
 */
var object = new MyJavaClass;
var res = object.nonStaticFunction('Vaibhav');
print(res);

print("Js file is accessing java global variable :- " + javaGlobalVar);

var user = {
    getFullName: function (firstName, lastName) {
        print("Executing getFullName() of user in js");
        return firstName + " : " + lastName;
    },
    run: function () {
        print("Executing run() of user in js");
    }
};

var javaStringClass = Java.type('java.lang.String');
var joinedString = javaStringClass.join("-", "Hello", " World", "me");
print("joinedString >> " + joinedString);

print("**** Script End's ****");


;