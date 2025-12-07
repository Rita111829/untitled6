object Main extends App {
  println("=== Электронная таблица ===")

  val table = new Table(3, 3)

  println("\n1. Пустая таблица:")
  for (y <- 0 until 3) {
    for (x <- 0 until 3) {
      print(table.getCell(x, y).get.toString + "\t")
    }
    println()
  }

  println("\n2. Добавляем числа и текст:")
  table.setCell(0, 0, new NumberCell(42))
  table.setCell(1, 1, new StringCell("Hello"))
  table.setCell(2, 2, new NumberCell(100))

  for (y <- 0 until 3) {
    for (x <- 0 until 3) {
      print(table.getCell(x, y).get.toString + "\t")
    }
    println()
  }

  println("\n3. Добавляем ссылки:")
  table.setCell(0, 2, new ReferenceCell(0, 0, table)) // ссылается на 42
  table.setCell(1, 2, new ReferenceCell(1, 1, table)) // ссылается на Hello

  println("Ячейка (0,2): " + table.getCell(0, 2).get.toString) // должно быть 42
  println("Ячейка (1,2): " + table.getCell(1, 2).get.toString) // должно быть Hello
}