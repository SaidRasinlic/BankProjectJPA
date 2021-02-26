package app.bank.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * CRUD
 *
 * <li> Create
 * <li> Retrieve
 * <li> Update
 * <li> Delete
 * <p>
 * SessionFactory -> EntityManagerFactory
 * <p>
 * Session -> EntityManager
 *
 * @author Grupa2
 */
public abstract class AbstractBankAccount {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY
            = Persistence.createEntityManagerFactory("BankProjectJPAPU");

    public void save() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(this);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void update() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public BankAccount get() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            BankAccount bankAccount = (BankAccount) entityManager.find(BankAccount.class, getThis().getAccountNumber());
            entityManager.getTransaction().commit();
            return bankAccount;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(this);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public BankAccount getThis() {
        return (BankAccount) this;
    }

    public static List<BankAccount> loadAll() {
        try {
            EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
            //HQL: from BankAccount
            //NAMED query: BankAccount.findAll
            entityManager.getTransaction().begin();
            Query query = entityManager.createNamedQuery("BankAccount.findAll");
            entityManager.getTransaction().commit();
            List<BankAccount> bankAccounts = query.getResultList();
            return bankAccounts;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean transferMoney(BankAccount fromAccount, BankAccount toAccount, double amount) {
        if (fromAccount == null || toAccount == null) {
            return false;
        }
        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            return false;
        }
        if (amount < 0) {
            return false;
        }
        try {
            EntityManager session = ENTITY_MANAGER_FACTORY.createEntityManager();
            session.getTransaction().begin();
            Object fromBankAccountObj = session.find(BankAccount.class, fromAccount.getAccountNumber());
            if (fromBankAccountObj == null) {
                return false;
            }
            BankAccount fromBankAccount = (BankAccount) fromBankAccountObj;
            if (fromBankAccount.getAmount() < amount) {
                return false;
            }
            Object toBankAccountObj = session.find(BankAccount.class, toAccount.getAccountNumber());
            if (toBankAccountObj == null) {
                return false;
            }
            BankAccount toBankAccount = (BankAccount) toBankAccountObj;
            //emir racun - 1000
            fromBankAccount.setAmount(fromBankAccount.getAmount() - amount);
            toBankAccount.setAmount(toBankAccount.getAmount() + amount);
            session.merge(fromBankAccount);
            session.merge(toBankAccount);
            session.getTransaction().commit();
            return true;
        } catch (Exception exc) {
            throw new RuntimeException(exc.getMessage());
        }
    }
}
