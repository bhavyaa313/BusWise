package buswise.dao;

import buswise.model.EmailLogs;
import buswise.model.Notifications;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;

@Repository
public class NotificationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    @Transactional
    public void NotificationSave(Notifications notifications) {
        template.save(notifications);
    }

    @Transactional
    public void EmailLogsSave(EmailLogs emailLogs) {
        template.save(emailLogs);
    }



}
