package il.ac.hit.functionalprogramming.haim.cw5.typedpattern.student

/**
 * @author Haim Adrian
 * @since 8 Aug 2021
 */
object StudentDegreePattern {
  def main(args: Array[String]): Unit = {
    val dave = Student("Dave", 25, 88, "BSc")
    val roze = Student("Roze", 32, 82, "MSc")
    val jane = Student("Jane", 24, 74, "Phd")

    for (person <- List(dave, roze, jane)) {
      person match {
        case Student(_, _, _, "BSc") => println(s"Hello ${person.name}! This is your first degree!")
        case Student(_, _, _, "MSc") => println(s"Hello ${person.name}! You are almost in your Phd studies!")
        case Student(_, _, _, _) => println(s"Hello student ${person.name}!")
        case _ => println(s"Hello ${person.name}!")
      }
    }
  }
}
