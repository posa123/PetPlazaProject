import axios from 'axios';
import {useState,useEffect} from 'react'
import {Link,useSearchParams} from 'react-router-dom';
import Modal from "../Modal"
import styles from '../../css/schedule/scheduleWrite.css'

export default function ScheduleUpdate( props ){

  //0. 현재 게시물의 정보를 가지는 상태관리 변수
    const [schedule,setSchedule]=useState({})

  //1. 개별 일정 출력( 기존 내용 가져오기)
    //1-1 쿼리스트링 매개변수 호출
    const[searchParams,setSearchParms]=useSearchParams();
    let sno=searchParams.get('sno');
    //1-2 axios
    const onGet=(e)=>{

            axios.get("/schedule/doGet" ,{params:{sno:sno}})
                .then(r=>{console.log(r);  setSchedule(r.data); console.log('여기'); console.log(r.data);
                })
        }
    //1-4 최초 한번만 실행
    useEffect(()=>{onGet()},[])

  //2. 수정
    const update=(e)=>{
                   console.log('수정버튼실행')
                   let scheduleForm=document.querySelectorAll('.scheduleForm')[0];
                   console.log(scheduleForm)
                   let scheduleFormData=new FormData(scheduleForm) ;
                   console.log(scheduleFormData)
                   scheduleFormData.set('sno',sno);
                   console.log(scheduleFormData)
                   axios.put("/schedule/put",scheduleFormData)
                           .then(r=>{
                               if(r.data){
                               alert('일정이 수정되었습니다');
                               window.location.href='/schedule';
                               }else{alert('일정 수정에실패했습니다'); }
                           })
                  }

  //3. 취소
    const cancel=(e)=>{window.location.href='/schedule'}

  //4. 삭제
    const onDelete=(e)=>{
        console.log('삭제버튼실행')
        axios.delete("/schedule/delete",{params:{sno:sno}}) //controller에서 param
            .then(r=>{
               // console.log('삭제버튼실행 시 axios 결과')
                //console.log(r.data)
                if(r.data){
                    alert('삭제 되었습니다.');
                    window.location.href='/schedule'
                }else {alert('삭제에 실패했습니다.')}
            })
    }

    return(<>
                    <div className="webcontainer">

                    <p>제목 {sno}</p>
                       <form className="scheduleForm" >
                            <select name="sclass"
                                 value={schedule.sclass}
                                onChange={(e)=>{setSchedule({...schedule,sclass:e.target.value})}}
                            >
                                <option>발주</option>
                                <option>기타</option>
                            </select>
                            <select name="sstate">
                                  <option>진행</option>
                                  <option>보류</option>
                                  <option>완료</option>
                            </select>

                           <input name="stitle" type="text"
                                    value={schedule.stitle}
                                    onChange={(e)=>{setSchedule({...schedule , stitle:e.target.value})}}
                           /> <br/>
                           <textarea name="scontent"
                                value={schedule.scontent}
                                onChange={(e)=>{setSchedule({...schedule,scontent:e.target.value})}}
                           ></textarea> <br/>

                            시작일<input name="sstart" type="date"
                            value={schedule.sstart}
                            onChange={(e)=>{setSchedule({...schedule,sstart:e.target.value})}}
                            />
                            종료일<input name="send" type="date"
                               value={schedule.send}
                               onChange={(e)=>{setSchedule({...schedule,send:e.target.value})}}
                            />
                        <button type="button" onClick={update}>수정</button>
                        <button type="button" onClick={cancel}>취소</button>
                        <button type="button" onClick={onDelete} >삭제</button>

                        </form>

                    </div>



    </>)
}
