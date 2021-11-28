package com.thai.service;


import com.thai.model.Track;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrackService implements ITrackService {
    private static SessionFactory sessionFactory;
    private static EntityManager em;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.config.xml")
                    .buildSessionFactory();
            em = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List findAll() {
        TypedQuery<Track> query = em.createQuery("select t from Track t", Track.class);
        return query.getResultList();
    }

    @Override
    public Track findById(Long id) {
        TypedQuery<Track> query = em.createQuery("select t from Track t where t.id=:id", Track.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Track save(Track track) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(track);
            transaction.commit();
            return track;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public Track update(Track track) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Track first = findById(track.getId());
            first.setName(track.getName());
            first.setLink(track.getLink());
            session.saveOrUpdate(track);
            transaction.commit();
            return first;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Track first = findById(id);
            session.delete(first);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null){
                session.close();
            }
        }
    }
}
