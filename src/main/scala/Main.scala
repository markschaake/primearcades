package schaake.primarcades

import scala.collection.mutable

object Main extends App {
  Resource.borrowSource("/games.txt") { lines =>
    var col1Num = 1
    var col2Num = 1480

    val LAST_COL_2 = 2957

    val col1 = mutable.ListBuffer.empty[String]
    val col2 = mutable.ListBuffer.empty[String]

    lines
      .getLines()
      .foreach(l => {
        if (col2Num <= LAST_COL_2) {
          l.drop(col1Num.toString.length + 1).split(col2Num.toString) match {
            case Array(col1Value, col2Value) =>
              col1 += col1Value.trim()
              col2 += col2Value.trim()
            case o => println(s"Got other split: ${o.toList}")
          }
        } else {
          col1 += l.drop(col1Num.toString.length + 1)
        }
        col1Num += 1
        col2Num += 1
      })

    val allLines = col1 ++ col2
    println(allLines.mkString("\"", "\",\n\"", "\""))
    println(s"There are ${allLines.size} games")
  }
}
