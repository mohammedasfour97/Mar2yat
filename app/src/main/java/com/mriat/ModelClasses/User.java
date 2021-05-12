package com.mriat.ModelClasses;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String ID, Code, UserName, AppearName, Password, Email, ID_Gender, ID_Country, ID_DefaultStyle, ID_DateFormat,
            MonthFullName, ActivateDaylight, ID_TimeDifference, ID_WeekBegin, picture, Status, AdminStatus,
            SignupDate, LastInteraction, ID_USER, ActivationCode, ActivationDate;

    public User(String ID, String code, String userName, String appearName, String password, String email, String ID_Gender,
                String ID_Country, String ID_DefaultStyle, String ID_DateFormat, String monthFullName, String activateDaylight,
                String ID_TimeDifference, String ID_WeekBegin, String picture, String status, String adminStatus, String signupDate,
                String lastInteraction, String ID_USER, String activationCode, String activationDate) {
        this.ID = ID;
        Code = code;
        UserName = userName;
        AppearName = appearName;
        Password = password;
        Email = email;
        this.ID_Gender = ID_Gender;
        this.ID_Country = ID_Country;
        this.ID_DefaultStyle = ID_DefaultStyle;
        this.ID_DateFormat = ID_DateFormat;
        MonthFullName = monthFullName;
        ActivateDaylight = activateDaylight;
        this.ID_TimeDifference = ID_TimeDifference;
        this.ID_WeekBegin = ID_WeekBegin;
        this.picture = picture;
        Status = status;
        AdminStatus = adminStatus;
        SignupDate = signupDate;
        LastInteraction = lastInteraction;
        this.ID_USER = ID_USER;
        ActivationCode = activationCode;
        ActivationDate = activationDate;
    }


    public User() {
    }


    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_Code = "Code";
    public static final String COLUMN_UserName = "UserName";
    public static final String COLUMN_AppearName = "AppearName";
    public static final String COLUMN_Password = "Password";
    public static final String COLUMN_Email = "Email";
    public static final String COLUMN_ID_Gender = "ID_Gender";
    public static final String COLUMN_ID_Country = "ID_Country";
    public static final String COLUMN_ID_DefaultStyle = "ID_DefaultStyle";
    public static final String COLUMN_ID_DateFormat = "ID_DateFormat";
    public static final String COLUMN_MonthFullName = "MonthFullName";
    public static final String COLUMN_ActivateDaylight = "ActivateDaylight";
    public static final String COLUMN_ID_TimeDifference= "ID_TimeDifference";
    public static final String COLUMN_ID_WeekBegin = "ID_WeekBegin";
    public static final String COLUMN_picture = "picture";
    public static final String COLUMN_Status = "Status";
    public static final String COLUMN_AdminStatus = "AdminStatus";
    public static final String COLUMN_SignupDate = "SignupDate";
    public static final String COLUMN_LastInteraction = "LastInteraction";
    public static final String COLUMN_ID_USER = "ID_USER";
    public static final String COLUMN_ActivationCode = "ActivationCode";
    public static final String COLUMN_ActivationDate = "ActivationDate";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " TEXT,"
                    + COLUMN_Code + " TEXT,"
                    + COLUMN_UserName + " TEXT,"
                    + COLUMN_AppearName + " TEXT,"
                    + COLUMN_Password + " TEXT,"
                    + COLUMN_Email + " TEXT,"
                    + COLUMN_ID_Gender + " TEXT,"
                    + COLUMN_ID_Country + " TEXT,"
                    + COLUMN_ID_DefaultStyle + " TEXT,"
                    + COLUMN_ID_DateFormat + " TEXT,"
                    + COLUMN_MonthFullName + " TEXT,"
                    + COLUMN_ActivateDaylight + " TEXT,"
                    + COLUMN_ID_TimeDifference + " TEXT,"
                    + COLUMN_ID_WeekBegin + " TEXT,"
                    + COLUMN_picture + " TEXT,"
                    + COLUMN_Status + " TEXT,"
                    + COLUMN_AdminStatus + " TEXT,"
                    + COLUMN_SignupDate + " TEXT,"
                    + COLUMN_LastInteraction + " TEXT,"
                    + COLUMN_ID_USER + " TEXT,"
                    + COLUMN_ActivationCode + " TEXT,"
                    + COLUMN_ActivationDate + " TEXT"
                    + ")";


    public void fillUserInfo(ArrayList<HashMap<String, String>> listHM){

        HashMap<String , String> map = listHM.get(0);
        this.ActivateDaylight = map.get("ActivateDaylight");
        this.ActivationCode = map.get("ActivationCode");
        this.ActivationDate = map.get("ActivationDate");
        this.AdminStatus = map.get("AdminStatus");
        this.AppearName = map.get("AppearName");
        this.Code = map.get("Code");
        this.Email = map.get("Email");
        this.ID = map.get("ID");
        this.ID_Country = map.get("ID_Country");
        this.ID_DateFormat = map.get("ID_DateFormat");
        this.ID_DefaultStyle = map.get("ID_DefaultStyle");
        this.ID_Gender = map.get("ID_Gender");
        this.ID_TimeDifference = map.get("ID_TimeDifference");
        this.ID_USER = map.get("ID_USER");
        this.ID_WeekBegin = map.get("ID_WeekBegin");
        this.LastInteraction = map.get("LastInteraction");
        this.MonthFullName = map.get("MonthFullName");
        this.Password = map.get("Password");
        this.picture = map.get("picture");
        this.SignupDate = map.get("SignupDate");
        this.Status = map.get("Status");
        this.UserName = map.get("UserName");

    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAppearName() {
        return AppearName;
    }

    public void setAppearName(String appearName) {
        AppearName = appearName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getID_Gender() {
        return ID_Gender;
    }

    public void setID_Gender(String ID_Gender) {
        this.ID_Gender = ID_Gender;
    }

    public String getID_Country() {
        return ID_Country;
    }

    public void setID_Country(String ID_Country) {
        this.ID_Country = ID_Country;
    }

    public String getID_DefaultStyle() {
        return ID_DefaultStyle;
    }

    public void setID_DefaultStyle(String ID_DefaultStyle) {
        this.ID_DefaultStyle = ID_DefaultStyle;
    }

    public String getID_DateFormat() {
        return ID_DateFormat;
    }

    public void setID_DateFormat(String ID_DateFormat) {
        this.ID_DateFormat = ID_DateFormat;
    }

    public String getMonthFullName() {
        return MonthFullName;
    }

    public void setMonthFullName(String monthFullName) {
        MonthFullName = monthFullName;
    }

    public String getActivateDaylight() {
        return ActivateDaylight;
    }

    public void setActivateDaylight(String activateDaylight) {
        ActivateDaylight = activateDaylight;
    }

    public String getID_TimeDifference() {
        return ID_TimeDifference;
    }

    public void setID_TimeDifference(String ID_TimeDifference) {
        this.ID_TimeDifference = ID_TimeDifference;
    }

    public String getID_WeekBegin() {
        return ID_WeekBegin;
    }

    public void setID_WeekBegin(String ID_WeekBegin) {
        this.ID_WeekBegin = ID_WeekBegin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAdminStatus() {
        return AdminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        AdminStatus = adminStatus;
    }

    public String getSignupDate() {
        return SignupDate;
    }

    public void setSignupDate(String signupDate) {
        SignupDate = signupDate;
    }

    public String getLastInteraction() {
        return LastInteraction;
    }

    public void setLastInteraction(String lastInteraction) {
        LastInteraction = lastInteraction;
    }

    public String getID_USER() {
        return ID_USER;
    }

    public void setID_USER(String ID_USER) {
        this.ID_USER = ID_USER;
    }

    public String getActivationCode() {
        return ActivationCode;
    }

    public void setActivationCode(String activationCode) {
        ActivationCode = activationCode;
    }

    public String getActivationDate() {
        return ActivationDate;
    }

    public void setActivationDate(String activationDate) {
        ActivationDate = activationDate;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getCOLUMN_Code() {
        return COLUMN_Code;
    }

    public static String getCOLUMN_UserName() {
        return COLUMN_UserName;
    }

    public static String getCOLUMN_AppearName() {
        return COLUMN_AppearName;
    }

    public static String getCOLUMN_Password() {
        return COLUMN_Password;
    }

    public static String getCOLUMN_Email() {
        return COLUMN_Email;
    }

    public static String getCOLUMN_ID_Gender() {
        return COLUMN_ID_Gender;
    }

    public static String getCOLUMN_ID_Country() {
        return COLUMN_ID_Country;
    }

    public static String getCOLUMN_ID_DefaultStyle() {
        return COLUMN_ID_DefaultStyle;
    }

    public static String getCOLUMN_ID_DateFormat() {
        return COLUMN_ID_DateFormat;
    }

    public static String getCOLUMN_MonthFullName() {
        return COLUMN_MonthFullName;
    }

    public static String getCOLUMN_ActivateDaylight() {
        return COLUMN_ActivateDaylight;
    }

    public static String getCOLUMN_ID_TimeDifference() {
        return COLUMN_ID_TimeDifference;
    }

    public static String getCOLUMN_ID_WeekBegin() {
        return COLUMN_ID_WeekBegin;
    }

    public static String getCOLUMN_picture() {
        return COLUMN_picture;
    }

    public static String getCOLUMN_Status() {
        return COLUMN_Status;
    }

    public static String getCOLUMN_AdminStatus() {
        return COLUMN_AdminStatus;
    }

    public static String getCOLUMN_SignupDate() {
        return COLUMN_SignupDate;
    }

    public static String getCOLUMN_LastInteraction() {
        return COLUMN_LastInteraction;
    }

    public static String getColumnIdUser() {
        return COLUMN_ID_USER;
    }

    public static String getCOLUMN_ActivationCode() {
        return COLUMN_ActivationCode;
    }

    public static String getCOLUMN_ActivationDate() {
        return COLUMN_ActivationDate;
    }

    public static String getCreateTable() {
        return CREATE_TABLE;
    }
}
