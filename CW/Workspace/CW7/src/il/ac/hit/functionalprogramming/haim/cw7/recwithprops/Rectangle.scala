package il.ac.hit.functionalprogramming.haim.cw7.recwithprops

/**
 * Define the Rectangle class with two properties: width and height.
 * These two properties should enable assigning width and height with positive values only.
 * @author Haim Adrian
 * @since 22 Aug 2021
 */
class Rectangle (val x: Int, val y: Int, private[this] var _width: Int, private[this] var _height: Int) {
  width = _width
  height = _height

  def this(x: Int, y: Int) = this(x, y, 0, 0)

  def width: Int = _width

  def width_=(width: Int): Unit = {
    require(width >= 0, "Width must be positive")
    this._width = width
  }

  def height: Int = _height

  def height_=(height: Int): Unit = {
    require(height >= 0, "Height must be positive")
    this._height = height
  }

  override def toString: String = s"{x=$x, y=$y, width=$width, height=$height}"
}
