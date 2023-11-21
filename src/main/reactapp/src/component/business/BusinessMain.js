import styles from '../../css/business/businessMain.css'
import axios from 'axios';
import { BrowserRouter , Routes , Route , Link } from 'react-router-dom'
import{useState,useEffect,useRef,useContext,createContext} from 'react'
import { BarChart } from '@mui/x-charts/BarChart';
import React, { Component } from "react";


export default function BusinessMain( props ){
   // console.log('매출관리 메인페이지 열림')

    let today= new Date(); //console.log(today);
    let year=today.getFullYear(); //console.log(year);

    //올해 월별 총 매출액[실행조건:컴포넌트 열릴 때]
    const [totalSales,setTotalSales]=useState([])
    const getTotalSales=(e)=>{
        //console.log('axios전')
        axios.get("/business/totalSales",{params:{year:year}})
            .then(r=>{
                //console.log('총매출액출력통신성공');console.log(r.data)
                setTotalSales(r.data);
            })
    }
    useEffect(()=>{getTotalSales()},[])

    //제품별 누적 판매량 [실행조건: 컴포넌트열릴 때]
    const [salesVolume,setSalesVolume] = useState([])
    const getSalesVolume=(e)=>{
        //console.log('제품별누적판매량 axios전');
        axios.get("/business/salesVolume")
            .then(r=>{
                //console.log('제품별누적판매량통신성공');console.log(r.data)
                setSalesVolume(r.data)
            })
    }
    useEffect(()=>{getSalesVolume()},[])


    return(<>
        <div className="Bwebcontainer">
            <div className="totalSales">

                    <h3>금년도 총 매출액</h3>
                    (단위:만원)
                {
                    totalSales.length!=0?
                    <BarChart
                      xAxis={[
                        {
                          id: 'barCategories',
                          data: totalSales.map((p)=>{return p.month+"월" }),
                          scaleType: 'band',
                        },
                      ]}
                      series={[
                        {
                          data: totalSales.map((p)=>{return p.monthSales/10000}),
                        },
                      ]}
                      width={1000}
                      height={300}
                    />
                    :<></>
                }
            </div>

            <div className="popular">
                    <h3>인기상품</h3>
                    <Link to='/business/product'>전상품영업이익</Link>
 {
                    salesVolume.length!=0?
                    <BarChart
                      xAxis={[
                        {
                          id: 'barCategories',
                          data: salesVolume.map((p)=>{return p.pname}),
                          scaleType: 'band',
                        },
                      ]}
                      series={[
                        {
                          data: salesVolume.map((p)=>{return -(p.amount)}),
                        },
                      ]}
                      width={1000}
                      height={300}
                    />
                    :<></>
                }

            </div>

        </div>


    </>)
}