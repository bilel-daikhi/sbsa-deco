package org.sbsa.deco.services.impl;

import com.sbsa.deco.dto.ItemImageDTO;
import org.sbsa.deco.entities.ItemImage;
import org.sbsa.deco.mappers.ItemImageMapper;
import org.sbsa.deco.repositories.ItemImageRepository;
import org.sbsa.deco.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ItemImageRepository itemImageRepository;
    @Autowired
    private ItemImageMapper itemImageMapper;


    @Override
    public Page<ItemImageDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ItemImageDTO findById(Long id) {
        return itemImageMapper.ItemImageToItemImageDTO(itemImageRepository.getOne(id));
    }

    @Override
    public boolean existsById(Long id) {
        return itemImageRepository.existsById(id);
    }

    @Override
    public ItemImageDTO save(ItemImageDTO object) {
        ItemImage itemImage=itemImageRepository.save(itemImageMapper.itemImageDTOToItemImage(object));
        return itemImageMapper.ItemImageToItemImageDTO(itemImage);
    }

    @Override
    public long count() {
        return itemImageRepository.count();
    }

    @Override
    public void delete(ItemImageDTO object) {
        itemImageRepository.delete(itemImageMapper.itemImageDTOToItemImage(object));

    }

    @Override
    public void deleteById(Long id) {
        itemImageRepository.deleteById(id);
    }

    @Override
    public List<ItemImage> findItemImagesByItem_Id(long id) {
        return itemImageRepository.findItemImagesByItem_Id(id);
    }
}
