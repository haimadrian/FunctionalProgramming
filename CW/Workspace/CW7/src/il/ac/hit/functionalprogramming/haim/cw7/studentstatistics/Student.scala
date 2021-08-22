package il.ac.hit.functionalprogramming.haim.cw7.studentstatistics

/**
 * @author Haim Adrian
 * @since 15 Aug 2021
 */
class Student(val id: String, val name: String, val age: Double, val average: Double) {

  override def toString = s"Student(id=$id, name=$name, age=$age, average=$average)"
}
