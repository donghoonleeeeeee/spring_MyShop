package portfolio1.Drink.DTO.Items;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Entity.Items.ItemsFileEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemsDTO
{
    private Long idx; // 기본키
    private String category1; // 1차분류 : 10 20 30 ...
    private String category2; // 2차분류 : 1020 1010 2030 ...
    private String category3; // 3차분류 : 용량(330ml, 500ml, 640ml)
    private String item; // 제품명
    private String price; // 가격
    private String inventory; // 재고
    private String content; // 제품설명
    private String regdate; // 제품 등록일
    private String likes; // 추천
    private String image1;
    private String image2;
    private String image3;
    private List<MultipartFile> files;

    private List<String> path;
    private List<String> origin;

    public ItemsEntity toEntity()
    {
        Date now = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ItemsEntity.builder()
                .idx(idx)
                .item(item)
                .price(price)
                .inventory(inventory)
                .content(content)
                .regdate(sd.format(now))
                .build();
    }

    public ItemsFileEntity toFileEntity(String origin, String path, ItemsEntity itemsEntity)
    {
        ItemsFileEntity itemsFileEntity = new ItemsFileEntity();
        itemsFileEntity.setOrigin(origin);
        itemsFileEntity.setPath(path);
        itemsFileEntity.setItemsEntity(itemsEntity);
        return itemsFileEntity;
    }

}
