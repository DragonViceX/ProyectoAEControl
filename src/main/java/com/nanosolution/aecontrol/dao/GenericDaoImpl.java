/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nanosolution.aecontrol.dao;


import com.nanosolution.aecontrol.util.HibernateUtil;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.criterion.Projections;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ThreadLocalSessionContext;
/**
 * @author Jessica
 * @param <T>
 * @param <E>
 */
public class GenericDaoImpl<T, E extends Serializable> implements GenericDao<T, E> {
    private final Class<T> clazz;
    protected Session session;
    protected Transaction tx;

    /**
     *
     */
    @SuppressWarnings("unchecked")
    
    public GenericDaoImpl() {
        this.clazz = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass())
                            .getActualTypeArguments()[0];
        
        this.session = HibernateUtil.getSessionFactory().openSession();
        ThreadLocalSessionContext.bind(session);
    }
    
    public E create(T entity) {
        E id;
        try {
            startOperation();
            id = (E) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            id = null;
            tx.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
        
        return  id;
    }
    
    public void delete(T entity) {
        try {
            startOperation();
            session.delete(entity);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        }  finally {
            if (session.isOpen()) {
                session.close();
            }
        } 
        
    }
    
    @SuppressWarnings("unchecked")
    public void update(T entity) {
        try {
            startOperation();
            session.merge(entity);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        }  finally {
            if (session.isOpen()) {
                session.close();
            }
        }

        //HibernateUtil.getSession().saveOrUpdate(entity);
        //entity = (T) HibernateUtil.getSession().merge(entity);
    }
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        List <T> list;
        try {
            startOperation();
            list = session.createCriteria(clazz).list();
            tx.commit();
        } catch (HibernateException e) {
            list = null ;
            tx.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        } 
        
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<T> findList(int pageNo, int pageSize) {
        List <T> list;
        try {
            startOperation();
            list = session.createCriteria(clazz)
                          .setFirstResult((pageNo - 1) * pageSize)
                          .setMaxResults(pageSize)
                          .list();
            tx.commit();
        } catch (HibernateException e) {
            list = null ;
            tx.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }  

        return list;
    }
   
    @SuppressWarnings("unchecked")
    public T findById(Serializable id) {
        T obj;
        try {
            startOperation();
            obj = (T) session.load(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            obj = null;
            tx.rollback();
            throw e;
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }       
        return  obj;
    }

    protected void startOperation() throws HibernateException {
        
        session = HibernateUtil.getSession();        
        tx = session.beginTransaction();
        
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public int getCountOfAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}