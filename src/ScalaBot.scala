import afk.bot.london.SmallTank
import java.util.Random
import com.hackoeur.jglm.support.FastMath._

class ScalaBot() extends SmallTank {
  var counter: Int = 0
  val rand: Random = new Random
  var action: () => Unit = if (rand.nextBoolean) move else turn
  var direction: () => Unit = if (rand.nextBoolean) turnClockwise else turnAntiClockwise
  var backwards: Boolean = false
  
  override def run() {
    if (events.hitWall) {
      counter = 5
      backwards = !backwards
      action = move
    }
    
    var vis = events.getVisibleBots;
    
    if (vis.length > 0) {
      var diff = abs(vis(0))

      if (diff < 1) {
        attack()
      } else {
        if (vis(0) < 0) {
          turnAntiClockwise()
        }
        else {
          turnClockwise()
        }
      }
    } else {
      action()
      counter = counter - 1
    }
  }

  def move() {
    if (counter > 0) {
      if (backwards) moveBackwards() else moveForward()
    } else {
      counter = rand nextInt 180
      direction = if (rand.nextBoolean) turnClockwise else turnAntiClockwise
      action = turn
    }
  }

  def turn() {
    if (counter > 0) {
      direction()
    } else {
      counter = rand nextInt 500
      backwards = false;
      action = move
    }
  }

}
