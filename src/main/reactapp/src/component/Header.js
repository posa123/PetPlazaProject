import { Link } from 'react-router-dom'
import styles from '../css/header.css';

// 테스트용
import { Sidebar, Menu, MenuItem } from 'react-pro-sidebar';

//로고 이미지 호출
import logoimg from '../img/logo.png'

export default function Header( props ){
    //로그인 페이지가 아니면 null 출력 (=푸터출력안됨)
     if (window.location.pathname === '/login') return null;


    return(<>


        <Sidebar>
            <Link to='/'>
                <div className="logo">
                    <img src={logoimg} className=""/>
                </div>
            </Link>
          <Menu
            menuItemStyles={{
              button: {
                // the active class will be added automatically by react router
                // so we can use it to style the active menu item
                           [`&.active`]: {
                         backgroundColor: '#13395e',
                  color: '#b6c8d9',
                  height : '80px'
                },
              },
            }}
           className="componentBox">
           <div className="componentsubBox">
               <a href="/logout">로그아웃</a>
                <MenuItem className="componentButton" component={<Link to="/MemberList" />}> 회원관리</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/client/list" />}> 거래처관리</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/companyOrder/list" />}> 발주현황 </MenuItem>
                <MenuItem className="componentButton" component={<Link to="/product" />}> 제품관리</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/UnitPrice" />}> 재고관리</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/UnitLogue" />}> 재고기록</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/Order" />}> 주문관리</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/schedule" />}> 일정관리</MenuItem>
                <MenuItem className="componentButton" component={<Link to="/business" />}> 매출관리</MenuItem>
            </div>
          </Menu>
        </Sidebar>
        </>)
    }
