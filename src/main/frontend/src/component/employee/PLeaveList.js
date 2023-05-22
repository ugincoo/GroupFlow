import React,{ useState, useEffect } from 'react';
import axios from 'axios';
/*------------------------------------------------ table mui */
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
/*-----------------------------------------------------------*/
import styles from '../../css/leave.css'; //css
import Container from '@mui/material/Container';
// 버튼
import Button from '@mui/material/Button';
// 페이징처리
import Pagination from '@mui/material/Pagination';

// 부서 연차 신청 내역 [ 부장직급조회 ]
export default function LeaveRequestList(props){
    // 0. 로그인 정보 변수
    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) )
    // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수[ 상태 관리변수 ]

         let [ rows , setRows ] = useState( [] );

         let [pageInfo , setPageInfo] = useState({ 'page':1 });
         let [ totalPage , setTotalPage] = useState( 1 )
         let [ totalCount , setTotalCount] = useState( 1 )
         console.log(login.dno);
         console.log(login);
         // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성 되었을때 ] // useEffect( ()=>{} , [] )
         useEffect( ()=>{
             axios.get('/dayoff/position',{params:{dno:login.dno}})
                 .then( r => {
                        console.log(r);
                        console.log(r.data);
                        setRows(r.data)

                    } )
                 .catch( err => { console.log("error : " + err); })

         } , [pageInfo] )

        // 로그인 정보 호촐
         useEffect( ()=>{
            axios.get("/login/confirm")
                .then(r => {
                    console.log(r);
                    if(r.data != ''){// 로그인이 되어 있으면 // 서비스에서 null 이면 js에서 ''이다
                        sessionStorage.setItem( "login_token",JSON.stringify(r.data) );
                        //상태변수에 저장
                        setLogin(JSON.parse( sessionStorage.getItem("login_token") ) );
                        console.log(r.data);
                    }
                })
        }, [] )


 // 결재 상태 함수
const approval = (e) => {
    const approvaldate = window.prompt('결재일을 입력 해주세요 (예: 2023-01-01)');
    console.log(e.target.value);
    let info ={
        lno : e.target.value,
        approvaldate : approvaldate,
        approvalsate : 1
    }

    axios.put("/dayoff/pok",info)
         .then(r => {
            if( r.data == true ){
                alert('승인되었습니다.');
                setPageInfo({...pageInfo});
            }else{
                alert('날짜 형식으로 입력 해주세요.');
            }
    })
};

// map
 const pLeaveList =  rows.map(row => {
     if( login.dno == row.dno && row.approvaldate == null ){
     console.log(row)
         return(
             <TableRow  key={row.name}   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
                 <TableCell align="center">{row.lrequestdate}</TableCell>
                 <TableCell align="center">{row.ename}</TableCell>
                 <TableCell align="center">{row.lstart} ~ {row.lend}</TableCell>
                 <TableCell align="center">{row.requestreason}</TableCell>
                 <TableCell>
                 <button onClick={approval} type="button" value={row.lno} className="pstate"> 승인 </button>
                 </TableCell>
             </TableRow>
         )
     }else if( login.dno == row.dno && row.approvaldate != null ){
         return(
                 <TableRow  key={row.name}   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
                     <TableCell align="center">{row.lrequestdate}</TableCell>
                     <TableCell align="center">{row.ename}</TableCell>
                     <TableCell align="center">{row.lstart} ~ {row.lend}</TableCell>
                     <TableCell align="center">{row.requestreason}</TableCell>
                     <TableCell align="center">{row.approvaldate}</TableCell>
                 </TableRow>
             )
     }else {
          return(<TableRow  key={row.name}   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
         </TableRow> )
     }

  });

     return (

     <Container>
        <h4> {login.dname} 연차 신청내역</h4 >
         <TableContainer component={Paper}>
           <Table sx={{ minWidth: 650 }} aria-label="simple table">
             <TableHead>
               <TableRow className="tableTitle">
                 <TableCell align="center" style={{ width:'15%' }}>연차신청일</TableCell>
                 <TableCell align="center" style={{ width:'10%' }}>신청자</TableCell>
                 <TableCell align="center" style={{ width:'30%' }}>연차사용일</TableCell>
                 <TableCell align="center" style={{ width:'20%' }}>연차사유</TableCell>
                 <TableCell align="center" style={{ width:'15%' }}>결재일 / 승인</TableCell>
               </TableRow>
             </TableHead>
             <TableBody>
                { pLeaveList }
             </TableBody>
           </Table>
         </TableContainer>
        <div style={{margin : "30px 0px", display : "flex", justifyContent:"center"}}>
            <Pagination count={ totalPage }  variant="outlined" color="primary"  />
        </div>
     </Container>
     );

}