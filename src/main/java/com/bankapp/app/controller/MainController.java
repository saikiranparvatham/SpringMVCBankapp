package com.bankapp.app.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.bankapp.app.account.SavingsAccount;
import com.bankapp.app.account.service.SavingsAccountService;
import com.bankapp.app.exception.AccountNotFoundException;


@Controller
public class MainController {

	static boolean counter = true;
	@Autowired
	private SavingsAccountService savingsAccountService;
	
	@Autowired
	BankValidator validation;

	
	public void setSavingsAccountService(SavingsAccountService savingsAccountService)
	{
		this.savingsAccountService=savingsAccountService;
	}
	
	
	
	@RequestMapping("/")
	public String home()
	{
		
		return "home";
	}
	
	@RequestMapping(value="/addnewaccount",method=RequestMethod.GET)
	public String addnewaccount(Model model)
	{
		model.addAttribute("savingsaccount", new SavingsAccount());
		
		return "addnewaccount";
	}
	
	@RequestMapping(value="/addnewaccount",method=RequestMethod.POST)
	public String save(@RequestParam("bankAccount.accountHolderName") String accountHolderName,
			@RequestParam("bankAccount.accountBalance")double accountBalance,@RequestParam("salary") String salary1,Model model,
			@ModelAttribute("savingsaccount")SavingsAccount savingsaccount,BindingResult result) throws ClassNotFoundException, SQLException
	{
		
		boolean salary=salary1.equalsIgnoreCase("y")?true:false;
		
		validation.validate(savingsaccount,result);
		if(result.hasErrors())
		{
			return "addnewaccount";
		}
		
		savingsAccountService.createNewAccount(accountHolderName, accountBalance, salary);
		return "getaccount";
	}
	
	
	@RequestMapping("/search")
	public String search(Model model) {
		
		return "search";
		
	}
	

	
	
	

	@RequestMapping("/getaccount")
	public String getaccount(@RequestParam("accountNumber") int accNum,Model model,@ModelAttribute("savingsaccount") SavingsAccount savingsaccount,BindingResult result) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{

		SavingsAccount account = savingsAccountService.getAccountById(accNum);
		model.addAttribute("account", account);
		return "getaccounts";
		
	}
	
	
	@RequestMapping("/getaccounts")
	public String getaccounts(Model model) throws ClassNotFoundException, SQLException
	{

		List<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
		model.addAttribute("accounts", accounts);
		return "getaccounts";
		
	}
	
	@RequestMapping("/search2")
	public String search2(Model model)
	{
		
		return "search2";
		
	}
	
	@RequestMapping("/updateform")
	public String updateForm(@RequestParam("accountNumber")int accNum,Model model) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{
		
		SavingsAccount account = savingsAccountService.getAccountById(accNum);
		model.addAttribute("account", account);		
		return "updateform";
		
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("accountNumber")int accountNumber,@RequestParam("account_hn")String account_hn,@RequestParam("y") String salary1,Model model,@ModelAttribute("savingsaccount") SavingsAccount savingsaccount,BindingResult result) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{
		SavingsAccount account= savingsAccountService.getAccountById(accountNumber);
		boolean salary=salary1.equalsIgnoreCase("y")?true:false;
		
		account.getBankAccount().setAccountHolderName(account_hn);
		account.setSalary(salary);
		savingsAccountService.updateAccount(account);
		
		
		model.addAttribute("account", account);
		return "getaccounts";
		
	}
	
	@RequestMapping("/close")
	public String close(Model model)
	{
		
		return "search3";
		
	}
	
	@RequestMapping("/closeaccount")
	public RedirectView closeaccount(@RequestParam("accountNumber")int accNum,Model model) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{
		
		
		savingsAccountService.deleteAccount(accNum);
		
		return new RedirectView("getaccounts");		
	}
	
	@RequestMapping("/fundtransfer")
	public String fundTransfer(Model model)
	{
		
		return "fundtransfer";
		
	}
	
	@RequestMapping("/fundtransferaction")
	public RedirectView fundTransferAction(@RequestParam("senderNumber")int senderNum,@RequestParam("receiverNumber")int receiverNum,@RequestParam("amount")double amount,Model model) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{
		
		SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderNum);
		SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverNum);
		savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount);
		
		return new RedirectView("getaccounts");		
	}
	
	@RequestMapping("/deposit")
	public String deposit(Model model)
	{
		
		return "depositform";
		
	}
	
	@RequestMapping("/depositaction")
	public RedirectView depositAction(@RequestParam("accountNumber")int accNum,@RequestParam("amount")double amount,Model model) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{
		
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accNum);
		savingsAccountService.deposit(savingsAccount, amount);
		
		return new RedirectView("getaccount");		
	}
	
	@RequestMapping("/withdraw")
	public String withdraw(Model model)
	{
		
		return "withdrawform";
		
	}
	
	@RequestMapping("/withdrawaction")
	public RedirectView withdrawAction(@RequestParam("accountNumber")int accNum,@RequestParam("amount")double amount,Model model) throws ClassNotFoundException, SQLException, AccountNotFoundException
	{
		
		SavingsAccount savingsAccount = savingsAccountService.getAccountById(accNum);
		savingsAccountService.withdraw(savingsAccount, amount);
		return new RedirectView("getaccount");		
	}
	
	@RequestMapping("/sortByName")
	public String sortByName(Model model)
	{
		try {
			Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
			Set<SavingsAccount> accountSet;
			
			if(counter)
			{
			accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					return arg0.getBankAccount().getAccountHolderName().compareTo
							(arg1.getBankAccount().getAccountHolderName());
				}
			});
			counter = false;
			}
			else
			{
				accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
					@Override
					public int compare(SavingsAccount arg0, SavingsAccount arg1) {
						return arg1.getBankAccount().getAccountHolderName().compareTo
								(arg0.getBankAccount().getAccountHolderName());
					}
				});
				counter = true;
			}
			accountSet.addAll(accounts);
			
			model.addAttribute("accounts", accountSet);
			
			return ("getaccounts");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("getaccounts");

		
	}
	
	@RequestMapping("/sortByBalance")
	public String sortByBalance(Model model)
	{
		try {
			Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
			Set<SavingsAccount> accountSet;
			if(counter)
			{
			accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					return Double.compare(arg0.getBankAccount().getAccountBalance(), arg1.getBankAccount().getAccountBalance());
				}
			});
			counter = false;
			}
			else
			{
				accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					return Double.compare(arg1.getBankAccount().getAccountBalance(), arg0.getBankAccount().getAccountBalance());
					}
				});
				counter = true;
			}
			accountSet.addAll(accounts);
			
			model.addAttribute("accounts", accountSet);
			
			return ("getaccounts");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("getaccounts");
	}
	
	@RequestMapping("/sortByNumber")
	public String sortByNumber(Model model)
	{
		try {
			Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
			Set<SavingsAccount> accountSet;
			if(counter)
			{
			accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					return Integer.compare(arg0.getBankAccount().getAccountNumber(), arg1.getBankAccount().getAccountNumber());
				}
			});
			counter = false;
			}
			else
			{
				accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					return Double.compare(arg1.getBankAccount().getAccountNumber(), arg0.getBankAccount().getAccountNumber());
					}
				});
				counter = true;
			}
			accountSet.addAll(accounts);
			
			model.addAttribute("accounts", accountSet);
			
			return ("getaccounts");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ("getaccounts");
	}
	
	
	
}
