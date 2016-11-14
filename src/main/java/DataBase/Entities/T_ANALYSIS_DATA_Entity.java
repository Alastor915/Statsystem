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
@Table(name = "T_ANALYSIS_DATA")

class T_ANALYSIS_DATA_Entity {
    @Id
    @Column(name="id")
    
    private Integer id;
    
    public Integer getId(){
        return this.id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    @Column(name = "ANALYSIS_TYPE_ID")
    private Integer typeid;
    
    public Integer getTypeId(){
        return this.typeid;
    }
    
    public void setTypeId(Integer typeid){
        this.typeid = typeid;
    }
    
    @Column(name = "SELECTION_ID")
    private Integer selectionid;
    
    public Integer getSelectionId(){
        return this.selectionid;
    }
    
    public void setSelectionId(Integer selectionid){
        this.selectionid = selectionid;
    }
    
    @ManyToOne
    @JoinTable(name = "ANALYSIS_TYPE_ID")
    private T_ANALYSIS_TYPE_Entity type;
    public T_ANALYSIS_TYPE_Entity getType(){
        return type;
    }
    
    @ManyToOne
    @JoinTable(name = "SELECTION_ID")
    private T_SELECTION_Entity selection;
    public T_SELECTION_Entity getSelection(){
        return selection;
    }
}
