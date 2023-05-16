import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { blue } from '@mui/material/colors';
//---------------------------테이블-------------------------------------
import { DataGrid } from '@mui/x-data-grid';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';



const columns = [
  { field: 'eno', headerName: '사원번호', width: 300},
  { field: 'cdate', headerName: '출근', width: 300 },
  { field: 'udate', headerName: '퇴근', width: 300 },
];



export default function Mypage(props) {
//const [ login,setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) );
 let [gotime,setGotime] = useState([]);

 useEffect(() =>{
    axios.get('/employee/gooutwork').then(r=>{
        console.log(r.data);
        setGotime(r.data);
    })
 },[])
console.log(gotime);
return (
    <Container>
        <div style={{ height: 400, width: '100%' }}>
          <DataGrid
            rows={gotime}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 5 },
              },
            }}
            pageSizeOptions={[5, 10]}

          />
        </div>
    </Container>
  );





}