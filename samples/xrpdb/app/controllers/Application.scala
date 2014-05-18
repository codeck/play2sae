package controllers

import play.api._
import play.api.mvc._

import java.math.BigInteger;
import org.bouncycastle.util.encoders.Hex

import org.codeck.sbtripple.AccountFamily.RootKey

object Application extends Controller {
  val maxseed = new BigInteger(1, Array.fill(16)(0xff.toByte))
  val pagesize = 20
  val psbn = BigInteger.valueOf(pagesize)
  val maxpage = maxseed.divide(psbn)
  
  // def index = Action {
  //   Ok(views.html.leak("Your new application is ready.", Seq.empty))
  // }

  def seedpage(page:String) = Action {
	val currpg = new BigInteger(page)
	val bn = currpg.multiply(psbn)
	val rk = RootKey(bn)
	Ok(views.html.leak("", (0 until pagesize).map(x => {
	  val rk = RootKey(bn.add(BigInteger.valueOf(x)))
	  (rk.toString('s'), rk.toString('r'))
	}))(currpg.toString, maxpage.toString, Some(currpg.subtract(BigInteger.ONE).toString), Some(currpg.add(BigInteger.ONE).toString)))
  }
}
