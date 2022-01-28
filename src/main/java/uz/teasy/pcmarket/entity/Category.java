package uz.teasy.pcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
//
//    @Column(nullable = false)
//    private Timestamp date; //etibor berish kk bovotti 2 ta kutubhonadan osa bolarkjan

    @ManyToOne
    private  Category parentCategory;//null,1,1,1

    @Column(nullable = false)
    private boolean active;
}