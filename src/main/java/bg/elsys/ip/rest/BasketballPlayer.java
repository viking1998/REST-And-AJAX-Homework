package bg.elsys.ip.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BasketballPlayer {
	int id;
	
	@ApiModelProperty(required = true,
						value = "This will show the first name of the basketball player",
						example = "John")
	String firstName;
	
	@ApiModelProperty(required = true,
						value = "This will show the last name of the player",
						example = "Bryant")
	String lastName;
	
	@ApiModelProperty(required = true,
						value = "This will show the height of the player",
						example = "200")
	int height;
	
	@ApiModelProperty(required = true,
						value = "This will show the age of the player",
						example = "20")
	int age;
	
	@ApiModelProperty(required = true,
						value = "This will show the team name of the player",
						example = "Chicago Bulls")
	String teamName;
	
	@ApiModelProperty(required = true,
						value = "This will show the jersey number of the player",
						example = "5")
	int jerseyNumber;

	public BasketballPlayer() {
	}

	public BasketballPlayer(String firstName, String lastName, 
							int height, int age, String teamName, 
							int jerseyNumber) {
		this.id = BasketballPlayersManager.getNextID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.height = height;
		this.age = age;
		this.teamName = teamName;
		this.jerseyNumber = jerseyNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

}
