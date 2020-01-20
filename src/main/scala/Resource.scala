package schaake.primarcades

import scala.io.{BufferedSource, Source}

object Resource {
  import java.io.InputStream
  import scala.language.reflectiveCalls
  // Structural type that requires the above import
  type Closeable = { def close(): Unit }

  def using[A <: Closeable, B](resource: A)(f: A => B): B = {
    require(resource != null, "Supplied resource is null")
    try {
      f(resource)
    } finally resource.close()
  }

  def borrow[A](resourceName: String)(f: InputStream => A): A =
    using(getClass.getResourceAsStream(resourceName))(f)

  def borrowSource[A](resourceName: String)(f: BufferedSource => A): A = borrow(resourceName) { is =>
    f(Source.fromInputStream(is))
  }

  def borrowAsString[A](resourceName: String)(f: String => A) = borrowSource(resourceName) { source =>
    f(source.getLines.mkString("\n"))
  }
}
