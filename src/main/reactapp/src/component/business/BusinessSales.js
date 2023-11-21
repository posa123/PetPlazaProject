import axios from 'axios';
import { useState, useEffect } from 'react';
import {  Link,useSearchParams   } from 'react-router-dom';

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
    const[searchParams,setSearchParams]=useSearchParams();
    let pno=searchParams.get('pno');

    // 0. 컴포넌트 상태변수 관리 [스프링으로부터 전달받은 객체]
    let [productPageDto, setProductPageDto] = useState({productDtoList : [] , totalPages : 0 , totalCount : 0});
    // 1. 스프링에게 전달한 객체
    const [ pageInfo, setPageInfo ] = useState({page : 1 , key : 'pname' , keyword : '' , view : 5});
    // 1-1. axios를 이용한 스픠링의 컨트롤과 통신
    const getProducts = (e) => {
        axios.get('/product/get', {params : pageInfo})
                .then(r =>{
                    setProductPageDto(r.data);
                });
    }

    useEffect(()=>{getProducts();},[pageInfo.page, pageInfo.view]);

    // 2. 페이지 번호를 클릭했을떄
    const onPageSelect = ( e , value ) => {
        pageInfo.page = value; // 클릭한 페이지번호로 변경
        setPageInfo({...pageInfo}); // 새로고침 [상태변수의 주소값이 바뀌면 재랜더링]
        getProducts();
     }

     // 3. 검색 버튼을 눌렀을 때 // 첫페이지 1페이지 초기화
     const onSearch = (e) =>{
        getProducts();
        setPageInfo(({...pageInfo , page : 1})); // 첫페이지 1페이지 초기화
     }


    return (
        <>
            <div className="webcontainer">
                <h3>전체 상품</h3>
                <select
                    style={{ float: 'left' }}
                    value={pageInfo.view}
                    onChange={(e) => { setPageInfo({ ...pageInfo, view: e.target.value }); }}
                >
                    <option value="5"> 5 </option>
                    <option value="10"> 10 </option>
                    <option value="20"> 20 </option>
                </select>
                <table className="unitTable">
                    {/* 테이블 제목 구역 */}
                    <tr className="trText">
                        <th style={{ width: '10%' }}>상품번호</th>
                        <th style={{ width: '50%' }}>상품이름</th>
                        <th style={{ width: '20%' }}>상품가격</th>
                        <th style={{ width: '20%' }}>영업이익</th>
                    </tr>
                    {/* 테이블 내용 구역 */}
                    {productPageDto.productDtoList.map((row) => (
                        <tr>
                            <td style={{ width: '10%', backgroundColor: 'white' }}>{row.pno}</td>
                            <td style={{ width: '60%', backgroundColor: 'white' }}>
                                <Link to={"/business/product/profit?pno=" +row.pno}>{row.pname}</Link>
                            </td>
                            <td style={{ width: '20%', backgroundColor: 'white' }}>{(row.pprice).toLocaleString()}</td>
                            <td style={{ width: '20%', backgroundColor: 'white' }}>
                                <Link to={"/business/product/profit?pno=" +row.pno}>자세히</Link>
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

