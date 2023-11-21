import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Pagination, Stack } from '@mui/material'; // 만약 Material-UI를 사용하고 있다면

import styles from '../../css/product.css';

export default function ProductWrite(props) {
    const productWrite = (e) => {

        // productWrite 함수를 수정하여 이미지를 다른 데이터와 함께 전송하도록 한다(GPT)
       /* const formData = new FormData();
            formData.append('pname', productName);
            formData.append('pprice', productPrice);
            formData.append('pimage', productImage);*/



        // 1. 폼 가져오기 (변수명=productForm) [첨부된 파일]
        let productForm = document.querySelectorAll('.productForm')[0];
            console.log(productForm);
        let productFormData = new FormData(productForm);
        console.log("내용확인해라");
        console.log(productFormData);

        // 2. axios로 전송
        axios.post("/product/doPost", productFormData)
            .then(result => {
                console.log("결과확인", result);
                if (result.data) {
                    alert('상품 등록 성공');
                    window.location.href = '/product';
                    productForm.reset();
                } else {
                    alert('상품 등록 실패');
                }
                console.log(result);
            });
    };

    const [productprices, setProductPrices] = useState([]);

    // -------------------------- 새로운 내용 (재고) ------------------------------ //
   let [unitPricePageDto, setUnitPricePageDto] = useState({ //재고가격 페이지에 대한 데이터를 저장하는 상태
           dtos: [],
           totalPages: 0,
           totalCount: 0,
       });
       // pageInfo 및 upageInfo 상태를 초기화한다.
       const [pageInfo, setPageInfo] = useState({
           page: 1, key: 'pname', keyword: '', view: 5
       });
       console.log(pageInfo);

       const [upageInfo, setuPageInfo] = useState({
           page: 1, key: 'uname', keyword: '', view: 5
       });
        // API에서 데이터를 가져오는 함수 getUnitprice를 정의한다.
       const getUnitprice = () => {
           axios.get('/unitprice/get', { params: pageInfo })
               .then(response => {
                   setUnitPricePageDto(response.data);
               });
       };
       console.log(getUnitprice);

       //pageInfo.page 또는 pageInfo.view가 변경될 때마다 getUnitprice 함수를 호출하여 데이터를 업데이트한다.
       useEffect(() => {
           getUnitprice();
       }, [pageInfo.page, pageInfo.view]);

       let [rows, setRows] = useState([]);
       let [unitPrices, setUnitPrices] = useState({
           dtos: [],
           totalPages: 0,
           totalCount: 0,
       });

       const onUnitPricePageSelect = (e, value) => {
           upageInfo.page = value;
           setuPageInfo({ ...upageInfo });
           getUnitprice();
       };
       //페이지가 변경되거나 뷰가 변경될 때 다른 재고가격 데이터를 가져오는 useEffect를 정의한다.
       useEffect(() => {
           axios.get('/unitprice/get', { params: upageInfo })
               .then((r) => {
                   setUnitPrices(r.data);
               });
       }, [upageInfo.page, upageInfo.view]);

       // 등록할 떄 이미지 추가하기

       const [productName, setProductName] = useState('');
       const [productPrice, setProductPrice] = useState('');
       const [productImage, setProductImage] = useState('');

       const handleImageChange = (e) => {
                // 이미지를 선택한 경우 상태 변수 업데이트
                setProductImage(e.target.files[0]);
            };
       const productImageWrite = () => {
            // FormData를 사용하여 데이터와 이미지를 함께 전송
            const formData = new FormData();
            formData.append('pname', productName);
            formData.append('pprice', productPrice);
            formData.append('pimage', productImage);

            console.log(productName);
            console.log(productPrice);
            console.log(productImage);

            // 서버로 데이터 전송
            axios.post('/product/write', formData)
                .then(response => {
                    // 성공적으로 등록된 경우 처리
                    console.log(response.data);
                    // 추가로 필요한 작업 수행
                })
                .catch(error => {
                    // 오류 발생 시 처리
                    console.error(error);
                });
        };

            console.log(productName);
            console.log(productPrice);
            console.log(productImage);





    return (
        <>
            <div className="webcontainer">
                {/* 재고 목록 */}
                <h3>재고 리스트</h3>
                <table className="unitTable">
                    <tr className="trText">
                        <th style={{ width: '30%' }}>재고 번호</th>
                        <th style={{ width: '30%' }}>재고 가격</th>
                    </tr>
                    {unitPrices.dtos.map(u => (
                        <tr key={u.uname}>
                            <td style={{ width: '30%', backgroundColor: 'white' }}>{u.uname}</td>
                            <td style={{ width: '30%', backgroundColor: 'white' }}>{u.uoriginal}</td>
                        </tr>
                    ))}
                </table>
                {/* 재고 가격에 대한 페이지네이션 추가 */}
                <Stack spacing={2} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', margin: '10px' }}>
                    <Pagination count={unitPrices.totalPages} onChange={onUnitPricePageSelect} />
                </Stack>

                <h2>상품 등록</h2>
                    <form className="productForm">
                        상품명: <input className="productname" placeholder='상품명' type="text" name="pname" /> <br />
                        상품 가격: <textarea className="productprice" placeholder='상품 가격' type='text' name="pprice"></textarea> <br />
                        상품 이미지: <input type="file" name="fileList" multiple/> <br />
                        <button type="button" onClick={productWrite}>상품 등록</button>
                    </form>
            </div>
        </>
    );
}
