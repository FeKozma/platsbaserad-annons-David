package com.API.JavaAPI2.db;
import com.API.JavaAPI2.db.*;

public class databaseHandler {

	private final MariaDBConnection mariaDB;
	private final RedisConnection redis;
	private final int meterMaxRadius = 5000;
	
	public databaseHandler() {
		this.mariaDB = new MariaDBConnection();
		this.redis = new RedisConnection();
		redis.connect();
		
	};
	
	public boolean addAd(double longitude, double latitude, int radius, String company, String summary, int days, int nrViews, String url) {
		
		boolean executed = false;
		
		int id = mariaDB.adAdd(longitude, latitude, radius, company, summary, days, nrViews, url);
		
		System.out.println("id: " + id);
		
		if(id != -1) {
			executed = redis.adLocation(longitude, latitude, id);
		}
		
		return executed;
	}
	
	public String[] getAd(double longitude, double latitude, int id, String app) {
		
		
		int[] nearestId = redis.getClosest(longitude, latitude, meterMaxRadius);
		
		/*
		for (int i = 0; i < nearestId.length; ++i)
		{
			System.out.println("id of nearest (on pos " + Integer.toString(i) + ") : " + Integer.toString(nearestId[i]));
		}
		if (nearestId.length == 0)
		{
			System.out.println("did not find any ads near");
		}*/
		
		String[] adArray = null;
		
		System.out.println("nearestId.length: " + Integer.toString(nearestId.length));
		
		for (int i = 0; i < nearestId.length; ++i)
		{
			//get distance between add and user
			int dist = redis.getDistance(nearestId[i], longitude, latitude);
			System.out.println(Integer.toString(dist));
			
			//check if the add is close enought
			if (mariaDB.checkDistance(nearestId[i], dist))
			{
				System.out.println("dist approved");
				//check if both the add and the company have accepted eatch other
				if(mariaDB.checkAppAndCompany(app, nearestId[i]))
				{
					System.out.println("comp and app approved");

					//System.out.println("approved dist: " + Integer.toString(dist));
					//check if ad can be played multiple times
					if(!mariaDB.oneTimeWatch(nearestId[i])) {
						adArray = mariaDB.checkIfActive(nearestId[i]);
						if(adArray != null)
						{
							i = nearestId.length;
						}

					}
					//check if user allready have watched add if the add is a one time thing
					else if(!mariaDB.checkIfUserHaveWatched(id, nearestId[i]))
					{
				
						adArray = mariaDB.checkIfActive(nearestId[i]);
						if(adArray != null)
						{
					
							mariaDB.setWatched(id, nearestId[i]);
							i = nearestId.length;
						}
					}
				}
			}
			
		}
		
		/*
		 * adArray[0] <= company
		 * adArray[1] <= summary 
		 * adArray[2] <= date
		 * adArray[3] <= url
		 * 
		 * */
		
		return adArray;
	}
	
}
