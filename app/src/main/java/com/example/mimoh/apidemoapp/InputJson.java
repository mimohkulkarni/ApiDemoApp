package com.example.mimoh.apidemoapp;

import android.graphics.Bitmap;

public class InputJson {

    private String email;
    private String password;
    private String uid;
    private String mobile_no;
    private String name;
    private String links;
    private String location;
    private String id;
    private String skills;
    private String image_path;
    private String start_year;
    private String end_year;
    private String degree;
    private String organisation;
    private String photo;

    public InputJson(String photo,String uid) {
        this.photo = photo;
        this.uid = uid;
    }

    private String end_date;
    //    private String organisation;
    private String designation;
    private String start_date;

    public InputJson(String location, String start_year, String end_year, String degree, String org) {
        this.location = location;
        this.start_year = start_year;
        this.end_year = end_year;
        this.degree = degree;
        this.organisation = org;
    }

    public InputJson(String end_date,String organisation,String designation,String start_date){
        this.end_date = end_date;
        this.organisation = organisation;
        this.designation = designation;
        this.start_date = start_date;
    }

    public InputJson(String email, String password,int a) {
        this.email = email;
        this.password = password;
    }

    public InputJson(String mobile_no, String name, String links, String location, String id, String skills) {
        this.mobile_no = mobile_no;
        this.name = name;
        this.links = links;
        this.location = location;
        this.id = id;
        this.skills = skills;
    }



    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public String getEnd_year() {
        return end_year;
    }

    public void setEnd_year(String end_year) {
        this.end_year = end_year;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String org) {
        this.organisation = org;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
