package buswise.dao;


import buswise.model.Routes;
import buswise.model.SubRoute;
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
public class RoutesDao {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateTemplate template;

    @Transactional
    public void routeSave(Routes routes) {
        template.save(routes);
    }

    @Transactional
    public void routeUpdate(Routes routes) {
        template.update(routes);
    }


    @Transactional
    public void subrouteSave(SubRoute subRoute) {
        template.save(subRoute);
    }

    @Transactional
    public void subrouteUpdate(SubRoute subRoute) {
        template.update(subRoute);
    }

    public List<Routes> getRoutesSearch(String region, int startIndex, int endIndex) {

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Routes> cr = cb.createQuery(Routes.class);
        Root<Routes> root = cr.from(Routes.class);



        if (region.equals("empty")) {
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.equal(root.get("isDeleted"), 0);
            cr.select(root).where(predicates);
        }


             else {
                Predicate isDeleted = cb.like(root.get("source"), "%" + region + "%");
                Predicate source = cb.equal(root.get("isDeleted"), 0);
                Predicate destination = cb.like(root.get("destination"), "%" + region + "%");
                cr.select(root).where(cb.and(source, cb.or(isDeleted, destination)));
            }

        Query<Routes> query = session.createQuery(cr);
        query.setFirstResult(startIndex);
        query.setMaxResults(endIndex);
        List<Routes> list = query.getResultList();
        return list;


    }


    public List<String> getsubRoutes(Routes routes)
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.stop from buswise.model.SubRoute b where b.routeId=:routes and isDeleted=false ";
        Query query = session.createQuery(hqlString);
        query.setParameter("routes", routes);
        List<String> list = query.list();
        return list;


    }

    public List<Integer> distanceSubRoute(Routes routes)
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.distance from buswise.model.SubRoute b where b.routeId=:routes and isDeleted=false";
        Query query = session.createQuery(hqlString);
        query.setParameter("routes", routes);
        List<Integer> list = query.list();
        return list;


    }

    public List<Integer> idSubRoute(Routes routes)
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.id from buswise.model.SubRoute b where b.routeId=:routes and isDeleted=false";
        Query query = session.createQuery(hqlString);
        query.setParameter("routes", routes);
        List<Integer> list = query.list();
        return list;


    }

    public List<Integer> sequeceSubRoute(Routes routes)
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.sequence from buswise.model.SubRoute b where b.routeId=:routes and isDeleted=false";
        Query query = session.createQuery(hqlString);
        query.setParameter("routes", routes);
        List<Integer> list = query.list();
        return list;


    }

    public List<String> getRoutes()
    {
        Session session = sessionFactory.openSession();
        String hqlString = " select b.routeId from buswise.model.Routes b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<String> list = query.list();
        return list;


    }

    public long getRoutesSearchCount(String region) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        Root<Routes> root = cr.from(Routes.class);



        if (region.equals("empty")) {
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.equal(root.get("isDeleted"), 0);
            cr.select(cb.count(root)).where(predicates);
        } else {
            Predicate isDeleted = cb.like(root.get("source"), "%" + region + "%");
            Predicate source = cb.equal(root.get("isDeleted"), 0);
            Predicate destination = cb.like(root.get("destination"), "%" + region + "%");
            cr.select(cb.count(root)).where(cb.and(source, cb.or(isDeleted, destination)));
        }

        Query<Long> query = session.createQuery(cr);
        Long count = query.getSingleResult();
        session.close();
        return count;
    }

    public List<Routes> getRoutesById(int routeId)
    {
        Session session = sessionFactory.openSession();
        String hqlString = "  from buswise.model.Routes b where b.isDeleted=false and b.routeId=:routeId";
        Query query = session.createQuery(hqlString);
        query.setParameter("routeId", routeId);
        List<Routes> list = query.list();
        return list;
    }

    public List<SubRoute> getSubRoutesByRouteID(Routes route)
    {
        Session session = sessionFactory.openSession();
        String hqlString = "  from buswise.model.SubRoute b where b.isDeleted=false and b.routeId=:route";
        Query query = session.createQuery(hqlString);
        query.setParameter("route", route);
        List<SubRoute> list = query.list();
        return list;

    }

    public List<Routes> getRoutesAll()
    {
        Session session = sessionFactory.openSession();
        String hqlString = " from buswise.model.Routes b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<Routes> list = query.list();
        return list;

    }



    public List<SubRoute> getSubRoutesAll()
    {
        Session session = sessionFactory.openSession();
        String hqlString = " from buswise.model.SubRoute b where b.isDeleted=false ";
        Query query = session.createQuery(hqlString);
        List<SubRoute> list = query.list();
        return list;

    }

}
