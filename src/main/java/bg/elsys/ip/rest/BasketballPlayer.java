package bg.elsys.ip.rest;

public class BasketballPlayer {
	int id;
	String firstName;
	String lastName;
	int height;
	int age;
	String teamName;
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
