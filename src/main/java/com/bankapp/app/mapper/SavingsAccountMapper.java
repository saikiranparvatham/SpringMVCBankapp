package com.bankapp.app.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bankapp.app.account.SavingsAccount;

;

public class SavingsAccountMapper implements RowMapper<SavingsAccount> {

	@Override
	public SavingsAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		int accountNumber = rs.getInt(1);
		String accountHolderName = rs.getString("account_hn");
		double accountBalance = rs.getDouble(3);
		boolean salary = rs.getBoolean("salary");
		SavingsAccount savingsAccount;
		return new SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
	}
}
