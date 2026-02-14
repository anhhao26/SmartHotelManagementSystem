package model;

import java.util.Date;

public class Customer {
    private int customerID;
    private String fullName;
    private double totalSpending;
    private int points;
    private String memberType;

    // thêm trường profile
    private String cccdPassport;
    private String phone;
    private String email;
    private String address;
    private String nationality;
    private Date dateOfBirth;
    private String gender;
    private String preferences;

    public Customer() {}

    public Customer(int customerID, String fullName, double totalSpending, int points, String memberType) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.totalSpending = totalSpending;
        this.points = points;
        this.memberType = memberType;
    }

    // --- getters & setters ---
    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public double getTotalSpending() { return totalSpending; }
    public void setTotalSpending(double totalSpending) { this.totalSpending = totalSpending; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getMemberType() { return memberType; }
    public void setMemberType(String memberType) { this.memberType = memberType; }

    public String getCccdPassport() { return cccdPassport; }
    public void setCccdPassport(String cccdPassport) { this.cccdPassport = cccdPassport; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public java.util.Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(java.util.Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
}