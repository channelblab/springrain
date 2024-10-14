package com.channelblab.springrain.test;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-12 17:03
 * @description：
 * @modified By：
 */
public class UtilTest {


    public static void main(String[] args) {
        double loanAmount = 480000; // 贷款金额
        double annualInterestRate = 0.0355; // 年利率
        int loanTerm = 240; // 贷款期限（月）

        double monthlyInterestRate = annualInterestRate / 12; // 月利率
        double monthlyPayment = loanAmount / loanTerm + loanAmount * monthlyInterestRate;

        System.out.println("每月还款金额：" + monthlyPayment);

        double remainingLoanAmount = loanAmount;
        for (int i = 1; i <= loanTerm; i++) {
            double principalPayment = loanAmount / loanTerm;
            double interestPayment = remainingLoanAmount * monthlyInterestRate;
            double totalPayment = principalPayment + interestPayment;
            remainingLoanAmount -= principalPayment;

            System.out.println("第 " + i + " 个月：");
            System.out.println("本金还款：" + principalPayment);
            System.out.println("利息还款：" + interestPayment);
            System.out.println("总还款：" + totalPayment);
            System.out.println("剩余贷款金额：" + remainingLoanAmount);
            System.out.println();
        }
    }


}
