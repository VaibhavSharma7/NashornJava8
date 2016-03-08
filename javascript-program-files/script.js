print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Evaluation of script.js startss ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

//We can use java class in Javascript via appending full package name :
//java.lang.System.out.println("Current timestamp via java.lang.System :: " + java.lang.System.currentTimeMillis());

//Initializing java object in javascript :
var javaPersonObject = new com.my.pack.User("Vaibhav");
print("Type of javaPersonObject :: " + javaPersonObject.getClass());
javaPersonObject.setLastName("Sharma");
print("Invoking getter of User class :: " + javaPersonObject.getName());


// Top level function of javascript :
var validateAge = function (name, age) {
    var responseMap;
    print('Validate Age function started with args :: ' + name + ' & age = ' + age);

    //Print params class through javascript api :
    print("Class :: ", Object.prototype.toString.call(name));
    print("Class :: ", Object.prototype.toString.call(age));

    //Print params class through java :
    print("Class :: ", name.getClass());
    print("Class :: ", age.getClass());

    if (age >= 18)
        return {
            name: 'Vaibhav Sharma', floatingVal: 1.7, age: age, message: name + " of " + age + " is eligible "
        };
    else
        return {
            name: 'Vaibhav Sharma',
            floatingVal: 1.7,
            age: age,
            message: name + " of " + age + " is  not eligible "
        };
};

function run(){
    print("Run function for multi threading is called ...")
}

var MyJavaClass = Java.type('com.my.pack.NashornDemo');

//Calling static method of a class :
var result = MyJavaClass.staticFunction('Vaibhav Sharma');
print(result + " but printed through js file");

/*In most cases, calling Java APIs from Oracle Nashorn is straightforward, with the resulting code being Java written in JavaScript.
 */
var object = new MyJavaClass;
var res = object.nonStaticFunction('Vaibhav');
print("Output of User.java in script :: ",res);

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