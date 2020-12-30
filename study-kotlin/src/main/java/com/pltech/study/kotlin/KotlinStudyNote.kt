package com.pltech.study.kotlin

/**
 *
 * Created by Pang Li on 2020/10/10
 */
//成员方法优先级高于扩展函数
class Son {
    fun foo() {
        println("foo in Class Son")
    }
}

open class Parent {
    open fun foo() {
        println("foo in Class Parent")
    }

    fun Son.foo2() {
        this.foo()
        this@Parent.foo()
        val nickname = "Alexy Pang"
        kotlin.run {
            val nickname = "Ali Pang"
            println(nickname)
        }
        println(nickname)
    }
}

object Test : Parent() {
    override fun foo() {
        println("foo in Class Test")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        fun Parent.foo3() {
            this.foo()
            this@Test.foo()
        }
        Son().foo2()
        Parent().foo3()
    }
}