/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Entities;

import java.io.Serializable;
import java.util.HashSet;
import javax.persistence.*;
import org.hibernate.mapping.Set;

/**
 *
 * @author sereg
 */

@Entity
@Table(name = "T_SELECTION")

public class T_SELECTION_Entity implements Serializable {
    
    @Id
    @Column(name = "SELECTION_ID")
    private Integer id;
    
    @OneToMany
    @JoinTable(name = "SELECTION_ID")
    private java.util.Set<T_PROJECT_has_T_SELECTION_Entity> projsel = new HashSet<T_PROJECT_has_T_SELECTION_Entity>(0);
    public java.util.Set<T_PROJECT_has_T_SELECTION_Entity> getProjSel(){
        return projsel;
    }
    
    @OneToMany
    @JoinTable(name = "SELECTION_ID")
    private java.util.Set<T_DATA_Entity> data = new HashSet<T_DATA_Entity>(0);
    public java.util.Set<T_DATA_Entity> getData(){
        return data;
    }
    
    @OneToMany
    @JoinTable(name = "SELECTION_ID")
    private java.util.Set<T_ANALYSIS_DATA_Entity> analysdata = new HashSet<T_ANALYSIS_DATA_Entity>(0);
    public java.util.Set<T_ANALYSIS_DATA_Entity> getAnalysData(){
        return analysdata;
    }
    
    @Column(name = "NAME", length = 45)
    private String name;
    
    public T_SELECTION_Entity(){    
    }
    
    public T_SELECTION_Entity(String name){
        this.name = name;
    }
    
    public T_SELECTION_Entity(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    
    public T_SELECTION_Entity(Integer id){
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameItem() {
        return name;
    }

    public void setNameItem(String name) {
        this.name = name;
    }
}
