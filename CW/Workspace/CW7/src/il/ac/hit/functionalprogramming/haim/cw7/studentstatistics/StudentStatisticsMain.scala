package il.ac.hit.functionalprogramming.haim.cw7.studentstatistics

/**
 * Develop a simple application that creates a list of students. Each object in that specific list represents
 * a specific student in our class. Each student object has various properties. One of them is average.
 * You should define the Student class accordingly. Define a separated function that uses the foldLeft function
 * for getting a tuple that holds three values. The number of students, the average of all students and the reference
 * for Student object that represents the best student.
 *
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
object StudentStatisticsMain {
  def main(args: Array[String]): Unit = {
    val students = List(new Student("111111111", "Orel", 31, 98),
      new Student("222222222", "Haim", 30, 90),
      new Student("333333333", "Or", 26, 88),
      new Student("555555555", "Lior", 26, 92),
      new Student("444444444", "Eden", 26, 85.5))

    val statistics = calcStatistics(students)
    println(
s"""Students Statistics1
--------------------
Count: ${statistics._1}
Overall Average: ${statistics._2}
Best Student: ${statistics._3}""")

    println()
    val statistics2 = calcStatistics2(students)
    println(
      s"""Students Statistics2
--------------------
Count: ${statistics2._1}
Overall Average: ${statistics2._2}
Best Student: ${statistics2._3}""")
  }

  def calcStatistics(students: List[Student]): (Int, Double, Student) = {
    val statsWithSum = students.foldLeft((0, 0d, students.head))((tuple, student) => (tuple._1 + 1, tuple._2 + student.average, if (tuple._3.average > student.average) tuple._3 else student))
    (statsWithSum._1, statsWithSum._2 / students.length, statsWithSum._3)
  }

  def calcStatistics2(students: List[Student]): (Int, Double, Student) = {
    students.foldLeft((0, 0d, students.head))((tuple, student) => (tuple._1 + 1, tuple._2 + (student.average / students.length), if (tuple._3.average > student.average) tuple._3 else student))
  }
}
