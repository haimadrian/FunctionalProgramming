package il.ac.hit.functionalprogramming.haim.cw3.scala

import scala.annotation.tailrec
import scala.io.StdIn

/**
 * Develop a recursive function that receives two numbers and returns the sum of the factorials of all numbers in between.
 *
 * @author Haim Adrian
 * @since 25 Jul 2021
 */
object SumFactorials {
  def factorialsInBetween(num1: Int, num2: Int): Int = {
    @tailrec
    def factorial(accumulator: Int, num: Int): Int = {
      if (num <= 1) {
        accumulator
      } else {
        factorial(accumulator * num, num - 1)
      }
    }

    val maxNumber = Math.max(num1, num2)
    val minNumber = Math.min(num1, num2)

    // Keep a reference to last calculated factorial, so avoid of repeating calculation over and over
    // of factorials we have already calculated.
    // For example, 3! + 4! + 5! is: 3! + 4*3! + 5*4*3!
    var lastFactorial = factorial(1, minNumber)
    var sumOfFactorials = lastFactorial

    for (num <- minNumber + 1 to maxNumber) {
      lastFactorial *= num
      sumOfFactorials += lastFactorial
    }

    sumOfFactorials
  }

  def main(args: Array[String]): Unit = {
    println("Please enter two natural numbers:")
    val num1 = readIntSafe(0, 50)
    val num2 = readIntSafe(0, 50)

    println(s"Sum of factorials between $num1 to $num2: ${factorialsInBetween(num1, num2)}")
  }

  def readIntSafe(minValue: Int, maxValue: Int): Int = {
    var choice = minValue - 1

    do {
      val input = StdIn.readLine().trim()
      try choice = input.toInt
      catch {
        case _: Exception => // Do nothing
      }

      if ((choice < minValue) || (choice > maxValue)) {
        println("Wrong input. Range is: [" + minValue + ", " + maxValue + "]. Please try again:")
      }
    } while (choice < minValue)

    choice
  }
}
