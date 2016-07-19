sealed trait Sayable extends Function1[Option[String], Sayable]

case object SayableTerminate extends Sayable {
  def apply(optStr : Option[String]) : Sayable = SayableTerminate
}

case class SayableAccumulate(f: Option[String] => Sayable) extends Sayable {
  def apply(optStr : Option[String]): Sayable = f(optStr)
}

def sayIt
  : Option[String] => Sayable
  = optStr         => {

    var acc = ""

    def helper
      : Option[String] => Sayable
      = {
          case None => {
            println(acc)
            SayableTerminate
          }
          case Some(str) => {
            acc = acc + " " + str
            SayableAccumulate(helper)
          }
      }

    helper(optStr)
  }

sayIt(Some("Hi"))(Some("There"))(Some("World"))(None)
