
print("********************** Evaluation of script2.js starts **********************");

print("Js file is accessing java global variable :- " + javaGlobalVar);

//using js method in java file :-
var str=javaGlobalVar;
var split=str.split(" ");

split.forEach(function(data){
    print(data)
});


