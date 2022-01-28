package uz.teasy.pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.teasy.pcmarket.entity.Attachment;
import uz.teasy.pcmarket.entity.Category;
import uz.teasy.pcmarket.entity.Product;
import uz.teasy.pcmarket.entity.User;
import uz.teasy.pcmarket.payload.ApiResponse;
import uz.teasy.pcmarket.payload.ProductDTO;
import uz.teasy.pcmarket.repository.AttachmentRepository;
import uz.teasy.pcmarket.repository.CategoryRepository;
import uz.teasy.pcmarket.repository.ProductRepository;
import uz.teasy.pcmarket.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    UserRepository userRepository;

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProductDTO productDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategory_id());
        if (!optionalCategory.isPresent()) return ResponseEntity.status(404).body("Category - not found!");

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getPhoto_id());
        if (!optionalAttachment.isPresent()) return ResponseEntity.status(404).body("Attachment - not found!");

        Optional<User> optionalUser = userRepository.findById(productDTO.getAdmin_id());
        if (!optionalUser.isPresent()) return ResponseEntity.status(404).body("User - not found!");

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(optionalCategory.get());

        product.setPhoto(optionalAttachment.get());
        product.setCode(productDTO.getCode());
        product.setPrice(productDTO.getPrice());
        product.setAmount(productDTO.getAmount());
        product.setAdmin(optionalUser.get());
        product.setActive(true);

        productRepository.save(product);
        return ResponseEntity.status(201).body("PRODUCT SAVED!");
    }

    @PreAuthorize(value = "hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public ResponseEntity<?> getList() {
        List<Product> productList = productRepository.findAll();
//        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PreAuthorize(value = "hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) return ResponseEntity.status(404).body("Product - not found!");
        return ResponseEntity.status(HttpStatus.OK).body(optionalProduct.get());
    }

    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) return ResponseEntity.status(404).body("Product - not found!");
        Product productEditing = optionalProduct.get();

        Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategory_id());
        if (!optionalCategory.isPresent()) return ResponseEntity.status(404).body("Category - not found!");

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getPhoto_id());
        if (!optionalAttachment.isPresent()) return ResponseEntity.status(404).body("Attachment - not found!");

        Optional<User> optionalUser = userRepository.findById(productDTO.getAdmin_id());
        if (!optionalUser.isPresent()) return ResponseEntity.status(404).body("User - not found!");

        productEditing.setName(productDTO.getName());
        productEditing.setCategory(optionalCategory.get());
        productEditing.setPhoto(optionalAttachment.get());
        productEditing.setCode(productDTO.getCode());
        productEditing.setPrice(productDTO.getPrice());
        productEditing.setAmount(productDTO.getAmount());
        productEditing.setAdmin(optionalUser.get());
        productEditing.setActive(true);

        productRepository.save(productEditing);
        return ResponseEntity.status(201).body("PRODUCT EDITED!");

    }

    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Product - deleted");
    }


}



