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
//모달
import LeaveRequest from "./LeaveRequest.js"

// 개인 연차 내역
export default function LeaveRequestList(props){
    // 0. 로그인 정보 변수
    const [ login , setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) );

    // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수[ 상태 관리변수 ]
     let [ rows , setRows ] = useState( [] );
     let [ totalPage , setTotalPage] = useState( 1 );
     let [ yearNo , setYearNo ] = useState(0);

    // 로그인 정보 호촐
     useEffect( ()=>{
        axios.get("/login/confirm")
            .then(r => {
                console.log(r);
                console.log(r.data);
                if(r.data != ''){// 로그인이 되어 있으면 //
                    sessionStorage.setItem( "login_token",JSON.stringify(r.data) );
                    //상태변수에 저장
                    setLogin(JSON.parse( sessionStorage.getItem("login_token") ) );
                    console.log(r.data);
                }
            })
    }, [] )
     // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성 되었을때 ] // useEffect( ()=>{} , [] )
     useEffect( ()=>{
         axios.get('/dayoff/myget',{param:pageInfo})
             .then( r => {
                    console.log(r);
                    console.log(r.data);
                    setRows(r.data)
                } )
             .catch( err => { console.log("error : " + err); })

     } , [pageInfo] )


    console.log(login);
    //3. 페이징 변경
    const selectPage = (e,value) =>{
        console.log(value);
        pageInfo.page = value; // 버튼이 바뀌었을때 페이지번호 가져와서 상태변수에 대입
        setPageInfo( {...pageInfo} ); // 클릭 된 페이지 번호를 상태변수에 새로고침
        console.log(e);
        console.log(e.target);           // button
        console.log(e.target.value);     // button이여서 value 값 없음
        console.log(e.target.innerHTML); // 해당 button에서 안에 출력되는 html 호출
        console.log(e.target.outerText); // 해당 button에서 밖으로 출력되는 text 호출
    }
// 연차 개수
useEffect( () => {
  if (rows.length > 0) {
    console.log(rows[0]);
    console.log(rows[0].yearno);
    setYearNo(rows[0].yearno);
  }
}, [rows]);
// 연차 개수 차감
const yearNoCount = rows.map( row => (
    if(row.approvaldate != ''){
        int yearno = yearNo
        for (let i = yearno; i < row.length; i--){

        }
    }
 ));

     return (

     <Container>
        <div className="topDiv">
        { /* <LeaveRequest /> */}
        <div>
            <span> </span>
            <span> /  </span>
            <span> {yearNo} </span>
        </div>
        </div>
         <TableContainer component={Paper}>
           <Table sx={{ minWidth: 650 }} aria-label="simple table">
             <TableHead>
               <TableRow className="tableTitle">
                 <TableCell align="center" style={{ width:'20%' }}>연차신청일</TableCell>
                 <TableCell align="center" style={{ width:'20%' }}>결재상태</TableCell>
                 <TableCell align="center" style={{ width:'20%' }}>연차사유</TableCell>
                 <TableCell align="center" style={{ width:'40%' }}>연차사용일</TableCell>
               </TableRow>
             </TableHead>
             <TableBody>
              {rows.map((row) => (
                 <TableRow key={row.name} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>

                     <TableCell align="center"> {row.lrequestdate}</TableCell>
                     <TableCell align="center">
                     {row.approvaldate != null ? '결재완료'+row.approvaldate : '결재대기중'}
                     </TableCell>
                     <TableCell align="center">{row.requestreason}</TableCell>
                     <TableCell align="center">
                       {row.lstart} ~ {row.lend}
                     </TableCell>
                   </TableRow>
               ))}
             </TableBody>
           </Table>
         </TableContainer>
        <div style={{margin : "30px 0px", display : "flex", justifyContent:"center"}}>
            <Pagination count={ totalPage }  variant="outlined" color="primary" onChange={selectPage} />
        </div>
     </Container>
     );

}



