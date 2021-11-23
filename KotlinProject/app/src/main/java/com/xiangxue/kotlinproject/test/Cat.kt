package com.xiangxue.kotlinproject.test

//1. 构造函数参数前面增加var关键字，不需要写成员变量
class Cat(name: String, var age: Int) : Animal(name), IEatInterface {

    override fun printName() {
        println("name:$name ---> age:$age")
    }

    override fun eat() {
        println("$name --- 开始吃东西咯")
    }
}

//2. 如果子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类
class Dog : Animal, IEatInterface {
    var age: Int = 0
    var score: Int = 0

    constructor(name: String, age: Int, score: Int) : super(name) {
        this.age = age
        this.score = score
    }

    override fun eat() {
        println("$name 开始吃东西咯")
    }

    override fun sleep() {
        println("$name 开始休息了")
    }
}

//3. 创建一个只包含数据的类，关键字为 data：
data class User(val name: String, val age: Int)

//4. 泛型
class Box<T>(width: T) {
    var width: T = width
}


fun main() {
    var cat = Cat("猫", 20);
    println("cat ${cat.name} ${cat.age}")
    cat.printName()
    cat.eat()
    cat.sleep()

    var dog = Dog("狗", 19, 100);
    println("cat ${dog.name} ${dog.age} ${dog.score}")
    dog.eat()
    dog.sleep()

    var user = User("tom", 30)
    println("user.name: ${user.name} user.age: ${user.age}")
    var user2 = user.copy(age = 20)
    println("user2.name: ${user2.name} user2.age: ${user2.age}")

    var box: Box<Int> = Box(10)
    println("box width:" + box.width)
}