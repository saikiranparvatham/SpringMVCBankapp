package com.bankapp.app.account.service;

import java.sql.SQLException;
import java.util.List;

import com.bankapp.app.account.SavingsAccount;
import com.bankapp.app.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) throws ClassNotFoundException, SQLException;

	SavingsAccount updateAccount(SavingsAccount savingsAccount) throws ClassNotFoundException, SQLException;

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) throws ClassNotFoundException, SQLException;
	void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;
	void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException;

	List<SavingsAccount> getAllSavingsAccountsSortedByNames() throws ClassNotFoundException, SQLException;

	List<SavingsAccount> getAllSavingsAccountsSortedByRange(double minimum,
			double maximum) throws ClassNotFoundException, SQLException;

	
	
}











