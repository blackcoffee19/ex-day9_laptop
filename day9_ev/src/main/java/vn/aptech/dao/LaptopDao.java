package vn.aptech.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.aptech.entity.Laptop;



public class LaptopDao {
	private Connection connectDb () throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/demo";
		return DriverManager.getConnection(url, "root","");
	}
	public List<Laptop> findAll(){
		List<Laptop> result =  new ArrayList<>();
		try {
			Connection con = connectDb();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM laptops");
			ResultSet rs =stm.executeQuery();
			while(rs.next()) {
				Laptop lt = new Laptop();
				lt.setId(rs.getInt(1));
				lt.setName(rs.getString(2));
				lt.setPrice(rs.getInt(3));
				lt.setDescription(rs.getString(4));
				lt.setImage(rs.getString(5));
				result.add(lt);
			}
			rs.close();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public List<Laptop> findByPrice(int min,int max){
		List<Laptop> result =  new ArrayList<>();
		try {
			Connection con = connectDb();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM laptops WHERE price BETWEEN ? AND ?");
			//gan gia tri cho tham so ?, ?
			stm.setInt(1,min);
			stm.setInt(2, max);
			ResultSet rs =stm.executeQuery();
			while(rs.next()) {
				Laptop lt = new Laptop();
				lt.setId(rs.getInt(1));
				lt.setName(rs.getString(2));
				lt.setPrice(rs.getInt(3));
				lt.setDescription(rs.getString(4));
				lt.setImage(rs.getString(5));
				result.add(lt);
			}
			rs.close();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public boolean create(Laptop laptop){
		boolean result = false;
		try {
			Connection con = connectDb();
			PreparedStatement stm = con.prepareStatement("INSERT INTO laptops (name,price,description,image) VALUES (?,?,?,?)");
			//gan gia tri cho tham so ?, ?
			stm.setString(1,laptop.getName());
			stm.setInt(2, laptop.getPrice());
			stm.setString(3, laptop.getDescription());
			stm.setString(4, laptop.getImage());
			result = stm.executeUpdate() >0;
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	public Laptop findById(int id) {
		Laptop result = new Laptop();
		try {
			Connection con = connectDb();
			PreparedStatement stm = con.prepareStatement("SELECT * FROM laptops WHERE id=?");
			stm.setInt(1, id);
			ResultSet res = stm.executeQuery();
			while(res.next()) {
				result.setId(res.getInt(1));
				result.setName(res.getString(2));
				result.setPrice(res.getInt(3));
				result.setDescription(res.getString(4));
				result.setImage(res.getString(5));
			}
			res.close();
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	public boolean update(Laptop laptop) {
		boolean result =false ;
		try {
			Connection con = connectDb();
			PreparedStatement stm = con.prepareStatement("UPDATE laptops SET name=?,price=?,description=?,image=? WHERE id=?");
			stm.setString(1, laptop.getName());
			stm.setInt(2, laptop.getPrice());
			stm.setString(3, laptop.getDescription());
			stm.setString(4, laptop.getImage());
			stm.setInt(5, laptop.getId());
			result = stm.executeUpdate()>0;
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result ;
	}
	public boolean delete(int id) {
		boolean result =false ;
		try {
			Connection con = connectDb();
			PreparedStatement stm = con.prepareStatement("DELETE FROM laptops WHERE id=?");
			stm.setInt(1, id);
			int num = stm.executeUpdate();
			result = num>0;
			System.out.println(num);
			stm.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result ;
	}
}
