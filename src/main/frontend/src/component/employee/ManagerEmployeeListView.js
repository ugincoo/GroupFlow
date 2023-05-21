import React,{useState,useEffect} from 'react';
import axios from 'axios';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import InboxIcon from '@mui/icons-material/Inbox';
import DraftsIcon from '@mui/icons-material/Drafts';
import { Paper , Stack , Box , Typography , Chip , TextareaAutosize , Grid , Button } from '@mui/material';
import { styled } from '@mui/material/styles';
import Alert from '@mui/material/Alert';



export default function ManagerEmployeeListView(props) {

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

    // 부서내 직원리스트
    const [ employeesByDepartment , setEmployeesByDepartment ] = useState([]);
    console.log(employeesByDepartment)

    // 선택한 직원 상태변수
    const [ selectEmployee , setSelectEmployee ] = useState({});
    console.log(selectEmployee)
    console.log( Object.keys(selectEmployee).length === 0 )

    // 등록버튼 상태변수
    const [  evRegistBtn , setEvRegistBtn ] = useState(<></>);
    // 직원을 선택할때마다 등록버튼 출력할지 판단
    useEffect( ()=>{},[selectEmployee])

    // 선택한 직원 한명의 인사평가 리스트
    const [ evaluationList , setEvaluationList ] = useState([]);
    console.log(evaluationList)


    // 알람html 상태변수
    const [ alerthtml , setAlerthtml ] = useState(<><Alert  variant="filled" severity="info" width="100%">안녕하세요</Alert></>);

    // 평가기간
    const [ evperiod , setEvperiod ] = useState({ startdate : "" , enddate : "" });
    console.log(evperiod)

    // 평가기간 설정
    const setUpEvperiod = ()=>{
        let today = new Date();
        let todayYear = today.getFullYear();
        let todayMonth = today.getMonth();
        console.log(todayMonth)
        if ( todayMonth < 7 ){
            evperiod.startdate = todayYear+"-05-21";
            //let july = new Date( todayYear+"-07-01" );
            let lDay = new Date( todayYear , 6 , 0 );
            let lMonth = lDay.getMonth()+1;
            if ( lMonth < 10 ){ lMonth = "0"+lMonth; }
            let ldate = lDay.getDate();
            let lastDay = todayYear+"-"+lMonth+"-"+ldate;
            evperiod.enddate = lastDay;
            setEvperiod({...evperiod})
        }else{
            evperiod.startdate = todayYear+"-12-01";
            //let nextYearFisrt = new Date( (todayYear+1)+"-01-01" );
            //var lastDay = new Date(nextYear.getTime() - 1);
            let lDay = new Date( (todayYear) , 12 , 0 );
            let lMonth = lDay.getMonth()+1;
            if ( lMonth < 10 ){ lMonth = "0"+lMonth; }
            let ldate = lDay.getDate();
            let lastDay = todayYear+"-"+lMonth+"-"+ldate;
            evperiod.enddate = lastDay;
            setEvperiod({...evperiod})
        }
    }

    // 평가기간이면 알림띄우기
    useEffect( ()=>{
       let today = new Date();
       let evperiodStartDate = new Date(evperiod.startdate);
       let evperiodEndDate = new Date(evperiod.enddate);
       console.log("today >= evperiodStartDate && today <= evperiodEndDate : " + (today >= evperiodStartDate && today <= evperiodEndDate) );
       if ( today >= evperiodStartDate && today <= evperiodEndDate ){
            setAlerthtml( <> <Alert  variant="filled" severity="info" width="100%">업무평가보고서 제출기간입니다.  <strong>{evperiod.startdate} ~ {evperiod.enddate}</strong> </Alert> </> )
       }
    },[evperiod])

    // 실행시 로그인한 사람의 부서직원리스트 가져오기 , 업무평가 보고서 제출기한인지 체크
    useEffect(() => {
        // 업무평가 보고서 기한설정
        setUpEvperiod();
        // 실행시 로그인한 사람의 부서직원리스트 가져오기
        axios.get("/employee/department").then(r=>{setEmployeesByDepartment(r.data);})
    }, [])



    // 선택한 직원의 업무평가리스트 갖고올 때마다 업무평가리스트 항목에 반기 기준 항목추가
    /*
    useEffect( ()=>{
        evaluationList.forEach(e=>{
            //e.cdate // "2023-05-17 오후 19:35:45"

            let cdate = e.cdate.split(" ")[0];
            console.log(cdate);
            let year = parseInt(cdate.split("-")[0]);
            console.log(year);
            let month = parseInt(cdate.split("-")[1]);
            console.log(month);
            if ( month < 7 ){
                e.halfPeriodTitle=year+"년 상반기"
            }else{
                e.halfPeriodTitle=year+"년 하반기"
            }
            console.log(e.halfPeriodTitle)
            /*
            let today = new Date();
            console.log(today);
            console.log(today.getFullYear());
            console.log(today.getMonth()+1);
            todayMonth = today.getMonth()+1;
            if ( todayMonth)

        })
        // for문 끝나고 렌더링
        setEvaluationList([...evaluationList])
    },[])
    */

    // 직원선택
    const listItemClick = (e)=>{
        //console.log(e)

        axios.get("/evaluation/list",{params:{eno:e.eno}}).then(r=>{

            r.data.forEach(e=>{
                //halfPeriodTitle
                let cdate = e.cdate.split(" ")[0];
                console.log(cdate);
                let year = parseInt(cdate.split("-")[0]);
                console.log(year);
                let month = parseInt(cdate.split("-")[1]);
                console.log(month);
                if ( month < 7 ){
                    e.halfPeriodTitle=year+"년 상반기"
                }else{
                    e.halfPeriodTitle=year+"년 하반기"
                }
                // disabled 평가기간이 지난 평가는 disabled
                    // 1.현재날짜
                    let today = new Date();
                    // 2. 평가날짜
                    let evaluationDate = new Date(cdate);
                    // 3. 현재날짜의 연도
                    let todayYear = today.getFullYear();
                    // 4. 현재연도의 7월1일
                    let july = new Date(todayYear+"-07-01")
                    // 5. 평가날짜와 비교할 날짜 [ 7월1일 미만이면 targetDate = 올해1월1일 / 7월1일 이상이면 targetDate = 올해7월1일 ]
                    let targetDate;
                    if ( today < july ){
                        targetDate = new Date(todayYear+"-01-01")
                    }else{
                        targetDate = july
                    }
                    // 6. 평가날짜와 targetDate와 비교 [ targetDate랑 비교해서 미만이면 버튼잠금 , 이상이면 버튼클릭가능 ]
                    if ( evaluationDate < targetDate ){
                        e.disabled = true;
                    }else{
                        e.disabled = false;
                    }

            })
            console.log(r.data)
            setEvaluationList(r.data)
            setSelectEmployee(e)
        })
    }



    return (
        <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
            <Item>
                <Grid container>
                    <Grid item xs={12} sm={12}>
                        {alerthtml}
                    </Grid>
                </Grid>
                <Grid container>
                    <Grid item xs={12} sm={5}>
                        <Box width='100%' maxWidth='180px' marginRight='40px' sx={{boxShadow: 1, bgcolor: 'white'}}>
                            <nav aria-label="secondary mailbox folders">
                                {
                                    employeesByDepartment.map(employee =>{
                                        return (
                                            <List>
                                                <ListItem disablePadding>
                                                    <ListItemButton onClick={ ()=> listItemClick(employee)}>
                                                        <ListItemText primary={employee.ename+" "+employee.pname} />
                                                    </ListItemButton>
                                                </ListItem>
                                            </List>
                                        );
                                    })

                                }
                            </nav>
                        </Box>
                    </Grid>
                    <Grid item xs={12} sm={7}>
                        <Box width='100%' maxWidth='400px' marginRight='40px' sx={{boxShadow: 1, bgcolor: 'white'}}>
                            <nav aria-label="secondary mailbox folders">
                                {
                                    evaluationList.map(e =>{
                                        return (
                                            <List>
                                                <ListItem disablePadding>
                                                    <ListItemButton>
                                                        <ListItemText primary={e.halfPeriodTitle} />
                                                       { e.disabled ? <Button variant="outlined" disabled={e.disabled}>완료</Button>
                                                            : <Button variant="outlined" disabled={e.disabled}>수정</Button>
                                                       }
                                                    </ListItemButton>
                                                </ListItem>
                                            </List>
                                        );
                                    })
                                }
                                <List>
                                    <ListItem disablePadding>
                                        <ListItemButton>
                                            <ListItemText primary="업무평가보고서 작성" />
                                            <Button variant="outlined">작성</Button>
                                        </ListItemButton>
                                    </ListItem>
                                </List>
                            </nav>
                        </Box>
                    </Grid>
                </Grid>
            </Item>
      </Stack>
      );
}