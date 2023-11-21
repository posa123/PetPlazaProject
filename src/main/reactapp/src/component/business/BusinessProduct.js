import axios from 'axios';
import {useState,useEffect} from 'react'
import {Link,useSearchParams} from 'react-router-dom';
import Pagination from '@mui/material/Pagination'; //페이징



export default function BusinessProduct( props ){
    const [searchParams,setSearchParams] = useSearchParams();
    let pno=searchParams.get('pno');
    const [productInfo,setProductInfo] = useState({});
    const getProductInfo=(e)=>{
        axios.get("/business/saleInfo",{params:{pno:pno}})
            .then(r=>{
               // console.log('axios통신성공'); console.log(r.data);
                setProductInfo(r.data);
            })
    }
    useEffect(()=>{getProductInfo()},[])

    return(<>
            <div className="webcontainer2">
                <table className="unitTable" style={{ width: '100%' }}>
                    <tr className="trText">
                        <th style={{width : '20%'}}> 상품명 </th>
                        <th style={{width : '20%'}}> 상품가격 </th>
                        <th style={{width : '15%'}}> 판매량 </th>
                        <th style={{width : '15%'}}> 상품원가 </th>
                        <th style={{width : '15%'}}> 상품수량(재고) </th>
                        <th style={{width : '15%'}}> 영업이익 </th>
                    </tr>
                    <tr>
                        <td style={{width : '20%'}}> {productInfo.pname}</td>
                        <td style={{width : '20%'}}>{productInfo.pprice}</td>
                        <td style={{width : '15%'}}>{productInfo.salesVolume}</td>
                        <td style={{width : '15%'}}>{productInfo.cost}</td>
                        <td style={{width : '15%'}}>{productInfo.stock}</td>
                        <td style={{width : '15%'}}>{((productInfo.pprice-productInfo.stock)*productInfo.salesVolume).toLocaleString()}</td>
                    </tr>
                </table>
            </div>
    </>)
}