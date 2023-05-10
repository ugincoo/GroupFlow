import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { blue } from '@mui/material/colors';
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

export default function Mypage(props) {

    const[mypage, setmypage]= useState();

    const gowork=()=>{
        axios.post("http://localhost:8080/employee/gowork").then((r)=>{
            console.log(r.data)
            if(r.data==true){alert("출근등록 되었습니다.");
            document.querySelector('.goworkbtn').disabled=true;
            document.querySelector('.outworkbtn').disabled=false;
                        }
        })

    }
    const outwork=()=>{
        axios.put("http://localhost:8080/employee/outwork").then((r)=>{
            console.log(r.data);
            if(r.data==true){alert("퇴근등록 되었습니다.");
            document.querySelector('.outworkbtn').disabled=true;
            document.querySelector('.goworkbtn').disabled=false;

                                    }

        })

    }



    return(<>
        <Container>
            <button
                type="button" className="goworkbtn"
                onClick={gowork}  > 출근 </button>

            <button
                type="button" className="outworkbtn"
                onClick={outwork}  > 퇴근 </button>

        </Container>
    </>)
}