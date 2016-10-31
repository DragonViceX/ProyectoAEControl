/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nanosolution.aecontrol.bean;


import com.nanosolution.aecontrol.dao.EquipoDaoImpl;
import com.nanosolution.aecontrol.dao.TipoEquipoDaoImpl;
import com.nanosolution.aecontrol.model.Equipo;
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

public class EquipoBean {

    private Equipo equipo;
    private List<Equipo> LEquipo;
    private List<Equipo> filteredEquipos;
    private List<SelectItem> tipoequipo;
    private int idtipo_equipo;

    /**
     * Creates a new instance of PersonaBean
     */
    public EquipoBean() {
        equipo = new Equipo();
        tipoequipo = new ArrayList();

    }

    @PostConstruct
    public void init() {
        EquipoDaoImpl equipodao = new EquipoDaoImpl();
        LEquipo = equipodao.findAll();
        tipoequipo = new ArrayList();

        for (Iterator iterador = listaTipoEquipo().listIterator(); iterador.hasNext();) {
            Tipoequipo p = (Tipoequipo) iterador.next();
            tipoequipo.add(new SelectItem(p.getId(), p.getNombre()));
        }

    }

    public List<SelectItem> getTipoequipo() {
        return tipoequipo;
    }

    public void setTipoequipo(List<SelectItem> tipoequipo) {
        this.tipoequipo = tipoequipo;
    }

    public int getIdtipo_equipo() {
        return idtipo_equipo;
    }

    public void setIdtipo_equipo(int idtipo_equipo) {
        this.idtipo_equipo = idtipo_equipo;
    }

    public List<Equipo> getFilteredEquipos() {
        return filteredEquipos;
    }

    public void setFilteredEquipos(List<Equipo> filteredEquipos) {
        this.filteredEquipos = filteredEquipos;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<Equipo> getLEquipo() {
        return LEquipo;
    }

    public void setLEquipo(List<Equipo> LEquipo) {
        this.LEquipo = LEquipo;
    }

    /**
     * Registra un equipo en la base de datos.
     *
     */
    public void registrar() {
        EquipoDaoImpl equipodao = new EquipoDaoImpl();
        if (equipo.getCodigo().equals("")) {
            TipoEquipoDaoImpl tipoequipodao = new TipoEquipoDaoImpl();
            equipo.setTipoequipo(tipoequipodao.getTipoEquipoId(idtipo_equipo));
            equipo.setCantidadStock(equipo.getCantidad());
            equipodao.create(equipo);
            equipo = new Equipo();
            init();
            FacesMessage msg = new FacesMessage("Equipo creado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            if (validar(equipo.getCodigo())) {
                FacesMessage msg = new FacesMessage("El codigo de equipo ya existe");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                TipoEquipoDaoImpl tipoequipodao = new TipoEquipoDaoImpl();
                equipo.setTipoequipo(tipoequipodao.getTipoEquipoId(idtipo_equipo));
                equipo.setCantidadStock(equipo.getCantidad());
                equipodao.create(equipo);
                equipo = new Equipo();
                init();
                FacesMessage msg = new FacesMessage("Equipo creado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    /**
     * Valida que no se repita el codigo de un Equipo, ya que debe ser unico.
     *
     * @param n String codigo del equipo.
     * @return boolean devuelve una bandera.
     *
     */
    public boolean validar(String n) {
        boolean b = false;
        for (Iterator iterador = LEquipo.listIterator(); iterador.hasNext();) {
            Equipo p = (Equipo) iterador.next();
            if (p.getCodigo().equals(n)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * Actualiza un equipo en la base de datos.
     *
     */
    public void actualizar() {

        EquipoDaoImpl equipodao = new EquipoDaoImpl();
        TipoEquipoDaoImpl tipoequipodao = new TipoEquipoDaoImpl();
        equipo.setTipoequipo(tipoequipodao.getTipoEquipoId(idtipo_equipo));
        equipo.setCantidadStock(equipo.getCantidad());
        equipodao.update(equipo);
        FacesMessage msg = new FacesMessage("Equipo actualizado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Elimina un equipo en la base de datos.
     *
     * @param e objeto de tipo Equipo.
     *
     */
    public void eliminar(Equipo e) {
        EquipoDaoImpl equipodao = new EquipoDaoImpl();
            equipodao.delete(e);
            init();
            FacesMessage msg = new FacesMessage("Equipo eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
       

    }

    /**
     * Evento para editar un equipo en la fila de una tabla y la BD.
     *
     * @param event Evento.
     *
     */
    public void onRowEdit(RowEditEvent event) {
        equipo = ((Equipo) event.getObject());
        actualizar();
        equipo = new Equipo();
    }

    /**
     * Evento para cancelar la edicion de un equipo en la fila de una tabla.
     *
     * @param event Evento.
     *
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelado", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * devuelve una lista de todos los tipos de equipo de la BD.
     *
     * @return List<Tipoequipo> retorna una lista de TipoEquipo.
     */
    public List<Tipoequipo> listaTipoEquipo() {
        TipoEquipoDaoImpl tipoequipodao = new TipoEquipoDaoImpl();
        return tipoequipodao.findAll();
    }

    /**
     * Retorna el nombre de un tipo de equipo
     *
     * @param id identificador del tipo de equipo.
     * @return String retorna una cadena con el nombre del tipo de equipo
     *
     */
    public String seleccion(int id) {
        String select;
        Tipoequipo obj = new Tipoequipo();
        for (Iterator iterador = listaTipoEquipo().listIterator(); iterador.hasNext();) {
            Tipoequipo p = (Tipoequipo) iterador.next();
            if (p.getId().equals(id)) {
                obj = p;
            }
        }
        select = obj.getNombre();
        return select;
    }

}
