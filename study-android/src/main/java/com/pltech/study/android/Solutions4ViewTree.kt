package com.pltech.study.android

import android.view.View
import android.view.ViewGroup
import java.util.*

class Solutions4ViewTree {
}

/**
 * A -> B -> finish
 * A: onCreate -> onStart -> onResume
 * B: A.onPause -> B.onCreate -> B.onStart -> B.onResume -> A.onStop
 * finish B: B.onPause -> A.onResume -> B.onStop -> B.onDestroy
 */
/**
 * 递归遍历View树
 */
fun recursivelyPrintView(root: View) {
    println(root)
    if (root is ViewGroup) {
        for (childIndex in 0 until root.childCount) {
            val child = root.getChildAt(childIndex)
            recursivelyPrintView(child)
        }
    }
}

/**
 * 广度优先遍历View树
 */
fun printInBreadthFirst(root: View) {
    val viewDeque = LinkedList<View>()
    var view = root
    viewDeque.push(view)
    while (!viewDeque.isEmpty()) {
        view = viewDeque.poll()
        println(view)
        if (view is ViewGroup) {
            for (index in 0 until view.childCount) {
                val child = view.getChildAt(index)
                viewDeque.addLast(child)
            }
        }
    }
}

/**
 * 深度优先遍历View树
 */
fun printInDepthFirst(root: View) {
    val viewDeque = LinkedList<View>()
    var view = root
    viewDeque.push(view)
    while (!viewDeque.isEmpty()) {
        view = viewDeque.pop()
        println(view)
        if (view is ViewGroup) {
            for (index in 0 until view.childCount) {
                val child = view.getChildAt(index)
                viewDeque.push(child)
            }
        }
    }
}

/**
 * 获取Activity中View树的最大深度
 */
fun getViewTreeDeep(view: View, currDeep: Int): Int {
    if (view is ViewGroup) {
        val childCount = view.childCount
        if (childCount > 0) {
            var max = currDeep + 1 //有子View，则深度+1
            for (index in 0 until childCount) {
                val child = view.getChildAt(index)
                val deep = getViewTreeDeep(child, currDeep + 1)
                if (deep > max) {
                    max = deep
                }
            }
            return max
        }
    }
    return currDeep
}