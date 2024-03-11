package org.sbsa.deco.service.impl;


import com.sbsa.deco.dto.CategoryDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.service.CategoryService;
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

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${sbsa.api.url}")
    private String apiURI;

    public boolean saveCategory(CategoryDTO categoryDTO, ObservableList<CategoryDTO> listCustomers) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI);
        HttpEntity<CategoryDTO> request = new HttpEntity<>(categoryDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.POST, request, String.class);

        log.info("save Category: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();

    }


    public boolean updateCategory(CategoryDTO categoryDTO) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/categories" + categoryDTO.getId());
        HttpEntity<CategoryDTO> request = new HttpEntity<>(categoryDTO);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.PATCH, request, String.class);

        log.info("update Category response code : " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();

    }


    public boolean deleteCategory(TableView<CategoryDTO> tbl, ObservableList<CategoryDTO> listCustomers) {
        long id = tbl.getSelectionModel().getSelectedItem().getId();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/categories/" + id);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.DELETE, null, String.class);

        log.info("delete Category response code: " + responseEntity.getStatusCode());
        return responseEntity.getStatusCode().is2xxSuccessful();
    }


    public Page<CategoryDTO> findAllCategory() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/categories");
        ResponseEntity<CustomPageImpl<CategoryDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<CategoryDTO>>() {
                });

        log.info("find all category response code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public Page<CategoryDTO> searchCategory(String query, Pageable pageable) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/categories/search/")
                .queryParam("query", query);
        ResponseEntity<CustomPageImpl<CategoryDTO>> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<CustomPageImpl<CategoryDTO>>() {
                });

        log.info("search category response code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();
    }


    public CategoryDTO findCategory(long id) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/categories/" + id);
        ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, CategoryDTO.class);

        log.info("find category response code: " + responseEntity.getStatusCode());
        return responseEntity.getBody();

    }


    public Long count() {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURI + "/v1/categories/count");
        ResponseEntity<Long> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.GET, null, Long.class);

        log.info("count response code: " + responseEntity.getStatusCode(), HttpStatus.OK);
        return responseEntity.getBody();
    }

}
