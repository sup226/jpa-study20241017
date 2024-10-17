package com.study.jpa.chap01.repository;

import com.jayway.jsonpath.EvaluationListener;
import com.study.jpa.chap01.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.study.jpa.chap01.entity.Product.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest2 {

    // Spring Data JPA 이용방법
    @Autowired
    ProductRepository productRepository;

    // @BeforeEach: 각각의 테스트를 진행하기 전에 먼저 진행함
    @Test
    void insertBeforeTest() {
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(2000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("주먹밥")
                .category(FOOD)
                .price(1500)
                .build();
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        productRepository.save(p4);
    }

    @Test
    @DisplayName("상품을 데이터베이스에 저장한다.")
    void saveTest() {
        // given
        Product p = Product.builder()
                .name("떡볶이")
                .category(Product.Category.FOOD)
                .build();

        // when
        // insert 후 저장된 데이터의 객체를 반환.
        Product saved = productRepository.save(p);

        // then
        assertNotNull(saved);
    }

    @Test
    @DisplayName("1번 상품을 삭제한다.")
    void deleteTest() {
//        // given
//        Long id = 1L;
//
//        // when
//        productRepository.deleteById(id);
//
//         /*
//            Optional: Java 8버전 이후에 사용이 가능
//            객체의 null 값을 검증할 수 있도록 여러가지 기능을 제공하는 타입. (NPE 방지)
//            Null Pointer Exception
//         */
//        Optional<Product> foundProduct = productRepository.findById(id);
//        boolean present = foundProduct.isPresent(); // 객체가 실존하는지를 논리값으로 리턴.
//        System.out.println("present = " + present);
//
//        Product product = foundProduct.orElse(new Product()); // 만약 값이 없다면 other를 리턴.
//
////        Product product1 = foundProduct.orElseThrow(() -> {
////            throw new IllegalArgumentException("값이 없습니다.");
////        });// 값이 존재한다면 원래대로 리턴하고, 값이 없다면(null) 예외가 발생함.
//        // 예외가 무조건 발생함
//
//        foundProduct.ifPresent(p -> {
//            System.out.println(p.getName());
//        }); // 값이 null이 아닐 경우 후속 동작을 객체화.

        // given
        Long id = 1L;

        // when
        productRepository.deleteById(id);

        Optional<Product> optional = productRepository.findById(id);
        boolean present = optional.isPresent();
        System.out.println("present = " + present);
        optional.ifPresent(p -> {
            System.out.println("p = " + p);
        });

        // then
        assertFalse(present);
    }

    @Test
    @DisplayName("상품 전체조회를 하면 개수는 3개여야 한다.")
    void selectAllTest() {
        // given

        // when
        List<Product> products = productRepository.findAll();

        // then
        assertEquals(3, products.size());
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 변경해야 한다.")
    void updateTest() {
        // given
        Long id = 2L;
        String newName = "마라탕";
        int newPrice = 10000;

        // when
        productRepository.findById(id).ifPresent(p -> {
            p.setName(newName);
            p.setPrice(newPrice);

            // jpa는 따로 update 메서드를 제공하지 않습니다.
            // 조회한 객체의 필드를 setter로 변경하면 자동으로 update가 나갑니다.

            productRepository.save(p);
        });

        // then
    }



}