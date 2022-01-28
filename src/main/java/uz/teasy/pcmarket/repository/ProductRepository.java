package uz.teasy.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.teasy.pcmarket.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
