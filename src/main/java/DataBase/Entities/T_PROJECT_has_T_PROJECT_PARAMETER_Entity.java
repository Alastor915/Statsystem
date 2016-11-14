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
@Table(name = "T_PROJECT_has_T_PROJECT_PARAMETER")

class T_PROJECT_has_T_PROJECT_PARAMETER_Entity implements Serializable {
   
    @Id
    @Column(name="id")
    private Integer id;
    
    public Integer getId(){
        return this.id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    @Column(name = "PROJECT_ID")
    private Integer projectid;
    
    public Integer getProjectId(){
        return this.projectid;
    }
    
    public void setProjectId(Integer projectid){
        this.projectid = projectid;
    }
    
    @Column(name = "PROJECT_PARAMETER_ID")
    private Integer selectionid;
    
    public Integer getSelectionId(){
        return this.selectionid;
    }
    
    public void setSelectionId(Integer selectionid){
        this.selectionid = selectionid;
    }
    
    @Column(name = "VALUE", length = 45)
    private String value;
    
    public String getValue(){
        return value;
    }
    
    public void setValue(String value){
        this.value = value;
    }
    
    @ManyToOne
    @JoinTable(name = "PROJECT_ID")
    private T_PROJECT_Entity project;
    public T_PROJECT_Entity getProject(){
        return project;
    }
    
    @ManyToOne
    @JoinTable(name = "PROJECT_PARAMETER_ID")
    private T_PROJECT_PARAMETER_Entity parameter;
    public T_PROJECT_PARAMETER_Entity getParameter(){
        return parameter;
    }
}
