package app;

import app.bank.model.BankAccount;

public class Application {

    public static void main(String[] args) {
        
        BankAccount bankAccount = new BankAccount();
        
        System.out.println(BankAccount.loadAll());
        
       /*bankAccount.setAccountNumber("987");
        bankAccount.setAmount(10000.0);
        bankAccount.save();
        
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountNumber("789");
        bankAccount1.setAmount(900.0);
        bankAccount1.save(); */
    }
}
