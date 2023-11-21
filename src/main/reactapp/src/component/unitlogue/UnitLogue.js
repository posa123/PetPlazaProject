import styles from '../../css/unitprice.css';
import axios from 'axios';
import { useState, useEffect } from 'react';

// 페이징 처리에 관한 라이브러리 import
import Pagination from '@mui/material/Pagination';

export default function UnitLogue(props) {


// 1. 수정 함수및 axios 통신
  const onUpdate = (ulno) => {
    const uincrease = prompt(`수정할 재고수량을 입력하세요:`); // 수정할 재고수량을 입력받아서 uincrease 변수에 저장
    const urecord = prompt(`수정할 재고기록내용 입력하세요:`); // 수정할 재고기록내용 입력받아서 uincrease 변수에 저장

    // 입력받은 재고수량, 재고기록내용을 info라는 변수명으로 객촤화시킨다 여기서 ulno도 같이 담아서 객채화 시킨다.
    const info = {
      ulno: ulno,
      uincrease: uincrease,
      urecord: urecord,
    }; console.log(info);

    // 수정 axios통신
    axios
      .put("/unitlogue/put", info)
      .then(r => {
        if (r.data) {
          alert('재고 수정 완료');
          window.location.reload();
        } else {
          alert('재고 수정 실패');
        }
      })
  }
  // 2. 삭제 함수및 axios 통신
  const onDelete=(ulno) =>{
        let confirmDelete = window.confirm('삭제하시겠습니까?');
        //  window.confirm 함수를 사용하여 사용자에게 확인 대화 상자를 표시하고,
        // 사용자가 "확인" 또는 "취소" 버튼을 누르면 해당 버튼에 따른 불리언 값을 반환하는 기능


        // 사용자가 취소를 누르면 삭제가안된다.
        if(!confirmDelete){
            return;
        }

        axios
          .delete(`/unitlogue/delete?ulno=${ulno}`)
          .then(r => {
            if (r.data) {
              alert('재고기록 삭제 완료');
              window.location.reload();
            } else {
              alert('재고기록 삭제 실패');
            }
          })
  }
  // 1. 상태 관리 변수
    const [unitLogue, setUnitLogue] = useState({
        dtos : [] ,
        totalPages : 0,
        totalCount : 0
    });
    const [pageInfo , setPageInfo] = useState({
            page: 1,
            key: '',
            keyword: '',
            view : 5
    });console.log(pageInfo);

    // 데이터베이스에 저장된 레코드 가져오기!!
    const getUnitLogue = (e) => {
      axios.get('/unitlogue/get' , {params : pageInfo} )
        .then(r => {
          setUnitLogue(r.data);
          console.log(r.data);
        });
    }
    useEffect( ()=>{ getUnitLogue(); } , [ pageInfo.page  , pageInfo.view ] );
  //
  const onPageSelect =(e, value)=>{
    pageInfo.page = value; // 클릭한 페이지 번호로 변경
    setPageInfo({...pageInfo}); // 새로고침 [ 상태변수 주소값이 바뀌면 재랜더링 ]
  }
  // 3. 검색버튼을 클릭했을때
  const onSearch =(e)=>{
      setPageInfo(({...pageInfo , page : 1} ) ); // 첫페이지 1페이지로 초기화
      getUnitLogue();
    }
  return (
    <>
      <div className="webcontainer">
            <h1> 재고 기록 페이지</h1>
            <select
                style={{ float: 'left' }}
                value={pageInfo.view}
                onChange={(e) => { setPageInfo({ ...pageInfo, view: e.target.value }); }}
            >
                <option value="5"> 5 </option>
                <option value="10"> 10 </option>
                <option value="15"> 15 </option>
            </select>
        <table className="unitTable">
            <tr className="trText">
              <th style={{ width : '10%'}}>기록번호</th>
              <th style={{ width : '20%'}}>제품이름</th>
              <th style={{ width : '10%'}}>재고수량</th>
              <th style={{ width : '15%'}}>재고기록내용</th>
              <th style={{ width : '25%'}}>재고수정날짜</th>
              <th style={{ width : '20%'}}>비고</th>
            </tr>
            {unitLogue.dtos.map(u => (
              <tr>
                <td style={{ width : '10%'}} className="tdcss">{u.ulno}</td>
                <td style={{ width : '20%'}} className="tdcss">{u.pname}</td>
                <td style={{ width : '10%'}} className="tdcss">{u.uincrease}</td>
                <td style={{ width : '10%'}} className="tdcss">{u.urecord}</td>
                <td style={{ width : '25%'}} className="tdcss">{u.udate}</td>
                <td style={{ width : '20%'}}>
                  <button type="button" onClick={() => onUpdate(u.ulno)}>수정</button>
                  <button type="button" onClick={() => onDelete(u.ulno)}>삭제</button>
                </td>
              </tr>
            ))}
        </table>
        <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>
        <Pagination count={unitLogue.totalPages} onChange={onPageSelect}/>
        </div>
        {/*검색 프론트 */}
         <div style={{margin : '20px' }}>
            <select
            value={pageInfo.key}
            style={{width : '100px', height : '30px'}}
            onChange ={
                (e) =>{setPageInfo({...pageInfo , key : e.target.value } ) }
                }
            >
                <option style={{textAlign : 'center'}} value="title"> 제목 </option>
                <option value="content"> 내용 </option>
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
