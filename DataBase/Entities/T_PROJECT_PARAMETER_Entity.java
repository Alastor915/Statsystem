/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Entities;

import java.io.Serializable;
import javax.persistence.*;



/**
 *
 * @author sereg
 */

@Entity
@Table(name = "T_PROJECT_PARAMETR")

public class T_PROJECT_PARAMETER_Entity implements Serializable{
    
    @Id
    @Column(name = "T_PROJECT_PARAMETR_ID")
    private Integer id;
    
    @Column(name = "NAME", length = 45)
    private String name;
    
    @Column(name = "DEFAULT_VALUE", length = 45)
    private String defval;
    
    public T_PROJECT_PARAMETER_Entity(){    
    }
    
    public T_PROJECT_PARAMETER_Entity(String name, String defval){
        this.name = name;
        this.defval = defval;
    }
    
    public T_PROJECT_PARAMETER_Entity(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    
    public T_PROJECT_PARAMETER_Entity(Integer id, String name, String defval){
        this.id = id;
        this.name = name;
        this.defval = defval;
    }
    
    public T_PROJECT_PARAMETER_Entity(Integer id){
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
    
    public String getDefaultValue(){
        return defval;
    }
    
    public void setDefaultValue(String defval){
        this.defval = defval;
    }
}
