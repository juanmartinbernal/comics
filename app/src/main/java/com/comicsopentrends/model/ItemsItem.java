package com.comicsopentrends.model;

public class ItemsItem{
	private String warFrequency;
	private int warWins;
	private int warWinStreak;
	private int clanLevel;
	private int requiredTrophies;
	private BadgeUrls badgeUrls;
	private boolean isWarLogPublic;
	private String type;
	private int clanPoints;
	private int warTies;
	private int warLosses;
	private int clanVersusPoints;
	private int members;
	private String name;
	private Location location;
	private String tag;

	public void setWarFrequency(String warFrequency){
		this.warFrequency = warFrequency;
	}

	public String getWarFrequency(){
		return warFrequency;
	}

	public void setWarWins(int warWins){
		this.warWins = warWins;
	}

	public int getWarWins(){
		return warWins;
	}

	public void setWarWinStreak(int warWinStreak){
		this.warWinStreak = warWinStreak;
	}

	public int getWarWinStreak(){
		return warWinStreak;
	}

	public void setClanLevel(int clanLevel){
		this.clanLevel = clanLevel;
	}

	public int getClanLevel(){
		return clanLevel;
	}

	public void setRequiredTrophies(int requiredTrophies){
		this.requiredTrophies = requiredTrophies;
	}

	public int getRequiredTrophies(){
		return requiredTrophies;
	}

	public void setBadgeUrls(BadgeUrls badgeUrls){
		this.badgeUrls = badgeUrls;
	}

	public BadgeUrls getBadgeUrls(){
		return badgeUrls;
	}

	public void setIsWarLogPublic(boolean isWarLogPublic){
		this.isWarLogPublic = isWarLogPublic;
	}

	public boolean isIsWarLogPublic(){
		return isWarLogPublic;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setClanPoints(int clanPoints){
		this.clanPoints = clanPoints;
	}

	public int getClanPoints(){
		return clanPoints;
	}

	public void setWarTies(int warTies){
		this.warTies = warTies;
	}

	public int getWarTies(){
		return warTies;
	}

	public void setWarLosses(int warLosses){
		this.warLosses = warLosses;
	}

	public int getWarLosses(){
		return warLosses;
	}

	public void setClanVersusPoints(int clanVersusPoints){
		this.clanVersusPoints = clanVersusPoints;
	}

	public int getClanVersusPoints(){
		return clanVersusPoints;
	}

	public void setMembers(int members){
		this.members = members;
	}

	public int getMembers(){
		return members;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

	public String getTag(){
		return tag;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"warFrequency = '" + warFrequency + '\'' + 
			",warWins = '" + warWins + '\'' + 
			",warWinStreak = '" + warWinStreak + '\'' + 
			",clanLevel = '" + clanLevel + '\'' + 
			",requiredTrophies = '" + requiredTrophies + '\'' + 
			",badgeUrls = '" + badgeUrls + '\'' + 
			",isWarLogPublic = '" + isWarLogPublic + '\'' + 
			",type = '" + type + '\'' + 
			",clanPoints = '" + clanPoints + '\'' + 
			",warTies = '" + warTies + '\'' + 
			",warLosses = '" + warLosses + '\'' + 
			",clanVersusPoints = '" + clanVersusPoints + '\'' + 
			",members = '" + members + '\'' + 
			",name = '" + name + '\'' + 
			",location = '" + location + '\'' + 
			",tag = '" + tag + '\'' + 
			"}";
		}
}
