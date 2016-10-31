/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nanosolution.aecontrol.bean;


import com.nanosolution.aecontrol.dao.TipoEquipoDaoImpl;
import com.nanosolution.aecontrol.model.Tipoequipo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Jessica
 */
@ManagedBean
@RequestScoped

public class TipoEquipoBean {

    private Tipoequipo tipo_equipo;
    private List <Tipoequipo> LTipoEquipo;
    private List <SelectItem> tipos;
    private int cod;
     private List<Tipoequipo> filteredTipoE; 
 
   
   

    /**
     * Creates a new instance of TipoEquipoBean
     */
    public TipoEquipoBean() {
        tipo_equipo= new Tipoequipo(); 
        tipos=new ArrayList(); 
        
    }
    
  
    
    @PostConstruct
    public void init() {  
        TipoEquipoDaoImpl tipoequipodao=new TipoEquipoDaoImpl(); 
        LTipoEquipo=tipoequipodao.findAll();        
       
        
              
    }



    public List<SelectItem> getTipos() {
        return tipos;
    }

    public void setTipos(List<SelectItem> tipos) {
        this.tipos = tipos;
    }

    public List<Tipoequipo> getFilteredTipoE() {
        return filteredTipoE;
    }

    public void setFilteredTipoE(List<Tipoequipo> filteredTipoE) {
        this.filteredTipoE = filteredTipoE;
    }

 
    
    
    

    public Tipoequipo getTipo_equipo() {
        return tipo_equipo;
    }

    public void setTipo_equipo(Tipoequipo tipo_equipo) {
        this.tipo_equipo = tipo_equipo;
    }

    public List<Tipoequipo> getLTipoEquipo() {
        return LTipoEquipo;
    }

    public void setLTipoEquipo(List<Tipoequipo> LTipoEquipo) {
        this.LTipoEquipo = LTipoEquipo;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
    
    

        
    public void registrar(){
        TipoEquipoDaoImpl tipoequipodao=new TipoEquipoDaoImpl();
        if(validar(tipo_equipo.getNombre())){
          FacesMessage msg=new FacesMessage("Ya existe este Tipo de Equipo");
          FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
          tipoequipodao.create(tipo_equipo);
          tipo_equipo=new Tipoequipo();
          init();
          FacesMessage msg=new FacesMessage("Tipo de Equipo creado");
          FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
 
    
     
    public void actualizar(){
        
        TipoEquipoDaoImpl tipoequipodao=new TipoEquipoDaoImpl();
        tipoequipodao.update(tipo_equipo);
        init();
        FacesMessage msg=new FacesMessage("Tipo de Equipo actualizado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    
    
    
    public void eliminar(Tipoequipo te){         
     TipoEquipoDaoImpl tipoequipodao=new TipoEquipoDaoImpl();
     tipoequipodao.delete(te);
     init();
     FacesMessage msg=new FacesMessage("Tipo de Equipo eliminado");
     FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    
    public Tipoequipo getTipoEquipo(int id){
        
         TipoEquipoDaoImpl tipoequipodao=new TipoEquipoDaoImpl();
         return tipoequipodao.getTipoEquipoId(id);         
    }
    
    public boolean validar(String n) {
      boolean b=false;
       for ( Iterator iterador = LTipoEquipo.listIterator(); iterador.hasNext(); ) {
             Tipoequipo p = (Tipoequipo) iterador.next();
             if(p.getNombre().equals(n)){
                 b=true;
             }            
       }
       return b;     
        
   }
    
    
     public void onRowEdit(RowEditEvent event) {       
        tipo_equipo=((Tipoequipo) event.getObject());
        actualizar();   
        tipo_equipo=new Tipoequipo();
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelado","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }    
    
  
    
    
        
    }
    
    
    
    

