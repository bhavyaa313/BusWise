package buswise.dao;

import buswise.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.transaction.Transactional;

@Repository
public class VerificationDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    public List<VerificationToken> checkMailStatusList(String email) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.VerificationToken where email = :email";
        Query query = session.createQuery(hqlString);
        query.setParameter("email" , email);
        List<VerificationToken> list = query.getResultList();
        session.close();
        return list;

    }



    @Transactional
    public void verificationTokenSave(VerificationToken verificationToken) {
        template.save(verificationToken);
    }

    @Transactional
    public void verificationTokenUpdate(VerificationToken verificationToken) {
        template.update(verificationToken);
    }


    public List<VerificationToken> checkToken(String token) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.VerificationToken where token = :token and validation=true ";
        Query query = session.createQuery(hqlString);
        query.setParameter("token" , token);
        List<VerificationToken> list = query.getResultList();
        session.close();
        return list;

    }


}
