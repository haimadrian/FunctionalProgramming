package il.ac.hit.functionalprogramming.haim.cw7.ctorparam

import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Haim Adrian
 * @since 5 Sep 2021
 */
class Counter (counter: AtomicInteger) {

  def count: Int = {
    println(s"Counter: $counter")
    counter.incrementAndGet()
  }
}
