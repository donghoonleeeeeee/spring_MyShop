package portfolio1.Drink.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import portfolio1.Drink.DTO.Items.CategoryDTO;
import portfolio1.Drink.DTO.Items.ItemsDTO;
import portfolio1.Drink.DTO.Items.ItemsModifyDTO;
import portfolio1.Drink.Entity.Items.ItemCategoryEntity;
import portfolio1.Drink.Entity.Items.ItemsEntity;
import portfolio1.Drink.Entity.Items.ItemsFileEntity;
import portfolio1.Drink.Repository.Items.ItemCategoryRepository;
import portfolio1.Drink.Repository.Items.ItemsFileRepository;
import portfolio1.Drink.Repository.Items.ItemsRepository;
import portfolio1.Drink.Service.ItemsService;
import portfolio1.Drink.Service.ToolsService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class
ItemsServiceImpl implements ItemsService
{
    private final ItemsRepository itemsRepository;
    private final ItemsFileRepository itemsFileRepository;
    private final ToolsService toolsService;
    private final ItemCategoryRepository itemCategoryRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ItemsService.class);


    @Override
    public void input_items(ItemsDTO itemsDTO) throws Exception
    {
        LOGGER.info("[물품 관리] 새로운 물품을 등록합니다! / 물품명: "+itemsDTO.getItem());
        String files = null;
        String pathfiles = null;
        UUID uuid = UUID.randomUUID();
        List<String> filenames = new ArrayList<>();
        LOGGER.info("[물품 관리] 물품 데이터 : "+itemsDTO);
        ItemsEntity itemsEntity = itemsDTO.toEntity();
        ItemCategoryEntity itemCategoryEntity = itemCategoryRepository.findByCategory(itemsDTO.getCategory3());
        LOGGER.info("[물품 관리] 물품을 등록할 카테고리를 탐색합니다. / 카테고리: "+itemCategoryEntity.getName());
        itemsEntity.setCategoryEntity(itemCategoryEntity);
        itemsRepository.save(itemsEntity);
        for(int a=0; a<itemsDTO.getFiles().size(); a++)
        {
            files = itemsDTO.getFiles().get(a).getOriginalFilename();
            pathfiles = uuid +"_"+itemsDTO.getFiles().get(a).getOriginalFilename();

            filenames.add(pathfiles);
            itemsFileRepository.save(itemsDTO.toFileEntity(files, pathfiles, itemsEntity));
        }
        toolsService.Upload(itemsDTO.getFiles(),filenames);
    }

    @Override
    public Page<ItemsEntity> item_list(String category, String keyword, Pageable pageable)
    {
        if(category.equals("none") || category.equals("none/none/none")) // 카테고리 선택 X
        {
            LOGGER.info("[물품 목록] 검색 카테고리가 존재하지 않습니다. / category: "+category);

            if(keyword.equals("none"))
            {
                LOGGER.info("[물품 목록] 검색 키워드가 존재하지 않습니다. / keyword: "+keyword);
                LOGGER.info("[물품 목록] 현재 등록된 모든 리스트를 출력합니다. / 결과 수: "+itemsRepository.findAll(pageable).getSize());
                return itemsRepository.findAll(pageable);
            }
            else
            {
                LOGGER.info("[물품 목록] 입력된 검색 키워드로 조회합니다. / 물품명: "+keyword);
                return itemsRepository.findByItemContaining(keyword, pageable);
            }
        }
        else // 카테고리 선택 O
        {
            List<String> category_code = new ArrayList<>();

            LOGGER.info("[물품 목록] 카테고리 조건으로 검색을 시작합니다."+category);

            for(int a=0; a<category.split("/").length; a++)
            {
                if(!category.split("/")[a].equals("none"))
                {
                    category_code.add(category.split("/")[a]);
                }
            }
            LOGGER.info("[물품 목록] 검색 조건: "+category_code.get(category_code.size()-1));
            List<ItemCategoryEntity> categoryEntities = itemCategoryRepository.findAllByCategoryStartingWith(category_code.get(category_code.size()-1));
            LOGGER.info("[물품 목록] 연관된 카테고리 목록 수: "+categoryEntities.size());


            List<ItemsEntity> items_entity = null;
            List<ItemsEntity> items = new ArrayList<>();

            int start = 0;
            int end = 0;
            if(keyword.equals("none"))
            {
                for(int a=0; a<categoryEntities.size(); a++)
                {
                    items_entity = categoryEntities.get(a).getItemsEntities();
                    items.addAll(items_entity);
                }
                start = (int)pageable.getOffset();
                end = Math.min((start + pageable.getPageSize()),items.size());
                LOGGER.info("[물품 목록] 검색 키워드는 없으므로 카테고리와 연관된 물품이 출력됩니다. / 결과 수: "+items.size());
                return new PageImpl<>(items.subList(start,end), pageable, items.size());
            }
            else
            {
                for(int a=0; a<categoryEntities.size(); a++)
                {
                    List<ItemsEntity> keyword_item = itemsRepository.findByItemContainingAndCategoryEntity_Idx(keyword, categoryEntities.get(a).getIdx());
                    if(!keyword_item.isEmpty())
                    {
                        items.addAll(keyword_item);
                    }
                }
                start = (int)pageable.getOffset();
                end = Math.min((start + pageable.getPageSize()),items.size());
                LOGGER.info("[물품 목록] 카테고리와 연관된 물품 중 검색어와 일치하는 물품을 조회합니다. / 결과 수: "+items.size());
                return new PageImpl<>(items.subList(start,end), pageable, items.size());
            }
        }
    }

    @Override
    public void items_modify(String[] check, ItemsModifyDTO itemsModifyDTO)
    {
        for(int a=0; a<check.length; a++)
        {
            if (!check[a].equals("0"))
            {
                ItemsEntity item = itemsRepository.findById(Long.valueOf(check[a])).orElse(null);
                item.list_modify(itemsModifyDTO.getInventory()[a], itemsModifyDTO.getPrice()[a]);
                itemsRepository.save(item);
            }
        }
    }

    @Override
    public void modify(List<String> check, ItemsDTO itemsDTO) throws Exception
    {
        UUID uuid = UUID.randomUUID();

        ItemsEntity after_itemsEntity = itemsDTO.toEntity();
        ItemCategoryEntity category = itemCategoryRepository.findByCategory(itemsDTO.getCategory3());
        List<ItemsFileEntity> before_file_itemsEntity = itemsFileRepository.findByItemsEntity_Idx(itemsDTO.getIdx());
        // after_file_itemsEntity 이상 무

        String filename = null;
        
        for(int a=0; a<check.size(); a++)
        {
            LOGGER.info(a+1+"번째 파일 수정 시작");
            LOGGER.info(a+1+"번째 CheckBox: "+check.get(a));
            LOGGER.info("업로드된 파일이 있으면 TRUE / 파일이 없으면 FALSE: "+!itemsDTO.getFiles().get(a).isEmpty());
            if(!check.get(a).equals("unchecked")) // 체크가 있으면
            {
                if(!itemsDTO.getFiles().get(a).isEmpty()) // 업로드 파일 있으면
                {
                    // 수정 될 파일 명
                    filename = uuid+"_"+itemsDTO.getFiles().get(a).getOriginalFilename();
                    if(a==0) // 그 중 첫번째 파일의 경우
                    {
                        LOGGER.info("[기존 파일 삭제] 파일명: "+before_file_itemsEntity.get(a).getPath());
                        LOGGER.info("[기존 썸네일 삭제] 파일명: "+"thum_"+before_file_itemsEntity.get(a).getPath());
                        toolsService.file_Delete(before_file_itemsEntity.get(a).getPath());
                        toolsService.thum_file_Delete(before_file_itemsEntity.get(a).getPath());
                        // 썸네일, 원본 파일 삭제
                        LOGGER.info("[새로운 파일 업로드] 파일명: "+filename);
                        LOGGER.info("[새로운 썸네일 업로드] 파일명: "+"thum_"+filename);
                        toolsService.file_Upload(itemsDTO.getFiles().get(a),filename);
                        toolsService.thum_file_Upload(itemsDTO.getFiles().get(a),filename);
                        // 썸네일, 원본 파일 업로드
                        LOGGER.info("[DB 수정] 기존 파일: "+before_file_itemsEntity.get(a).getPath()+" → 새로운 파일: "+filename);
                        before_file_itemsEntity.get(a).setPath(filename);
                        before_file_itemsEntity.get(a).setOrigin(itemsDTO.getFiles().get(a).getOriginalFilename());
                        // ItemFileEntity에 있는 정보 수정
                    }
                    else // 두번째, 세번째 파일의 경우 파일만 삭제 후 업로드
                    {
                        LOGGER.info("[기존 파일 삭제] 파일명: "+before_file_itemsEntity.get(a).getPath());
                        toolsService.file_Delete(before_file_itemsEntity.get(a).getPath());
                        // 원본 파일 삭제
                        LOGGER.info("[새로운 파일 업로드] 파일명: "+filename);
                        toolsService.file_Upload(itemsDTO.getFiles().get(a),filename);
                        // 원본 파일 업로드
                        LOGGER.info("[DB 수정] 기존 파일: "+before_file_itemsEntity.get(a).getPath()+" → 새로운 파일: "+filename);
                        before_file_itemsEntity.get(a).setPath(filename);
                        before_file_itemsEntity.get(a).setOrigin(itemsDTO.getFiles().get(a).getOriginalFilename());
                    }
                }
                else // 업로드 파일이 없으면
                {
                    if(a==0) //그 중 첫번째 파일의 경우
                    {
                        LOGGER.info("[경고] 첫번째 파일은 썸네일용으로 반드시 업로드 해야합니다!");
                    }
                    else // 파일 삭제만 수행
                    {
                        LOGGER.info("[기존 파일 삭제] 파일명: "+before_file_itemsEntity.get(a).getPath());
                        toolsService.file_Delete(before_file_itemsEntity.get(a).getPath());
                        LOGGER.info("[새로운 파일 업로드] 업로드된 파일 없음!");
                        LOGGER.info("[DB 수정] 기존 파일: "+before_file_itemsEntity.get(a).getPath()+" none으로 초기화");
                        before_file_itemsEntity.get(a).setPath("none");
                        before_file_itemsEntity.get(a).setOrigin("none");
                        // DB는 none으로 초기화
                    }
                }
            }
            after_itemsEntity.setCategoryEntity(category);
            itemsRepository.save(after_itemsEntity);
            itemsFileRepository.save(before_file_itemsEntity.get(a));
        }
    }

    @Override
    public ItemsDTO Items_view(Long idx)
    {
        ItemsEntity entity_items = itemsRepository.findById(idx).orElse(null);

        return entity_items.toDTO();
    }
    @Override
    public String itemCategory(Long idx)
    {
        ItemsEntity entity = itemsRepository.findById(idx).orElse(null);
        return entity.getCategoryEntity().getCategory();
    }

    @Override
    public List<String> itemCategories(Long idx)
    {
        ItemsEntity entity = itemsRepository.findById(idx).orElse(null);
        String code = entity.getCategoryEntity().getCategory();
        List<String> categories = new ArrayList<>();
        ItemCategoryEntity first = itemCategoryRepository.findByCategory(code.substring(0,2));
        ItemCategoryEntity second = itemCategoryRepository.findByCategory(code.substring(0,4));
        ItemCategoryEntity third = itemCategoryRepository.findByCategory(code.substring(0,6));


        categories.add(first.getCategory()+"/"+first.getName());
        categories.add(second.getCategory()+"/"+second.getName());
        categories.add(third.getCategory()+"/"+third.getName());
        return categories;
    }
    @Override
    public void Items_List_Delete(List<Long> idx)
    {
        ItemsEntity itemsEntity = null;
        
        for(int a=0; a<idx.size(); a++)
        {
            itemsEntity = itemsRepository.findById(idx.get(a)).orElse(null);
            itemsRepository.delete(itemsEntity);
            if(a==0)
            {
                toolsService.thum_file_Delete(itemsEntity.getItemsFileEntities().get(a).getPath());
                toolsService.file_Delete(itemsEntity.getItemsFileEntities().get(a).getPath());
            }
            else
            {
                toolsService.file_Delete(itemsEntity.getItemsFileEntities().get(a).getPath());
            }
        }
    }

    @Override
    public String Label_Check(String category, String info)
    {
        String result = null;
        LOGGER.info("[카테고리] 카테고리를 조회합니다. / 검색 할 카테고리: "+category+" 검색 조건: "+info);
        if(category.equals("category1"))
        {
            LOGGER.info("[카테고리] 1번 카테고리를 조회합니다.");
            List<ItemCategoryEntity> categories = itemCategoryRepository.findAll();
            List<Integer> first = new ArrayList<>();

            if(categories.isEmpty())
            {
                result = "10";
            }
            else
            {
                for(int a=0; a<categories.size(); a++)
                {
                    first.add(Integer.parseInt(categories.get(a).getCategory().substring(0,2)));
                }
                LOGGER.info("[카테고리] 1번 카테고리에 입력된 값 중 최댓값: "+Collections.max(first));
                result = String.valueOf(Collections.max(first)+10);
            }
        }

        else if(category.equals("category2"))
        {
            LOGGER.info("[카테고리] 2번 카테고리를 조회합니다.");
            List<ItemCategoryEntity> categoryEntities = itemCategoryRepository.findAllByCategoryStartingWith(info);
            List<Integer> second = new ArrayList<>();

                if(categoryEntities.size() == 1)
                {
                    LOGGER.info("[카테고리] 조회된 카테고리가 없습니다.");
                    result = info+"10";
                }
                else
                {
                    for(int a=0; a<categoryEntities.size(); a++)
                    {
                        if(categoryEntities.get(a).getCategory().length()>2)
                        {
                            second.add(Integer.parseInt(categoryEntities.get(a).getCategory().substring(2,4)));
                        }
                    }
                    LOGGER.info("[카테고리] 조회된 2번 카테고리 수: "+second.size());
                    LOGGER.info("[카테고리] 2번 카테고리에 입력된 값 중 최댓값: "+Collections.max(second));
                    int max = Collections.max(second)+10;
                    result = info+max;

                }

        }
        else
        {
            LOGGER.info("[카테고리] 3번 카테고리를 조회합니다.");
            List<ItemCategoryEntity> categoryEntities = itemCategoryRepository.findAllByCategoryStartingWith(info);
            List<Integer> third = new ArrayList<>();

            if(categoryEntities.size() == 1)
            {
                LOGGER.info("[카테고리] 조회된 카테고리가 없습니다.");
                result = info+"10";
            }
            else
            {
                for(int a=0; a<categoryEntities.size(); a++)
                {
                    if(categoryEntities.get(a).getCategory().length()>4)
                    {
                        third.add(Integer.parseInt(categoryEntities.get(a).getCategory().substring(4,6)));
                    }
                }
                LOGGER.info("[카테고리] 조회된 3번 카테고리 수: "+third.size());
                LOGGER.info("[카테고리] 3번 카테고리에 입력된 값 중 최댓값: "+Collections.max(third));
                int max = Collections.max(third)+10;
                result = info+max;

            }
        }
        LOGGER.info("[카테고리] "+result+"(으)로 시작합니다.");
        return result;
    }

    @Override
    public void Category_Register(CategoryDTO categoryDTO)
    {
        String label = categoryDTO.getCode();
        String category_name = categoryDTO.getCategory_name();

        itemCategoryRepository.save(categoryDTO.toLabel(label,category_name));
    }

    @Override
    public List<CategoryDTO> first_View()
    {
        List<ItemCategoryEntity> categoryEntities = itemCategoryRepository.first_code();
        List<CategoryDTO> categoryDTO = new ArrayList<>();
        for(int a=0; a<categoryEntities.size(); a++)
        {
            categoryDTO.add(categoryEntities.get(a).toDTO());
        }
        return categoryDTO;
    }

    @Override
    public List<String> SetLabel(String code)
    {
        LOGGER.info("[카테고리] SelectBox 세팅 / 조건: "+code);
        List<ItemCategoryEntity> categoryEntities = itemCategoryRepository.findAllByCategoryStartingWith(code);
        List<String> category_names = new ArrayList<>();
        if(code.length()==2) // 대분류
        {
            LOGGER.info("[카테고리] 조건을 기준으로 2차 카테고리를 조회합니다.");
            for (int a=0; a<categoryEntities.size(); a++)
            {
                if(categoryEntities.get(a).getCategory().length() > 2)
                {
                    category_names.add(categoryEntities.get(a).getCategory() + "/" + categoryEntities.get(a).getName());
                    // 라벨과 이름을 붙여서 출력 ex) 2010한국, 1010일본 (DTO자체를 보냈을 때 ajax로 받을 때 이유는 모르겠으나 안됨)
                }
            }
        }
        else if(code.length()==4)
        {
            LOGGER.info("[카테고리] 조건을 기준으로 3차 카테고리를 조회합니다.");
            for(int a=0; a<categoryEntities.size(); a++)
            {
                if(categoryEntities.get(a).getCategory().length()>4)
                {
                    category_names.add(categoryEntities.get(a).getCategory()+"/"+categoryEntities.get(a).getName());
                }
            }
        }
        LOGGER.info("[카테고리] 조회 결과: "+ category_names);
        return category_names;
    }

    @Override
    public List<CategoryDTO> Category_All()
    {
        List<ItemCategoryEntity> entity_list = itemCategoryRepository.findAll();
        List<CategoryDTO> dto_list = new ArrayList<>();
        for(int a=0; a<entity_list.size(); a++)
        {
            dto_list.add(entity_list.get(a).toDTO());
        }
        return dto_list;
    }

    @Override
    public List<ItemCategoryEntity> StartWithCategory(String code)
    {
        return itemCategoryRepository.findAllByCategoryStartingWith(code.substring(0,2));
    }

    @Override
    public void Category_modify(CategoryDTO categoryDTO)
    {
        ItemCategoryEntity entity = itemCategoryRepository.findByCategory(categoryDTO.getModify_code());
        entity.setName(categoryDTO.getModify_name());

        itemCategoryRepository.save(entity);
    }

    @Override
    public void Category_delete(CategoryDTO categoryDTO)
    {
        List<ItemCategoryEntity> entity = itemCategoryRepository.findAllByCategoryStartingWith(categoryDTO.getModify_code());

        for(int a=0; a<entity.size(); a++)
        {
            itemCategoryRepository.delete(entity.get(a));
        }

    }

    @Override
    public List<String> Item_Images(Long idx)
    {
        List<ItemsFileEntity> files = itemsRepository.findById(idx).orElse(null).getItemsFileEntities();
        List<String> images = new ArrayList<>();
        for(int a=0; a<files.size(); a++)
        {
            images.add(files.get(a).getPath());
        }
        return images;
    }
}


