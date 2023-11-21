package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import petplaza.model.dto.ProductDto;
import petplaza.model.dto.ProductPageDto;
import petplaza.model.entity.ProductEntity;
import petplaza.model.repository.ProductRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UnitLogueService unitLogueService;

    // 1. 제품출력 // 수정함
    @Transactional
    public ProductPageDto getAllProducts(int page, int view, String key, String keyword){
        Pageable pageable = PageRequest.of(page-1,view);
        // 모든 제품정보 호출
        Page<ProductEntity> products = productRepository.findBySearch(key,keyword,pageable);
        // dto 리스트에 담기
        List<ProductDto> productDtoList =new ArrayList<>();
        products.forEach(e->{
            ProductDto productDto = e.allDto();

            productDto.setUincrease(  unitLogueService.nowCount( productDto.getPno() )  );
            productDtoList.add( productDto );
        });
        // 총 페이지수
        int totalPages = products.getTotalPages();
        // 출력할 제품정보 수
        Long totalCount = products.getTotalElements();
        // pageDto 구성해서 axios에게 전달
        ProductPageDto productPageDto = ProductPageDto.builder()
                .productDtoList(productDtoList)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
        // 리턴
        return productPageDto;

    }
    // 1-2. 개별조회
    @Transactional
    public ProductDto doGet(int pno){
        // 1. optional 이용한 pk찾기
        Optional<ProductEntity> productEntityOptional = productRepository.findById(pno);
        if(productEntityOptional.isPresent()){
            ProductEntity productEntity = productEntityOptional.get();
            //엔티티 dto 변환
            ProductDto productDto = productEntity.allDto();
            return  productDto;
        }
    return null;
    }

    // 2. 제품등록
    @Transactional
    public boolean postProduct(ProductDto productDto){
        System.out.println("productDto = " + productDto);

        // productEntity 구성해서 AXIOS에게 전달
        ProductEntity productEntity = ProductEntity.builder()
                .pname(productDto.getPname())
                .pprice(productDto.getPprice())
                .build();

        // 파일처리

        // 1. 게시물 생성 [ fk에 해당하는 레코드 생성 ]
        //ProductEntity productEntity1 = productRepository.save(productDto.productEntity());
        // 2. 생성된 게시물에 엔티티 넣어주기 [ fk 넣어주기 ]
        //productEntity.setImageURL(productEntity.getImageURL());


        // JPARepository 를 이용한 엔티티 저장 [insert 대체]
        ProductEntity product = productRepository.save(productEntity);

        if( product.getPno() >= 1){
            // 게시물 쓰기 성공시 파일 처리
            String fileName
                    = productFileService.fileUpload(productDto.getFileList());
            System.out.println("fileName = " + fileName);
            // 파일처리 결과를 DB에 저장
            if(fileName != null){productEntity.setImageURL(fileName);
                System.out.println("fileName = " + fileName);
            }
            return true;
        }

        return true;
    } // Write End

    @Autowired ProductFileService productFileService;


    // 3. 제품수정 [pname,pprice]
    @Transactional
    public boolean putProduct( ProductDto productDto){
        System.out.println("productDto = " + productDto);
        // 1. 수정할 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productDto.getPno());
        // 2. 수정할 엔티티가 존재하면
        if(optionalProductEntity.isPresent()){
            // 3. 엔티티 꺼내기
            ProductEntity productEntity = optionalProductEntity.get();
            // 4. 엔티티 수정
            productEntity.setPname(productDto.getPname());
            productEntity.setPprice(productDto.getPprice());
            // 수정 성공시
            return true;
        }
        return true;
    }
    // 4. 제품삭제
    @Transactional
    public boolean deleteProduct( int pno){
        // 삭제할 엔티티 찾기
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(pno);
        // 만약에 삭제할 엔티티가 반환/검색 존재하면
        if(optionalProductEntity.isPresent()){
            productRepository.deleteById(pno);
            // 삭제 성공시
            return true;
        }
        return false;
    }
}
