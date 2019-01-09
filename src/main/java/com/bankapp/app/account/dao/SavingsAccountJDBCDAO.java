package com.bankapp.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bankapp.app.account.SavingsAccount;
import com.bankapp.app.exception.AccountNotFoundException;
import com.bankapp.app.mapper.SavingsAccountMapper;
@Repository
@Primary
public class SavingsAccountJDBCDAO implements SavingsAccountDAO  {
	@Autowired
	private JdbcTemplate jdbctemplate;
	
	@Override
	public SavingsAccount createNewAccount(SavingsAccount account)
	{
		jdbctemplate.update("INSERT INTO ACCOUNT VALUES (?,?,?,?,?,?)",new Object[] {account.getBankAccount().getAccountNumber(),account.getBankAccount().getAccountHolderName(),account.getBankAccount().getAccountBalance(),account.isSalary(),null,"SA"});
		return account;
		
	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount savingsAccount) throws ClassNotFoundException, SQLException {
		jdbctemplate.update("UPDATE ACCOUNT SET account_hn=? ,salary=? where account_id=?",
				new Object[] { savingsAccount.getBankAccount().getAccountHolderName(), savingsAccount.salary,savingsAccount.getBankAccount().getAccountNumber() });
		return null;
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return jdbctemplate.queryForObject("SELECT * FROM account where account_id=?", new Object[] { accountNumber },
				new SavingsAccountMapper());
		
	}

	@Override
	public SavingsAccount deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		jdbctemplate.update("DELETE FROM account WHERE account_id=?", new Object[] { accountNumber });
		return null;
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return jdbctemplate.query("SELECT * FROM ACCOUNT", new SavingsAccountMapper());
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException {
		jdbctemplate.update("UPDATE ACCOUNT SET account_bal=? where account_id=?",
				new Object[] { currentBalance, accountNumber });		
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SavingsAccount> getAllAccountsSortedByNames() throws ClassNotFoundException, SQLException {
		return jdbctemplate.query("SELECT * FROM ACCOUNT ORDER BY account_hn", new SavingsAccountMapper());
		
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccountsSortedByRange(double minimum, double maximum)
			throws ClassNotFoundException, SQLException {
		return jdbctemplate.query("SELECT * FROM ACCOUNT WHERE account_bal BETWEEN ? AND ?;", new Object[] {minimum,maximum},new SavingsAccountMapper());
	}
	

}
