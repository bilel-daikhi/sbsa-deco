package org.sbsa.deco.services.impl;


import com.sbsa.deco.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.entities.Item;
import org.sbsa.deco.mappers.ItemImageMapper;
import org.sbsa.deco.mappers.ItemMapper;
import org.sbsa.deco.repositories.ItemImageRepository;
import org.sbsa.deco.repositories.ItemRepository;
import org.sbsa.deco.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemImageMapper itemImageMapper;
    @Autowired
    private ItemImageRepository itemImageRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.info("Find all called!");
        Page<Item> items = itemRepository.findAll(pageable);
        List<ItemDTO> categoryDTOS = items.getContent().stream()
                .map(item -> itemMapper.itemToItemDTO(item))
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, items.getTotalElements());

    }

    @Override
    public Page<ItemDTO> findAllBySubCategoryId(long id, Pageable pageable) {

        Page<Item> items = itemRepository.findAllBySubCategory_Id(id, pageable);
        List<ItemDTO> categoryDTOS = items.getContent().stream().map(item -> itemMapper.itemToItemDTO(item)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, items.getTotalElements());

    }

    @Override
    public Page<ItemDTO> findAllByName(String name, Pageable pageable) {
        Page<Item> items = itemRepository.findAllByName(name, pageable);
        List<ItemDTO> categoryDTOS = items.getContent().stream().map(item -> itemMapper.itemToItemDTO(item)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, items.getTotalElements());

    }

    @Override
    public Page<ItemDTO> findAllBySubCategory_Name(String name, Pageable pageable) {
        Page<Item> items = itemRepository.findAllBySubCategory_Name(name, pageable);
        List<ItemDTO> categoryDTOS = items.getContent().stream().map(item -> itemMapper.itemToItemDTO(item)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, items.getTotalElements());

    }

    @Override
    public Page<ItemDTO> findAllByDiscount(Boolean isDescount, Pageable pageable) {
        Page<Item> items = itemRepository.findAllByDiscount(isDescount, pageable);
        List<ItemDTO> categoryDTOS = items.getContent().stream().map(item -> itemMapper.itemToItemDTO(item)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, items.getTotalElements());

    }

    @Override
    public ItemDTO findById(Long id) {
        return itemMapper.itemToItemDTO(itemRepository.getOne(id));
    }


    @Override
    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    @Override
    public ItemDTO save(ItemDTO object) {
        Item item = itemRepository.save(itemMapper.itemDTOToitem(object));

        return itemMapper.itemToItemDTO(item);
    }

    @Override
    public long count() {
        return itemRepository.count();
    }

    @Override
    public void delete(ItemDTO object) {
        itemRepository.delete(itemMapper.itemDTOToitem(object));
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);

    }
}
