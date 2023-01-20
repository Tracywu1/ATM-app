package com.cc;

/**
 * 账户类
 */
public class Account {
    /**
     * 卡号
     */
    private String cardId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 账户余额
     */
    private double balance;

    /**
     * 每次取现额度
     */
    private double quotaMoney;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}
