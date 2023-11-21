import styles from '../../css/unitprice.css';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import Pagination from '@mui/material/Pagination';

export default function UnitPriceWrite(props) {
    const [selectedClient, setSelectedClient] = useState([]);
   const [unitPrices, setUnitPrices] = useState([]);
   const [ searchParams , setSearchParams ] = useSearchParams();
   const cno = searchParams.get('cno');


        // 거래처 선택 시 호출되는 함수
             const selectClient = (client) => {
                    // 선택한 거래처 정보를 상태에 업데이트
                    setSelectedClient(client);
                    console.log('선택된 클라이언트 번호:', client.cno);
                };


      // 0. 컴포넌트 상태변수 관리 [ 스프링으로 전달받은 객체 ]
                const [ clients, setClients ] = useState({
                    clientDtoList : [] ,
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


    const unitpriceWrite = () => {
        if (selectedClient) {
            const unitPriceForm = document.querySelectorAll('.unitPriceForm')[0];
            const unitPriceFormData = new FormData(unitPriceForm);
            unitPriceFormData.set('cno', selectedClient.cno);
            axios.post("/unitprice/post", unitPriceFormData)
                .then((r) => {
                    if (r.data) {
                        alert('단가 등록 성공');
                        window.location.href = '/unitPrice';
                    } else {
                        alert('단가 등록 실패');
                    }
                })
                .catch(error => {
                    console.error("단가 등록 중 오류 발생:", error);
                });
        } else {
            alert('거래처를 선택해주세요.');
        }
    };

    return (
        <div className="webcontainer">
            <h2>신규 등록</h2>

            {/* 거래처 리스트 표시 */}
            <table className="unitTable">
              <tr className="trText">
                <th> <input type="radio" name="check" disabled/> </th>
                <th>거래처 이름</th>
              </tr>
              {clients.clientDtoList.map((client) => (
                <tr>
                  <td>
                  <input
                    type="radio"
                    name="selectedClient"
                    value={client.cno}
                    checked={client.cno === selectedClient.cno}
                    onChange={() => selectClient(client)}
                  />
                  </td>
                  {/* 거래처 이름 가져오기 */}
                  <td className="tdcss">{client.cname}</td>
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
                              <option style={{textAlign : 'center'}} value="title"> 이름 </option>
                              <option style={{textAlign : 'center'}} value="content"> 거래처 </option>
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
                                     (<> <button  type="button" onClick = { (e)=> { window.location.href="/client"; } } > 검색제거 </button>  </>)
                                   }
                            </div>
            {/* 단가 등록 폼 */}
            <form className="unitPriceForm">
              {/* 재고 이름 입력란 */}
              <input type="text" placeholder="재고이름" name="uname" className="smallInput" /><br/>

              {/* 재고 단가 입력란 */}
              <input type="text" placeholder="재고단가" name="uoriginal" className="smallInput" /><br/>

              {/* 등록 버튼 */}
              <button type="button" className="formBtn" onClick={unitpriceWrite}>등록</button>
            </form>

        </div>
    );
   }
