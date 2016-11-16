package bg.elsys.ip.rest;

import java.util.ArrayList;
import java.util.Random;

public class BasketballPlayersManager {
	
	private static int nextId = 1;
	private static ArrayList<BasketballPlayer> playerList = new ArrayList<>(generateTestData());
	
	public static ArrayList<BasketballPlayer> getPlayersList() {
		return playerList;
	}
	
	public static int getNextID() {
		return nextId++;
	}
	
	public static boolean playerExists(BasketballPlayer player) {
		for(BasketballPlayer p : playerList) {
			if(p.getFirstName().equals(player.getFirstName())
				&& p.getLastName().equals(player.getLastName()) 
				&& p.getHeight() == player.getHeight()
				&& p.getAge() == player.getAge()
				&& p.getTeamName().equals(player.getTeamName())
				&& p.getJerseyNumber() == player.getJerseyNumber()) {
				return true;
			}
		}
		return false;
	}
	
	public static BasketballPlayer addNewPlayer(BasketballPlayer player) {
		player.setId(getNextID());
		playerList.add(player);
		return player;
	}
	
	private static ArrayList<BasketballPlayer> generateTestData() {
		ArrayList<BasketballPlayer> list = new ArrayList<>();
		String[] firstNames = {"Petar", "George", "Kobe", "Michael", "Shaquille",
								"Lebron", "Dwayne", "Derrick", "Dirk", "Chris"};
		String[] lastNames = {"Petrov", "Bosh", "Bryant", "Jordan", "James",
								"O'Neill", "Rose", "Nowitzki", "Wade", "Jackson"};
		int[] heights = {180, 190, 185, 200, 205, 195, 210, 199, 182, 193};
		int[] ages = {18, 19, 20, 25, 30, 33, 28, 35, 22, 31};
		String[] teamNames = {"Chicago Bulls", "Golden State Warriors", 
								"Cleveland Cavaliers", "LA Lakers", 
								"LA Clippers", "Miami Heat",
								"Boston Celtics", "Brooklyn Nets",
								"Memphis Grizzlies", "Milwaukee Bucks"};
		int[] jerseyNumbers = {1, 10, 15, 20, 23, 12, 99, 80, 4, 7};
		
		Random rand = new Random();
		for(int i = 0; i < 30; i++) {
			list.add(new BasketballPlayer(	firstNames[rand.nextInt(10)],
											lastNames[rand.nextInt(10)],
											heights[rand.nextInt(10)],
											ages[rand.nextInt(10)],
											teamNames[rand.nextInt(10)],
											jerseyNumbers[rand.nextInt(10)]));
		}
		return list;
 	}

}
