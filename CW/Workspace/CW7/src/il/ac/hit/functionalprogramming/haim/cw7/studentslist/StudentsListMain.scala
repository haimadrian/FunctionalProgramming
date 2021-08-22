package il.ac.hit.functionalprogramming.haim.cw7.studentslist

/**
 * Develop a simple application that creates a list of students.
 * Each Student object should have the following properties (at the minimum): firstName, lastName and average.
 * Your code should iterate the list and print out the details of each student. The type of average should be
 * Option[Double].
 * Students that have just started their studies still don't have an average.
 *
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
object StudentsListMain {
  def main(args: Array[String]): Unit = {
    val students = List(new Student("111111111", "Orel", 31, Some(98)),
      new Student("222222222", "Haim", 30, Some(90)),
      new Student("333333333", "Or", 26, Some(88)),
      new Student("555555555", "Lior", 26, Some(92)),
      new Student("444444444", "Eden", 26, Some(85.5)),
      new Student("444444444", "Eden", 26, None))

    students.foreach(println)
  }
}
