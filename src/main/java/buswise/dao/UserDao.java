package buswise.dao;

import buswise.model.Role;
import buswise.model.User;
import buswise.model.UserProfile;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.hibernate.Session;
import javax.transaction.Transactional;


@Repository
public class UserDao {

    @Autowired
    private org.hibernate.SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    @Transactional
    public void userSave(User user) {
        template.save(user);
    }
    @Transactional
    public void userUpdate(User user) {
        template.update(user);
    }

    @Transactional
    public void userProfileSave(UserProfile userProfile) {
        template.save(userProfile);
    }

    @Transactional
    public void userProfileUpdate(UserProfile userProfile) {
        template.update(userProfile);
    }



    public List<User> checkEmail(String mail) {

        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.User A where A.email=:mail";
        org.hibernate.Query query = session.createQuery(hqlString);
        query.setParameter("mail", mail);
        List<User> list = query.list();
        session.close();
        return list;

    }


    public int getUserId(String mail) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT U.userId from buswise.model.User U where U.email=:mail";
        Query query = session.createQuery(hqlString);
        query.setParameter("mail", mail);
        return (int) query.uniqueResult();

    }

    public int getRoleId(int userId) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT U.roleId.roleId from buswise.model.User U where U.userId = :userId";
        Query query = session.createQuery(hqlString);
        query.setParameter("userId", userId);
        return (int) query.uniqueResult();

    }

    public List<UserProfile> userProfiles(int id){
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.UserProfile U where U.userId.id=:id";
        Query query = session.createQuery(hqlString);
        query.setParameter("id", id);
        List<UserProfile> list = query.list();
        session.close();
        return list;
    }


    public List<User> getUserbyUserId(int id){
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.User U where U.userId=:id";
        Query query = session.createQuery(hqlString);
        query.setParameter("id", id);
        List<User> list = query.list();
        session.close();
        return list;
    }

    public List<Role> getRolebyRoleId(int id){
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Role U where roleId=:id";
        Query query = session.createQuery(hqlString);
        query.setParameter("id", id);
        List<Role> list = query.list();
        session.close();
        return list;
    }

    public String getUsernameByuserId(int userId) {
        Session session = sessionFactory.openSession();
        String hqlString = "SELECT concat(U.firstName, ' ', U.lastName) FROM buswise.model.UserProfile U WHERE U.userId.id = :userId";
        Query query = session.createQuery(hqlString);
        query.setParameter("userId", userId);
        String name = (String) query.uniqueResult();
        session.close();
        return name;
    }

}
