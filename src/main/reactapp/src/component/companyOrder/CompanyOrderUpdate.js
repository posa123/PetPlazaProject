import axios from 'axios'; // axios
import { useSearchParams } from "react-router-dom";
import { useState , useEffect } from 'react';

export default function CompanyOrderUpdate( props ){

    const [ searchParams , setSearchParams ] = useSearchParams();
    const cono = searchParams.get('cono');

    // 2. 현재 게시물의 정보를 가지는 상태관리 변수
    const [ companyOrder , setCompanyOrder ] = useState( {  } );

    const onGet = () => {
        axios.get('/companyOrder/doGet', { params: { cono: cono } })
            .then(r => { console.log(r); setCompanyOrder(r.data); })  // 쉼표 추가 및 r.data로 상태 업데이트
    }
    useEffect(() => { onGet() }, []);

    const companyOrderUpdate = (e) => {
        let info = {
            cono : cono ,
            coamount: document.querySelector('.coamount').value,
            costate: document.querySelector('.costate').value,
            cocontent: document.querySelector('.cocontent').value,
        };console.log(info);

        // 3. 통신
        axios.put('/companyOrder', info)
            .then(r => {
                if (r.data) {
                    alert('발주 수정 성공');
                    window.location.href = '/companyOrder/list';
                } else {
                    alert('발주 수정 실패');
                }
            });
    }

    return(<>
        <div className ="webcontainer">
        <h3> 발주 수정 페이지 </h3>
        <div className ="inputdiv">
            <input type="text"
                placeholder="발주수량"
                className="coamount"
                value={ companyOrder.coamount }
                onChange = { (e) => { setCompanyOrder({ ...companyOrder , coamount : e.target.value }) } }
            />    <br/>
            <input type="text"
                placeholder="발주상태"
                className="costate"
                value={ companyOrder.costate }
                onChange = { (e) => { setCompanyOrder({ ...companyOrder , costate : e.target.value }) } }
            />     <br/>
            <input type="text"
                placeholder="발주내역"
                className="cocontent"
                value={ companyOrder.cocontent }
                onChange = { (e) => { setCompanyOrder({ ...companyOrder , cocontent : e.target.value }) } }
            />   <br/>
            <button onClick={ companyOrderUpdate } type="button"> 등록 </button>
            </div>
        </div>
    </>)
}