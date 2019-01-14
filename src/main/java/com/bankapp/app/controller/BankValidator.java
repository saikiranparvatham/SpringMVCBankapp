package com.bankapp.app.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bankapp.app.account.SavingsAccount;

@Component
public class BankValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		SavingsAccount savingsaccount = (SavingsAccount)target;
		
		if(savingsaccount.getBankAccount().getAccountHolderName().length()<2)
		{
			errors.rejectValue("bankAccount.accountHolderName", "bankAccount.accountHolderName.length","Employee Name must have 2 or more characters");
		}
		
	}

}
