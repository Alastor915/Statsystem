/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Entities;

import javax.persistence.*;

/**
 *
 * @author sereg
 */

@Entity
@Table(name = "T_ANALYSIS_TYPE")

public class T_ANALYSIS_TYPE_Entity{
    
    @Id
    @Column(name = "ANALYSIS_TYPE_ID")
    private Integer id;
    
    @Column(name = "TITLE", length = 45)
    private String title;
    
    @Column(name = "DESCRIPTION", length = 45)
    private String description;
    
    @Column(name = "IS_ACTIVE")
    private Boolean isact;
    
    public T_ANALYSIS_TYPE_Entity(){    
    }
    
    public T_ANALYSIS_TYPE_Entity(String title, String description){
        this.title = title;
        this.description = description;
    }
    
    public T_ANALYSIS_TYPE_Entity(Integer id, String title){
        this.id = id;
        this.title = title;
    }
    
    public T_ANALYSIS_TYPE_Entity(Integer id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }
    
    public T_ANALYSIS_TYPE_Entity(Integer id){
        this.id = id;
    }
    
    public T_ANALYSIS_TYPE_Entity(Integer id, String title, String description, Boolean isact){
        this.id = id;
        this.title = title;
        this.description = description;
        this.isact = isact;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameItem() {
        return title;
    }

    public void setNameItem(String name) {
        this.title = name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public Boolean getIsActive(){
        return isact;
    }
    
    public void setIsActive(Boolean isact){
        this.isact = isact;
    }
}
