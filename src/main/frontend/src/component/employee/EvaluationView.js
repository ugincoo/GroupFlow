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
    let intnum = parseInt(num);
    return `${intnum.toFixed(2)}`; // toFixed(2) 변수에 할당된 숫자를 소수점 아래 둘째 자리까지 반올림한 후 문자열로 반환
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


function invoiceTotal(items) {
  return items.map(({ 점수 }) => 점수).reduce((sum, i) => sum + i, 0);
}




/*
  createRow(1,'기여도·업무추진실력', '회사 발전을 위해 노력하며 업무추진력과 실력이 뛰어나다.', 10 ),
  createRow(2,'기여도·업무추진실력', '회사 발전을 위해 노력하며 업무추진력과 실력이 뛰어나다.', 8 ),
  createRow(3,'기여도·업무추진실력', '회사 발전을 위해 노력하며 업무추진력과 실력이 뛰어나다.', 10 ),
*/


//const invoiceSubtotal = subtotal(rows);
//const invoiceTaxes = TAX_RATE * invoiceSubtotal;
//const invoiceTotal = invoiceTaxes + invoiceSubtotal;

export default function EvaluationView(props) {
    // props.evaluation.evno 업무평가 식별번호
    // props.selectEmployee

    const [ rows , setRows] = useState([]); // 업무평가 정보 저장하는 상태
    const [totalScore, setTotalScore] = useState(0); // 수정: 총점을 저장하는 상태 추가
    console.log(props.selectEmployee)

    useEffect(()=>{
        console.log("props.evaluation.evno : " + props.evaluation.evno)
        axios.get("/evaluation/one" , {params:{evno : props.evaluation.evno}})
            .then(r=>{
                console.log(r.data);
                const newRows = r.data.evscoreDtoList.map((r) =>
                  createRow(r.eqno, r.eqtitle, r.equestion, r.eqscore)
                );
                setRows(newRows);
                const total = invoiceTotal(newRows); // 수정: 총점 계산
                setTotalScore(total); // 수정: 총점 상태 업데이트
            })
    },[])


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

    const evno = props.evaluation.evno;

    useEffect(()=>{
        axios.get("/evaluation/view",{params:{evno:props.evaluation.evno}})
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
                        <TableCell align="left">
                          {props.evaluation.halfPeriodTitle}
                        </TableCell>
                        <TableCell align="center">평가일: {props.evaluation.cdate} / 수정일: {props.evaluation.udate}</TableCell>
                        <TableCell align="right">
                            평가대상자: {props.selectEmployee.ename} {props.selectEmployee.pname}
                        </TableCell>
                      </TableRow>

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
                          <TableCell align="right">{row.점수}</TableCell>
                        </TableRow>
                      ))}

                      <TableRow>
                        <TableCell colSpan={2}>Total</TableCell>
                        <TableCell align="right">{ccyFormat(totalScore)}</TableCell>
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
            </Item>
        </Stack>
    </div>
    )
}