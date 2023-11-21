import axios from 'axios';
import { useState, useEffect } from 'react';
//import styles from '../../css/product.css'; //CSS 이상하게 먹어서 주석처리함

import {  Link   } from 'react-router-dom';

// --------------mui table 관련 컴포넌트 import---------------- //
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
// ------------------------ Pagination ---------------------- //
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
// ---------------------------------------------------------- //


export default function ProductList(props) {

    // 0. 컴포넌트 상태변수 관리 [스프링으로부터 전달받은 객체]
    let [productPageDto, setProductPageDto] = useState({
        productDtoList : [] , totalPages : 0 , totalCount : 0
    }); // 스프링에서 전달해주는 DTO와 일치화
    console.log(productPageDto);
    // 1. 스프링에게 전달한 객체
    const [ pageInfo, setPageInfo ] = useState({
        page : 1 , key : 'pname' , keyword : '' , view : 5
    }); console.log(pageInfo);

    // 1-1. axios를 이용한 스픠링의 컨트롤과 통신
    const getProducts = async (e) => {
        await axios.get('/product/get', {params : pageInfo})
                .then(r =>{   // r.data : pageDto // r.data.boardDtos : pageDto 안에 있는 boardDtos
                    setProductPageDto(r.data); // 응답받은 모든 게시물을 상태변수에 저장 // setState : 해당 컴포넌트가 업데이트(새로고침/재랜더링/return재실행)
                });
    }
    // --------------------------새로 만든 내용(재고)-------------------------------//
    // 0. 컴포넌트 상태변수 관리 [스프링으로부터 전달받은 객체]
    let [UnitPricePageDto, setUnitPricePageDto] = useState({
            dtos : [] , totalPages : 0 , totalCount : 0
        }); // 스프링에서 전달해주는 DTO와 일치화

    // 0. 스프링에게 전달한 객체
    const [ upageInfo, setuPageInfo ] = useState({
        page : 1 , key : 'uname' , keyword : '' , view : 5
    }); console.log("이거 확인해라 upageInfo"); console.log(upageInfo);

    // 1-1. axios를 이용한 스픠링의 컨트롤과 통신
    const getUnitprice = (e) => {
        axios.get('/unitprice/get', {params : upageInfo})
                .then(r =>{   // r.data : pageDto // r.data.boardDtos : pageDto 안에 있는 boardDtos
                    setUnitPricePageDto(r.data); // 응답받은 모든 게시물을 상태변수에 저장 // setState : 해당 컴포넌트가 업데이트(새로고침/재랜더링/return재실행)
                }); console.log("이거 확인해라2 getUnitprice");  console.log(getUnitprice);
    }


    // 1-2. 컴포넌트가 생성될 때 // + 의존성배열 : page (주소값) 변경될때  // +의존성배열 : view (주소값) 변경될떄
    useEffect(()=>{getProducts();},[pageInfo.page, pageInfo.view]);

    // 2. 페이지 번호를 클릭했을떄
    const onPageSelect = ( e , value ) => { console.log(value);
        pageInfo.page = value; // 클릭한 페이지번호로 변경
        console.log(value);
        setPageInfo({...pageInfo}); // 새로고침 [상태변수의 주소값이 바뀌면 재랜더링]
        getProducts();
     }
     // 2-1. 페이지 번호를 클릭했을떄(재고)
     const onUnitPricePageSelect = ( e , value ) => { console.log(value);
             upageInfo.page = value; // 클릭한 페이지번호로 변경
             console.log(value); // 이 값이 페이지번호로 들어오는게 맞나??
             setuPageInfo({...upageInfo}); // 새로고침 [상태변수의 주소값이 바뀌면 재랜더링]
             console.log("setuPageInfo 아래 확인");
             console.log(setuPageInfo);
             console.log("upageInfo 아래 확인");
             console.log(upageInfo);
             getUnitprice();
          }

     // 3. 검색 버튼을 눌렀을 때 // 첫페이지 1페이지 초기화
     const onSearch = (e) =>{
        getProducts();
        setPageInfo(({...pageInfo , page : 1})); // 첫페이지 1페이지 초기화
     }


    // 0. 컴포넌트 상태변수 관리
    let [rows, setRows] = useState([]);
    let [unitPrices, setUnitPrices] = useState({
        dtos : [] , totalPages : 0 , totalCount : 0
    });
    console.log(unitPrices.dtos[0]);

    useEffect(() => {

        // axios를 이용한 스프링의 unitprice컨트롤과 통신
        axios.get('/unitprice/get',{ params : upageInfo } ).then((r) => {
            setUnitPrices(r.data);
        });

    }, [upageInfo.page, upageInfo.view]);

    // 수정
     let onUpdate = (pno) => {

            console.log(pno);
            let pname = prompt('수정할 제품이름을 입력하세요', "");
            console.log(pname);
            let pprice = prompt('수정할 제품가격을 입력하세요', "");
            console.log(pprice);

        let updateInfo = { pno:pno , pname:pname , pprice:pprice }
        console.log(updateInfo);
            axios.put('/product/put', updateInfo)
                .then(r=>{
                    if(r.data){alert('수정성공');
                        window.location.reload();
                    }
                    else{alert('수정실패')}
                })
     }
     // 삭제
     let onDelete = (pno) => {
              console.log(pno);
              let confirmDelete = window.confirm('삭제하시겠습니까?');
              if (!confirmDelete) { return; }

              axios.delete(`/product/delete?pno=${pno}`)
                .then(r=>{
                    if(r.data){alert('삭제성공');
                        window.location.reload();
                    }
                    else{alert('삭제실패')}
                })
     }

    return (
        <>
            <div className="webcontainer">
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
                    {/* 테이블 제목 영역 */}
                    <tr className="trText">
                        <th style={{ width: '10%' }}>상품번호</th>
                        <th style={{ width: '15%' }}>상품이미지</th>
                        <th style={{ width: '15%' }}>상품이름</th>
                        <th style={{ width: '10%' }}>상품수량</th>
                        <th style={{ width: '10%' }}>상품가격</th>
                        <th style={{ width: '20%' }}>상품등록날짜</th>
                        <th style={{ width: '20%' }}>비고</th>
                    </tr>
                    {/* 테이블 내용 영역 */}
                    {productPageDto.productDtoList?.map((p) => (
                        <tr>
                            <td style={{ width: '10%', backgroundColor: 'white' }}>{p.pno}</td>
                             <td style={{ width: '15%', backgroundColor: 'white' }}>
                                        <img
                                            src={ "http://192.168.17.34:8080/static/media/"+p.imageUrl} // 상품 이미지 URL
                                            style={{ width: '125px', height: '162px' }} // 이미지 크기 조절
                                        />
                             </td>
                            <td style={{ width: '15%', backgroundColor: 'white' }}>{p.pname}</td>
                            <td style={{ width: '10%', backgroundColor: 'white' }}>{p.uincrease}</td>
                            <td style={{ width: '10%', backgroundColor: 'white' }}>{p.pprice}</td>
                            <td style={{ width: '20%', backgroundColor: 'white' }}>{p.cdate}</td>
                            <td style={{ width: '20%', backgroundColor: 'white' }}>
                                <button type="button" style={{margin: '3px'}} onClick={() => onUpdate(p.pno)}>수정</button>
                                <button type="button" style={{margin: '3px'}} onClick={() => onDelete(p.pno)}>삭제</button>
                                <a href={`/companyOrder/write?pno=${p.pno}`}>
                                    <button type="button" style={{margin: '3px'}} >발주</button>
                                </a>
                            </td>
                        </tr>
                    ))}
                </table>
                {/* 페이지 전환 */}
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', margin: '10px' }}>
                    <Pagination count={productPageDto.totalPages} onChange={onPageSelect} />
                </div>

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
                    <button type="button" onClick={onSearch}>검색</button>
                </div>
            </div>
        </>
    );
}

