import { Link } from 'react-router-dom'
import styles from '../css/header.css';



export default function Header( props ){
   //로그인 페이지가 아니면 null 출력 (=헤더출력안됨)
    if (window.location.pathname === '/login') return null;
    return(<>
        <footer>
                <div>오늘일정</div>
                <div>매출1위제품</div>
                <div>발주가필요한제품(조건문)</div>
        </footer>
        </>)
    }
