/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nanosolution.aecontrol.dao;


import com.nanosolution.aecontrol.model.Equipo;
import com.nanosolution.aecontrol.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;




/**
 *
 * @author Jessica
 */
public class EquipoDaoImpl extends GenericDaoImpl<Equipo,Integer> implements EquipoDao {
    
    public List<Equipo> getEquipoStock(){
       List <Equipo> list;      
       
        try{
            session= HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=(session.createQuery("from Equipo where cantidad_stock>0")).list();
            tx.commit();
        }catch(HibernateException e){
            
            tx.rollback();
            throw e;
        }
                
        return list;
    }
    
       public Equipo getEquipoId(int id){
       
        List <Equipo> list;
        Equipo c;
       
        try{
            session= HibernateUtil.getSession();
            tx=session.beginTransaction();
            list=(session.createQuery("from Equipo where id_equipo="+id+"")).list();
            tx.commit();
        }catch(HibernateException e){
            
            tx.rollback();
            throw e;
        }
        c=list.get(0);        
        return c;
    }
    
     
    
   
    
}
