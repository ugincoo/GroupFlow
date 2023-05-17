import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { Paper , Stack , Box , Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    flexGrow: 1,
    width : '1200px',
    display: 'flex',
    justifyContent: 'flex-start',
    alignItems: 'center',
    padding : '20px',
}));



export default function Evaluation(props) {
  let evaluationList = [
    { no : 1 , title: '기여도·업무추진실력', question: ["회사 발전을 위해 노력한다.", "업무추진력과 실력이 뛰어나다."] } ,
    { no : 2 , title: '기획력·판단력', question: ["정확한 판단으로 실현 가능한 대안을 제시한다.", "모든 상황을 충분히 고려하여 결정한다."] } ,
    { no : 3 , title: '업무개선·창의력', question: ["기존 관행에 얽매이지 않고 창의력을 바탕으로 문제를 해결한다."] } ,
    { no : 4 , title: '추진력·결단력', question: ["상황에 따라 문제점을 찾아내고 해결할 능력이 있다.", "논리적으로 설명하거나 설득하는 능력이 있다."] } ,
    { no : 5 , title: '문제해결·논리·협상력', question: ["상황에 따라 문제점을 찾아내고 해결할 능력이 있다.", "논리적으로 설명하거나 설득하는 능력이 있다."] } ,
    { no : 6 , title: '적시성·정확성', question: ["주어진 업무를 적시에 처리하여 정확성이 높다."] } ,
    { no : 7 , title: '조직관리 및 조직기여도', question: ["조직의 목표에 대해 관리능력이 있으며, 조직발전에 많은 기여를 하고 있다."] } ,
    { no : 8 , title: '성실성·책임감', question: ["항상 근무태도가 양호하며, 담당업무는 물론 부서의 업무까지도 내일처럼 책임진다."] } ,
    { no : 9 , title: '공감·협조능력', question: ["업무관련 정보를 공유하고 상하, 동료 간에 원만한 인간관계를 유지한다."] } ,
    { no : 10 , title: '애사심·봉사심', question: ["회사를 아끼는 마음이 있다.", "어려운 업무에 자신이 먼저 나서서 처리한다."] }
  ];

  return (
    <>
      <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
        {
            evaluationList.map(e=>{
                return (
                    <Item>
                      <Box width='100%' maxWidth='200px'>
                        <Typography textAlign='left'>{e.title}</Typography>
                      </Box>
                      <Box display="flex" flexDirection="column" alignItems="flex-start"  width='100%' maxWidth='600px'>
                        {e.question.map((q) => (
                          <Typography>{q}</Typography>
                        ))}
                      </Box>
                      <Box>
                        <FormControl>
                          <RadioGroup row aria-labelledby="demo-row-radio-buttons-group-label" name="row-radio-buttons-group">
                            <FormControlLabel value="10" control={<Radio />} label="탁월" />
                            <FormControlLabel value="8" control={<Radio />} label="우수" />
                            <FormControlLabel value="6" control={<Radio />} label="보통" />
                            <FormControlLabel value="4" control={<Radio />} label="미흡" />
                            <FormControlLabel value="2" control={<Radio />} label="불량" />
                          </RadioGroup>
                        </FormControl>
                      </Box>
                    </Item>
                )
            })
        }
      </Stack>
    </>
  );
}
