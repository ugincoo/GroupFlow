import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------테이블-------------------------------------
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';
import Department from './Department';
//---------------------------사용자-------------------------------------
import Logo from '../../logo.svg';  //기본사진
import styles from '../../css/employee.css'; //css



export default function AllEmplyee(props) {
    let [allEmplyee,setAllEmplyee] = useState([]);
    let [info,setInfo]=useState({'dno':0 , 'dcendreason':1})    //1 : 입사 2:퇴사 임시값



        //전직원 출력하기[관리자입장]
        useEffect( ()=>{
          axios
            .get("http://localhost:8080/employee",{params:info})
            .then(r=>{
             setAllEmplyee(r.data)
            })
            .catch(err=>{console.log(err)})
        },[info])



        const departmentchange=(dno)=>{ //[부서별출력]

            console.log(dno)
            info.dno=dno;
            setInfo({...info})
        };
        const inoutEmployee=(dcendreason)=>{ //[입/퇴사자]

            console.log(dcendreason)
            info.dcendreason=dcendreason;
            setInfo({...info})
        };


    console.log(allEmplyee)
     return (
        <Container>
            <Department departmentchange={departmentchange} inoutEmployee={inoutEmployee} />
            <TableContainer component={Paper} style={{width:'70%'}} >
              <Table  aria-label="simple table" >
                <TableHead>
                  <TableRow className="head">
                    <TableCell  align="center" style={{width:'10%',color:'white'}}>프로필</TableCell>
                    <TableCell  align="center" style={{width:'20%',color:'white'}}>사원번호</TableCell>
                    <TableCell  align="center" style={{width:'10%',color:'white'}}>사원명</TableCell>
                    <TableCell  align="center" style={{width:'10%',color:'white'}}>직급</TableCell>
                    <TableCell  align="center" style={{width:'10%',color:'white'}}>부서명</TableCell>
                    <TableCell  align="center" style={{width:'20%',color:'white'}}>핸드폰번호</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {allEmplyee.map((e) => (
                    <TableRow className="row">
                    <TableCell align="center">
                        <img className="ephoto" src={e.ephoto==null?Logo:e.ephoto}/>
                    </TableCell>
                    <TableCell align="center"><a href={"/"+e.eno}>{e.eno}</a></TableCell>
                    <TableCell align="center">{e.ename}</TableCell>
                    <TableCell align="center">{e.dno}</TableCell>
                    <TableCell align="center">{e.dno}</TableCell>
                    <TableCell align="center">{e.ephone}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
        </Container>
        )



}
