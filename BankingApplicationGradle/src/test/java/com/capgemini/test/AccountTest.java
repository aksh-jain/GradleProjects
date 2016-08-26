package com.capgemini.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
public class AccountTest {

	AccountService accountService ;
	Account account;
	int accountNumber;
	
	@Mock
	AccountRepository accountRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
		account = new Account();
		accountNumber = 101;
	}

	/*  create account
	 *  1.when the amount is less than 500 system should throw exception
	 *  2.when the valid info is passed account should be created successfully
	 */
	
	
	@Test(expected = com.capgemini.exceptions.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101, 200);
	}
	
	
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account,accountService.createAccount(101, 5000));
		
	}
	
	
	
	@Test(expected = InvalidAccountNumberException.class)
	public void WhenTheAccountNoisInvalidInShowBalance() throws InvalidAccountNumberException{
		when(accountRepository.searchAccount(accountNumber)).thenReturn(null);
		accountService.showBalance(accountNumber);
		
	}
	
	
	
	@Test
	public void SystemSuccessfullyShowsBalance() throws InvalidAccountNumberException{
		account.setAmount(1000);
		when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
		assertEquals(1000, accountService.showBalance(accountNumber));
		
	}
	
	
	
	@Test(expected = InvalidAccountNumberException.class)
	public void WhenTheAccountNoisInvalidInDepositAmount() throws InvalidAccountNumberException{
		when(accountRepository.searchAccount(accountNumber)).thenReturn(null);
		int amount = 800;
		accountService.depositAmount(accountNumber, amount);
		
	}
	
	
	
	@Test
	public void SystemSuccessfullyDepositsAmount() throws InvalidAccountNumberException{
		account.setAmount(1000);
		int deposit = 500;
		when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
		assertEquals(1500, accountService.depositAmount(accountNumber, deposit));
		
	}
	
	
	@Test(expected = InvalidAccountNumberException.class)
	public void WhenTheAccountNoisInvalidInWithdrawAmount() throws InvalidAccountNumberException, InsufficientBalanceException{
		when(accountRepository.searchAccount(accountNumber)).thenReturn(null);
		int amount = 800;
		accountService.withdrawAmount(accountNumber, amount);
		
	}
	
	
	@Test(expected = InsufficientBalanceException.class)
	public void WhenBalanceIsInsufficientInWithdrawAmount() throws InvalidAccountNumberException, InsufficientBalanceException{
		when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
		account.setAmount(600);
		int amount = 800;
		accountService.withdrawAmount(accountNumber, amount);
		
	}
	
	@Test
	public void SystemSuccessfullyWithdrawAmount() throws InvalidAccountNumberException, InsufficientBalanceException{
		when(accountRepository.searchAccount(accountNumber)).thenReturn(account);
		account.setAmount(1500);
		int WithdrawlAmount = 800;
		assertEquals(700, accountService.withdrawAmount(accountNumber, WithdrawlAmount));
	}
	
	
	
	

}
