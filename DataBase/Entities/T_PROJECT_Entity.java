/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import sun.security.util.Length;

/**
 *
 * @author sereg
 */

@Entity
@Table(name = "T_PROJECT")

public class T_PROJECT_Entity implements Serializable {
    
    @Id
    @Column(name = "PROJECT_ID")
    private Integer id;
    
    @OneToMany
    @JoinTable(name = "PROJECT_ID")
    private Set<T_PROJECT_has_T_SELECTION_Entity> projsel = new HashSet<T_PROJECT_has_T_SELECTION_Entity>(0);
    public Set<T_PROJECT_has_T_SELECTION_Entity> getProjSel(){
        return projsel;
    }
    
    @Column(name = "NAME", length = 45)
    private String name;
    
    public T_PROJECT_Entity(){
        
    }
    
    public T_PROJECT_Entity(String name){
        this.name = name;
    }
    
    public T_PROJECT_Entity(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    
    public T_PROJECT_Entity(Integer id){
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
