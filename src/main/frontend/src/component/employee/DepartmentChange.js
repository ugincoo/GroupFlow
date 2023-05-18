import React,{useState,useEffect} from 'react';
import axios from 'axios';
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
  { field: 'id', headerName: '번호', width: 200},
  { field: 'dcstartdate', headerName: '적용날짜', width: 200 },
  { field: 'dcenddate', headerName: '끝날짜', width: 200 },
  { field: 'dcstartreason', headerName: '부서변경이유', width: 200 },
  { field: 'dname', headerName: '부서명', width: 200 }
];



export default function DepartmentChange(props) {
//const [ login,setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) );
 const [ rows , setRows ] = useState([]);
console.log(rows);
 useEffect(() =>{
    axios.get('/employee/departmentprint').then(r=>{
        console.log(r.data);
        setRows(r.data);
    })
 },[])

return (
    <Container>
        <Box
        sx={{ px: 6, py:4, borderRadius: 3, boxShadow: 1, bgcolor: 'white', width: '100%', maxWidth: '1200px', mb : 4 }}
        >
            <div style={{ height: 400, width: '100%' }}>
              <DataGrid
                rows={rows}
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