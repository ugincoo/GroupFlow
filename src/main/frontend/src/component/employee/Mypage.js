import React,{useState,useEffect} from 'react';
import axios from 'axios';
import DepartmentChange from './employee/DepartmentChange';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { blue } from '@mui/material/colors';
//--------------------------텝------------------------------------------
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
//---------------------------테이블-------------------------------------
import { DataGrid } from '@mui/x-data-grid';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';



const columns = [
  { field: 'id', headerName: '번호', width: 300},
  { field: 'cdate', headerName: '출근', width: 300 },
  { field: 'udate', headerName: '퇴근', width: 300 },
  { field: 'eno', headerName: '사원번호', width: 300 }
];



export default function Mypage(props) {
//const [ login,setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) );
 const [ rows , setRows ] = useState([]);

 useEffect(() =>{
    axios.get('/employee/gooutwork').then(r=>{
        console.log(r.data);
        setRows(r.data);
    })
 },[])
console.log(rows);
return (
    <Container>
        <Box
        sx={{ px: 6, py:4, borderRadius: 3, boxShadow: 1, bgcolor: 'white', width: '100%', maxWidth: '1200px', mb : 4 }}
        >
            <div style={{ height: 400, width: '100%' }}>
              <DataGrid
                rows={rows}
                <DepartmentChange/>
                columns={columns}
                initialState={{
                  pagination: {
                    paginationModel: { page: 0, pageSize: 5 },
                  },
                }}
                pageSizeOptions={[5, 10]}

              />
            </div>
        </Box>
    </Container>
  );





}