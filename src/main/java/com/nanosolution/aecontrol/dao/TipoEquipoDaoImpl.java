/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nanosolution.aecontrol.dao;


import com.nanosolution.aecontrol.model.Tipoequipo;
import com.nanosolution.aecontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Jessica
 */
public class TipoEquipoDaoImpl extends GenericDaoImpl<Tipoequipo,Integer> implements TipoEquipoDao {
    
    public Tipoequipo getTipoEquipoId(int id){
       List <Tipoequipo> list;
       Tipoequipo p;
       
        try{
            session= HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=(session.createQuery("from Tipoequipo where id="+id+"")).list();
            tx.commit();
        }catch(HibernateException e){
            
            tx.rollback();
            throw e;
        }  
        p=list.get(0);        
        return p;
    }
    
}