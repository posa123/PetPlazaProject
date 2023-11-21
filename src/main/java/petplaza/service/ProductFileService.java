package petplaza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import petplaza.model.entity.ProductEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Service
public class ProductFileService {

    /* 파일관련 메소드 정의 : */

    // 0. 파일 경로 [ 배포전 ]
    // private String fileRootPath = "c:\\java\\";
    private String fileRootPath = "C:\\Users\\504\\Desktop\\PetPlazaProject\\build\\resources\\main\\static\\static\\media\\";

    // 1. 업로드
    public String fileUpload(MultipartFile multipartFile) {
        System.out.println("업로드서비스 들어옴?");
        // 0.유효성검사 ,
        if (multipartFile.isEmpty()) { // 파일이 비어 있으면
            System.out.println("파일없음?");
            return null; // 비어 있으면 null 리턴
        }
        // 1. 파일명[ 파일명은 식별자가 될수 없다. 1.UUID 조합 2.날짜/시간 조합 3.상위컨텐츠PK 등등 ]
        String fileName =
                UUID.randomUUID().toString() + "_" + // UUID 이용한 파일 식별자 만들기
                        multipartFile.getOriginalFilename().replaceAll("_", "-"); // 만일 식별을 위해 '_'를  '-' 변경
        System.out.println("파일명"+fileName);
        // 2. 파일 경로
        File file = new File(fileRootPath + fileName);
        System.out.println("파일경로 확인" + file);
        // 3. 업로드
        try {
            multipartFile.transferTo(file);
            System.out.println("업로드 들어옴?");
            // 파일 경로를 filePath 필드에 저장
            //ProductEntity.setFilePath(fileRootPath+fileName);
            return fileName; // 성공시 파일명 리턴 [ db에 저장할려고 ]
        } catch (Exception e) {
            System.out.println("업로드 실패 " + e);
            return null; // 실패시 null 리턴
        }
    }
}
