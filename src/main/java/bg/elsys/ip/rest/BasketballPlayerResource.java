package bg.elsys.ip.rest;

import java.util.ArrayList;

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
					@DefaultValue("0") @QueryParam("dataPerPage") int playersPerPage,
					@DefaultValue("") @QueryParam("height") String height,
					@DefaultValue("") @QueryParam("age") String age,
					@DefaultValue("") @QueryParam("tName") String teamName,
					@DefaultValue("") @QueryParam("jNum") String jerseyNumber) {
		ArrayList<BasketballPlayer> players = new ArrayList<>(BasketballPlayersManager
																.getPlayersList());
		if(!"".equalsIgnoreCase(height) && !"None".equalsIgnoreCase(height)) {
			players.removeIf(player -> player.getHeight() != Integer.parseInt(height));
		}
		if(!"".equalsIgnoreCase(age) && !"None".equalsIgnoreCase(age)) {
			players.removeIf(player -> player.getAge() != Integer.parseInt(age));
		}
		if(!"".equalsIgnoreCase(teamName)) {
			
		}
		if(!"".equalsIgnoreCase(jerseyNumber) && !"None".equalsIgnoreCase(jerseyNumber)) {
			players.removeIf(player -> player.getJerseyNumber() != Integer.parseInt(jerseyNumber));
		}
		if(page > 0  && playersPerPage > 0) {
			if(page*playersPerPage <= players.size()) {
				return Response.ok(players.subList((page-1)*playersPerPage,
													(page++)*playersPerPage)).build();
			}
			else if((page-1)*playersPerPage < players.size()) {
				return Response.ok(players.subList((page-1)*playersPerPage,
													players.size())).build();
			}
			else { //Send a page-not-found status as there are no more pages to load
				return Response
								.status(Response.Status.NOT_FOUND)
								.entity("No more players to load.")
								.build();
			}
		}
		else { //If no query params are passed, thus their default value being used
			return Response
							.status(Response.Status.BAD_REQUEST)
							.entity("Bad request!")
							.build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPlayer(BasketballPlayer player) {
		if(BasketballPlayersManager.playerExists(player)) {
			return Response
							.status(Response.Status.NOT_ACCEPTABLE)
							.entity("Such player already exists!")
							.build();
		}
		return Response.ok(BasketballPlayersManager.addNewPlayer(player)).build();
	}
}
