import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { Paper , Stack , Box , Typography , Chip , TextareaAutosize , Grid , Button } from '@mui/material';
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

    // 각 문항의 점수를 저장할 상태 설정
    const [scores, setScores] = useState({});
    console.log(scores);

    // 평가의견 상태변수
    const [eopinion,setEopinion] = useState('');

    // 평가의견 onChange
    const opinionOnChange = (e)=>{
        e.preventDefault();
        console.log(e.target.value);
        setEopinion(e.target.value)
    }

    // 점수가 선택될 때 호출되는 함수
    const handleScoreChange = (questionNo, score) => {
        setScores((prevScores) => ({
          ...prevScores,
          [questionNo]: score,
        }));
    };

    // 점수합계 계산
    const calculateTotalScore = ()=>{
        // score안의 점수를 하나씩 꺼내서 합산
        const totalScore = Object.values(scores).reduce((accmulator, currentValue)=> accmulator + parseInt(currentValue), 0);
        return totalScore
    }



  const evaluationSubmit = (e)=>{
    console.log(scores)
  }

  return (
    <>
      <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
        <Item >
          <Box width='100%' maxWidth='180px' marginRight='40px'>
            <Typography textAlign='center'>평가대상</Typography>
          </Box>
          <Box width='100%' maxWidth='410px' marginRight='40px'>
            <Typography textAlign='center'>2023001</Typography>
          </Box>
          <Box width='100%' maxWidth='400px' marginRight='40px'>
            <Typography textAlign='center'>김하나</Typography>
          </Box>
          <Box width='120px'>
            <Typography textAlign='center'></Typography>
          </Box>
        </Item>
        <Item sx={{backgroundColor:'aliceblue'}}>
          <Box width='100%' maxWidth='180px' marginRight='40px'>
            <Typography textAlign='center'>항목</Typography>
          </Box>
          <Box width='100%' maxWidth='410px' marginRight='40px'>
            <Typography textAlign='center'>문항</Typography>
          </Box>
          <Box width='100%' maxWidth='400px' marginRight='40px'>
            <Typography textAlign='center'>점수선택</Typography>
          </Box>
          <Box width='120px'>
            <Typography textAlign='center'>선택한 점수</Typography>
          </Box>
        </Item>
        {
            evaluationList.map(e=>{
                return (
                    <Item>
                      <Box width='100%' maxWidth='180px' marginRight='40px'>
                        <Typography textAlign='left' fontWeight='bold'>{e.title}</Typography>
                      </Box>
                      <Box display="flex" flexDirection="column" alignItems="flex-start"  width='100%' maxWidth='410px' marginRight='40px'>
                        {e.question.map((q) => (
                          <Typography  variant="body2" textAlign='left'>- {q}</Typography>
                        ))}
                      </Box>
                      <Box width='100%' maxWidth='400px' marginRight='40px'>
                        <FormControl>
                          <RadioGroup
                            row
                            aria-labelledby="demo-row-radio-buttons-group-label"
                            name="row-radio-buttons-group"
                            value={scores[e.no] || ''}
                            onChange={(event) => handleScoreChange(e.no, event.target.value)}
                          >
                            <FormControlLabel value="10" control={<Radio />} label="탁월" />
                            <FormControlLabel value="8" control={<Radio />} label="우수" />
                            <FormControlLabel value="6" control={<Radio />} label="보통" />
                            <FormControlLabel value="4" control={<Radio />} label="미흡" />
                            <FormControlLabel value="2" control={<Radio />} label="불량" />
                          </RadioGroup>
                        </FormControl>
                      </Box>
                      <Box width='120px'>
                        <Chip
                          label={scores[e.no] || 0}
                          color="primary"
                          size="large"
                        />
                      </Box>
                    </Item>
                )
            })
        }
        <Item>
          <Box width='100%' maxWidth='180px' marginRight='40px'>
            <Typography textAlign='center'>평가의견</Typography>
          </Box>
          <Box width='100%' maxWidth='850px' marginRight='40px'>
            <textarea
                rows={4}
                placeholder="평가의견을 남겨주세요"
                onChange={opinionOnChange}
                value={eopinion}
                style={{ width: '810px', height: '100px', resize: 'none' }}
            />
          </Box>
          <Box width='120px'>
            <Typography textAlign='center'>합계</Typography>
            <Typography textAlign='center'>{calculateTotalScore()} / 100점</Typography>
          </Box>
        </Item>
        <Item>
            <Grid container marginTop='20px' justifyContent="center">
                <Grid item sx={4} sm={3} ><Typography fontWeight='bold'>평가날짜 : 2023-05-17</Typography></Grid>
                <Grid item sx={4} sm={3} ><Typography fontWeight='bold'>담당자 : 김수진 부장</Typography></Grid>
                <Grid item sx={4} sm={6} >
                    <Button
                        variant="contained"
                        sx={{ bgcolor: '#0c5272', color: 'white', width: '100%', mb:4 }}
                        type="button"
                        onClick={evaluationSubmit}
                      >
                        평가완료
                    </Button>
                </Grid>
            </Grid>
        </Item>
      </Stack>
    </>
  );
}
