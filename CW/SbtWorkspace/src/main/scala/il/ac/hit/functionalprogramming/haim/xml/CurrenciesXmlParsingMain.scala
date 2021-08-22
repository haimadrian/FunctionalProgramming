package il.ac.hit.functionalprogramming.haim.xml

import java.net.URL
import scala.xml._

/**
 * Develop a simple application that prints out the currency exchange rates.
 * You can fetch an xml describing the rates from Bank Israel website: https://www.boi.org.il/currency.xml
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
object CurrenciesXmlParsingMain {
  private val columnWidth = 20
  private val padChar = " "

  def main(args: Array[String]): Unit = {
    val currencyDoc = XML.load(new URL("https://www.boi.org.il/currency.xml"))

    println(s"Last update: ${(currencyDoc \ "LAST_UPDATE").text}")
    println()

    val captions = List("Currency", "Unit", "Country", "Rate (NIS)", "Daily Change")
    val sb = new StringBuilder

    for (caption <- captions) {
      sb.append(centerText(caption))
    }

    sb.append(System.lineSeparator())

    // String overloads '*' as repeat
    sb.append("-" * (columnWidth * captions.length))

    // When parsing XML, we use '\' in order to get to a child element, and '\\' in order to get to children and grandchildren (recursively)
    // There is also an option to get attribute, using '@', e.g. DOC \ "@attributeName"

    for (currency <- currencyDoc \ "CURRENCY") {
      sb.append(System.lineSeparator())

      for (caption <- captions) {
        caption match {
          case "Currency" => sb.append(centerText((currency \ "NAME").text + " (" + (currency \ "CURRENCYCODE").text + ")"))
          case "Rate (NIS)" => sb.append(centerText((currency \ "RATE").text))
          case "Daily Change" => sb.append(centerText((currency \ "CHANGE").text + "%"))
          case other: String => sb.append(centerText((currency \ other.toUpperCase).text))
        }
      }
    }

    println(sb)
  }

  def centerText(text: String): String = {
    val left = (columnWidth - text.length) / 2
    val right = columnWidth - left - text.length
    (padChar * left) + text + (padChar * right)
  }
}
