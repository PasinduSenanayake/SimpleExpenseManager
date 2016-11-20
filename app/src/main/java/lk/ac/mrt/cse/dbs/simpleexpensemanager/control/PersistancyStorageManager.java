package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Pasindu on 11/15/2016.
 */
public class PersistancyStorageManager extends ExpenseManager{
    private Context context ;
    private DBHelper mydb;
    private ArrayList<String> getAccountDb;
    private ArrayList<String> getTransactionDb;
    public PersistancyStorageManager(Context passedContext) {
        context = passedContext;
        setup();
    }
    public void createAccounts()
    {
        getAccountDb=mydb.getAllAccounts();
        for (int i =0; i<getAccountDb.size();i=i+4) {
            Account tempAccount = new Account(getAccountDb.get(i), getAccountDb.get(i+1), getAccountDb.get(i+2), Double.parseDouble(getAccountDb.get(i+3)));
            getAccountsDAO().addAccount(tempAccount);
        }
    }


    public void createLogs() throws InvalidAccountException {
        getTransactionDb=mydb.getAllTransactions();
        for (int i =0; i<getTransactionDb.size();i=i+6) {
            String selectedAccount = getTransactionDb.get(i);
            String day = getTransactionDb.get(i+1);
            String month = getTransactionDb.get(i+2);
            String year = getTransactionDb.get(i+3);
            String type = getTransactionDb.get(i+4);
            String amountStr = getTransactionDb.get(i+5);

            updateAccountBalance(selectedAccount, Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year), ExpenseType.valueOf(type.toUpperCase()), amountStr);
        }
        }
    //Update logs
    public void updateLog(String accountNo, int day, int month, int year, ExpenseType expenseType,
                          String amount)
    { String type = expenseType.toString();
        mydb.insertLog(accountNo, Integer.toString(day), Integer.toString(month),Integer.toString(year),type,Double.parseDouble(amount));

    }

    //update new Accounts
    public void updateNewAccount( String  accountNumStr,String bankNameStr,String accountHolderStr,double initialBalance)
    {
         mydb.insertAccount(accountNumStr, bankNameStr, accountHolderStr,initialBalance);
    }

    public void setup(){



        TransactionDAO inMemoryTransactionDAO = new InMemoryTransactionDAO();
        setTransactionsDAO(inMemoryTransactionDAO);

        AccountDAO inMemoryAccountDAO = new InMemoryAccountDAO();
        setAccountsDAO(inMemoryAccountDAO);


    //Create database Instance
        mydb = new DBHelper(context);
        //get Accounts from DB
        createAccounts();

        try {
            //get Logs from DB
            createLogs();
        } catch (InvalidAccountException e) {
            e.printStackTrace();
        }
        /*** End ***/
    }


}
