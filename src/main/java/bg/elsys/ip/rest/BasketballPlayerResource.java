package bg.elsys.ip.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/players")
public class BasketballPlayerResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response getPlayers(@DefaultValue("0") @QueryParam("page") int page,
					@DefaultValue("0") @QueryParam("dataPerPage") int playersPerPage) {
		if(page > 0  && playersPerPage > 0) {
			if(page*playersPerPage <= BasketballPlayersManager
															.getPlayersList()
															.size()) {
				return Response.ok(BasketballPlayersManager
												.getPlayersList()
												.subList((page-1)*playersPerPage,
														(page++)*playersPerPage)).build();
			}
			else if((page-1)*playersPerPage < BasketballPlayersManager
																.getPlayersList()
																.size()) {
				return Response.ok(BasketballPlayersManager
										.getPlayersList()
										.subList((page-1)*playersPerPage,
												BasketballPlayersManager
																.getPlayersList()
																.size())).build();
			}
			else { //Send a page-not-found status as there are no more pages to load
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		}
		else { //If no query params are passed, thus their default value being used
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPlayer(BasketballPlayer player) {
		if(BasketballPlayersManager.playerExists(player)) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		return Response.ok(BasketballPlayersManager.addNewPlayer(player)).build();
	}

}
