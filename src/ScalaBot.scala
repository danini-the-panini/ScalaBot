import afk.bot.london.SmallTank
import java.util.Random
import com.hackoeur.jglm.support.FastMath._

class ScalaBot extends SmallTank {
  var movement: Int = 0
  var rotation: Int = 0
  var action: () => Unit = move
  var direction: () => Unit = turnClockwise
  var rand: Random = new Random
  
  override def run() {
    if (events.hitWall) {
      movement = 200
      rotation = 180
      action = turn
    }
    
    var vis = events.getVisibleBots;
    
    if (vis.length > 0) {
      var diff = abs(vis(0))

      if (diff < 1) {
        attack();
      } else {
        if (vis(0) < 0) {
          turnAntiClockwise();
        }
        else {
          turnClockwise();
        }
      }
    } else action()
  }
  
  def turn() {
    if (rotation > 0) {
      direction()
      rotation = rotation-1
    } else {
      rotation = rand nextInt 180
      direction = if (rand.nextBoolean) turnClockwise else turnAntiClockwise
      action = move;
    }
  }

  def move() {
    if (movement > 0) {
      moveForward()
      movement = movement-1
    } else {
      movement = rand nextInt 800
      action = turn
    }
  }
}
