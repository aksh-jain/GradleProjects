package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

	AccountRepository accountRepository;
	
	
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	@Override
	public Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException {
		if(amount<500)
		{
			throw new InsufficientInitialBalanceException();
		}
		
		Account account = new Account();
		
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account)){
			return account;
		}
		return null;
	}

	@Override
	public int showBalance(int accountNumber) throws InvalidAccountNumberException {

		Account account = accountRepository.searchAccount(accountNumber);
		
		if(account == null){
			throw new InvalidAccountNumberException();
		}
		
		return account.getAmount();
	}

	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAccountNumberException {

		Account account = accountRepository.searchAccount(accountNumber);
		
		if(account == null){
			throw new InvalidAccountNumberException();
		}

		account.setAmount(account.getAmount()+amount);
		return account.getAmount();
	}

	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InvalidAccountNumberException, InsufficientBalanceException {
		
		Account account = accountRepository.searchAccount(accountNumber);
		
		if(account == null){
			throw new InvalidAccountNumberException();
		}
		
		int balance = account.getAmount();
		
		if(amount>balance){
			throw new InsufficientBalanceException();
		}
		balance = balance - amount;
		account.setAmount(balance);
		return account.getAmount();
	}

}
