package bootstrap.liftweb

object BootTest {
  var booted = false

  def boot {
    if (!booted) {
      new Boot().boot
      booted = true
    }
  }
}