/* import */
import axios from 'axios';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Pagination from '@mui/material/Pagination';

/*주문관리*/
export default function Order(props) {
// 0. 컴포넌트 상태변수 관리 [스프링으로부터 전달받은 객체]
    const [orderData, setOrderData] = useState({
        orderDtos: [],
        totalPages: 0,
        totalCount: 0
    });
    // 1. 스프링에게 전달한 객체
    const [pageInfo, setPageInfo] = useState({
        page: 1,
        key: 'ostate',
        keyword: '',
        view: 5
    });

    // 데이터베이스에 저장된 레코드 가져오기!!
    const getOrder = () => {
        axios.get('/order/get', { params: pageInfo })
            .then(r => {
                setOrderData(r.data);
                console.log(r.data);
            });
    };

    useEffect(() => {
        getOrder();
    }, [pageInfo.page, pageInfo.view]);

    const onPageSelect = (e, value) => {
        setPageInfo({ ...pageInfo, page: value });
    };

    // 검색버튼을 클릭했을때
    const onSearch = (e) => {
        setPageInfo({ ...pageInfo, page: 1 }); // 첫 페이지 1페이지로 초기화
        getOrder();
    };

    const onUpdate = (ono) => {
        let ostate = prompt('상태를 입력하세요:');

        if (!ostate) {
            alert('상태를 입력하지 않았습니다.');
            return;
        }

        let info = {
            ono: ono,
            ostate: ostate
        };

        axios.put("/order/put", info)
            .then(r => {
                if (r.data) {
                    alert('상태 변경 완료');
                    window.location.reload();
                } else {
                    alert('상태 변경 실패');
                }
            });
    };

    return (
        <>
            <div className="webcontainer">
                <h2>주문페이지</h2>
                <div className="addInventory"><Link to='/orderWrite'><span>주문등록</span> </Link></div>
                <table className="unitTable">
                   <tr className="trText">
                     <th style={{ width: '10%', padding: '10px' }}>주문번호</th>
                     <th style={{ width: '15%', padding: '10px' }}>주문상태</th>
                     <th style={{ width: '15%', padding: '10px' }}>주문요청사항</th>
                     <th style={{ width: '15%', padding: '10px' }}>주문날짜</th>
                     <th style={{ width: '15%', padding: '10px' }}>배송지주소</th>
                     <th style={{ width: '15%', padding: '10px' }}>회원아이디</th>
                     <th style={{ width: '15%', padding: '10px' }}>비고</th>
                   </tr>
                   {orderData.orderDtos.map((row) => (
                     <tr>
                       <td style={{ width: '10%', backgroundColor: 'white', padding: '10px' }}>{row.ono}</td>
                       <td style={{ width: '15%', backgroundColor: 'white', padding: '10px' }}>{row.ostate}</td>
                       <td style={{ width: '15%', backgroundColor: 'white', padding: '10px' }}>{row.orequest}</td>
                       <td style={{ width: '15%', backgroundColor: 'white', padding: '10px' }}>{row.cdate}</td>
                       <td style={{ width: '15%', backgroundColor: 'white', padding: '10px' }}>{row.odestination}</td>
                       <td style={{ width: '15%', backgroundColor: 'white', padding: '10px' }}>{row.mid}</td>
                       <td style={{ width: '15%', backgroundColor: 'white', padding: '10px' }}>
                             <button type="button" onClick={() => onUpdate(row.ono)}>상태변경</button>
                             {row.ostate === '취소' ? (
                                 <></>
                             ) : (
                                 <Link to={`/orderDetail?ono=${row.ono}`}>
                                     <button type="button">상세관리</button>
                                 </Link>
                             )}
                       </td>
                     </tr>
                   ))}
                </table>
        <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>
                <Pagination count={orderData.totalPages} onChange={onPageSelect}/>
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
                        <option style={{textAlign : 'center'}} value="ostate"> 상태 </option>
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
                                       (<> <button  type="button" onClick = { (e)=> { window.location.href="/order"; } } > 검색제거 </button>  </>)
                                   }
                </div>

       </div>
    </>
    );
}
