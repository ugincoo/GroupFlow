import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------테이블-------------------------------------
import { DataGrid } from '@mui/x-data-grid';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
//---------------------------사용자-------------------------------------
import Logo from '../../logo.svg';  //기본사진
import styles from '../../css/employee.css'; //css
import Department from './Department'; //부서 [하위]
import SearchEmplyee from './SearchEmplyee'; //검색 [하위]
import ViewDetailEmployee from './ViewDetailEmployee'; //검색 [하위]
import { blue } from '@mui/material/colors';


const columns = [

  { field: 'id', headerName: '사원번호', width: 200 },
  { field: 'ename', headerName: '사원명', width: 200 },
  { field: 'pname',headerName: '직급', width: 200},
  { field: 'dname',headerName: '부서명', width: 250},
  { field: 'ephone',headerName: '핸드폰번호',width: 300}

];
export default function AllEmplyee(props) {

    let [allEmplyee,setAllEmplyee] = useState([]);
    let [info,setInfo]=useState({'dno':0 , 'leavework':1, 'key':0 , 'keyword':'' })    //1 : 입사 2:퇴사
    let [mydno,setMydno] = useState();
    let [oneEmployee,setOneEmployee] = useState({});//상세보기에 ID넘기는 기능


        //전직원 출력하기[관리자입장]
        useEffect( ()=>{
          axios
            .get("/employee/print",{params:info})
            .then(r=>{
             setAllEmplyee(r.data)
            })
            .catch(err=>{console.log(err)})
        },[info])

        //로그인한 자신의 부서구하기
        useEffect( ()=>{
            axios
                .get("/employee/print/finddno")
                .then(r=>{
                console.log(r.data)
                setMydno(r.data)
                })
        },[mydno])




        const departmentchange=(dno)=>{ //[부서별출력]

            console.log(dno)
            info.dno=dno;
            setInfo({...info})
        };
        const inoutEmployee=(leavework)=>{ //[입/퇴사자]

            console.log(leavework)
            info.leavework=leavework;
            setInfo({...info})
        };
         const searchinfo=(key,keyword)=>{ //키+키워드 검색

            console.log(key + keyword)
            info.key=key;
            info.keyword=keyword;
            setInfo({...info})
         };








  const handleRowClick = (params) => {

    setOneEmployee(params.row)

  };

    console.log(allEmplyee)
    console.log(mydno)

     return (
 

             <Box sx={{display: 'flex',flexDirection: 'column', mt:'50px',alignItems: 'center',height: '100vh' }} >


                <Box
                 sx={{ px: 6, py:4, borderRadius: 3, boxShadow: 1, bgcolor: 'white', width: '100%', maxWidth: '1200px', mb : 4 }} >
                   <div className="upperPart">
                       <div className="top">
                          <Department departmentchange={departmentchange} inoutEmployee={inoutEmployee} />
                          <SearchEmplyee searchinfo={searchinfo}/>
                       </div>
                       <div style={{ height: '100%', width: '100%' }}>
                          <DataGrid
                           rows={allEmplyee}
                           columns={columns}
                           initialState={{ pagination: {paginationModel: { page: 0, pageSize: 5 }}}}
                           pageSizeOptions={[5,10]}
                           onRowClick={handleRowClick}/>
                       </div>
                   </div>
                </Box>
                { (mydno==1 && oneEmployee.eno !== undefined) ? <ViewDetailEmployee oneEmployee={oneEmployee} /> : ''}


            </Box>

        )



}