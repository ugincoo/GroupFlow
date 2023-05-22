import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { Paper , Stack , Box , Typography , Chip , TextareaAutosize , Grid , Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import { Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';


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

const ConfirmationModal = ({ open, onClose, onConfirm }) => {
  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>확인</DialogTitle>
      <DialogContent>
        <p>기한이 지나 저장 후 수정이 불가합니다. 제출하시겠습니까?</p>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>아니오</Button>
        <Button onClick={onConfirm} variant="contained" autoFocus>
          예
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default function UpdateEvaluation(props) {
    // props.evaluation / props.listItemClick / props.removeComponentPrint / props.targetEmployee / props.checkIncomplete
    // props.eno대신에 임시 eno
    const eno = props.targetEmployee.eno; // 상위컴포넌트에게 넘겨받은 평가대상자의 eno
    // 로그인한 평가자 정보 상태변수
    const [ evaluator , setEvaluator ] = useState({})
    // 평가대상자 정보 상태변수
    const [ targetEmployee , setTargetEmployee ] = useState({})
    // 문항리스트 상태변수배열
    const [ equestionList , setEquestionList ] = useState([])
    // 각 문항의 점수를 저장할 상태 설정
    const [scores, setScores] = useState({});
    console.log(scores);
    // 평가의견 상태변수
    const [eopinion,setEopinion] = useState('');

    // 첫실행시 문항리스트 가져오기 , 로그인 정보(평가자) , 평가대상자(props로 받은 eno)정보 가져오기
    useEffect(()=>{
        //문항리스트 가져오기
        axios.get("/evaluation/equestion").then(r=>{console.log(r.data); setEquestionList(r.data); })
        //로그인 정보 가져오기
        axios.get("/login/confirm").then(r=>{console.log(r.data); setEvaluator(r.data);})
        //평가대상자(props로 받은 eno)정보 가져오기
        axios.get("/employee/select/info" , {params:{eno:eno}}).then(r=>{console.log(r.data); setTargetEmployee(r.data);})
        // 기존문항 점수선택값 적용하기
        setScores( props.evaluation.evscoreMap )
        // 기존 업무평가 의견가져와서 적용하기
        setEopinion(props.evaluation.evopnion)

    },[])


    // 오늘날짜
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = String(currentDate.getMonth() + 1).padStart(2, '0');
    const day = String(currentDate.getDate()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;


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

    // 모달 열기 닫기용 상태변수
    const [open, setOpen] = useState(false);

    // 모달닫기
    const handleCloseModal = () => { setOpen(false); };

    // 평가 저장 누르면
    const evaluationUpdateSubmit = (e)=>{
        console.log(scores)
        // 기한이 지난 업무평가는 수정불가를 모달로 안내.
        if (props.evaluation.disabled === true) { setOpen(true); }
        else{ handleConfirmModal(); } // 기한문제 없으면 안내 없이 저장
    }
    // 모달 예 누르면 업무평가 제출처리
    const handleConfirmModal = () => {
        setOpen(false);
        // 업무평가 제출 처리
        /*
        if ( props.evaluation.disabled === true ){
            let result = confirm("기한이 지나 저장 후 수정이 불가합니다. 제출하시겠습니까?");
            if (!result) { return; }
        }*/
        let info = { evno:props.evaluation.evno , targetEno : eno , evopnion : eopinion , evscoreMap : scores }
        console.log(info)
        axios.put("/evaluation",info).then(r=> {
            console.log(r.data)

            if( r.data == 1 ){ alert('로그인 하세요')}
            else if( r.data == 2 ){ alert('부서담당자 외에는 평가 불가합니다.')}
            else if( r.data == 3 ){ alert('평가등록실패 - 관리자문의 오류번호: '+r.data)}
            else if( r.data == 4 ){ alert('평가등록실패 - 관리자문의 오류번호: '+r.data)}
            else if( r.data == 5 ){ alert('해당 직원에 대한 평가권한이 없습니다. - 관리자문의 오류번호: '+r.data)}
            else if( r.data == 6 ){ alert('평가등록실패 - 관리자문의 오류번호: '+r.data)}
            else if( r.data == 7 ){ alert('평가등록실패 - 관리자문의 오류번호: '+r.data)}
            else if( r.data == 8 ){
                alert('수정되었습니다.')
                props.listItemClick(props.targetEmployee); // 업무평가를 작성한 직원의 업무평가리스트(상위컴포넌트) 다시 DB에서 가져오기
                props.removeComponentPrint(); // 상위컴포넌트에서 호출한 자신컴포넌트 출력을 지우는 함수
                props.checkIncomplete(); // 미완료 업무평가가 있는지 확인하는 함수
            }
        })
    }

  return (
    <>
      {/* 모달 컴포넌트 */}
      <ConfirmationModal open={open} onClose={handleCloseModal} onConfirm={handleConfirmModal} />
      <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
        <Item >
          <Box width='100%' maxWidth='180px' marginRight='40px'>
            <Typography textAlign='center'>평가대상</Typography>
          </Box>
          <Box width='100%' maxWidth='410px' marginRight='40px'>
            <Typography textAlign='center'>사번: {targetEmployee.eno}</Typography>
          </Box>
          <Box width='100%' maxWidth='400px' marginRight='40px'>
            <Typography textAlign='center'>평가대상자명: {targetEmployee.ename}</Typography>
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
            equestionList.map(e=>{
                return (
                    <Item>
                      <Box width='100%' maxWidth='180px' marginRight='40px'>
                        <Typography textAlign='left' fontWeight='bold'>{e.eqtitle}</Typography>
                      </Box>
                      <Box display="flex" flexDirection="column" alignItems="flex-start"  width='100%' maxWidth='410px' marginRight='40px'>
                          <Typography  variant="body2" textAlign='left'>{e.equestion}</Typography>
                      </Box>
                      <Box width='100%' maxWidth='400px' marginRight='40px'>
                        <FormControl>
                          <RadioGroup
                            row
                            aria-labelledby="demo-row-radio-buttons-group-label"
                            name="row-radio-buttons-group"
                            value={scores[e.eqno] || ''}
                            onChange={(event) => handleScoreChange(e.eqno, event.target.value)}
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
                          label={scores[e.eqno] || 0}
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
                <Grid item sx={4} sm={3} ><Typography fontWeight='bold'>평가날짜 : {formattedDate}</Typography></Grid>
                <Grid item sx={4} sm={3} ><Typography fontWeight='bold'>담당자 : {evaluator.ename} {evaluator.pname}</Typography></Grid>
                <Grid item sx={4} sm={6} >
                    <Button
                        variant="contained"
                        sx={{ bgcolor: '#0c5272', color: 'white', width: '100%', mb:4 }}
                        type="button"
                        onClick={evaluationUpdateSubmit}
                      >
                        평가저장
                    </Button>
                </Grid>
            </Grid>
        </Item>
      </Stack>
    </>
  );
}
