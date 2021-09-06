package il.ac.hit.functionalprogramming.haim.cw7.ctorparam

import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Haim Adrian
 * @since 5 Sep 2021
 */
object CounterMain {
  def main(args: Array[String]): Unit = {
    val counter = new Counter(new AtomicInteger())

    println(counter.count)
    println(counter.count)
  }
}
