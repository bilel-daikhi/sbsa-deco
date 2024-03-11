package org.sbsa.deco.service.impl;


import com.sbsa.deco.dto.CategoryDTO;
import com.sbsa.deco.dto.SubCategoryDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.service.SubCategoriesService;
import org.sbsa.deco.service.utils.CustomPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class SubCategoriesServiceImpl implements SubCategoriesService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${sbsa.api.url}")
    private String apiURI;

    @Override
    public boolean saveSubCategory(SubCategoryDTO subCategoryDTO, CategoryDTO categoryDTO) {
        subCategoryDTO.setCategory(categoryDTO);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories");
        HttpEntity<SubCategoryDTO> request = new HttpEntity<>(subCategoryDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.POST, request, String.class);

        log.info("save sub Category response code: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean updateSubCategory(SubCategoryDTO subCategoryDTO, CategoryDTO categoryDTO) {
        subCategoryDTO.setCategory(categoryDTO);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories");
        HttpEntity<SubCategoryDTO> request = new HttpEntity<>(subCategoryDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.PATCH, request, String.class);

        log.info("update sub Category response code: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();

    }


    public boolean deleteSubCategory(TableView<SubCategoryDTO> tbl, ObservableList<SubCategoryDTO> listOwners) {
        long id = tbl.getSelectionModel().getSelectedItem().getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories/" + id);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.DELETE, null, String.class);

        log.info("delete sub Category response code: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();

    }


    public Page<SubCategoryDTO> findAll() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories");
        ResponseEntity<CustomPageImpl<SubCategoryDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<SubCategoryDTO>>() {
                });

        log.info("find all response code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();

    }

    public Long count() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories/count");
        ResponseEntity<Long> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<Long>() {
                });

        log.info("find all response code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();

    }

    public Page<SubCategoryDTO> searchSubCategory(String query, Pageable pageable) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories/searchByCategoryName/" + query);
        ResponseEntity<CustomPageImpl<SubCategoryDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<SubCategoryDTO>>() {
                });

        log.info("search subcategory reponse code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();

    }


    @Override
    public SubCategoryDTO findSubCategoryById(long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/sub-categories/" + id);
        ResponseEntity<SubCategoryDTO> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<SubCategoryDTO>() {
                });

        log.info("find subcategories response code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();
    }


}
