import { useSearchParams }  from 'react-router-dom';
import axios from 'axios';
import { useState , useEffect } from 'react';
import { Link } from 'react-router-dom';
// 페이징
import Pagination from '@mui/material/Pagination';

export default function CompanyOrderList( props ){ // 발주 리스트 페이지

    const [ searchParams , setSearchParams ] = useSearchParams();
    const cono = searchParams.get('cono');

    // 0. 컴포넌트 상태변수 관리 [ 스프링으로 전달받은 객체 ]
    const [ companys, setCompanys ] = useState({
                  companyOrderDtoList : [] ,
                  totalPages : 0 ,
                  totalCount : 0
              });

           // 0.스프링에게 전달하는 객체
              const[pageInfo , setPageInfo] = useState({
                  page : 1 ,
                  key : '' ,
                  keyword : '' ,
                  view : 5
              }); console.log(pageInfo);

           // 1. 전체페이지 컨트롤에서 가져오기
              const getCompanys= (e) => {
                  axios.get('/companyOrder/get',{ params : pageInfo } )
                      .then(r => {
                        console.log( r.data );
                        setCompanys(r.data);
                      });
              }
              useEffect( ()=>{   getCompanys();  } , [ pageInfo.page  , pageInfo.view ] );
              // 2. 페이징번호를 클릭했을때
              const onPageSelect = (e, value)=>{
                   pageInfo.page = value; // 클릭한 페이지 번호로 변경
                   setPageInfo({...pageInfo}); // 새로고침 [상태변수 주소값이 바뀌면 재랜더링 ]
              }

          // 3. 검색버튼을 눌렀을때
          const onSearch =(e)=>{
            setPageInfo(({...pageInfo , page : 1} ) ); // 첫페이지 1페이지로 초기화
            getCompanys();
          }

   /* // 1. axios를 이용한 스프링의 컨트롤과 통신
    useEffect( () => {
       axios.get('/companyOrder/get').then(r => {
          console.log(r);
          setRows(r.data); // 응답받은 모든 게시물을 상태변수에 저장
          // setState : 해당 컴포넌트가 업데이트([새로고침/재랜더링/return재실행)

        });
    } , [ ] );*/

  return (<>
          <div className="webcontainer">
              <h1>발주 목록</h1>
              <select
                  style={{ float: 'left' }}
                  value = { pageInfo.view }
                  onChange={ (e)=>{  setPageInfo( { ...pageInfo , view : e.target.value} );  } }
              >
                  <option value="5"> 5 </option>
                  <option value="10"> 10 </option>
                  <option value="20"> 20 </option>
              </select>
              <table className="unitTable">
                  <tr className="trText">
                      <th style={{ width: '20%' }}>발주번호</th>
                      <th style={{ width: '20%' }}>발주수량</th>
                      <th style={{ width: '20%' }}>발주상태</th>
                      <th style={{ width: '20%' }}>발주내역</th>
                      <th style={{ width: '20%' }}>비고</th>
                  </tr>

                  { companys.companyOrderDtoList.map((company) => ( // 발주 출력
                      <tr key={company.cono}>
                          <td style={{ width: '20%', backgroundColor: 'white' }}>{company.cono}</td>
                          <td style={{ width: '20%', backgroundColor: 'white' }}>{company.coamount}</td>
                          <td style={{ width: '20%', backgroundColor: 'white' }}>{company.costate}</td>
                          <td style={{ width: '20%', backgroundColor: 'white' }}>{company.cocontent}</td>
                          <td style={{ width: '20%', backgroundColor: 'white' }}>
                              <Link to={'/companyOrder/update?cono=' + company.cono}>
                                  <button type="button"> 수정하기 </button>
                              </Link>
                          </td>
                      </tr>
                  ))}
              </table>
                <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>
                {/* count : 전체페이지수 onChange : 페이지번호를 클릭/변경 했을때 이벤트*/}

                <Pagination count={companys.totalPages} onChange={onPageSelect}/>
                </div>
                {/*검색 프론트 */}
                <div style={{margin : '20px' }}>
                <select
                  value={ pageInfo.key }
                  style={{width : '50px',}}
                  onChange ={
                        (e) =>{setPageInfo({...pageInfo , key : e.target.value } ) }
                  }
                    >
                  <option style={{textAlign : 'center'}} value="costate"> 발주상태 </option>
                  <option style={{textAlign : 'center'}} value="cocontent"> 발주내역 </option>
                  </select>
                  <input type="text"
                  value = { pageInfo.keyword }
                  onChange = {
                  (e)=>{setPageInfo({...pageInfo , keyword : e.target.value })}
                  }
                  />
                    <button type="button" onClick={onSearch}> 검색 </button>
                     {
                         pageInfo.keyword == '' ?
                         (<> </>)
                                :
                         (<> <button  type="button" onClick = { (e)=> { window.location.href="/companyOrder/list"; } } > 검색제거 </button>  </>)
                       }
                </div>


          </div>
      </>);
  }
