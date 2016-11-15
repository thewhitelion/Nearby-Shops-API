package org.nearbyshops.DAOPreparedCartOrder;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.ContractClasses.CartContract;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.JDBCContract;
import org.nearbyshops.Model.Cart;

import java.sql.*;
import java.util.ArrayList;


public class CartService {


	private HikariDataSource dataSource = Globals.getDataSource();

	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	



	
	public int saveCart(Cart cart)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		int rowIdOfInsertedRow = -1;

		String insertItemCategory = "INSERT INTO "
				+ CartContract.TABLE_NAME
				+ "("  
				+ CartContract.SHOP_ID + ","
				+ CartContract.END_USER_ID + ""
				+ " ) VALUES ( ?,?)";

//		+ "" + cart.getShopID()	+ ","
//				+ "" + cart.getEndUserID() + ""


		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,Statement.RETURN_GENERATED_KEYS);

			statement.setObject(1,cart.getShopID());
			statement.setObject(2,cart.getEndUserID());
			
			rowIdOfInsertedRow = statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				rowIdOfInsertedRow = rs.getInt(1);
			}
			
			
			
			System.out.println("Key autogenerated Save Cart: " + rowIdOfInsertedRow);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		return rowIdOfInsertedRow;
	}
	


	public int updateCart(Cart cart)
	{	
		String updateStatement = "UPDATE " + CartContract.TABLE_NAME
				+ " SET "
				+ CartContract.END_USER_ID + " = ?,"
				+ CartContract.SHOP_ID + " = ?"
				+ " WHERE " + CartContract.CART_ID + " = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
		int updatedRows = -1;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			statement.setObject(1,cart.getEndUserID());
			statement.setObject(2,cart.getShopID());

			updatedRows = statement.executeUpdate();
			
			
			System.out.println("Total rows updated: " + updatedRows);	
			
			//conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return updatedRows;
		
	}
	

	public int deleteCart(int cartID)
	{
		
		String deleteStatement = "DELETE FROM " + CartContract.TABLE_NAME
								+ " WHERE " + CartContract.CART_ID + " = ?";
		
		
		Connection connection= null;
		PreparedStatement statement = null;
		int rowsCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setObject(1,cartID);

			rowsCountDeleted = statement.executeUpdate();
			System.out.println(" Deleted Count: " + rowsCountDeleted);

			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		return rowsCountDeleted;
	}
	
	
	
	
	
	public ArrayList<Cart> readCarts(Integer endUserID,Integer shopID)
	{
		String query = "SELECT * FROM " + CartContract.TABLE_NAME;

		boolean isFirst = true;

		if(endUserID != null)
		{
			query = query + " WHERE " + CartContract.END_USER_ID + " = " + endUserID;

			isFirst = false;
		}

		if(shopID != null)
		{
			if(isFirst)
			{
				query = query + " WHERE " + CartContract.SHOP_ID + " = " + shopID;

			}else
			{
				query = query + " AND " + CartContract.SHOP_ID + " = " + shopID;

			}

		}



		ArrayList<Cart> cartsList = new ArrayList<Cart>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{

				Cart cart = new Cart();

				cart.setCartID(rs.getInt(CartContract.CART_ID));
				cart.setEndUserID(rs.getInt(CartContract.END_USER_ID));
				cart.setShopID(rs.getInt(CartContract.SHOP_ID));

				cartsList.add(cart);
				
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
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
								
		return cartsList;
	}

	
	public Cart readCart(int cartID)
	{
		
		String query = "SELECT * FROM " + CartContract.TABLE_NAME
						+ " WHERE " + CartContract.CART_ID + " = " + cartID;
		
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		Cart cart = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				cart = new Cart();
				cart.setCartID(rs.getInt(CartContract.CART_ID));
				cart.setShopID(rs.getInt(CartContract.SHOP_ID));
				cart.setEndUserID(rs.getInt(CartContract.END_USER_ID));
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
			
				if(statement!=null)
				{statement.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				
				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return cart;
	}	
}