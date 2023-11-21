package petplaza.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petplaza.model.dto.ClientDto;
import petplaza.model.dto.ClientPageDto;
import petplaza.service.ClientService;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController { // 거래처 컨트롤러

    // Controller --> Server 요청
    // Controller <-- Server 응답

    @Autowired
    private ClientService clientService;

    // [C] 거래처 등록
    @PostMapping("/post")
    public boolean postClient(ClientDto clientDto){

        boolean result = clientService.postClient(clientDto);

        return result;
    }

    // [R] 거래처 출력
   @GetMapping("/get")
   public ClientPageDto getClient(@RequestParam int page ,
                                  @RequestParam String key ,
                                  @RequestParam String keyword ,
                                  @RequestParam int view){

       System.out.println("page = " + page + ", key = " + key + ", keyword = " + keyword + ", view = " + view);


       return clientService.getClient(page , key , keyword , view);
   }


    // 2-2 개별 거래처목록 출력
    @GetMapping("/doGet")
    public ClientDto doGet(@RequestParam int cno){

        return clientService.doGet(cno);
    }

    // [U] 거래처 수정
    @PutMapping("")
    public boolean putClient(ClientDto clientDto){


        return clientService.putClient(clientDto);
    }


}
