package com.vaccination;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Scanner;

public class Bookings {
	
	public void seeCities() throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		String sql="SELECT * FROM city";
		System.out.println("2");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-8s  %-20s  %-20s\n\n", "city_id", "City_name", "State");
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
		while(rs.next()) {
		    System.out.printf("%-8d %-20s %-20s\n", rs.getInt(1), rs.getString(2), rs.getString(3));
		    
		}
		System.out.println("----------------------------------------------------------------------------------------------------------------");
	}
	public void selectCity(String str) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		String sql="SELECT * FROM vaccination_center WHERE city_id=(SELECT city_id FROM city WHERE city_name=?)";
		PreparedStatement st = con.prepareCall(sql);
		st.setString(1, str);
		ResultSet rs = st.executeQuery();
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s  %-25s  %-20s\n", "Center id", "Center name", "Location");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		while(rs.next()) {
			System.out.printf("%-10d  %-25s  %-20s\n",rs.getInt(1),rs.getString(2),rs.getString(3));
		
			
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------");
	}
	public int checkDate(Date d) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		int a=0;
		String sql="SELECT COUNT(*) FROM holiday WHERE hdate = ?";
		PreparedStatement st = con.prepareCall(sql);
		st.setDate(1, d);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			a=rs.getInt(1);
			
		}
		if(a==1) {
			System.out.println("Sorry, the selected date is a holiday. Please choose another date.");
		}
		else
			System.out.println("Date is available. You can continue.");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		return a;
	}
	public void showSlotAvailable(int a,Date d) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		
		String sql="CALL avaliable_slots(?, ?)";
		CallableStatement st = con.prepareCall(sql);
		st.setDate(1, d);
		st.setInt(2, a);
		ResultSet rs = st.executeQuery();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-10s  %-10s\n", "Slot id", "Time");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		while(rs.next()) {
			System.out.printf("%-10d  %-10s\n",rs.getInt(1),rs.getTime(2).toString());
		
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	
	public void insertUserInfo(long a, String b, Date c, long d, String em) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		String sql="INSERT INTO user (Aadhar, full_name, dob, mobile, email) VALUES (?,?,?,?,?)";
		PreparedStatement st = con.prepareCall(sql);
		st.setLong(1, a);
		st.setString(2, b);
		st.setDate(3, c);
		st.setLong(4, d);
		st.setString(5, em);
		int rs = st.executeUpdate();
		System.out.println(rs);
	}
	public int insertIntoBookings(long Aadhar, Date d, int center_id, int slot_id, int vacc_type) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		String sql="INSERT INTO bookings (Aadhar, date, center_id, slot_id,vacc_type) VALUES (?,?,?,?,?)";
		PreparedStatement st = con.prepareCall(sql);
		st.setLong(1, Aadhar);
		st.setDate(2, d);
		st.setInt(3, center_id);
		st.setInt(4, slot_id);
		st.setInt(5, vacc_type);
		int rs = st.executeUpdate();
		System.out.println(rs);
		return rs;
	}
	public void deleteFromUser(long aadhar) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		String sql="DELETE FROM user WHERE Aadhar=?";
		PreparedStatement st = con.prepareCall(sql);
		st.setLong(1, aadhar);
		int rs=st.executeUpdate();
		System.out.println(rs);
	}
	public void ShowUserInfo(long Aadhar) throws ClassNotFoundException, SQLException {
		Connection con = ConnectionFormDb.connection1();
		
		String sql="CALL Show_UserInfo(?)";
		CallableStatement st = con.prepareCall(sql);
		st.setLong(1, Aadhar);
		
		ResultSet rs = st.executeQuery();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-15s  %-15s   %-15s   %-15s   %-15s   %-15s\n", "Aadhar", "Full Name", "Booking Date", "Center_id","Slot_id","Vacc_type");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		while(rs.next()) {
			System.out.printf("%-15s  %-15s   %-15s   %-15s   %-15s   %-15s\n", rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getInt(4),rs.getInt(5),rs.getInt(6));
		
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
