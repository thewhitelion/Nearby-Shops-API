package org.nearbyshops.DAOsRemaining;

import org.nearbyshops.ContractClasses.DeliveryGuySelfContract;
import org.nearbyshops.JDBCContract;
import org.nearbyshops.ModelDeliverySelf.DeliveryGuySelf;

import java.sql.*;
import java.util.ArrayList;


public class DeliveryGuySelfDAO {

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	
	public int saveDeliveryVehicleSelf(DeliveryGuySelf deliveryGuySelf)
	{

		Connection conn = null;
		Statement stmt = null;
		int rowIdOfInsertedRow = -1;

		String insertItemCategory = "INSERT INTO "
				+ DeliveryGuySelfContract.TABLE_NAME
				+ "("
				+ DeliveryGuySelfContract.VEHICLE_NAME + ","
				+ DeliveryGuySelfContract.SHOP_ID + ""
				+ " ) VALUES ( "
				+ "'" + deliveryGuySelf.getVehicleName()	+ "',"
				+ "" + deliveryGuySelf.getShopID() + ""
				+ ")";
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowIdOfInsertedRow = stmt.executeUpdate(insertItemCategory,Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();

			if(rs.next())
			{
				rowIdOfInsertedRow = rs.getInt(1);
			}
			
			
			
			System.out.println("Key autogenerated SaveDistributor: " + rowIdOfInsertedRow);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		return rowIdOfInsertedRow;
	}
	

	public int updateDeliveryVehicleSelf(DeliveryGuySelf deliveryGuySelf)
	{	
		String updateStatement = "UPDATE " + DeliveryGuySelfContract.TABLE_NAME
				+ " SET "
				+ DeliveryGuySelfContract.VEHICLE_NAME + " = "  + "'" + deliveryGuySelf.getVehicleName() + "'"  + ","
				+ DeliveryGuySelfContract.SHOP_ID + " = "  + " " + deliveryGuySelf.getShopID() + " "  + ""
				+ " WHERE " + DeliveryGuySelfContract.ID + " = " + deliveryGuySelf.getID();
		
		Connection conn = null;
		Statement stmt = null;
		int updatedRows = -1;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			updatedRows = stmt.executeUpdate(updateStatement);
			
			
			System.out.println("Total rows updated: " + updatedRows);	
			
			//conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return updatedRows;
		
	}
	

	public int deleteDeliveryVehicleSelf(int vehicleID)
	{
		
		String deleteStatement = "DELETE FROM " + DeliveryGuySelfContract.TABLE_NAME
				+ " WHERE " + DeliveryGuySelfContract.ID + " = " + vehicleID;
		
		
		Connection conn= null;
		Statement stmt = null;
		int rowsCountDeleted = 0;
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rowsCountDeleted = stmt.executeUpdate(deleteStatement);
			
			System.out.println(" Deleted Count: " + rowsCountDeleted);	
			
			conn.close();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		return rowsCountDeleted;
	}
	
	
	
	
	
	public ArrayList<DeliveryGuySelf> readDeliveryVehicleSelf(int shopID)
	{
		String query = "SELECT * FROM " + DeliveryGuySelfContract.TABLE_NAME;

		//boolean isFirst = true;

		if(shopID > 0)
		{
			query = query + " WHERE " + DeliveryGuySelfContract.SHOP_ID + " = " + shopID;

				//isFirst = false;
		}

		/*

		if(shopID > 0 )
		{
			if(isFirst)
			{
				query = query + " WHERE " + CartContract.ITEM_ID + " = " + shopID;

			}else
			{
				query = query + " AND " + CartContract.ITEM_ID + " = " + shopID;

			}

		}

		*/



		ArrayList<DeliveryGuySelf> vehiclesList = new ArrayList<DeliveryGuySelf>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
					,JDBCContract.CURRENT_USERNAME
					, JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{

				DeliveryGuySelf deliveryGuySelf = new DeliveryGuySelf();

				deliveryGuySelf.setID(rs.getInt(DeliveryGuySelfContract.ID));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelfContract.SHOP_ID));
				deliveryGuySelf.setVehicleName(rs.getString(DeliveryGuySelfContract.VEHICLE_NAME));

				vehiclesList.add(deliveryGuySelf);
				
			}
			

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
								
		return vehiclesList;
	}

	
	public DeliveryGuySelf readVehicle(int vehicleID)
	{
		
		String query = "SELECT * FROM " + DeliveryGuySelfContract.TABLE_NAME
						+ " WHERE " + DeliveryGuySelfContract.ID + " = " + vehicleID;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;


		DeliveryGuySelf deliveryGuySelf = null;
		
		try {
			
			conn = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL,
					JDBCContract.CURRENT_USERNAME,JDBCContract.CURRENT_PASSWORD);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				deliveryGuySelf = new DeliveryGuySelf();

				deliveryGuySelf.setVehicleName(rs.getString(DeliveryGuySelfContract.VEHICLE_NAME));
				deliveryGuySelf.setShopID(rs.getInt(DeliveryGuySelfContract.SHOP_ID));
				deliveryGuySelf.setID(rs.getInt(DeliveryGuySelfContract.ID));

			}
			
			
			//System.out.println("Total itemCategories queried " + itemCategoryList.size());	
	
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		
		{
			
			try {
					if(rs!=null)
					{rs.close();}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			try {
			
				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return deliveryGuySelf;
	}	
}