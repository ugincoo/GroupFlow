import React,{useState,useEffect} from 'react';
import axios from 'axios';
import DepartmentChange from './DepartmentChange';
import PositionChange from './PositionChange';
import GoworkPrint from './GoworkPrint';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { blue } from '@mui/material/colors';
//--------------------------텝------------------------------------------
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import Tab from '@mui/material/Tab';
//---------------------------테이블-------------------------------------
import { DataGrid } from '@mui/x-data-grid';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';





export default function Mypage(props) {

const [value, setValue] = React.useState('1');
     //2.탭 변경 함수
      const handleTabsChange = (event, newValue) => {
         setValue(newValue);
       };
return (
       <Box sx={{ width: '100%', typography: 'body1' }}>
         <TabContext value={value}>
           <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
             <TabList onChange={handleTabsChange} aria-label="lab API tabs example">
               <Tab label="출근이력 " value="1" />
               <Tab label="직급변경 이력" value="2" />
               <Tab label="부서이동 이력" value="3" />

             </TabList>
           </Box>
           <TabPanel value="1">
             <GoworkPrint />
           </TabPanel>
           <TabPanel value="2">
              <PositionChange />
           </TabPanel>
           <TabPanel value="3">
             <DepartmentChange />
           </TabPanel>

         </TabContext>
       </Box>
  );





}



