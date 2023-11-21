import { useSearchParams }  from 'react-router-dom';
import axios from 'axios';
import { useState , useEffect } from 'react';
import { Link } from 'react-router-dom';
// 페이징
import Pagination from '@mui/material/Pagination';

export default function ClientList( props ){ // 입력받은 거래처 리스트 페이지

      const [ searchParams , setSearchParams ] = useSearchParams();
      const cno = searchParams.get('cno');

       // 0. 컴포넌트 상태변수 관리 [ 스프링으로 전달받은 객체 ]
          const [ clients, setClients ] = useState({
              clientDtoList : [] ,
              totalPages : 0 ,
              totalCount : 0
          });

       // 0.스프링에게 전달하는 객체
          const[pageInfo , setPageInfo] = useState({
              page : 1 ,
              key : 'cname' ,
              keyword : '' ,
              view : 5
          }); console.log(pageInfo);

       // 1. 전체페이지 컨트롤에서 가져오기
          const getClients= (e) => {
              axios.get('/client/get',{ params : pageInfo } )
                  .then(r => {
                    console.log( r.data );
                    setClients(r.data);
                  });
          }
          useEffect( ()=>{   getClients();  } , [ pageInfo.page  , pageInfo.view ] );
     // 2. 페이징번호를 클릭했을때
          const onPageSelect = (e, value)=>{
            pageInfo.page = value; // 클릭한 페이지 번호로 변경
            setPageInfo({...pageInfo}); // 새로고침 [상태변수 주소값이 바뀌면 재랜더링 ]
          }

      // 3. 검색버튼을 눌렀을때
      const onSearch =(e)=>{
        setPageInfo(({...pageInfo , page : 1} ) ); // 첫페이지 1페이지로 초기화
        getClients();
      }

/*     // 1. axios를 이용한 스프링의 컨트롤과 통신
     useEffect( () => {
         axios.get('/client/get' , { params : pageInfo } ).then( r => {
             console.log(r);
             setClients(r.data); // 응답받은 모든 게시물을 상태변수에 저장
         // setState : 해당 컴포넌트가 업데이트([새로고침/재랜더링/return재실행)
         });
     } , [ ] );*/


        return (<>
            <div className="webcontainer">
              <h1> 거래처 목록 페이지 </h1>
              <div className="addInventory">
                <a href="/client/write">
                  <span>거래처등록하기</span>
                </a>
              </div>
              <select
                  style={{ float: 'left' }}
                  value = { pageInfo.view }
                  onChange={ (e)=>{  setPageInfo( { ...pageInfo , view : e.target.value} );  } }
                  >
                  <option value="5"> 5 </option>
                  <option value="10"> 10 </option>
                  <option value="20"> 20 </option>
              </select>
              <table className="unitTable" style={{ width: '100%' }}>
                <tr className="trText">
                  <th style={{ width: '10%' }}>거래처번호</th>
                  <th style={{ width: '15%' }}>거래처이름 </th>
                  <th style={{ width: '15%' }}>전화번호 </th>
                  <th style={{ width: '30%' }}>주소 </th>
                  <th style={{ width: '20%' }}>날짜 </th>
                  <th style={{ width: '10%' }}>비고 </th>
                </tr>

                {clients.clientDtoList.map((client) => (
                  <tr key={client.cno}>
                    <td style={{ width: '10%', backgroundColor: 'white' }}>{client.cno}</td>
                    <td style={{ width: '15%', backgroundColor: 'white' }}>{client.cname}</td>
                    <td style={{ width: '15%', backgroundColor: 'white' }}>{client.cphone}</td>
                    <td style={{ width: '30%', backgroundColor: 'white' }}>{client.caddress}</td>
                    <td style={{ width: '20%', backgroundColor: 'white' }}>{client.cdate}</td>
                    <td style={{ width: '10%', backgroundColor: 'white' }}>
                      <Link to={'/client/update?cno=' + client.cno}>
                        <button type="button"> 수정 </button>
                      </Link>
                    </td>
                  </tr>
                ))}
              </table>
               <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>
               {/* count : 전체페이지수 onChange : 페이지번호를 클릭/변경 했을때 이벤트*/}

              <Pagination count={clients.totalPages} onChange={onPageSelect}/>
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

                  <option style={{textAlign : 'center'}} value="cname"> 거래처이름 </option>
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
                         (<> <button  type="button" onClick = { (e)=> { window.location.href="/client/list"; } } > 검색제거 </button>  </>)
                       }
                </div>
            </div>
          </>);
}


