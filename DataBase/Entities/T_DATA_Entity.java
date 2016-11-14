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
@Table(name = "T_PROJECT")

public class T_DATA_Entity implements Serializable {
    
    @Id
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "PROJECT_ID")
    private Integer projid;
    
    @Column(name = "POINT_X")
    private Double point;
    
    public T_DATA_Entity(){
        
    }
    
    public T_DATA_Entity(Double point){
        this.point = point;
    }
    
    public T_DATA_Entity(Integer projid, Double point){
        this.projid = projid;
        this.point = point;
    }
    
    public T_DATA_Entity(Integer projid){
        this.projid = projid;
    }
    
    public Integer getId() {
        return projid;
    }

    public void setId(Integer projid) {
        this.projid = projid;
    }

    public Double getPoint() {
        return point;
    }

    public void setNameItem(Double point) {
        this.point = point;
    }
}
