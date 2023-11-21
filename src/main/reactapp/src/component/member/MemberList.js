import axios from 'axios'; // axios
import { useState , useEffect } from 'react'; // react
import { Link } from 'react-router-dom' // Link
// ------------ mui table 관련 컴포넌트 import --------------- //
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
// --------------------------- //
// ---------------- mui table sample -------------- //
// ------------------------------------------------ //
// ---------------- Pagination -------------------- //
import Pagination from '@mui/material/Pagination';
// ------------------------------------------------ //
export default function MemberList(props){


    // 페이징 처리
    let [ memberPageDto , setMemberPageDto ] = useState({
        memberDtoList : [] ,
        totalPages : 0 ,
        totalCount : 0

    }); // 페이징 처리 end
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , view : 5 , key : 'mid' , keyword : ''
    });




    /*// 멤버리스트호출 상태 관리
    let [ members , setMembers ] = useState( [] )
    useEffect(()=>{
        axios
                .get('/member/get')
                .then( r => {setMembers(r.data)})

    } ,[]) // 멤버리스트호출 end*/
    const getMembers = (e)=>{
        axios.get('/member/get',{ params : pageInfo } )
                    .then(r =>{setMemberPageDto(r.data)})
    }
    useEffect(()=>{
      getMembers();
    },[ pageInfo , pageInfo.view ] )


    const onPageSelect = ( e , value ) => {
        pageInfo.page = value;
        console.log(value);
        setPageInfo({...pageInfo});
        getMembers();
    }


    // 삭제버튼 함수
    const memberDelete = (mno) =>{
        console.log(mno)
        axios
            .delete(`/member/delete?mno=${mno}`)
            .then( r => {
                if(r.data) { alert('삭제 성공');
                window.location.reload();
                }
                else{alert('삭제실패')}
            })
    } //삭제 버튼 end
    const onSearch=(e) =>{ getMembers(); setPageInfo({...pageInfo , page : 1 })}
    console.log(pageInfo);
    return(<>
        <div className="webcontainer">
            <h1>회원관리 페이지</h1>
            <select
            style={{float : 'left'}}
            value={pageInfo.view}
            onChange={(e)=>{setPageInfo({...pageInfo , view : e.target.value }); }}
            >
                <option value="5">5</option>
                <option value="15">15</option>
                <option value="20">20</option>
            </select>
              <table className="unitTable" style={{ width: '100%' }}>
                {/*테이블 제목 구역*/}
                   <tr className="trText">
                    <th style={{ width: '10%' }}>회원번호</th>
                    <th style={{ width: '15%' }}>회원이름</th>
                    <th style={{ width: '10%' }}>회원아이디</th>
                    <th style={{ width: '20%' }}>회원전화번호</th>
                    <th style={{ width: '30%' }}>회원가입일시</th>
                    <th style={{ width: '15%' }}>비고</th>
                </tr>
                {/*테이블 내용 구역*/}
                          { memberPageDto.memberDtoList.map((row) => (  // map is not function
                            <tr>
                                <td style={{ width: '10%', backgroundColor: 'white' }}>{row.mno}</td>
                                <td style={{ width: '15%', backgroundColor: 'white' }}>{row.mname}</td>
                                <td style={{ width: '10%', backgroundColor: 'white' }}>{row.mid}</td>
                                <td style={{ width: '20%', backgroundColor: 'white' }}>{row.mphone}</td>
                                <td style={{ width: '30%', backgroundColor: 'white' }}>{row.cdate}</td>
                                <td style={{ width: '15%', backgroundColor: 'white' }}>
                                <Link to={"/MemberUpdate?mno="+row.mno}><button type="button">수정</button></Link>
                                <button type="button" onClick={() => memberDelete(memberPageDto.mno)}>삭제</button>
                                </td>
                            </tr>

                            ))}
                      </table>
                <div style ={{display : 'flex' , flexDirection : 'column' , alignItems : 'center' , margin : '10px' }}>

                    { /* count : 전체페이지수   onChange : 페이지번호를 클릭/변경 했을떄 이벤트 */}
                    <Pagination  count={ memberPageDto.totalPages } onChange={ onPageSelect } />
                </div>

                {/*검색기능구역*/}
                <div style={{ display : 'flex' , justifyContent : 'center' , margin : '20px'}}>
                    <select value={pageInfo.key}
                    onChange={ (e)=>{setPageInfo({...pageInfo , key : e.target.value})} }
                    >
                        <option value="mname">이름</option>
                        <option value="mid">아이디</option>
                    </select>
                    <input type="text"
                    value={pageInfo.keyword}
                    onChange={(e)=>{setPageInfo({...pageInfo , keyword : e.target.value})}}
                    />
                    <button type="button" onClick={onSearch}>검색</button>
                </div>
             </div>

    </>)
}

/*
<Link to={"/MemberUpdate?mno="+member.mno}><button type="button">수정</button></Link>
                            <button onClick={()=>memberDelete(member.mno)} type="button">삭제</button>
{ pageDto.memberPageDto.map((row) => ( // map is not a function
                                <TableRow key={row.name}  sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                  <TableCell align="right">{row.mno}</TableCell>
                                  <TableCell align="right">{row.mname} </TableCell>
                                  <TableCell align="right">{row.mid}</TableCell>
                                  <TableCell align="right">{row.mphone}</TableCell>
                                  <TableCell align="right">{row.cdate}</TableCell>
                                </TableRow>
                              ))}
EEEEEEEddddd
*/