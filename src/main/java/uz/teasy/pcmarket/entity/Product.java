package uz.teasy.pcmarket.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Attachment photo;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private Integer price; //tiyinlarda beriladi kk payit 100 ga bolib yuboriladi;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    private User admin;

    private boolean active=true;
}
