package uz.teasy.pcmarket.payload;

import lombok.Data;
import uz.teasy.pcmarket.entity.Attachment;
import uz.teasy.pcmarket.entity.Category;
import uz.teasy.pcmarket.entity.User;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private Integer category_id;
    private Integer photo_id;
    private String code;
    private Integer price;
    private Integer amount;
    private Integer admin_id;
    private boolean active;
}
