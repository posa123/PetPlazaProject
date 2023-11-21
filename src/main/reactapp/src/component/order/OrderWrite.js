/* import */
import axios from 'axios';
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
// ------------ mui table 관련 컴포넌트 import --------------- //
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
// --------------------------- //

// ------------------------ Pagination ---------------------- //
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
// ---------------------------------------------------------- //


export default function OrderWrite(props) {

// ----------------회원 목록 페이징 -----------------------//
    // 페이징 처리
        let [ memberPageDto , setMemberPageDto ] = useState({
            memberDtoList : [] ,
            totalPages : 0 ,
            totalCount : 0

        }); // 페이징 처리 end
        const [ mPageInfo , setMpageInfo ] = useState({
            page : 1 , view : 5 , key : 'mid' , keyword : ''
        });

        const getMembers = (e)=>{
                axios.get('/member/get',{ params : mPageInfo } )
                            .then(r =>{setMemberPageDto(r.data)})
            }
            useEffect(()=>{
              getMembers();
            },[ mPageInfo , mPageInfo.view ] )


            const onPageSelect = ( e , value ) => {
                mPageInfo.page = value;
                console.log(value);
                setMpageInfo({...mPageInfo});
                getMembers();
            }

            const onSearch=(e) =>{ getMembers(); setMpageInfo({...mPageInfo , page : 1 })}
            console.log(mPageInfo);

// ----------------회원 목록 페이징-----------------------//




 // ----------------제품 목록 페이징 -----------------------//

    // 0. 컴포넌트 상태변수 관리 [스프링으로부터 전달받은 객체]
        let [productPageDto, setProductPageDto] = useState({
            boardDtos : [] , totalPages : 0 , totalCount : 0
        }); // 스프링에서 전달해주는 DTO와 일치화

        // 0. 스프링에게 전달한 객체
        const [ pageInfo, setPageInfo ] = useState({
            page : 1 , key : 'pname' , keyword : '' , view : 5
        }); console.log(pageInfo);

        // 1-1. axios를 이용한 스픠링의 컨트롤과 통신
        const getProducts = (e) => {
            axios.get('/product/get', {params : pageInfo})
                    .then(r =>{   // r.data : pageDto // r.data.boardDtos : pageDto 안에 있는 boardDtos
                        setProductPageDto(r.data); // 응답받은 모든 게시물을 상태변수에 저장 // setState : 해당 컴포넌트가 업데이트(새로고침/재랜더링/return재실행)
                    });
        }
        // 1-2. 컴포넌트가 생성될 때 // + 의존성배열 : page (주소값) 변경될때  // +의존성배열 : view (주소값) 변경될떄
        useEffect(()=>{getProducts();},[pageInfo.page, pageInfo.view]);

        // 2. 페이지 번호를 클릭했을떄
        const onPageSelect2 = ( e , value ) => { console.log(value);
            pageInfo.page = value; // 클릭한 페이지번호로 변경
            console.log(value);
            setPageInfo({...pageInfo}); // 새로고침 [상태변수의 주소값이 바뀌면 재랜더링]
            getProducts();
         }
         // 3. 검색 버튼을 눌렀을 때 // 첫페이지 1페이지 초기화
         const onSearch2 = (e) =>{
            setPageInfo({...pageInfo , page : 1}); // 첫페이지 1페이지 초기화
            getProducts();
         }

 // ----------------제품 목록 페이징 -----------------------//

 // ----------------회원 상태 관리 -----------------------//

     // 4. 리스트에서 회원을 선택했을때
  const [selectedMemberId, setSelectedMemberId] = useState({ mno: '', mid: '' });

      // 클릭한 상품을 선택하는 함수
      const memberChoice = (row) => {
          setSelectedMemberId({ mno: row.mno, mid: row.mid });
      };
   // ----------------회원 상태 관리 -----------------------//



  // ----------------제품 상태 관리 -----------------------//

    // 1. 리스트에서 제품을 선택했을때
 const [selectedProductName, setSelectedProductName] = useState({ pno: '', pname: '' });

     // 클릭한 상품을 선택하는 함수
     const productChoice = (product) => {
         setSelectedProductName({ pno: product.pno, pname: product.pname });
     };
  // ----------------제품 상태 관리 -----------------------//


    const onCreate = (e) => {
        // 1. 폼(변수 name값으로 식별) 가져오기 폼전송은 RequestBody 안씀
        let orderForm = document.querySelectorAll('.orderForm')[0];
        let orderFormData = new FormData(orderForm);

            // 폼에 데이터 추가
        orderFormData.set('pno', selectedProductName.pno );
        orderFormData.set('mno', selectedMemberId.mno)


        // 2. axios 전송
        axios.post("/order/post", orderFormData)
            .then(result => {
                console.log(result.data);
                if (result.data) {
                    alert('글 등록 성공');
                    window.location.href = '/order';
                } else {
                    alert('글 등록 실패')
                }
            });
    }

    return(<>

             <div className="webcontainer">
                                     <h1>회원관리 페이지</h1>
                                     <select
                                     style={{float : 'left'}}
                                     value={mPageInfo.view}
                                     onChange={(e)=>{setMpageInfo({...mPageInfo , view : e.target.value }); }}
                                     >
                                         <option value="5">5</option>
                                         <option value="15">15</option>
                                         <option value="20">20</option>
                                     </select>
                                       <table className="unitTable" style={{ width: '100%' }}>
                                         {/*테이블 제목 구역*/}
                                            <tr className="trText">
                                             <th style={{ width: '10%' }}>회원번호</th>
                                             <th style={{ width: '15%' }}>회원이름</th>
                                             <th style={{ width: '10%' }}>회원아이디</th>
                                             <th style={{ width: '20%' }}>회원전화번호</th>
                                             <th style={{ width: '30%' }}>회원가입일시</th>
                                         </tr>
                                         {/*테이블 내용 구역*/}
                                                   { memberPageDto.memberDtoList.map((row) => (  // map is not function
                                                     <tr>
                                                         <td style={{ width: '10%', backgroundColor: 'white' }}>{row.mno}</td>
                                                         <td style={{ width: '15%', backgroundColor: 'white' }}>{row.mname}</td>
                                                         <td style={{ width: '10%', backgroundColor: 'white' }} onClick={() => memberChoice(row)}>{row.mid}</td>
                                                         <td style={{ width: '20%', backgroundColor: 'white' }}>{row.mphone}</td>
                                                         <td style={{ width: '30%', backgroundColor: 'white' }}>{row.cdate}</td>
                                                     </tr>

                                                     ))}
                                               </table>
                                                <div>{selectedMemberId.mno} : {selectedMemberId.mid}</div>
                                         <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>

                                             { /* count : 전체페이지수   onChange : 페이지번호를 클릭/변경 했을떄 이벤트 */}
                                             <Pagination  count={ memberPageDto.totalPages } onChange={ onPageSelect } />
                                         </div>

                                         {/*검색기능구역*/}
                                         <div style={{ display : 'flex' , justifyContent : 'center' , margin : '20px'}}>
                                             <select value={mPageInfo.key}
                                             onChange={ (e)=>{setMpageInfo({...mPageInfo , key : e.target.value})} }
                                             >
                                                 <option value="mname">이름</option>
                                                 <option value="mid">아이디</option>
                                             </select>
                                             <input type="text"
                                             value={mPageInfo.keyword}
                                             onChange={(e)=>{setMpageInfo({...mPageInfo , keyword : e.target.value})}}
                                             />
                                             <button type="button" onClick={onSearch}>검색</button>
                                         </div>
                                         {/*싱픔목록리스트 시작*/}
                                              <h3>상품 목록 페이지</h3>
                                                 <select
                                                     style={{ float: 'left' }}
                                                     value={pageInfo.view}
                                                     onChange={(e) => { setPageInfo({ ...pageInfo, view: e.target.value }); }}
                                                 >
                                                     <option value="5"> 5 </option>
                                                     <option value="10"> 10 </option>
                                                     <option value="20"> 20 </option>
                                                 </select>
                                                 <div className="addInventory"> <a href="/product/post">상품등록</a> </div>
                                                 <table className="unitTable">
                                                     {/* 테이블 제목 구역 */}
                                                     <tr className="trText">
                                                         <th style={{ width: '10%' }}>상품번호</th>
                                                         <th style={{ width: '15%' }}>상품이미지</th>
                                                         <th style={{ width: '15%' }}>상품이름</th>
                                                         <th style={{ width: '15%' }}>상품수량</th>
                                                         <th style={{ width: '15%' }}>상품가격</th>
                                                         <th style={{ width: '15%' }}>상품등록날짜</th>
                                                     </tr>
                                                     {/* 테이블 내용 구역 */}
                                                     {productPageDto.productDtoList?.map((p) => (
                                                         <tr>
                                                             <td style={{ width: '20%', backgroundColor: 'white' }}>{p.pno}</td>
                                                              <td style={{ width: '20%', backgroundColor: 'white' }}>
                                                                         <img
                                                                             src={ "http://192.168.17.34:8080/static/media/"+p.imageUrl} // 상품 이미지 URL
                                                                             style={{ width: '125px', height: '162px' }} // 이미지 크기 조절
                                                                         />
                                                              </td>
                                                             <td style={{ width: '10%', backgroundColor: 'white' }} onClick={() => productChoice(p)}>{p.pname}</td>
                                                             <td style={{ width: '25%', backgroundColor: 'white' }}>{p.uincrease}</td>
                                                             <td style={{ width: '15%', backgroundColor: 'white' }}>{p.pprice}</td>
                                                             <td style={{ width: '20%', backgroundColor: 'white' }}>{p.cdate}</td>
                                                         </tr>
                                                     ))}
                                                 </table>
                                             <div>{selectedProductName.pno} : {selectedProductName.pname}</div>
                                                 {/* 검색 */}
                                                 <div style={{ margin: '20px' }}>
                                                     <select
                                                         style={{ width: '100px', height: '30px' }}
                                                         value={pageInfo.key}
                                                         onChange={(e) => { setPageInfo({ ...pageInfo, key: e.target.value }); }}
                                                     >
                                                         <option value="pname"> 상품명 </option>
                                                     </select>

                                                     <input
                                                         type="text"
                                                         value={pageInfo.keyword}
                                                         onChange={(e) => { setPageInfo({ ...pageInfo, keyword: e.target.value }); }}
                                                     />
                                                     <button type="button" onClick={onSearch2}>검색</button>
                                                 </div>

                                                 <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', margin: '10px' }}>
                                                     <Pagination count={productPageDto.totalPages} onChange={onPageSelect2} />
                                                 </div>
                                       <form class = "orderForm">
                                         <input type = "text" placeholder = '배송지주소' name = "odestination"/> < br/>
                                         <input type = "number" placeholder = '수량' name = "damount"/> < br/>
                                         <textarea placeholder = '요청사항' name = "orequest"></textarea>   < br/>

                                         <button type = "button" onClick = { onCreate }>등록</button>
                                     </form>
                                 </div>
                 </>)
             }