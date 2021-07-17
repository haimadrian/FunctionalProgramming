package il.ac.hit.functionalprogramming.haim.cw2

object LineUtils {
  def isParallel(line1: Line, line2: Line): Boolean = line1.slope() == line2.slope()
}
