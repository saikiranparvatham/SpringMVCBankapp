package com.bankapp.app.account.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankapp.app.account.SavingsAccount;
import com.bankapp.app.account.service.SavingsAccountService;
import com.bankapp.app.account.util.DBUtil;
import com.bankapp.app.exception.AccountNotFoundException;
@Component
public class AccountCUI {
	private static Scanner scanner = new Scanner(System.in);
	
	@Autowired
	private SavingsAccountService savingsAccountService;
	
	public void setSavingsAccountService(SavingsAccountService savingsAccountService)
	{
		this.savingsAccountService=savingsAccountService;
	}

	public void start() {
		
		do {
			System.out.println("****** Welcome to Money Money Bank********");
			System.out.println("1. Open New Savings Account");
			System.out.println("2. Update Account");
			System.out.println("3. Close Account");
			System.out.println("4. Search Account");
			System.out.println("5. Withdraw");
			System.out.println("6. Deposit");
			System.out.println("7. FundTransfer");
			System.out.println("8. Check Current Balance");
			System.out.println("9. Get All Savings Account Details");
			System.out.println("10. Sort Accounts");
			System.out.println("11. Exit");
			System.out.println();
			System.out.println("Make your choice: ");
			
			int choice = scanner.nextInt();
			
			performOperation(choice);
			
		} while(true);
	}

	private void performOperation(int choice) {
		switch (choice) {
		case 1:
			acceptInput("SA");
			break;	
		case 2:
			updateAccount();
			break;
		case 3:
			closeAccount();
			break;	
		case 4:
			searchAccount();
			break;	
		case 5:
			withdraw();
			break;
		case 6:
			deposit();
			break;
		case 7:
			fundTransfer();
			break;
		case 8:
			getCurrentBalance();
			break;
		case 9:
			showAllAccounts();
			break;
		case 10:
			sortAccounts();
			break;
		case 11:
			try {
				DBUtil.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.exit(0);
			break;
		default:
			System.err.println("Invalid Choice!");
			break;
		}
		
	}

	private void updateAccount() {
		System.out.println("Enter the account number to update :");
		int accountNumber = scanner.nextInt();
		int choice1;
		
		do
		{
			System.out.println("Select the field that you want to update:");
			System.out.println("1.Name \n2.isSalary \n3.Exit");
			choice1 = scanner.nextInt();
			SavingsAccount savingsAccount;
			try {
				savingsAccount = savingsAccountService.getAccountById(accountNumber);
				String account_hn=savingsAccount.getBankAccount().getAccountHolderName();
				boolean salary=savingsAccount.isSalary();
				switch(choice1)
				{
				case 1:
					System.out.println("Enter your Full Name: ");
					account_hn = scanner.nextLine();
					account_hn = scanner.nextLine();
					savingsAccount.getBankAccount().setAccountHolderName(account_hn);
					savingsAccountService.updateAccount(savingsAccount);
					break;
					
				case 2:
					System.out.println("Salaried?(y/n): ");
					salary = scanner.next().equalsIgnoreCase("n")?false:true;
					savingsAccount.setSalary(salary);
					savingsAccountService.updateAccount(savingsAccount);
					break;
					
				case 3:
					break;
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (AccountNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}while(choice1!=3);
		
	}

	private void sortAccounts() {
		System.out.println("Select the criteria on which you want to sort the accounts:");
		System.out.println("1.Account Names\n2.Range of account balance\n");
		int choiceSort = scanner.nextInt();
		
		switch(choiceSort)
		{
		case 1:
			List<SavingsAccount> savingsAccounts;
			try {
				savingsAccounts = savingsAccountService.getAllSavingsAccountsSortedByNames();
				for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			} 
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case 2:
			List<SavingsAccount> savingsAccountsOrderedByRange;
			System.out.println("Enter the range :\nenter the maximum amount :");
			double maximum = scanner.nextDouble();
			System.out.println("enter the minimum amount :");
			double minimum = scanner.nextDouble();
					
			try {
				savingsAccountsOrderedByRange = savingsAccountService.getAllSavingsAccountsSortedByRange(minimum,maximum);
				for (SavingsAccount savingsAccount : savingsAccountsOrderedByRange) {
				System.out.println(savingsAccount);
			} 
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}		
			
		
		}

	private  void searchAccount() {
		System.out.println("Enter the account number to retrieve the details:");
		int accountNumber = scanner.nextInt();
		SavingsAccount savingsAccount;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			String result = savingsAccount.getBankAccount().toString();
			System.out.println(result);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void getCurrentBalance() {
		System.out.println("Enter the account number to check balance:");
		int accountNumber = scanner.nextInt();
		
		try {
			SavingsAccount savingsAccount = savingsAccountService.getAccountById(accountNumber);
			System.out.println(savingsAccount.getBankAccount().getAccountBalance());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	private void closeAccount() {
		System.out.println("enter the account number that you want to close:");
		int accountNumber = scanner.nextInt();
			try {
				savingsAccountService.deleteAccount(accountNumber);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
	}

	private void fundTransfer() {
		System.out.println("Enter Account Sender's Number: ");
		int senderAccountNumber = scanner.nextInt();
		System.out.println("Enter Account Receiver's Number: ");
		int receiverAccountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		try {
			SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
			SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
			savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deposit() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.deposit(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void withdraw() {
		System.out.println("Enter Account Number: ");
		int accountNumber = scanner.nextInt();
		System.out.println("Enter Amount: ");
		double amount = scanner.nextDouble();
		SavingsAccount savingsAccount = null;
		try {
			savingsAccount = savingsAccountService.getAccountById(accountNumber);
			savingsAccountService.withdraw(savingsAccount, amount);
			DBUtil.commit();
		} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (Exception e) {
			try {
				DBUtil.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static void sortMenu(String sortWay) {
		do {
			System.out.println("+++++Ways of Sorting+++++++");
			System.out.println("1. Account Number");
			System.out.println("2. Account Holder Name");
			System.out.println("3. Account Balance");
			System.out.println("4. Exit from Sorting");
			
			int choice = scanner.nextInt();
			
		}while(true);
		
	}

	private void showAllAccounts() {
		List<SavingsAccount> savingsAccounts;
		try {
			savingsAccounts = savingsAccountService.getAllSavingsAccount();
			for (SavingsAccount savingsAccount : savingsAccounts) {
				System.out.println(savingsAccount);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void acceptInput(String type) {
		if(type.equalsIgnoreCase("SA")) {
			System.out.println("Enter your Full Name: ");
			String accountHolderName = scanner.nextLine();
			accountHolderName = scanner.nextLine();
			System.out.println("Enter Initial Balance(type na for Zero Balance): ");
			String accountBalanceStr = scanner.next();
			double accountBalance=0.0;
			if(!accountBalanceStr.equalsIgnoreCase("na")) {
				accountBalance = Double.parseDouble(accountBalanceStr);
			}
			System.out.println("Salaried?(y/n): ");
			boolean salary = scanner.next().equalsIgnoreCase("n")?false:true;
			createSavingsAccount(accountHolderName,accountBalance, salary);
		}
	}

	private void createSavingsAccount(String accountHolderName, double accountBalance, boolean salary) {
		try {
			
			savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}



