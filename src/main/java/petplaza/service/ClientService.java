package petplaza.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import petplaza.model.dto.ClientDto;
import petplaza.model.dto.ClientPageDto;
import petplaza.model.dto.ProductDto;
import petplaza.model.entity.ClientEntity;
import petplaza.model.repository.ClientRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // 해당 클래스 Service로 사용
public class ClientService { // 거래처 서비스

    // servuce -> Service -> Repository
    // servuce <- Service <- Repository

    @Autowired
    private ClientRepository clientRepository;

    // 1. [C] 거래처 등록
    @Transactional
    public boolean postClient( ClientDto clientDto){

        // 1. dto -> entity 변경후 repository 통한 insert 후 결과 entity 받기
        ClientEntity clientEntity = clientRepository.save(clientDto.toEntity() );

        return true;
    }

    // 2-1. [R] 거래처 출력
    @Transactional
    public ClientPageDto getClient(int page , String key , String keyword , int view){
        System.out.println("getService");

        Pageable pageble = PageRequest.of(page-1 , view);

        // 1. 모든 거래처 엔티티 호출
        Page<ClientEntity> clients = clientRepository.findBySearch(key , keyword , pageble);

        // 2.  Dto 리스트 생성
        List<ClientDto> clientDtoList = new ArrayList<>();


        // 3. 반환되는 dto 여러개를 담을 리스트
        clientDtoList = clients.stream().map( (c)->{ return c.toDto(); }).collect(Collectors.toList());

        int totalPages = clients.getTotalPages();

        // 출력할 거래처정보 수
        Long toatalCount = clients.getTotalElements();

        // pageDto 구성해서 axios에게 전달
        ClientPageDto clientPageDto = ClientPageDto.builder()
                .clientDtoList(clientDtoList)
                .totalCount(toatalCount)
                .totalPages(totalPages)
                .build();

        // 리턴
        return clientPageDto;
    }

    // 2-2 개별 거래처 출력
    @Transactional
    public ClientDto doGet(int cno){
        System.out.println("doService");
        // 1. Pk 번호에 해당하는 엔티티 찾기
        Optional<ClientEntity> clientEntityOptional
                = clientRepository.findById( cno );
        // 2. 검색된 엔티티가 존재하면
        if( clientEntityOptional.isPresent() ){
            // 3. 엔티티 꺼내기
            ClientEntity clientEntity = clientEntityOptional.get();
            // 4. 엔티티 -> 디티오 변환
            ClientDto clientDto = clientEntity.toDto();
            // 5. dto 변환
            return clientDto;
        }
        return null;
    }

    // 3. [U] 거래처 수정
    @Transactional
    public boolean putClient(ClientDto clientDto){
        System.out.println("putService");
        System.out.println(clientDto);

        // 1. 수정할 엔티티 찾기 [ cno 해서 ]
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById( clientDto.getCno() );

        // 2. 만약에 수정할 엔티티가 존재하면?
        if(optionalClientEntity.isPresent() ){

            // 3. 엔티티 꺼내기
            ClientEntity clientEntity = optionalClientEntity.get();

            // 4. 엔티티 수정
            clientEntity.setCname( clientDto.getCname() );
            clientEntity.setCphone( clientDto.getCphone() );
            clientEntity.setCaddress( clientDto.getCaddress() );
        }

        return true;
    }


}
