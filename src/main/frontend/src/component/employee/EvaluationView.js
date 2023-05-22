import React,{useState,useEffect} from 'react';
import axios from 'axios';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Paper , Stack , Box , Typography , Grid , Button } from '@mui/material';
import { styled } from '@mui/material/styles';

const TAX_RATE = 0.07;

function ccyFormat(num) {
  return `${num.toFixed(2)}`;
}

/*
function priceRow(qty, unit) {
  return qty * unit;
}
*/

function createRow( 번호, 주제 , 문항 , 점수 ) {
  //const price = priceRow(qty, unit);
  return { 번호, 주제 , 문항 , 점수 };
}

function subtotal(items) {
  return items.map(({ 점수 }) => 점수).reduce((sum, i) => sum + i, 0);
}

const rows = [
  createRow(1,'기여도·업무추진실력', '회사 발전을 위해 노력하며 업무추진력과 실력이 뛰어나다.', '10'),
  createRow(2,'기여도·업무추진실력', '회사 발전을 위해 노력하며 업무추진력과 실력이 뛰어나다.', '10'),
  createRow(3,'기여도·업무추진실력', '회사 발전을 위해 노력하며 업무추진력과 실력이 뛰어나다.', '10'),
];


const invoiceSubtotal = subtotal(rows);
const invoiceTaxes = TAX_RATE * invoiceSubtotal;
const invoiceTotal = invoiceTaxes + invoiceSubtotal;

export default function EvaluationView(props) {
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        flexGrow: 1,
        width : '1200px',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'flex-start',
        alignItems: 'flex-start',
        padding : '20px',
    }));

    const evno = props.evno;

    useEffect(()=>{
        axios.get("/evaluation/view",{params:{evno:props.evno}}) // 아직 백엔드 없음
            .then( r=> {
                console.log(r.data)
            })
    },[])

  return (
    <div>
        <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
            <Item>
                <TableContainer component={Paper}>
                  <Table sx={{ minWidth: 700 }} aria-label="spanning table">
                    <TableHead>
                      <TableRow>
                        <TableCell>주제</TableCell>
                        <TableCell>문항</TableCell>
                        <TableCell align="right">점수</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {rows.map((row) => (
                        <TableRow key={row.번호}>
                          <TableCell>{row.주제}</TableCell>
                          <TableCell>{row.문항}</TableCell>
                          <TableCell align="right">{ccyFormat(row.점수)}</TableCell>
                        </TableRow>
                      ))}

                      <TableRow>
                        <TableCell colSpan={2}>Total</TableCell>
                        <TableCell align="right">{ccyFormat(invoiceTotal)}</TableCell>
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
            </Item>
        </Stack>
    </div>
    )
}