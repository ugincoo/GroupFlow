import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------테이블-------------------------------------
/*import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';*/
import { DataGrid } from '@mui/x-data-grid';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';
//---------------------------사용자-------------------------------------
import Logo from '../../logo.svg';  //기본사진
import styles from '../../css/employee.css'; //css
import Department from './Department'; //부서 [하위]
import SearchEmplyee from './SearchEmplyee'; //검색 [하위]

const columns = [

  { field: 'id', headerName: '사원번호', width: 100 },
  { field: 'ename', headerName: '사원명', width: 100 },
  { field: 'pname',headerName: '직급', width: 100},
  { field: 'dname',headerName: '부서명', width: 120},
  { field: 'ephone',headerName: '핸드폰번호',width: 150}

];
export default function AllEmplyee(props) {

    let [allEmplyee,setAllEmplyee] = useState([]);
    let [info,setInfo]=useState({'dno':0 , 'leavework':1 , 'key': '' , 'keyword':''})    //1 : 입사 2:퇴사




        //전직원 출력하기[관리자입장]
        useEffect( ()=>{
          axios
            .get("http://localhost:8080/employee/print",{params:info})
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
    console.log( params );
    console.log( params.row );
    console.log( params.row.eno );
  };

    console.log(allEmplyee)
     return (
            <div className="side">
                <div className="top">
                    <Department departmentchange={departmentchange} inoutEmployee={inoutEmployee} />
                    <SearchEmplyee searchinfo={searchinfo}/>
                </div>
                 <div style={{ height: '100%', width: '100%' }}>
                      <DataGrid
                        rows={allEmplyee}
                        columns={columns}
                        initialState={{
                          pagination: {
                            paginationModel: { page: 0, pageSize: 10 },
                          },
                        }}
                        pageSizeOptions={[10,20]}
                        onRowClick={handleRowClick}

                      />
                 </div>
             </div>

        )



}