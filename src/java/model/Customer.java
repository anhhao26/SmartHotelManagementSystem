package com.smarthotel.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private Integer customerID;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "TotalSpending")
    private Double totalSpending;

    @Column(name = "Points")
    private Integer points;

    @Column(name = "MemberType")
    private String memberType;

    @Column(name = "CccdPassport") // Đã fix chuẩn tên cột
    private String cccdPassport;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Email")
    private String email;

    @Column(name = "Address")
    private String address;

    @Column(name = "Nationality")
    private String nationality;

    @Temporal(TemporalType.DATE)
    @Column(name = "DateOfBirth")
    private Date dateOfBirth;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Preferences")
    private String preferences;

    // --- GETTERS & SETTERS ---
    public Integer getCustomerID() { return customerID; }
    public void setCustomerID(Integer customerID) { this.customerID = customerID; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Double getTotalSpending() { return totalSpending == null ? 0.0 : totalSpending; }
    public void setTotalSpending(Double totalSpending) { this.totalSpending = totalSpending; }
    public Integer getPoints() { return points == null ? 0 : points; }
    public void setPoints(Integer points) { this.points = points; }
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
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
}