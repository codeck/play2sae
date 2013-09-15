package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Logger.debug("Oohh Yes!")
    Ok(views.html.index("Your new application is ready. from play2.2@SAE"))
  }

}
