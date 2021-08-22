package il.ac.hit.functionalprogramming.haim.cw7.studentslist

/**
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
class Student(val firstName: String, val lastName: String, val age: Double, val average: Option[Double]) {

  def getAverage: Double = {
    average match {
      case Some(avg) => avg
      case None => 0d
    }
  }

  override def toString = s"Student(firstName=$firstName, lastName=$lastName, age=$age, average=${if (average.isDefined) average.get else "No Grades Yet"})"
}
