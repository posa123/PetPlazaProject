/* import */
import axios from 'axios';
import { useState, useEffect } from 'react';
import { useSearchParams } from "react-router-dom";
/* mui import*/
/*import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';*/

/*
mui : 리액트 전용 라이브러리
    1. 설치
            cd/src/main/reactapp
            npm install @mui/material @emotion/react @emotion/styled
            npm install @mui/material @mui/styled-engine-sc styled-components

    2. 예제
            1. 사용할 mui 컴포넌트를 상단에 import
                import Button from '@mui/material/Button';
            2. 호출된 mui 컴포넌트를 사용
                <Button variant="contained">Hello world</Button>;
*/



/* 상세관리 */
export default function OrderDetail(props) {

    /*상태관리*/
    const [orderDetailData, setOrderDetailData] = useState([]);
    const [searchParams, setSearchParams] = useSearchParams();

    const ono = searchParams.get('ono');

    const onGet = (e) => {
        axios.get('/orderdetail/get' , { params : { ono : ono}})
        .then(r => { setOrderDetailData(r.data);})
    }
    console.log(orderDetailData)
    useEffect(() => onGet() , []);

    const onDelete = (dno) => { // dno : 상세관리번호
        // 사용자에게 정말로 삭제할 것인지 물어보기
        const confirmDelete = window.confirm('정말로 삭제하시겠습니까?');

        if (confirmDelete) {
            axios.delete('/orderdetail/delete', { params: { dno: dno } })
                .then(r => {
                    if (r.data) {
                        alert('삭제 성공');
                        window.location.reload(); // 리로드 , 새로고침
                    } else {
                        alert('삭제 실패');
                    }
                });
        }
    }


    const onUpdate = (dno) => { // dno : 상세관리번호

            let damount = document.querySelector('.damountChange').value;

            if (!damount) {
                    alert('수량을 입력해주세요.');
                    return;
                }

           let info = {
                dno : dno ,
                damount : damount
            }

            axios.put('/orderdetail/put' , info)
                        .then(r =>{
                            if(r.data) {
                                alert('수정 성공')
                                window.location.reload(); // 리로드 , 새로고침
                            } else {
                                alert('수정 실패')
                            }
                        })

        }


    return (<>
         <div className="webcontainer">
              <h3>상세 주문 관리</h3> <br/>
            <table className="unitTable">
                    <tr>
                           <th style={{ width: '15%' }}>주문번호</th>
                           <th style={{ width: '10%' }}>주문상세번호</th>
                           <th style={{ width: '10%' }}>수량</th>
                           <th style={{ width: '10%' }}>주문가격</th>
                           <th style={{ width: '10%' }}>총가격</th>
                           <th style={{ width: '10%' }}>제품번호</th>
                           <th style={{ width: '15%' }}>제품이름</th>
                           <th style={{ width: '10%' }}>비고</th>
                    </tr>
                    {orderDetailData.map((row) => (
                        <tr>
                           <td style={{ width: '5%', backgroundColor: 'white' }}>{row.ono}</td>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{row.dno}</td>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{row.damount}</td>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{row.dprice}</td>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{row.dprice * row.damount}</td>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{row.pno}</td>
                           <td style={{ width: '25%', backgroundColor: 'white' }}>{row.pname}</td>
                           <td style={{ width: '20%', backgroundColor: 'white' }}>
                                <button type="button" onClick={() => onDelete(row.dno)}>제품삭제</button>
                               <input
                               style={{ width: '100px', height: '20px' }}
                               className = "damountChange"
                               type ="number" placeHolder = "수량 입력"
                               />
                               <button type="button" onClick={() => onUpdate(row.dno)}>수량변경</button>
                           </td>
                        </tr>
                    ))}
            </table>
        </div>
    </>)



}