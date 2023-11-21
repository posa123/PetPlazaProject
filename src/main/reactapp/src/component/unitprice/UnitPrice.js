import React, { useEffect, useState } from 'react'; // React와 useState, useEffect 임포트
import axios from 'axios';
import styles from '../../css/unitprice.css'

// 페이징
import Pagination from '@mui/material/Pagination';

export default function UnitPrice(props) {



    const onUpdate = (uno) => {
      // uno에 해당하는 재고를 찾음
      const currentUnitPrice = unitPrices.dtos.find(unitPrices => unitPrices.uno == uno);

     /* // 재고이름 입력안하면 수정이 안되게 유효성 검사로 막아놈
     if (!uname) {
            alert('재고이름을 입력해주세요.');
           return;
     }*/
       // 사용자로부터 새로운 재고이름 입력 받음
      const uname = prompt(`수정할 재고이름을 입력하세요:`);

      // 사용자로부터 새로운 재고단가 입력 받음
      const uoriginal = prompt(`수정할 재고단가를 입력하세요:`);
      // 단가를 입력안하면 수정이 안되게 유효성 검사로 막아놈
      if (!uoriginal) {
          alert('재고단가를 입력해주세요.');
          return;
      }

      // 수정할 정보를 담은 객체 생성
      let info = {
        uno: uno,
        // 만약 사용자가 재고이름을 입력했으면 그 값 사용, 입력하지 않았으면 기존의 값 사용
        uname: uname || currentUnitPrice.uname,
        // 만약 사용자가 재고단가를 입력했으면 그 값 사용, 입력하지 않았으면 기존의 값 사용
        uoriginal: uoriginal || currentUnitPrice.uoriginal
      }; console.log(info);

        axios
            .put("/unitprice/put", info)
            .then(r => {
                if (r.data) {
                    alert('재고 수정 완료');
                    getUnitPrice();
                } else {
                    alert('재고 수정 실패');
                }
            });
    }
      let onDelete = (uno) => {
          console.log(uno);
          let confirmDelete = window.confirm('삭제하시겠습니까?');

          if (!confirmDelete) {
              return;
          }
        // 1. uno 컨트롤에게  보내서  해당하는 uno 삭제
          axios
              .delete(`/unitprice/delete?uno=${uno}`)
              .then(r => {
                  if (r.data) {
                      alert('재고단가 삭제 성공');
                      window.location.reload();
                  } else {
                      alert('재고단가 삭제 실패');
                  }
              });
      }
       // 0. 컴포넌트 상태변수 관리 [ 스프링으로 전달받은 객체 ]
          const [unitPrices, setUnitPrices] = useState({
              dtos : [] ,
              totalPages : 0,
              totalCount : 0
          });

       // 0.스프링에게 전달하는 객체
          const[pageInfo , setPageInfo] = useState({
              page : 1,
              key : '',
              keyword : '',
              view : 5
          }); console.log(pageInfo);

       // 1. 전체페이지 컨트롤에서 가져오기
          const getUnitPrice= (e) => {
              axios.get('/unitprice/get',{ params : pageInfo } )
                  .then(r => {
                      setUnitPrices(r.data);
                  });
          }
          useEffect( ()=>{   getUnitPrice();  } , [ pageInfo.page  , pageInfo.view ] );
     // 2. 페이징번호를 클릭했을때
               const onPageSelect = (e, value)=>{
               pageInfo.page = value; // 클릭한 페이지 번호로 변경
               setPageInfo({...pageInfo}); // 새로고침 [상태변수 주소값이 바뀌면 재랜더링 ]
           }

      // 3. 검색버튼을 눌렀을때
      const onSearch =(e)=>{
        setPageInfo(({...pageInfo , page : 1} ) ); // 첫페이지 1페이지로 초기화
        getUnitPrice();
      }

    return (
        <>
        <div className="webcontainer">
            <h1>거래처 단가 페이지</h1>
             <select
                style={{float : 'left'}}
                value = { pageInfo.view }
                onChange={ (e)=>{  setPageInfo( { ...pageInfo , view : e.target.value} );  } }
                >
                <option value="5"> 5 </option>
                <option value="10"> 10 </option>
                <option value="20"> 20 </option>
            </select>

            <div className="addInventory"><a href="/unitprice/list"> <span>재고신규등록</span> </a></div>
           <table className="unitTable" style={{ width: '100%' }}>
                   <tr className="trText">
                       <th style={{ width: '10%' }}>재고번호</th>
                       <th style={{ width: '25%' }}>재고이름</th>
                       <th style={{ width: '10%' }}>재고단가</th>
                       <th style={{ width: '20%' }}>재고등록날짜</th>
                       <th style={{ width: '20%' }}>재고수정날짜</th>
                       <th style={{ width: '15%' }}>비고</th>
                   </tr>
                   {unitPrices.dtos.map((unitprice) => (
                       <tr>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{unitprice.uno}</td>
                           <td style={{ width: '25%', backgroundColor: 'white' }}>{unitprice.uname}</td>
                           <td style={{ width: '10%', backgroundColor: 'white' }}>{unitprice.uoriginal}</td>
                           <td style={{ width: '20%', backgroundColor: 'white' }}>{unitprice.cdate}</td>
                           <td style={{ width: '20%', backgroundColor: 'white' }}>{unitprice.udate}</td>
                           <td style={{ width: '15%', backgroundColor: 'white' }}>
                               <button type="button" onClick={() => onUpdate(unitprice.uno)}>수정</button>
                               <button type="button" onClick={() => onDelete(unitprice.uno)}>삭제</button>
                           </td>
                       </tr>
                   ))}
           </table>

            <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>
            {/* count : 전체페이지수 onChange : 페이지번호를 클릭/변경 했을때 이벤트*/}

            <Pagination count={unitPrices.totalPages} onChange={onPageSelect}/>
            </div>
            {/*검색 프론트 */}
            <div style={{margin : '20px' }}>
                <select
                value={pageInfo.key}
                style={{width : '50px',}}
                onChange ={
                    (e) =>{setPageInfo({...pageInfo , key : e.target.value } ) }
                    }
                >
                    <option style={{textAlign : 'center'}} value="uname"> 이름 </option>
                </select>
                <input type="text"
                value = {pageInfo.keyword}
                onChange = {
                (e)=>{setPageInfo({...pageInfo , keyword : e.target.value })}
                     }
                />
               <button type="button" onClick={onSearch}> 검색 </button>
                               {
                                   pageInfo.keyword == '' ?
                                   (<> </>)
                                   :
                                   (<> <button  type="button" onClick = { (e)=> { window.location.href="/UnitPrice"; } } > 검색제거 </button>  </>)
                               }
            </div>
        </div>
        </>
    );
}








































