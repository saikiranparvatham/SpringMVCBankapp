package com.bankapp.app.account.service;


import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.app.account.SavingsAccount;
import com.bankapp.app.account.dao.SavingsAccountDAO;

import com.bankapp.app.account.factory.AccountFactory;
import com.bankapp.app.account.util.DBUtil;
import com.bankapp.app.exception.AccountNotFoundException;
import com.bankapp.app.exception.InsufficientFundsException;
import com.bankapp.app.exception.InvalidInputException;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;
	
	
	public SavingsAccountServiceImpl() {
		factory = AccountFactory.getInstance();
		
		
	}

	Logger logger = Logger.getLogger(ValidateAccount.class.getName());
	@Override
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		return savingsAccountDAO.createNewAccount(account);
		
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	@Override
	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();

	}
	
	
	@Override
	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			logger.info("this is after calling updatebalance");
			//savingsAccountDAO.commit();
		 
	}

	
	
	@Transactional(rollbackForClassName= {"Throwable"})
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		
			
			deposit(receiver, amount);
			withdraw(sender, amount);
		
	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount savingsAccount) throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.updateAccount(savingsAccount);
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.deleteAccount(accountNumber);
		
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccountsSortedByNames() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllAccountsSortedByNames();
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccountsSortedByRange(
			double minimum, double maximum) throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccountsSortedByRange(minimum,maximum);
	
	}

	

}
