import axios from 'axios';
import {useState,useEffect} from 'react'
import {Link,useSearchParams} from 'react-router-dom';
import styles from '../../css/schedule/scheduleList.css'
//페이징
import Pagination from '@mui/material/Pagination';

import ScheduleWrite from './ScheduleWrite'

export default function ScheduleList    ( props ){
    const[searchParams,setSearchParams]=useSearchParams();
    let sno=searchParams.get('bno')

    //0. 스프링으로부터 받은 객체 정보
    let [pageDto,setPageDto]=useState({scheduleDtos:[], totalPages:0,totalCount:0}); // console.log('여기1');console.log(pageDto)
   //0. 스프링으로 전달 할 객체
    const [pageInfo,setPageInfo]=useState({
        page:1, key:'stitle' , keyword:''
    });
    //console.log(pageInfo);



    //1. 일정출력 [실행조건:처음열었을때, 페이지가 달라졌을때(의존성배열)]
    useEffect(()=>{
        axios.get('/schedule/list',{params:pageInfo})
                .then(r=>{ //console.log(r.data);console.log(r.data.scheduleDtos)
                   setPageDto(r.data); //console.log(pageDto)
        })
    },[pageInfo])

    //2. 일정등록
    const [scheduleWrite,setScheduleWrite] =useState()

    //3. 일정삭제
    const onDelete=(sno)=>{
        axios.delete('/schedule/delete',{params:{sno:sno}})
            .then(r=>{
                if(r.data){alert('일정이 삭제되었습니다'); window.location.href='/schedule'}
            })
    }

    //4. 페이징 처리[실행조건: 페이지 눌렀을때]
    const onPageSelect=(e,value)=>{
        pageInfo.page=value;
        setPageInfo({...pageInfo}) //새로고침 (상태변수의 주소값 변경)
    }


    return(<>
        <div className="webcontainer">
                <h3>일정 페이지</h3>
                <div className="addInventory"><Link to="/schedule/write"><span>글쓰기</span></Link></div>
                 <table className="unitTable" style={{ width: '100%' }}>
                     <tr className="trText">
                        <th style={{ width: '10%' }}>종류</th>
                        <th style={{ width: '15%' }}>제목</th>
                        <th style={{ width: '20%' }}>내용</th>
                        <th style={{ width: '20%' }}>시작일</th>
                        <th style={{ width: '20%' }}>종료일</th>
                        <th style={{ width: '15%' }}>상태</th>
                     </tr>
                     {pageDto.scheduleDtos.map((row) => (
                       <tr key={row.sno}>
                         <td style={{ width: '10%', backgroundColor: 'white' }}>{row.sclass}</td>
                         <td style={{ width: '15%', backgroundColor: 'white' }}>
                           <Link to={"/schedule/update?sno=" + row.sno}>{row.stitle}</Link>
                         </td>
                         <td style={{ width: '20%', backgroundColor: 'white' }}>
                           <Link to={"/schedule/update?sno=" + row.sno}>{row.scontent}</Link>
                         </td>
                         <td style={{ width: '20%', backgroundColor: 'white' }}>{row.sstart}</td>
                         <td style={{ width: '20%', backgroundColor: 'white' }}>{row.send}</td>
                         <td style={{ width: '15%', backgroundColor: 'white' }}>{row.sstate}</td>
                       </tr>
                     ))}
                 </table>
                 <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>
                    <Pagination count={pageDto.totalPages} onChange={onPageSelect}/>
                 </div>
                 {/*-----검색기능S--------------------------------------------------------------------------------------*/}
                 <select
                     style={{width : '50px',}}
                     value={pageInfo.key}
                     onChange={(e)=>{setPageInfo({...pageInfo,key:e.target.value})}}
                 >
                     <option style={{textAlign : 'center'}}  value="stitle">제목</option>
                     <option style={{textAlign : 'center'}}  value="scontent">내용</option>
                 </select>
                 <input
                     value={pageInfo.keyword}
                     onChange={(e)=>{setPageInfo({...pageInfo,keyword:e.target.value})}}
                     type="text" />
                 {/*-----검색기능E--------------------------------------------------------------------------------------*/}
        </div>
        </>)
    }

/*
    private String sclass; //분류
    private String stitle; //제목
    private String scontent; //내용
    private boolean sstate; //상태
    private String sstart; //시작일
    private String send; //종료일

*/

/*

// 모달s--------------------------------------------------------------
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
                               window.location.href="/schedule";
                               }else{alert('일정등록에실패했습니다'); }
                           })
              }
// 모달e--------------------------------------------------------------




*/

/*
 <button onClick={openModal}>등록</button>
                        <Modal isOpen={isModalOpen} closeModal={closeModal} register={register}>
                           <form className="scheduleForm" >
                               <select name="sclass">
                                   <option>발주</option>
                                   <option>기타</option>
                               </select>
                               <input name="stitle" type="text" placeholder="제목"/> <br/>
                               <textarea name="scontent" placeholder="내용"></textarea> <br/>
                               시작일<input name="sstart" type="date"/>
                               종료일<input name="send" type="date"/>
                           </form>
                        </Modal>
*/