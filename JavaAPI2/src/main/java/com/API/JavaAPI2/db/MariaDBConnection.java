package com.API.JavaAPI2.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.util.JSONPObject;

public class MariaDBConnection {
	
	
	
	final private String databaseURL = "jdbc:mariadb://localhost:3306/Location";
	private static String user = "root";
    private static String pass = "admin";
	Connection conn = null;
/*
    private ResultSet connect(String sql, String[] inject) {

    	try { 
			tryConnect();
			return tryConnection(sql, inject);
		} catch (ClassNotFoundException ex) {
			System.out.println("\nDid no find database driver class\n");
        	ex.printStackTrace();	
        } catch (SQLException ex) {
       		System.out.println("\nAn error occurred while connecting to database\npleas check username and password\n");
       		ex.printStackTrace();
       	} finally {
       		if (conn != null) {
       			try {
       				System.out.println("\ntrying to close database connection\n");
       			conn.close();
        			System.out.println("\nconnection with database closed\n");
       			}
       			catch (SQLException ex){
       				System.out.println("\nfaild to disconnect from database\n");
       				ex.printStackTrace();
       			}
       		}
       	}
		return null;
		
	}*/
	/*
	private ResultSet tryConnection(String sql, String[] inject)  throws ClassNotFoundException, SQLException{
		//stmt = conn.createStatement();
	    //ResultSet rs = stmt.executeQuery(sql);
		
		PreparedStatement stmt = conn.prepareStatement(sql);

		for(int i = 0; i < inject.length; ++i)
		{
			stmt.setString(i, inject[i]);
		}
		ResultSet rs = stmt.executeQuery();
	    return rs;


	}*/
	
	

