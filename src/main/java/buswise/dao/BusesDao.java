package buswise.dao;

import buswise.model.Buses;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class BusesDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    @Transactional
    public void busSave(Buses buses) {
        template.save(buses);
    }

    @Transactional
    public void busUpdate(Buses buses) {
        template.update(buses);
    }

    public List<Buses> getBusesSearch(String busNumber, int startIndex, int endIndex) {

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Buses> cr = cb.createQuery(Buses.class);
        Root<Buses> root = cr.from(Buses.class);
        if (busNumber.equals("empty")) {
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.equal(root.get("isDeleted"), 0);
            cr.select(root).where(predicates);
        } else {
            Predicate[] predicates = new Predicate[2];
            predicates[0] = cb.like(root.get("busNumber"), "%" + busNumber + "%");
            predicates[1] = cb.equal(root.get("isDeleted"), 0);
            cr.select(root).where(predicates);
        }
        Query<Buses> query = session.createQuery(cr);
        query.setFirstResult(startIndex);
        query.setMaxResults(endIndex);
        List<Buses> list = query.getResultList();
        return list;


    }

    public long getBusesSearchCount(String busNumber) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<Buses> root = cr.from(Buses.class);

        if (busNumber.equals("empty")) {
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.equal(root.get("isDeleted"), 0);
            cr.select(cb.count(root)).where(predicates);
        } else {
            Predicate[] predicates = new Predicate[2];
            predicates[0] = cb.like(root.get("busNumber"), "%" + busNumber + "%");
            predicates[1] = cb.equal(root.get("isDeleted"), 0);
            cr.select(cb.count(root)).where(predicates);
        }

        Query<Long> query = session.createQuery(cr);
        Long count = query.getSingleResult();
        session.close();
        return count;
    }


    public List<Buses> getBusbyId(int id) {
        Session session = sessionFactory.openSession();
        String hqlString = "from buswise.model.Buses b WHERE b.busId=:id ";
        Query query = session.createQuery(hqlString);
        query.setParameter("id", id);
        List<Buses> list = query.list();
        return list;

    }

    public List<String> getBusNumber()
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.busNumber from buswise.model.Buses b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<String> list = query.list();
        return list;


    }


    public List<Buses> getBusAll()
    {
        Session session = sessionFactory.openSession();
        String hqlString = " from buswise.model.Buses b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<Buses> list = query.list();
        return list;

    }

}
