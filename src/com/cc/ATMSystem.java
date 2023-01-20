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
                        showUserCommand(sc,acc,accounts);
                        return;//退出登录方法

                    } else {
                        System.out.println("对不起，您输入的密码有误，请重新输入！");
                    }
                }
            } else {
            }
            System.out.println("对不起，系统中不存在该账户卡号");
        }
    }

    /**
     * 用户成功登录后的操作页
     * @param sc 扫描器
     * @param acc 当前账户对象
     * @param accounts 全部账户对象的集合
     */
    private static void showUserCommand(Scanner sc,Account acc,ArrayList<Account>accounts) {
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
                   showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc,sc);
                    break;
                case 3:
                    //取款
                    drawMoney(acc,sc);
                    break;
                case 4:
                    //转账
                    transferMoney(acc,sc,accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassWord(sc,acc);
                    return;
                case 6:
                    //退出
                    System.out.println("退出成功！");
                    return;//退出登录后操作页面方法
                case 7:
                    //注销账户
                   deleteAccount(acc,sc,accounts);
                    return;
                default:
                    System.out.println("您输入的操作命令不正确，请您重新输入！");
            }
        }
    }

    /**
     * 销户功能
     * @param acc 当前账户对象
     * @param sc 扫描器
     * @param accounts 所有账户对象的集合
     */
    private static void deleteAccount(Account acc, Scanner sc, ArrayList<Account> accounts) {
        System.out.println("===============用户销户操作===============");
        System.out.println("您真的要销户吗？y/n");
        String rs=sc.next();
        switch(rs){
            case"y":
                //真正的销户
                //从当前账户集合中，删除当前账户对象，销毁完成
                if(acc.getBalance()>0){
                    System.out.println("您账户的余额为" + acc.getBalance() + "元，不允许进行销户操作");
                }else{
                    accounts.remove(acc);
                    System.out.println("您已销户完成!");
                }
                break;
            default:
                System.out.println("好的，当前账户继续保留");
        }
    }

    /**
     * 修改密码
     * @param sc 扫描器
     * @param acc 当前登录成功的对象
     */
    private static void updatePassWord(Scanner sc, Account acc) {
        System.out.println("===============用户密码修改===============");
        while (true) {
            System.out.println("请您输入当前密码：");
            String passWord=sc.next();
            //1.判断这个密码是否正确
            if(acc.getPassWord().equals(passWord)){
                while (true) {
                    //密码正确
                    //2.输入新密码
                    System.out.println("请您输入新密码：");
                    String newPassWord=sc.next();

                    System.out.println("请您确认新密码：");
                    String okPassWord=sc.next();

                    if(newPassWord.equals(okPassWord)){
                        //两次密码一致，可以修改了
                        acc.setPassWord(newPassWord);
                        System.out.println("恭喜您，您的密码修改成功了！");
                        return;
                    }else{
                        System.out.println("您两次输入的密码不正确");
                    }
                }

            }else{
                System.out.println("您输入的密码不正确，请您重新输入！");
            }
        }
    }

    /**
     * 转账功能
     * @param acc 自己的账户对象
     * @param sc 扫描器
     * @param accounts 全部账户的集合
     */
    private static void transferMoney(Account acc, Scanner sc, ArrayList<Account> accounts) {
        System.out.println("===============用户转账操作===============");
        //1.判断系统中是否有两个账户
        if(accounts.size()<2){
            System.out.println("当前系统中，不足两个账户，不能进行转账，请去开户");
            return;
        }

        //2.判断自己的账户是否有钱
        if(acc.getBalance()==0){
            System.out.println("对不起，您的账户余额为0元，无法进行转账操作");
            return;
        }

        while (true) {
            //3.真正开始转账
            System.out.println("请您输入对方账户的卡号：");
            String cardId=sc.next();

            //判断该卡号是否是自己的卡号
            if(cardId.equals(acc.getCardId())){
                System.out.println("对不起，您无法给自己转账");
                continue;//结束当前循环
            }

            //判断卡号是否存在
            Account account=getAccountByCardId(cardId,accounts);
            if(account==null){
                System.out.println("对不起，您输入的卡号不存在，请您重新输入！");
            }else{
                //卡号存在，继续认证该对象的姓氏
                String userName=account.getUserName();
                String tip="*"+userName.substring(1);
                System.out.println("请您输入[" + tip + "]的姓氏");
                String preName=sc.next();

                //认证姓氏是否输入正确
                if(userName.startsWith(preName)){
                    while (true) {
                        //认证通过，开始转账
                        System.out.println("认证成功！");
                        System.out.println("请您输入转账金额：");
                        double money=sc.nextDouble();
                        //判断金额是否足够
                        if(money>acc.getBalance()){
                            System.out.println("对不起，您余额不足，您最多可以转账：" + acc.getBalance());
                        }else{
                            acc.setBalance(acc.getBalance()-money);
                            account.setBalance(account.getBalance()+money);
                            System.out.println("转账成功！您的账户还剩余：" + acc.getBalance() + "元");
                            return;//结束转账方法
                        }
                    }
                }else{
                    System.out.println("对不起，您输入的信息有误");
                }
            }
        }

    }

    /**
     * 取钱功能
     * @param acc 当前账户对象
     * @param sc 扫描器
     */
    private static void drawMoney(Account acc, Scanner sc) {
        System.out.println("===============用户取钱操作===============");
        //1.判断账户中是否有100元
        if(acc.getBalance()<100){
            System.out.println("对不起，当前账户余额不足100元，无法取钱");
            return;
        }

        //2.提示用户输入取钱金额
        System.out.println("请您输入取款金额：");
        double money=sc.nextDouble();

        //3.判断这个金额是否满足要求
        if(money>acc.getQuotaMoney()){
            System.out.println("对不起，您当前的取款金额超过了每次取现额度，每次最多可取" + acc.getQuotaMoney() + "元");
            return;
        }else{
            //没有超过每次取现额度
            //4.判断是否超过账户余额
            if(money>acc.getBalance()){
                System.out.println("余额不足，您账户目前的余额是：" + acc.getBalance() + "元");
                return;
            }else{
                //可以取钱了
                System.out.println("恭喜您，取钱" + money + "元成功！");
                //更新余额
                acc.setBalance(acc.getBalance()-money);
                //取钱结束，展示当前账户信息
                showAccount(acc);
                return;//结束取钱方法
            }
        }
    }

    /**
     * 存钱改变
     * @param acc 当前账户对象
     * @param sc 扫描器
     */
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("===============用户存钱操作===============");
        System.out.println("请您输入存款金额：");
        double money=sc.nextDouble();

        //*****更新账户余额：原来的钱+新存入的钱
        acc.setBalance(acc.getBalance()+money);
        //*****因为acc传的是地址没有必要再把该账户对象放到集合中去
        System.out.println("恭喜您，存钱成功，当前账户信息如下：");
        showAccount(acc);
    }

    /**
     * 展示当前账户的信息
     * @param acc 当前账户
     */
    private static void showAccount(Account acc) {
        System.out.println("===============当前账户信息如下===============");
        System.out.println("卡号：" + acc.getCardId());
        System.out.println("户主:" + acc.getUserName());
        System.out.println("余额:" + acc.getBalance());
        System.out.println("每次取现额度:" + acc.getQuotaMoney());
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
        System.out.println("恭喜您，" + userName + appellation + "，您已开户成功，" + "您的卡号是：" + cardId);
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
