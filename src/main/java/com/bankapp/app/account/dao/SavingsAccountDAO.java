package com.bankapp.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankapp.app.account.SavingsAccount;
import com.bankapp.app.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	SavingsAccount updateAccount(SavingsAccount savingsAccount) throws ClassNotFoundException, SQLException;
	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException;
	void commit() throws SQLException;
	List<SavingsAccount> getAllAccountsSortedByNames() throws ClassNotFoundException, SQLException;
	List<SavingsAccount> getAllSavingsAccountsSortedByRange(double minimum,
			double maximum) throws ClassNotFoundException, SQLException;
	
	
}
