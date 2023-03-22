package com.mvi.demo.data.repository

import okhttp3.internal.notify
import okhttp3.internal.wait

/**
 * **************************************
 * 描 述：
 *
 * 作 者： Pudding
 *
 * 日 期： 2023/3/1
 * ***************************************
 */
class MyThread() : Thread() {

  val lock = Any()

  @Volatile
  var pause = true

  override fun run() {

    synchronized(lock) {
      println("thread start")
      while (pause) {
        lock.wait()
      }
      println("thread finished")
    }
  }
}

fun main() {
  val t = MyThread()
  t.start()
  Thread.sleep(2000)
  synchronized(t.lock) {
    println("NOTIFY")
    t.pause = false
    t.lock.notify()
  }
  Thread.sleep(2000)
}
