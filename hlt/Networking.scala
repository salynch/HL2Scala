package hlt

import java.io.{FileWriter, IOException}

import hlt.Move.{Dock, Noop, Thrust, Undock}

import scala.io.StdIn

object Networking {
  private val UNDOCK_KEY = 'u'
  private val DOCK_KEY = 'd'
  private val THRUST_KEY = 't'

  def sendMoves(moves: Iterable[Move]): Unit = {
    val moveString = new StringBuilder
    for (move <- moves) {
      move.getType match {
        case Undock =>
          moveString.append(UNDOCK_KEY).append(" ").append(move.getShip.getId).append(" ")
        case Dock =>
          moveString.append(DOCK_KEY).append(" ").append(move.getShip.getId).append(" ").append(move.asInstanceOf[DockMove].getDestinationId).append(" ")
        case Thrust =>
          moveString.append(THRUST_KEY).append(" ").append(move.getShip.getId).append(" ").append(move.asInstanceOf[ThrustMove].getThrust).append(" ").append(move.asInstanceOf[ThrustMove].getAngle).append(" ")
        case Noop =>
      }
    }
    println(moveString)
  }

  def readAndSplitLine = readLine.trim.split(" ").iterator

  private def readLine: String = {
    StdIn.readLine()
  }
}

class Networking {
  def initialize(botName: String): (Short, Short, Short) = {
    val myId = Networking.readLine.toShort
    try DebugLog.initialize(new FileWriter(s"$myId - $botName.log"))
    catch {
      case e: IOException =>
        e.printStackTrace()
    }
    val inputStringMapSize = Networking.readAndSplitLine
    val width = inputStringMapSize.next.toShort
    val height = inputStringMapSize.next.toShort
    // Associate bot name
    println(botName)
    (width, height, myId)
  }
}