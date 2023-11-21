import axios from 'axios'; // axios
import { useSearchParams } from "react-router-dom";
import { useState , useEffect } from 'react';

export default function CompanyOrderWrite( props ){ // 발주 등록 페이지

     const [searchParams, setSearchParams] = useSearchParams();
     const pno = searchParams.get('pno');

     const onGet = () => {
          axios.get('/companyOrder/get', { params: { pno : pno } } )
              .then(r => { console.log(r); setSearchParams(r.data); })  // 쉼표 추가 및 r.data로 상태 업데이트
     }
     useEffect(() => { onGet() }, [  ]);

          const onCompanyOrder = (e) => {
              let info = {
                  pno : pno ,
                  coamount  : document.querySelector('.coamount').value ,
                  costate   : document.querySelector('.costate').value ,
                  cocontent : document.querySelector('.cocontent').value ,
              }; console.log(info);

      // 3. 통신
      axios
         .post( '/companyOrder' , info )
         .then( r => {
            console.log('데이터들어옵니다'+r);
            if(r.data){
                console.log(r.data);
               alert('발주 등록 성공');
               window.location.href = '/companyOrder/list';
            }else{
                  alert('발주 등록 실패');
          }
       })
    }


    return(<>
        <div className ="webcontainer">
            <h3> 발주 등록 </h3>
            { pno }
           <div className="inputdiv">
                <input type="text" placeholder="발주수량" className="coamount"/>    <br/>
                <input type="text" placeholder="발주상태" className="costate"/>     <br/>
                <input type="text" placeholder="발주내역" className="cocontent"/>   <br/>
                <button onClick={ onCompanyOrder } type="button"> 등록 </button>
            </div>
        </div>

    </>)

}