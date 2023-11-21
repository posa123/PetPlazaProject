import axios from 'axios'; // axios
import { useSearchParams } from "react-router-dom";

export default function ClientWrite( props ){ // 거래처 입력/등록 페이지
       const [searchParams, setSearchParams] = useSearchParams();
       const cno = searchParams.get('cno');

     // 1. 등록함수
     const clientWrite = (e) => {
         let clientForm = document.querySelectorAll('.clientForm')[0];
         let clientFormData = new FormData( clientForm ); console.log('폼데이터들어옵니다.'+clientFormData);
         // 2. azio 통신
         axios.post("/client/post" , clientFormData)
                .then( r => {
                    console.log('거래처데이터들어옵니다.'+r);
                    if(r.data){
                        console.log(r.data);
                        alert('거래처 등록 성공');
                        window.location.href='/client/list';

                    }else{
                        alert('거래처 등록 실패');
                    }

                     })

    }


    return(<>
        <div className ="webcontainer">
             <h3> 거래처 등록 </h3>
            { cno }
             <form className="clientForm">
                <input type="text" placeholder='거래처이름' name="cname" />    <br/>
                <input type="text" placeholder='전화번호' name="cphone" />    <br/>
                <input type="text" placeholder='주소' name="caddress" />    <br/>
                 <button type="button" onClick={ clientWrite } > 등록 </button>
             </form>
        </div>

    </>)

}