	private void tryConnect() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver"); 
    	conn = DriverManager.getConnection(databaseURL, user, pass);
    	if (conn != null)
    	{
    		System.out.println("Connected to the database");
    	}
	}
	
	public int adAdd(double longitude, double latitude, int radius, String company, String summary, int days, int nrViews, String url) {
		
		int added = -1;
		
		try {
			tryConnect();
			
			String sql = "INSERT INTO ads (lon, lat, radius, company, summary, expirationDate, nrViews, url) VALUES(?, ?, ?, ?, ?, DATE_ADD(CURDATE(), INTERVAL ? DAY), ?, ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			
			
			stmt.setDouble(1, longitude);
			stmt.setDouble(2, latitude);
			stmt.setInt(3, radius);
			stmt.setString(4, company);
			stmt.setString(5, summary);
			stmt.setInt(6,  days);
			stmt.setInt(7, nrViews);
			stmt.setString(8, url);
			
			//race condition
			stmt.executeQuery();
			
			sql = "SELECT  id From ads  ORDER BY id DESC LIMIT 1;";
			
			
			ResultSet res = stmt.executeQuery(sql);
			if(res.next())
			{
				//res.next();
				added = res.getInt("id");
			}
		    
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		return added;
	}
	
	private void printErr (ClassNotFoundException ex) {
		System.out.println("error: Did not find database driver class");
		ex.printStackTrace();
	}
	
	private void printErr (SQLException ex) {
		System.out.println("error: An error occurred while connecting to database");
		ex.printStackTrace();
	}
	
	private void disconnect() {
		if (conn != null) {
   			try {
   				System.out.println("\ntrying to close database connection\n");
   			conn.close();
    			System.out.println("\nconnection with database closed\n");
   			}
   			catch (SQLException ex){
   				System.out.println("\nfaild to disconnect from database\n");
   				ex.printStackTrace();
   			}
   		}
		conn = null;
	}

	//set watched only if the add have specified that it wants to only have one view per user

	public boolean checkIfUserHaveWatched(int userId, int adId)
	{
		boolean watched = false;
		try {
			tryConnect();
			

			String sql = "SELECT id FROM haveWatched WHERE"
					+ " (id = ? AND userId = ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			System.out.println("adId: " + Integer.toString(adId) + "\nuserId: " + Integer.toString(userId));
			stmt.setInt(1, adId);
			stmt.setInt(2, userId);
			
			ResultSet res = stmt.executeQuery();
			
			System.out.println(res);
			
			
			if (res.next())
			{
				watched = true;
				System.out.println("-watched" + Integer.toString(res.getInt(1)));
				
				
			}
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		return watched;
	}
	
	public String[] checkIfActive(int id) {
		String[] data = null;
		try {
			tryConnect();
			
			String sql = "SELECT company, summary, id, url, expirationDate, nrViews FROM ads WHERE id = ? AND expirationDate > NOW() AND nrViews > 0;";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);	
			
			ResultSet res = stmt.executeQuery();
			
			if (res.next())
			{
				System.out.println(res.getString("company"));
				data = new String[4];
				
				data[0] = res.getString("company");
				
				data[1] = res.getString("summary");
				
				Date expirationDate = (res.getDate("expirationDate"));
				DateFormat expirationDateFormated = new SimpleDateFormat("yyyy-MM-dd");
				data[2] = expirationDateFormated.format(expirationDate);
				
				data[3] = res.getString("url");
				
				int id2 = res.getInt("id");
				
				subtractView(id2);
				addCurView(id2);
				
			}
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		return data;
	}

	private void addCurView(int id) {
		try {
			tryConnect();
			
			String sql = "UPDATE ads SET curNrViews = (curNrViews + 1) WHERE (id = ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			
			stmt.executeQuery();
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
	}

	private void subtractView(int id) {
		
		try {
			tryConnect();
			
			String sql = "UPDATE ads SET nrViews = (nrViews -1) WHERE (id = ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			
			stmt.executeQuery();
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
	}

	public void setWatched(int userId, int adId) {
		
		try {
			tryConnect();
			
			String sql = "INSERT INTO haveWatched (id, userId) VALUES (?, ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, adId);
			stmt.setInt(2, userId);
			
			stmt.executeQuery();
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
	}

	public boolean checkDistance(int id, int dist) {
		
		boolean distanceInScope = false;
		try {
			tryConnect();
			
			String sql = "SELECT id radius FROM ads WHERE (id = ? AND radius > ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			stmt.setInt(2, dist);
			
			ResultSet res = stmt.executeQuery();
			System.out.println("--id: ");//  + Integer.toString(res.getInt(id)));
			if(res.next())
			{
				System.out.println("res.next() check");
				distanceInScope = true;
			}
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		return distanceInScope;
	}

	public boolean adAppWhoLikeCompany(String app, String company) {
		
		
		boolean added = false;
		try {
			tryConnect();
			
			String sql = "INSERT INTO apps (app, approvedCompany) VALUES (?, ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, app);
			stmt.setString(2, company);
		
			
			stmt.executeQuery();
			
			added = true;
			
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		return added;
	}

	public boolean adCompanyWhoLikeApp(String appName, String company) {
		
		boolean added = false;
		try {
			tryConnect();
			
			String sql = "INSERT INTO companies (company, approvedApp) VALUES (?, ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, company);
			stmt.setString(2, appName);
		
			
			stmt.executeQuery();
			
			added = true;
			
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		return added;

	}

	public boolean checkAppAndCompany(String app, int id) {
		
		
		boolean approved = false;
		
		
		try {
			tryConnect();
			
			String sql = "SELECT company FROM ads WHERE id = ?;";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			
			
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
			{
				String company = res.getString("company");
				
				
				sql = "SELECT app FROM apps WHERE app = ? AND approvedCompany = ?;";

				
				stmt = conn.prepareStatement(sql);

				stmt.setString(1, app);
				stmt.setString(2, company);
			
				res = null;
				res = stmt.executeQuery();
				if(res.next())
				{
					sql = "SELECT company FROM companies WHERE company = ? AND approvedApp = ?;";

					
					stmt = conn.prepareStatement(sql);

					stmt.setString(1, company);
					stmt.setString(2, app);
				
					
					res = stmt.executeQuery();
					if (res.next())
					{
						approved = true;
					}
				}
			}
			
			
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		
		
		return approved;
	}

	public String[] getApps()
	{
		ArrayList<String> arr = new ArrayList<String> ();

		try {
			tryConnect();
			
			String sql = "SELECT DISTINCT app from apps;";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			
		
			
			ResultSet res = stmt.executeQuery();
			
			
			while(res.next())
			{
				arr.add(res.getString("app"));
			}
			
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		String[] retArr = new String[arr.size()];
		retArr = arr.toArray(retArr);
		
		return retArr;
	}
	
	public String[] getCompanies()
	{
		ArrayList<String> arr = new ArrayList<String> ();

		try {
			tryConnect();
			
			String sql = "SELECT DISTINCT company from companies;";

			
			Statement stmt = conn.createStatement();

			
		
			
			ResultSet res = stmt.executeQuery(sql);
			
			
			while(res.next())
			{
				arr.add(res.getString("company"));
			}
			
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		String[] retArr = new String[arr.size()];
		retArr = arr.toArray(retArr);
		
		return retArr;
	}

	public boolean oneTimeWatch(int id) {
		boolean oneTimeWatch = true;
		
		try {
			tryConnect();
			
			String sql = "SELECT oneTimeView radius FROM ads WHERE (id = ?);";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);
			
			
			ResultSet res = stmt.executeQuery();
			System.out.println("--id: ");//  + Integer.toString(res.getInt(id)));
			if(res.next())
			{
				oneTimeWatch = res.getBoolean("oneTimeWatch");
			}
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		return oneTimeWatch;
	}

	public String[] getCurNrViews(String company) {
		
		
		ArrayList<String> arr = new ArrayList<String> ();

		try {
			tryConnect();
			
			String sql = "SELECT (curNrViews) FROM ads WHERE company = ?;";

			
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, company);
		
			
			ResultSet res = stmt.executeQuery();
			
			String add;
			while(res.next())
			{
				add = Integer.toString(res.getInt("curNrViews"));
				arr.add(add);
			}
			
			
			
		}catch (ClassNotFoundException ex) {
			printErr(ex);
        } catch (SQLException ex) {
       		printErr(ex);
       	} finally {
       		disconnect();
       	}
		
		String[] retArr = new String[arr.size()];
		retArr = arr.toArray(retArr);
		return retArr;
	}
}