package buswise.dao;

import buswise.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class WalletDao {


        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private HibernateTemplate template;

        @Transactional
        public void walletSave(Wallet wallet) {
            template.save(wallet);
        }

        @Transactional
        public void walletUpdate(Wallet wallet) {
            template.update(wallet);
        }


    @Transactional
    public void transactionSave(Transaction transaction) {
        template.save(transaction);
    }

    public Wallet getWalletByUserId(User user) {
        Session session = sessionFactory.openSession();
        try {
            String hqlString = "from  buswise.model.Wallet where user=:user";
            Query<Wallet> query = session.createQuery(hqlString, Wallet.class);
            query.setParameter("user", user);
            List<Wallet> list = query.list();

            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        } finally {
            session.close();
        }
    }

    public double getBalanceByuserId(int userId){
        Session session = sessionFactory.openSession();
        String hqlString = "select balance from buswise.model.Wallet  where user.userId=:userId ";
        Query<Double> query = session.createQuery(hqlString, Double.class);
        query.setParameter("userId", userId);
        double amount = query.uniqueResult();
        return amount;

    }

    public List<Transaction> getTransactionsByUserId(int userId, int startIndex, int endIndex) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Transaction t where  t.wallet.user.userId = :userId order by t.date desc";
        Query<Transaction> query = session.createQuery(hqlString, Transaction.class);
        query.setFirstResult(startIndex);
        query.setMaxResults(endIndex);
        query.setParameter("userId", userId);
        List<Transaction> transactions = query.getResultList();
        session.close();
        return transactions;
    }


    public long getTransactionCountByUserId(int userId) {
        Session session = sessionFactory.openSession();
        String hqlString = "select count(*) from buswise.model.Transaction where wallet.user.userId = :userId";
        Query<Long> query = session.createQuery(hqlString, Long.class);
        query.setParameter("userId", userId);
        long count = query.uniqueResult();
        session.close();
        return count;
    }

}
