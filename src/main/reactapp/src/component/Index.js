import { BrowserRouter, Routes, Route } from'react-router-dom';
import Header from './Header';
import Main from './Main'
import Footer from './Footer'
// Member
import MemberList from './member/MemberList'
import MemberUpdate from './member/MemberUpdate'
// Order - 이진형
import Order from './order/Order'
import OrderDetail from './order/OrderDetail'
import OrderWrite from './order/OrderWrite'
import Login from './Login'
// ----------------------------------------------------------------------------------- //
/* 정희락 */
// 거래처 import
import ClientList from './client/ClientList'
import ClientWrite from './client/ClientWrite'
import ClientUpdate from './client/ClientUpdate'
// 발주 import
import CompanyOrderList from './companyOrder/CompanyOrderList'
import CompanyOrderWrite from './companyOrder/CompanyOrderWrite'
import CompanyOrderUpdate from './companyOrder/CompanyOrderUpdate'

// ----------------------------------------------------------------------------------- //
/*재고단가및 로그 관리-박상빈*/
import UnitPrice from './unitprice/UnitPrice';
import UnitPriceWrite from './unitprice/UnitPriceWrite'
import UnitLogue from './unitlogue/UnitLogue'



import ProductList from './product/ProductList'
import ProductWrite from './product/ProductWrite'

// ----------------------------------------------------------------------------------- //
/*일정관리,매출관리 -고연진*/
import ScheduleList from'./schedule/ScheduleList';
import ScheduleWrite from'./schedule/ScheduleWrite';
import ScheduleUpdate from'./schedule/ScheduleUpdate';
import BusinessMain from'./business/BusinessMain';
import BusinessProduct from'./business/BusinessProduct';
import BusinessSales from'./business/BusinessSales';

export default function Index( props ){
    return(<>
        <div className="webContainer">

            <BrowserRouter>
                <Header/>
                <Routes >
                    {/*Login*/}
                    <Route path='/login' element = { <Login /> } />
                    {/*Schedule(일정)*/}
                    <Route path='/schedule' element = { <ScheduleList/>}/>
                    <Route path='/schedule/write' element = { <ScheduleWrite/>}/>
                    <Route path='/schedule/update' element = { <ScheduleUpdate/>}/>
                    {/*business(매출)*/}
                    <Route path='/business' element = { <BusinessMain/>}/>
                    <Route path='/business/product' element = { <BusinessSales/>}/>
                    <Route path='/business/product/profit' element = { <BusinessProduct/>}/>

                    {/*Main*/}
                    <Route path='/' element = { <Main /> } />
                    {/*Member*/}
                     <Route path='/MemberList' element = { <MemberList /> } />
                     <Route path='/MemberUpdate' element = { <MemberUpdate /> } />
                    {/*Order(주문)*/}
                     <Route path='/order' element = { <Order /> } />
                     <Route path='/orderDetail' element = { <OrderDetail /> } />
                     <Route path='/orderWrite' element = { <OrderWrite /> } />

                     <Route path='/unitprice' element = { <UnitPrice /> } />
                     <Route path='/unitprice/list' element = { <UnitPriceWrite /> } />
                     <Route path='/unitlogue' element = { <UnitLogue /> } />

                    {/*Client(거래처)*/}
                    <Route path='/client/list' element = { <ClientList /> } />
                    <Route path='/client/write' element = { <ClientWrite /> } />
                    <Route path='/client/update' element = { <ClientUpdate /> } />

                    {/* CompanyOrder(발주) */}
                    <Route path='/companyOrder/list' element = { <CompanyOrderList /> } />
                    <Route path='/companyOrder/write' element = { <CompanyOrderWrite /> } />
                    <Route path='/companyOrder/update' element = { <CompanyOrderUpdate /> } />

                    {/*Product(제품관리)*/}
                     <Route path='/product' element = { <ProductList /> } />
                     <Route path='/product/post' element = { <ProductWrite /> } />

                    {/*없는 주소는 로그인 페이지로 이동*/}
                     <Route path="/*" element={<Login />} />
                </Routes >

            </BrowserRouter>





        </div>
        </>)
    }
