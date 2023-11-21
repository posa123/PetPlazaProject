import axios from 'axios'; // axios
import { useState , useEffect } from 'react'; // react
import { Link } from 'react-router-dom' // Link
import { useSearchParams } from 'react-router-dom'

export default function MemberUpdate(props){

    const [ searchParams , setSearchParams ] = useSearchParams();
    const mno = searchParams.get('mno');

    // 1. 개별 회원 출력 요청
    const [members , setMembers] = useState({})

        const onGet = (e) => {
            axios.get('/member/doGet',{params:{mno:mno}})
                .then(r=>{ setMembers(r.data);})
        }
        useEffect(()=>{onGet()},[])
    // 2. 개별 회원 수정
    const mUpdate = (e) => {
        const memberForm = document.querySelectorAll('.memberForm')[0]
        const memberFormData = new FormData(memberForm);

        memberFormData.set('mno', mno)
        // 2. axios 통신
        axios.put('/member/put' ,memberFormData)
            .then( r => {
                if(r.data) { alert('수정 성공')
                window.location.href = '/MemberList';
                }else{alert('수정실패')}

            })
    }

    return(<>
        <div className ="webcontainer">
            <h3>회원 수정페이지</h3>
            <form className="memberForm">
                    아이디 : <input disabled type="text" value={members.mid}/>
                이름 : <input type="text"
                    name="mname"
                    value={members.mname}
                    onChange={ (e)=>{
                    setMembers( {...members , mname : e.target.value})}}/>
                비밀번호 : <input type="password"
                    name="mpwd"
                    onChange={ (e)=>{
                    setMembers( {...members , mpwd : e.target.value})}}/>
                전화번호 : <input type="text" value={members.mphone}
                    name="mphone"
                    onChange={ (e)=>{
                    setMembers( {...members , mphone : e.target.value})}}/>
                회원가입일시 : <div>{members.cdate}</div> <br/>
                수정일시 : <div>{members.udate}</div> <br/>
                <button onClick={mUpdate}type="button">수정</button>
            </form>
        </div>


    </>)
}