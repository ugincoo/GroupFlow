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

function priceRow(qty, unit) {
  return qty * unit;
}

function createRow(desc, qty, unit) {
  const price = priceRow(qty, unit);
  return { desc, qty, unit, price };
}

function subtotal(items) {
  return items.map(({ price }) => price).reduce((sum, i) => sum + i, 0);
}

const rows = [
  createRow('Paperclips (Box)', 100, 1.15),
  createRow('Paper (Case)', 10, 45.99),
  createRow('Waste Basket', 2, 17.99),
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
        axios.get("/evaluation/view",{params:{evno:props.evno}})
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
                        <TableCell align="center" colSpan={3}>
                          Details
                        </TableCell>
                        <TableCell align="right">Price</TableCell>
                      </TableRow>
                      <TableRow>
                        <TableCell>Desc</TableCell>
                        <TableCell align="right">주제</TableCell>
                        <TableCell align="right">문항</TableCell>
                        <TableCell align="right">점수</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {rows.map((row) => (
                        <TableRow key={row.desc}>
                          <TableCell>{row.desc}</TableCell>
                          <TableCell align="right">{row.qty}</TableCell>
                          <TableCell align="right">{row.unit}</TableCell>
                          <TableCell align="right">{ccyFormat(row.price)}</TableCell>
                        </TableRow>
                      ))}

                      <TableRow>
                        <TableCell rowSpan={3} />
                        <TableCell colSpan={2}>Subtotal</TableCell>
                        <TableCell align="right">{ccyFormat(invoiceSubtotal)}</TableCell>
                      </TableRow>
                      <TableRow>
                        <TableCell>Tax</TableCell>
                        <TableCell align="right">{`${(TAX_RATE * 100).toFixed(0)} %`}</TableCell>
                        <TableCell align="right">{ccyFormat(invoiceTaxes)}</TableCell>
                      </TableRow>
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