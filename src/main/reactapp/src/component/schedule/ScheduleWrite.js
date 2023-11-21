import axios from 'axios';
import {useState,useEffect} from 'react'
import {Link} from 'react-router-dom';
import Modal from "../Modal"
import styles from '../../css/schedule/scheduleWrite.css'

export default function ScheduleWrite( props ){
 const register=(e)=>{
                   let scheduleForm=document.querySelectorAll('.scheduleForm')[0];
                   let scheduleFormData=new FormData(scheduleForm) ;
                   //console.log('여기')
                  // console.log(scheduleFormData)
                   axios.post("/schedule/register",scheduleFormData)
                           .then(r=>{
                               if(r.data){
                               alert('일정이 추가되었습니다');
                               window.location.href='/schedule';
                               }else{alert('일정등록에실패했습니다'); }
                           })
                  }
    return(<>
                    <div className="webcontainer">
                       <form className="scheduleForm" >
                            <select name="sclass"
                             style={{width : '50px'}}
                            >
                                <option style={{textAlign : 'center'}}>기타</option>
                                <option style={{textAlign : 'center'}}>발주</option>
                            </select>

                            <select name="sstate"
                            style={{width : '50px'}}
                            >
                                <option style={{textAlign : 'center'}}>진행</option>
                                <option style={{textAlign : 'center'}}>보류</option>
                                <option style={{textAlign : 'center'}}>완료</option>
                            </select>

                           <input name="stitle" type="text" placeholder="제목"/> <br/>
                           <textarea name="scontent" placeholder="내용"></textarea> <br/>
                            시작일<input name="sstart" type="date"/>
                            종료일<input name="send" type="date"/>
                        <button type="button" onClick={register}>등록</button>

                        </form>
                    </div>
    </>);
}









/*
export default function ScheduleWrite( props ){
     const [isModalOpen,setIsModalOpen] =useState(false);
     const openModal=()=>setIsModalOpen(true);
     const closeModal=()=>setIsModalOpen(false);
     const register=(e)=>{
                   let scheduleForm=document.querySelectorAll('.scheduleForm')[0];
                   let scheduleFormData=new FormData(scheduleForm) ;
                   axios.post("/schedule/register",scheduleFormData)
                           .then(r=>{
                               if(r.data){
                               alert('일정이 추가되었습니다');
                               window.location.href='/schedule';
                               }else{alert('일정등록에실패했습니다'); }
                           })
                  }
    return(<>
                       <form className="scheduleForm" >
                            <select name="sclass">
                                <option>발주</option>
                                <option>기타</option>
                            </select>

                           <input name="stitle" type="text" placeholder="제목"/> <br/>
                           <textarea name="scontent" placeholder="내용"></textarea> <br/>
                            시작일<input name="sstart" type="date"/>
                            종료일<input name="send" type="date"/>
                        <button type="button" onClick={register}>등록</button>

                        </form>




    </>)
}
*/