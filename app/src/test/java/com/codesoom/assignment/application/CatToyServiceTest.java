package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.domain.DogToy;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.codesoom.assignment.exception.NotSupportedTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;
import static com.codesoom.assignment.Constant.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("CatToyService 서비스 테스트")
class CatToyServiceTest {

    private ProductService<CatToy> service;
    private CatToyRepository repository;

    private CatToy catToy;

    @BeforeEach
    void setUp() {

        repository = mock(CatToyRepository.class);

        service = new CatToyService(repository);

        setUpFixtures();
    }

    private void setUpFixtures() {
        catToy = new CatToy(1L, NAME, MAKER, PRICE, IMAGE_URL);

        given(repository.findById(1L)).willReturn(Optional.of(catToy));
        given(repository.findById(100L)).willReturn(Optional.empty());
        given(repository.save(any(CatToy.class))).willReturn(catToy);

    }

    @DisplayName("고양이 장난감들이 비어있는 목록을 조회할 수 있습니다.")
    @Test
    void findAllNotExistsCatToy() {
        given(service.findAll()).willReturn(new ArrayList<>());

        List<CatToy> catToys = service.findAll();

        assertThat(catToys.isEmpty()).isTrue();

        verify(repository).findAll();
    }

    @DisplayName("고양이 장난감들이 존재하는 목록을 조회할 수 있습니다.")
    @Test
    void findAllExistsCatToy() {
        given(service.findAll())
                .willReturn(Arrays.asList(catToy));
        List<CatToy> catToys = service.findAll();

        assertThat(catToys).hasSize(1);

        verify(repository).findAll();
    }


    @DisplayName("식별자를 통해 고양이 장난감을 찾을 수 있습니다.")
    @Test
    void findCatToyById() {
        assertThat(service.findById(1L)).isEqualTo(catToy);
        verify(repository).findById(1L);
    }

    @DisplayName("존재하지 않는 식별자를 통해 고양이 장난감을 찾을 경우 예외가 발생합니다.")
    @Test
    void findCatToyByNotExistsId() {
        assertThatThrownBy(() -> service.findById(100L))
                .isInstanceOf(CatToyNotFoundException.class);
    }

    @DisplayName("새로운 고양이 장난감을 등록할 수 있습니다.")
    @Test
    void saveCatToy() {
        final CatToy catToy = CatToy.of("Other", MAKER, PRICE, IMAGE_URL);

        assertThat(catToy.getId()).isNull();

        CatToy createdCatToy = service.save(catToy);

        assertThat(createdCatToy.getId()).isNotNull();
        assertThat(createdCatToy.getMaker()).isEqualTo(MAKER);
        assertThat(createdCatToy.getPrice()).isEqualTo(PRICE);
        assertThat(createdCatToy.getImageUrl()).isEqualTo(IMAGE_URL);

        verify(repository).save(catToy);
    }

    @DisplayName("고양이 장난감의 정보를 수정할 수 있습니다. ")
    @Test
    void updateCatToy() {
        final CatToy newCatToy = CatToy.of("Other", "OtherMaker", 3000L, "OtherUrl");
        CatToy updatedCatToy = service.updateProduct(1L, newCatToy);

        assertThat(updatedCatToy.getName()).isEqualTo(newCatToy.getName());
        assertThat(updatedCatToy.getMaker()).isEqualTo(newCatToy.getMaker());
        assertThat(updatedCatToy.getPrice()).isEqualTo(newCatToy.getPrice());
        assertThat(updatedCatToy.getImageUrl()).isEqualTo(newCatToy.getImageUrl());

    }

    @DisplayName("존재하지 않는 식별자의 고양이 장난감을 수정하려하면 예외가 발생합니다.")
    @Test
    void updateCatToyNotExistsId() {
        final CatToy newCatToy = CatToy.of("Other", "OtherMaker", 3000L, "OtherUrl");

        assertThatThrownBy(() -> service.updateProduct(100L, newCatToy))
                .isInstanceOf(CatToyNotFoundException.class);

        verify(repository).findById(100L);
    }

    @DisplayName("지원하지 않는 타입으로 장난감 정보를 수정하려 하면 예외가 발생합니다.")
    @Test
    void updateCatToyNotSupportedType() {
        final DogToy dogToy = DogToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

        assertThatThrownBy(()-> catToy.update(dogToy))
                .isInstanceOf(NotSupportedTypeException.class);

    }

    @DisplayName("고양이 장난감을 삭제할 수 있습니다.")
    @Test
    void deleteCatToy() {

        final CatToy foundCatToy = service.findById(1L);
        service.deleteProduct(foundCatToy);

        verify(repository).findById(1L);
        verify(repository).delete(any(CatToy.class));
    }

    @DisplayName("존재하지 않는 식별자의 고양이 장난감을 삭제하려 하면 예외가 발생합니다.")
    @Test
    void deleteCatToyNotExistsId() {
        assertThatThrownBy(() -> {
            final CatToy foundCatToy = service.findById(100L);
            service.deleteProduct(foundCatToy);
        })
                .isInstanceOf(CatToyNotFoundException.class);

        verify(repository).findById(100L);
    }


}
