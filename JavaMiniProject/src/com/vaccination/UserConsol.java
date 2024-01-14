package com.vaccination;

import java.util.Scanner;
import java.sql.Date;
import java.sql.SQLException;

public class UserConsol {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		long aadharcard = 0;
		int valid = 0;
		Date vaccination_date = null;
		String fullname = null;
		Date dob = null;
		long mob = 0;
		String email = null;
		int query = 0;
		Scanner sc = new Scanner(System.in);
		Bookings b = new Bookings();

		System.out.println("Welcome to the Vaccination Booking System! 😊😊");

		System.out.println("To start booking process, please press 1");
		int start = sc.nextInt();
		if (start == 1) {
			System.out.println("Cities Available for Vaccination:");
			System.out.println("_________________________________________________________________________");
			b.seeCities();

			System.out.println("Select your city");
			System.out.println("____________________________________________________________________________");
			String selectedCity = sc.next();
			b.selectCity(selectedCity);

			do {
				System.out.println("Add date of vaccination (yyyy-mm-dd)");
				System.out.println("_______________________________________________________________________________");
				vaccination_date = Date.valueOf(sc.next());
				valid = b.checkDate(vaccination_date);
				if (valid != 0) {
					System.out.println("Invalid date. Please choose a valid date. ☠️💀☠️💀");
				}
			} while (valid != 0);

			System.out.println("Add center id");
			System.out.println("_________________________________________________________________________");
			int center_id = sc.nextInt();
			b.showSlotAvailable(center_id, vaccination_date);

			System.out.println("Select slot id");
			System.out.println("___________________________________________________________________________");
			int slot_id = sc.nextInt();

			System.out.println("Please specify your vaccination type:");
			System.out.println("1. First Dose (Provide User Information) 👍");
			System.out.println("2. Second Dose (Provide Aadhar Card Only)✌️");
			int vacc_type = sc.nextInt();

			if (vacc_type == 1) {
				System.out.println("You need to add user information 🥲🥲🥲");
				System.out.println("Enter your Aadhar card number");
				aadharcard = sc.nextLong();
				System.out.println("Enter your full name");
				fullname = sc.next();
				System.out.println("Enter your Date of birth (yyyy-mm-dd)");
				dob = Date.valueOf(sc.next());
				System.out.println("Enter your Mobile number");
				mob = sc.nextLong();
				System.out.println("Enter your email");
				email = sc.next();
				b.insertUserInfo(aadharcard, fullname, dob, mob, email);
			} else if (vacc_type == 2) {
				System.out.println("Provide your Aadhar card number 😎");
				aadharcard = sc.nextLong();
			}

			try {
				query = b.insertIntoBookings(aadharcard, vaccination_date, center_id, slot_id, vacc_type);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (query == 0 && vacc_type == 1) {
					b.deleteFromUser(aadharcard);
					System.out.println("Booking unsuccessful. User data deleted.😆😆😆");
				} else if (query == 0 && vacc_type == 2) {
					System.out.println("Booking unsuccessful.🤣🤣🤣");
				} else {
					System.out.println("Booking successful. Your vaccination is scheduled.😃😃");
				}
			}
			System.out.println("\nDo you want to see your booking? Type 'yes' or 'no': 👨‍💻👩‍💻");
			String ss = sc.next().toLowerCase();
			if (ss.equals("yes")) {
				b.ShowUserInfo(aadharcard);
			} else {
				System.out.println("\nThank you! See you soon.🫡🫡🫡");
			}
		}
	}
}
