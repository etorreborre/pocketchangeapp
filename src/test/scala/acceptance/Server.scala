package acceptance

import org.mortbay.jetty.{ Server => JettyServer }
import org.mortbay.jetty.webapp.WebAppContext

object Server {
  val PORT = 8090
  private lazy val server  = new JettyServer(PORT)

  def start = {
    val context = new WebAppContext()
    context.setServer(server)
    context.setContextPath("/")
    context.setWar("src/main/webapp")
    server.addHandler(context)
    server.start()
  }

  def stop = server.stop
}