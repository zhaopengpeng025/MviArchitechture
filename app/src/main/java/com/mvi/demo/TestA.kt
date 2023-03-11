package com.mvi.demo

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/3/10
 * ***************************************
 */
class Apple {}

fun main() {

  val flag = AtomicBoolean(false)

  val lock = Any()

  thread {
    Thread.sleep(20)
    synchronized(lock) {
      Thread.sleep(1000)
      if (flag.compareAndSet(false, true)) {
        println("init success")
      }
    }
  }

  thread {
    synchronized(lock) {
      Thread.sleep(1500)
      if (flag.get()) {
        println("init finished")
      } else {
        println("init now")
      }
    }
  }
  Thread.sleep(5000)
}
