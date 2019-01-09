package com.bankapp.app.account.service;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.bankapp.app.account.SavingsAccount;

@Aspect
@Component
public class ValidateAccount {
	Logger logger = Logger.getLogger(ValidateAccount.class.getName());
	
	@Around("execution(* com.bankapp.app.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void withdrawValidation(ProceedingJoinPoint pjp) throws Throwable {
		Object[] param=pjp.getArgs();
		SavingsAccount savingsAccount = (SavingsAccount)param[0];
		if(savingsAccount==null) {
			logger.warning("Account number doesnot exists!!");
		}
		double currentBalance = savingsAccount.getBankAccount().getAccountBalance();
		double amount = (Double)param[1];
		if (amount > 0 && currentBalance >= amount) {
			
			logger.info("amount deducted from object" );
			pjp.proceed();
			logger.info("success with aspect withdraw");
		} else {
			logger.warning("Withdraw amount should begreater than 0 and amount should be greater than account balance ");
		}
	}
	
	@Around("execution(* com.bankapp.app.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void depositValidation(ProceedingJoinPoint pjp) throws Throwable {
	
		Object[] param=pjp.getArgs();
		SavingsAccount savingsAccount = (SavingsAccount)param[0];
		if(savingsAccount==null) {
			logger.warning("Account number doesnot exists!!");
		}
		
		double amount = (Double)param[1];
		
		if (amount > 0) {
			
			pjp.proceed();
			logger.info("success with aspect deposit");
		} else {
			logger.warning("Withdraw amount should begreater than 0 and amount should be greater than account balance ");
		}
	
}
}
