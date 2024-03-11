package org.sbsa.deco.service.impl;

import com.sbsa.deco.dto.ItemDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.controllers.tv.ItemTV;
import org.sbsa.deco.mapper.ItemMapper;
import org.sbsa.deco.service.ItemService;
import org.sbsa.deco.service.utils.CustomPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ItemMapper itemMapper;
    @Value("${sbsa.api.url}")
    private String apiURI;

    public boolean saveItem(ItemTV itemTV, ObservableList<ItemTV> list) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items");
        ItemDTO itemDTO = itemMapper.itemTVToItemDTO(itemTV);
        HttpEntity<ItemDTO> request = new HttpEntity<>(itemDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.POST, request, String.class);

        log.info("save Item: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();
    }


    public boolean updateItem(ItemTV itemTV) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "+/v1/items/" + itemTV.getId());
        ItemDTO itemDTO = itemMapper.itemTVToItemDTO(itemTV);
        HttpEntity<ItemDTO> request = new HttpEntity<>(itemDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.PATCH, request, String.class);

        log.info("Status Code: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();
    }


    public boolean deleteItem(TableView<ItemTV> tbl, ObservableList<ItemTV> listVehicles) {
        long id = tbl.getSelectionModel().getSelectedItem().getId();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items/" + id);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.DELETE, null, String.class);

        log.info("Status Code: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();


    }


    public Page<ItemTV> findAll() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items");
        ResponseEntity<CustomPageImpl<ItemDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<ItemDTO>>() {
                });

        log.info("Status Code: " + responseEntity.getStatusCode());
        List<ItemTV> itemTVS = responseEntity.getBody().stream()
                .map(itemTV -> itemMapper.itemDTOToItemTV(itemTV)).collect(Collectors.toList());
        return new CustomPageImpl<>(itemTVS);


    }

    public Page<ItemTV> searchItems(String query, Pageable pageable) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items/" + query);
        ResponseEntity<CustomPageImpl<ItemDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<ItemDTO>>() {
                });
        log.info("Status Code: " + responseEntity.getStatusCode());

        List<ItemTV> itemTVS = responseEntity.getBody().stream()
                .map(itemTV -> itemMapper.itemDTOToItemTV(itemTV)).collect(Collectors.toList());
        return new CustomPageImpl<>(itemTVS);
    }

    @Override
    public ItemDTO findItemById(long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items/" + id);
        ResponseEntity<ItemDTO> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<ItemDTO>() {
                });

        log.info("response Status Code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();
    }


    public List<ItemTV> getAllDiscountItems(Boolean isDescount) {


        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items/" + (isDescount ? "discount/1" : "discount/0"));
        ResponseEntity<CustomPageImpl<ItemDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<ItemDTO>>() {
                });
        log.info("response Status Code: " + responseEntity.getStatusCode());
        log.info("body : " + responseEntity.getBody().toString());
        List<ItemTV> itemTVS = responseEntity.getBody().stream()
                .map(itemTV -> itemMapper.itemDTOToItemTV(itemTV)).collect(Collectors.toList());
        return itemTVS;
    }


    public List<ItemDTO> findItemsByName(String name) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items/search/" + name);
        ResponseEntity<CustomPageImpl<ItemDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<ItemDTO>>() {
                });
        log.info("response Status Code: " + responseEntity.getStatusCode());
        return responseEntity.getBody().getContent();
    }


    @Override
    public Long count() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/items/count");
        ResponseEntity<Long> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Long>() {
                });
        log.info("count status code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    @Override
    public String getLandingImageByItem(long id) {
        //http://localhost:8080/v1/images/landing/1
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/images/landing/"+id);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });
        log.info("get landing image count status code: " + responseEntity.getStatusCode());
      if(responseEntity.getStatusCode()== HttpStatus.OK)
        return responseEntity.getBody();
      return null;

    }

    @Override
    public Image getImageItem(long id) {
        log.info("get Image method called!!");
        String landing =getLandingImageByItem(id);
        byte[] imageBytes = restTemplate.getForObject(landing, byte[].class);
        try {
             Files.write(Paths.get("image.jpg"), imageBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream targetStream = new ByteArrayInputStream(imageBytes);
        Image  image = new Image(targetStream, 200, 200, true, true);
        return image;
    }


}
