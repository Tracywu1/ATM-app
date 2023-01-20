package com.cc;

import com.sun.deploy.util.SessionState;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ATM入口类
 */
public class ATMSystem {
    public static void main(String[] args) {
        //定义一个集合容器，负责以后存储的全部账户对象，进行相关的业务操作
        ArrayList<Account> accounts = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        //展示系统的首页
        while (true) {
            System.out.println("==========ATM系统==========");
            System.out.println("1.账户登录");
            System.out.println("2.账户开户");

            System.out.println("请您选择操作：");
            int command = sc.nextInt();

            switch (command) {
                case 1:
                    //用户登录操作
                    login(accounts, sc);
                    break;
                case 2:
                    //用户账户开户
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("您输入的操作命令不存在，请重新输入！");
            }
        }
    }

    /**
     * 登录功能
     *
     * @param accounts 全部账户对象的集合
     * @param sc       扫描器
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===============系统登录操作===============");
        //1.判断账户集合中是否存在账户，如果不存在账户，登录功能不能运行
        if (accounts.size() == 0) {
            System.out.println("对不起，当前系统中无任何账户，请先开户，再来登录");
            return;//卫语言风格，解决方法的执行
        }

        //2.正式进入登录操作
        System.out.println("请您输入您的卡号：");
        String cardId = sc.next();

        //3.判断卡号是否存在，查询功能
        while (true) {
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc != null) {
                //卡号存在
                while (true) {
                    //4.让用户输入密码，验证密码
                    System.out.println("请您输入登录密码：");
                    String passWord = sc.next();
                    //判断当前账户对象的密码是否与用户输入的一致
                    if (acc.getPassWord().equals(passWord)) {
                        //登录成功
                        String appellation = acc.getSex().equals("女") ? "女士" : "先生";
                        System.out.println("恭喜您，" + acc.getUserName() + appellation + "，您已登录成功！");
                        //跳入下一个界面
                        showUserCommand(sc,acc);

                    } else {
                        System.out.println("对不起，您输入的密码有误，请重新输入！");
                    }
                }
            } else {
            }
            System.out.println("对不起，系统中不存在该账户卡号");
        }
    }

    private static void showUserCommand(Scanner sc,Account acc) {
        while (true) {
            System.out.println("===============用户操作页===============");
            System.out.println("1.查询账户");
            System.out.println("2.存款");
            System.out.println("3.取款");
            System.out.println("4.转账");
            System.out.println("5.修改密码");
            System.out.println("6.退出");
            System.out.println("7.注销");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command) {
                case 1:
                    //查询账户(展示当前登录的账户信息）
                   showaccount(acc);
                    break;
                case 2:
                    //存款
                    break;
                case 3:
                    //取款
                    break;
                case 4:
                    //转账
                    break;
                case 5:
                    //修改密码
                    break;
                case 6:
                    //退出
                    break;
                case 7:
                    //注销
                    break;
                default:
                    System.out.println("您输入的操作命令不正确，请您重新输入！");
            }
        }
    }

    private static void showaccount(Account acc) {
    }


    /**
     * 开户功能
     *
     * @param accounts 接受全部账户的集合
     * @param sc       扫描器
     */
    private static void register(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("===============系统开户操作===============");
        //1.创建一个账户对象，用于后期封装账户信息
        Account account = new Account();

        //2.录入当前账户的信息，注入到账户对象中去
        System.out.println("请您输入账户用户名：");
        String userName = sc.next();
        account.setUserName(userName);

        System.out.println("请您输入您的性别：");
        String sex = sc.next();
        account.setSex(sex);

        while (true) {
            System.out.println("请您输入账户密码：");
            String passWord = sc.next();
            System.out.println("请您输入确认密码：");
            String okPassWord = sc.next();
            if (okPassWord.equals(passWord)) {
                //密码验证通过，可以注入给账户对象
                account.setPassWord(okPassWord);
                break;//密码录入成功，结束死循环
            } else {
                System.out.println("对不起，您两次输入的密码不一致，请您重新输入！");
            }
        }

        System.out.println("请您输入账户当次限额：");
        double quotaMoney = sc.nextDouble();
        account.setQuotaMoney(quotaMoney);

        //为账户随机一个8位且与其他账户的卡号不重复的号码（独立成方法）
        String cardId = getRandomCardId(accounts);
        account.setCardId(cardId);

        //3.把账户对象添加到账户集合中去
        accounts.add(account);
        String appellation = sex.equals("女") ? "女士" : "先生";
        System.out.println("恭喜您，" + userName + appellation + "，您已开户成功" + "您的卡号是：" + cardId);
    }

    /**
     * 为账户生成8位与其他账户卡号不同的号码
     *
     * @param accounts 账户集合
     * @return 新卡号
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        Random r = new Random();
        while (true) {
            //1.先生成8位数字
            String cardId = "";
            for (int i = 0; i < 8; i++) {
                cardId += r.nextInt(10);
            }

            //2.判断这个8位数组的卡号是否与其他账户的卡号重复
            //根据该卡号查询账户对象（方法）
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc == null) {
                //说明cardId此时无重复，是新卡号，可以使用该卡号作为新注册账户的卡号
                return cardId;
            }
        }
    }

    /**
     * 根据卡号查询出对应的账户对象
     *
     * @param cardId   卡号
     * @param accounts 全部账户的集合
     * @return 账户对象||null
     */
    private static Account getAccountByCardId(String cardId, ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }
        }
        return null;
    }
}
