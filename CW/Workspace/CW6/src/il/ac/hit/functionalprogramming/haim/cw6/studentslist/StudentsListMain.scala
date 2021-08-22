package il.ac.hit.functionalprogramming.haim.cw6.studentslist

/**
 * 1. Develop a stand alone application that creates a list of students, sort them in accordance with their average and print out their details.
 * You should define the class Student with the following properties (at the minimum): average, name and id.
 *
 * 2. Develop a simple application that creates 5 objects that represent your friends in class. The references to these 5
 * objects should be stored in a List object. Use the reduceLeft method for finding the best student.
 *
 * 3. Develop a simple application that creates 5 objects that represent your friends in class. The references to these 5
 * objects should be stored in a List object. Use the foldLeft method for finding the friends' average.
 *
 * @author Haim Adrian
 * @since 15 Aug 2021
 */
object StudentsListMain {
  def main(args: Array[String]): Unit = {
    val students = List(new Student("111111111", "Orel", 31, 98),
      new Student("222222222", "Haim", 30, 90),
      new Student("333333333", "Or", 26, 88),
      new Student("555555555", "Lior", 26, 92),
      new Student("444444444", "Eden", 26, 85.5))

    // 1. Sort
    println(s"Students sorted by average:${System.lineSeparator()}${students.sortBy(_.average).mkString(System.lineSeparator())}")

    // 2. Best student
    println()
    val reducerMaxAverage = (stud1: Student, stud2: Student) => if (stud1.average > stud2.average) stud1 else stud2
    println(s"Best student: ${students.reduceLeft(reducerMaxAverage)}")

    // 3. Overall average - use foldLeft to calculate the sum and divide it by amount of students.
    println()
    println(s"Overall Average: ${students.map(_.average).foldLeft(0.0)((avg1, avg2) => avg1 + avg2) / students.length}")
  }
}
