package vn.aptech.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.aptech.dto.ProductDto;
public class ProductDb {
	//SQL Server url: "jdbc:sqlserver://localhost:1433;databaseName=";
	
	private Connection connectDb() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/bookdb";
		return DriverManager.getConnection(url,"root","1313");
	}
	public List<ProductDto> findAll(){
		List<ProductDto> result = new ArrayList<ProductDto>();
		try {
			Connection con = connectDb();
			String query = "SELECT * FROM books;";
			PreparedStatement stm = con.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				ProductDto p = new ProductDto();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setPrice(rs.getInt(3));
				p.setImage(rs.getString(4));
				result.add(p);
			}
			rs.close();
			stm.close();
			con.close();
			return result;			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public boolean create(ProductDto pro) {
		boolean boo= false;
		try {
			Connection conn = connectDb();
			String query = "INSERT INTO books (Name,Price,Image) VALUES(?,?,?);";
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setString(1, pro.getName());
			stm.setInt(2, pro.getPrice());
			stm.setString(3, pro.getImage());
			boo = stm.executeUpdate()>0;
			stm.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return boo;
	}
	public ProductDto FindId(int id) {
		ProductDto pro = new ProductDto();
		try {
			Connection conn =  connectDb();
			String query = "SELECT * FROM books WHERE Id=?;";
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				pro.setId(rs.getInt(1));
				pro.setName(rs.getString(2));
				pro.setPrice(rs.getInt(3));
				pro.setImage(rs.getString(4));				
			}
			rs.close();
			stm.close();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pro;
	}
	public boolean update(ProductDto pro) {
		boolean boo= false;
			try {
				Connection conn  = connectDb();
				String query = "UPDATE books SET Name= ?, Price= ?, Image=? WHERE Id =?;";
				PreparedStatement stm = conn.prepareStatement(query);
				stm.setString(1, pro.getName());
				stm.setInt(2, pro.getPrice());
				stm.setString(3, pro.getImage());
				stm.setInt(4, pro.getId());
				boo = stm.executeUpdate()>0;
				stm.close();
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return boo;
	}
	public boolean delete(int id) {
		boolean boo = false;
		try {
			Connection con = connectDb();
			String query = "DELETE FROM books WHERE Id=?;";
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id);
			boo = stm.executeUpdate()>0;
			stm.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return boo;
	}
}
