package il.ac.hit.functionalprogramming.haim.cw6.lottonumbers

import scala.util.Random

/**
 * Develop a simple application that generates 6 randomly generated numbers within the range 1 and 42 (included).
 * The numbers cannot repeat themselves. The application should print the generated numbers to the screen.
 *
 * @author Haim Adrian
 * @since 15 Aug 2021
 */
object LottoNumbersMain {
  def main(args: Array[String]): Unit = {
    val maxNum = 42
    val amountOfNumbers = 6
    val numbersSeed = 1 to maxNum

    var lottoNumbers: Set[Int] = Set()
    val rand = new Random()

    while (lottoNumbers.size < amountOfNumbers) {
      lottoNumbers += numbersSeed(rand.nextInt(maxNum));
    }

    println(s"Lotto numbers: $lottoNumbers")

    println()
    println(s"Lotto numbers, 1 liner: ${rand.shuffle((1 to maxNum).toList).slice(0, amountOfNumbers)}")
  }
}
