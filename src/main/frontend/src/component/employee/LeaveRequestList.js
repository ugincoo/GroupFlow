import React,{ useState, useEffect } from 'react';
import axios from 'axios';
/*---------------------table mui-------------------*/
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
/*-------------------------------------------------*/
import Container from '@mui/material/Container';
//버튼
import Button from '@mui/material/Button';
// 페이징처리
import Pagination from '@mui/material/Pagination';



export default function LeaveRequestList(props){
    // 1. 요청한 게시물 정보를 가지고 있는 리스트 변수[ 상태 관리변수 ]
        // let [ rows , setRows ] = useState( [] )
         let [pageInfo , setPageInfo] = useState({ 'cno':0 , 'page':1 ,'key':'','keyword':'' });
         let [ totalPage , setTotalPage] = useState( 1 )
         let [ totalCount , setTotalCount] = useState( 1 )
         // 2. 서버에게 요청하기 [ 컴포넌트가 처음 생성 되었을때 ] // useEffect( ()=>{} , [] )
         useEffect( ()=>{
             axios.get('/dyaoff',{ params : pageInfo })
                 .then( r => {
                        console.log(r.data);
 //                       setRows( r.data.boardDtoList )      // 응답받은 게시물 리스트 대입
 //                       setTotalPage( r.data.totalPage )    // 응답받은 총 페이지수 대입
 //                       setTotalCount( r.data.totalCount)   // 응답받은 총 게시물수 대입
                    } )
                 .catch( err => { console.log(err); })

         } , [pageInfo] ) //pageInfo(cno, page)

        //3. 카테고리 변경
        const categoryChange = (cno) => {
            { pageInfo.cno = cno ;  setPageInfo( {...pageInfo} ); }
        }
        //4. 페이징 변경
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
        //5. 검색
        const onSearch = (e)=>{
            console.log(e);
            pageInfo.key = document.querySelector('.key').value;
            pageInfo.keyword = document.querySelector('.keyword').value;
            setPageInfo({...pageInfo});

        }
     return (

     <Container>
        <div> 현재 페이지 : { pageInfo.page } 게시물수 : { totalCount } </div>
         <TableContainer component={Paper}>
           <Table sx={{ minWidth: 650 }} aria-label="simple table">
             <TableHead>
               <TableRow>
                 <TableCell align="center" style={{ width:'10%' }}>번호</TableCell>
                 <TableCell align="center" style={{ width:'10%' }}>신청자</TableCell>
                 <TableCell align="center" style={{ width:'60%' }}>연차신청일</TableCell>
                 <TableCell align="center" style={{ width:'10%' }}>결제일</TableCell>
               </TableRow>
             </TableHead>
             <TableBody>
              {/* } {rows.map((row) => (
                 <TableRow  key={row.name}   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
                   <TableCell component="th" scope="row"> {row.bno} </TableCell>
                                              {/* == 이동 :"/board/view/?bno"=row.bno *
                   <TableCell align="center">{row.mname}</TableCell>
                   <TableCell align="center">{row.bdate}</TableCell>
                   <TableCell align="center">{row.bview}</TableCell>
                 </TableRow>
               ))} */}
             </TableBody>
           </Table>
         </TableContainer>
        <div style={{margin : "30px 0px", display : "flex", justifyContent:"center"}}>
            <Pagination count={ totalPage }  variant="outlined" color="secondary" onChange={selectPage} />
        </div>
     </Container>
     );

}