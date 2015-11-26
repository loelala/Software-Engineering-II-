# Coding Standard 
__by wedoIT__

## Table of Content 
1. [Java coding style](#java-coding-style)
1. [Angular coding style](#angularjs-coding-style)


## Java coding style 

__Naming conventions__

1. Always use describing names 
```java
public void catName 
public void catColor 
```
2. Packages always to lower case 
```java
de.wedoit.business.cat 
```
3. Classes / Interfaces / Enums in CamelCase
```java
/** Class */
public class CatDao 
/** Interface */ 
public interface AnimalLogic 
/** Enum **/ 
public enum AnimalColors
```
4. Fields / Variables in camelCase
```java
public String animalColor 
public int ageOfAnimal
```

*** 
__Comments__

1. Every class needs a JavaDoc-documentation 
```java
/** 
* The data access object for a cat
*/
public class CatDao  {
 // something 
}
``` 
2. Public mehtods needs a  JavaDoc-documentation 
```java
/** 
* Returns all cats with the same color as the given color. 
* @param color 
*/
public Car getAllCatsByColor(String color)  {
 // do something 
}
``` 
3. Complex algorithm must be commented. So that is clear what they are doing and how they do it. 

***

__Error and Exception handling__

1. Error handling should be done on methods with input parameters <br>
_illegal input like null or someting throw IllegalArgument Exception_
```java
public Cat getAllCatsByColor(String color) {
  if (color == null) {
    throw new IllegalArgumentException("can not find a cat with color null!");
  }
  // do something 
}
```

***

__Rules for working with code repositroy__

* Every team member works on his own branch
 <br> Naming:   [first letter name][fist letter surname]-wedoit  _(example:   hm-wedoit   for Hans Meier)_ 
* There are no feature branches 
* Merging to master only on __Thursday before class__, because there could be conflicts! 

***

__Rules for Unit Tests__ 

> NOT YET IMPLEMENTED 

***

__Rules for Logging__

* Use Logger-Instance 
* Log every method call (with parameter) at the beginning of ervery mehtod at log-level DEBUG 

## Angularjs coding style 
We use the coding style by john papa. Here we just copied some examples out of the regular style guide! 
For more specific details please see:   [John Papa - Angular Stlye Guide](https://github.com/johnpapa/angular-styleguide)

### Single Responsibility 
__Rule of 1__

```javascript
  /* avoid */
  angular
      .module('app', ['ngRoute'])
      .controller('SomeController', SomeController)
      .factory('someFactory', someFactory);

  function SomeController() { }

  function someFactory() { }
  ```
Better define only 1 component per file: 

  ```javascript
  /* recommended */

  // app.module.js
  angular
      .module('app', ['ngRoute']);
  ```

  ```javascript
  /* recommended */

  // some.controller.js
  angular
      .module('app')
      .controller('SomeController', SomeController);

  function SomeController() { }
  ```

  ```javascript
  /* recommended */

  // someFactory.js
  angular
      .module('app')
      .factory('someFactory', someFactory);

  function someFactory() { }
  ```
  *** 
  
### Controller
#### controllerAs Controller Syntax 

  * Use the `controllerAs` syntax over the `classic controller with $scope` syntax. 
  * The `controllerAs` syntax uses `this` inside the controllers which gets bound to `$scope`
   
  *Why?*: `controllerAs` is syntactic sugar over `$scope`. You can still bind to the View and still access `$scope` methods.

  *Why?*: Helps avoid the temptation of using `$scope` methods inside a controller when it may otherwise be better to avoid them or move the method to a factory, and reference them from the controller. Consider using `$scope` in a controller only when needed. For example when publishing and subscribing events using [`$emit`](https://docs.angularjs.org/api/ng/type/$rootScope.Scope#$emit), [`$broadcast`](https://docs.angularjs.org/api/ng/type/$rootScope.Scope#$broadcast), or [`$on`](https://docs.angularjs.org/api/ng/type/$rootScope.Scope#$on).

  ```javascript
  /* avoid */
  function CustomerController($scope) {
      $scope.name = {};
      $scope.sendMessage = function() { };
  }
  ```

  ```javascript
  /* recommended - but see next section */
  function CustomerController() {
      this.name = {};
      this.sendMessage = function() { };
  }
  ```
  
#### controllerAs with vm 
- Use a capture variable for `this` when using the `controllerAs` syntax. Choose a consistent variable name such as `vm`, which stands for ViewModel.

  *Why?*: The `this` keyword is contextual and when used within a function inside a controller may change its context. Capturing the context of `this` avoids encountering this problem.

  ```javascript
  /* avoid */
  function CustomerController() {
      this.name = {};
      this.sendMessage = function() { };
  }
  ```

  ```javascript
  /* recommended */
  function CustomerController() {
      var vm = this;
      vm.name = {};
      vm.sendMessage = function() { };
  }
  ```

  Note: You can avoid any [jshint](http://www.jshint.com/) warnings by placing the comment above the line of code. However it is not needed when the function is named using UpperCasing, as this convention means it is a constructor function, which is what a controller is in Angular.

  ```javascript
  /* jshint validthis: true */
  var vm = this;
  ```

  Note: When creating watches in a controller using `controller as`, you can watch the `vm.*` member using the following syntax. (Create watches with caution as they add more load to the digest cycle.)

  ```html
  <input ng-model="vm.title"/>
  ```

  ```javascript
  function SomeController($scope, $log) {
      var vm = this;
      vm.title = 'Some Title';

      $scope.$watch('vm.title', function(current, original) {
          $log.info('vm.title was %s', original);
          $log.info('vm.title is now %s', current);
      });
  }
  ```

  Note: When working with larger codebases, using a more descriptive name can help ease cognitive overhead & searchability. Avoid overly verbose names that are cumbersome to type.

  ```html
  <!-- avoid -->
  <input ng-model="customerProductItemVm.text">
  ```

  ```html
  <!-- recommended -->
  <input ng-model="productVm.id">
  ```
  
  *** 
  
  __For more details please visit [John Papa Angular-Styleguide](https://github.com/johnpapa/angular-styleguide)__
  
  *** 
