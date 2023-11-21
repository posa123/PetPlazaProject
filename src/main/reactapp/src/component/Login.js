import { BrowserRouter , Routes , Route , Link } from "react-router-dom"
import axios from 'axios'
import{useState,useEffect} from 'react'
import styles from'../css/login.css';




export default function Login( props ){
    return(<>
         <div className="loginContainer">

            <form action="/loginProcessingUrl" method="post">
                <div><input type="text" placeholder="아이디를 입력하세요" name='username'/></div>
                <div><input type="password" placeholder="비밀번호를 입력하세요" name='password'/></div>
                <div><button type="submit">Login</button></div>
            </form>
        </div>
        </>)
    }
