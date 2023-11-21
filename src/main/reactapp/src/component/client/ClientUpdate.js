import axios from 'axios'; // axios
import { useSearchParams } from "react-router-dom";
import { useState , useEffect } from 'react';

export default function ClientUpdate( props ){ // 거래처 입력/등록 페이지

       const [ searchParams, setSearchParams ] = useSearchParams();
       const cno = searchParams.get('cno');

       // 2. 현재 게시물의 정보를 가지는 상태관리 변수
       const [ client , setClient ] = useState( {  } );

       // 3. 개별 게시물 호출 axios - [ 실행조건 : 컴포넌트 최초 1번 실행/열렸을때 ]
       const onGet = () => {
            axios.get( '/client/doGet' , { params : { cno : cno } } )
               .then( r => { console.log(r); setClient(r.data); } )
       }
           useEffect( ()=>{ onGet() } , [  ] );

     // 1. 등록함수
     const clientUpdate = (e) => { console.log(e);

         let clientForm = document.querySelectorAll('.clientForm')[0];
         let clientFormData = new FormData( clientForm ); console.log('폼데이터들어옵니다.'+clientFormData);

         // boardFormData : 입력받은 수정할제목,내용 +++ 수정할 게시물번호
         clientFormData.set( 'cno' , cno ); // 수정할 게시물번호를 폼 속성에 추가

         // 2. azio 통신
         axios.put("/client" , clientFormData)
                .then( r => {
                    console.log('거래처데이터들어옵니다.'+r);

                    if(r.data){
                        console.log(r);
                        alert('거래처 수정 성공');
                        window.location.href='/client/list?cno='+cno;
                    }else{
                        alert('거래처 수정 실패');
                    }
         })
}


    return(<>
        <div className="webcontainer">
             <h3> 거래처 수정 </h3>

             <form className="clientForm">
                <input type="text"
                    placeholder='거래처이름'
                    name="cname"
                    value={ client.cname }
                    onChange = { (e) => { setClient({ ...client , cname : e.target.value }) } }
                />    <br/>
                <input type="text"
                    placeholder='전화번호'
                    name="cphone"
                    value= { client.cphone }
                    onChange = { (e) => { setClient({ ...client , cphone : e.target.value }) } }
                 />    <br/>
                <input type="text"
                    placeholder='주소'
                    name="caddress"
                    value= { client.caddress }
                    onChange = { (e) => { setClient({ ...client , caddress : e.target.value }) } }
                />    <br/>
                 <button type="button" onClick={ clientUpdate } > 등록 </button>
             </form>
        </div>
    </>)
}
