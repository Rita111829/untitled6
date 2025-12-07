import scala.collection.mutable

trait Cell {
  def toString: String
}

class EmptyCell extends Cell {
  override def toString: String = "empty"
}

class NumberCell(number: Int) extends Cell {
  override def toString: String = number.toString
}

class StringCell(text: String) extends Cell {
  override def toString: String = text
}

class ReferenceCell(val ix: Int, val iy: Int, table: Table) extends Cell {
  override def toString: String = {
    if (checkCyclic(ix, iy, Set((ix, iy)))) {
      return "cyclic"
    }

    table.getCell(ix, iy) match {
      case Some(cell) => cell.toString
      case None => "outOfRange"
    }
  }

  private def checkCyclic(currentX: Int, currentY: Int, visited: Set[(Int, Int)]): Boolean = {
    table.getCell(currentX, currentY) match {
      case Some(refCell: ReferenceCell) =>
        if (visited.contains((refCell.ix, refCell.iy))) true
        else refCell.checkCyclic(refCell.ix, refCell.iy, visited + ((refCell.ix, refCell.iy)))
      case _ => false
    }
  }
}

class Table(width: Int, height: Int) {
  private val cells: mutable.ArrayBuffer[Cell] =
    mutable.ArrayBuffer.fill(width * height)(new EmptyCell)

  def getCell(ix: Int, iy: Int): Option[Cell] = {
    if (ix < 0 || iy < 0 || ix >= width || iy >= height) {
      None
    } else {
      Some(cells(ix + iy * width))
    }
  }

  def setCell(ix: Int, iy: Int, cell: Cell): Unit = {
    if (ix >= 0 && iy >= 0 && ix < width && iy < height) {
      cells(ix + iy * width) = cell
    }
  }
}