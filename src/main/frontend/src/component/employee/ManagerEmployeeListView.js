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
import Evaluation from './Evaluation';
import UpdateEvaluation from './UpdateEvaluation';
import EvaluationView from './EvaluationView';


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

    // 등록구역 상태변수
    const [  evRegistBtn , setEvRegistBtn ] = useState(<></>);

    // 알람html 상태변수
    const [ alerthtml , setAlerthtml ] = useState(<><Alert  variant="filled" severity="info" width="100%">안녕하세요</Alert></>);

    // 평가기간
    const [ evperiod , setEvperiod ] = useState({ startdate : "" , enddate : "" });
    //console.log(evperiod)

    // 선택한 직원 한명의 인사평가 리스트
    const [ evaluationList , setEvaluationList ] = useState([]);
    console.log(evaluationList)

    // 작성 클릭시 업무평가 컴포넌트 출력하기 위한 상태변수
    const [ evaluationComponent , setEvaluationComponent ] = useState(<></>)

    // 작성 클릭시 업무평가 컴포넌트 출력 , 컴포넌트에 props = 선택한직원정보, 업무평가리스트DB에서 가져오는함수, 컴포넌트 지우는함수 , 미완료업무평가체크함수
    const evaluationPrint = ()=>{
        setEvaluationComponent( <><Evaluation targetEmployee={ selectEmployee } listItemClick={listItemClick} removeComponentPrint={removeComponentPrint} checkIncomplete={checkIncomplete} /></> )
    }

    // 선택한 직원의 evaluationList 가져오고, 선택직원변수가 변경되기때문에
    // useEffect( ()=>{},[selectEmployee] ) 선택직원이 변경될때마다 evaluationList for문돌려서 등록버튼 출력할지 판단
    //[ 1.직원을 선택했는지 2.업무평가기간에 이미 등록된 업무평가가 있는지  ]

    // 하위컴포넌트 출력 제거하는 함수
    const removeComponentPrint = ()=>{
        setEvaluationComponent(<></>)
    }
    // 선택한 직원이 바뀔때마다 실행
    useEffect( ()=>{
        // 다른 직원선택하면(selectEmployee가 변경되면) 아래 하위컴포넌트 출력제거
        removeComponentPrint();

        //* 업무평가기간에 포함되는게 하나라도 있으면 count++
        let count = 0;

        //1.직원을 선택했는지
        if ( Object.keys(selectEmployee).length === 0 ){ return; }
        console.log("직원을 선택함")

        //2.업무평가기간에 이미 등록된 업무평가가 있는지
            // 업무평기기간 가져오기
        let evperiodStartDate = new Date(evperiod.startdate);
        let evperiodEndDate = new Date(evperiod.enddate);

            // 업무평가리스트 하나씩 업무평가등록한 날짜 확인하기
        evaluationList.forEach(e=>{
           let cdate =  new Date(e.cdate.split(" ")[0]);
           console.log(cdate);

           console.log(cdate >= evperiodStartDate && cdate <= evperiodEndDate)
           // 업무평가기간에 포함되는게 없으면 등록출력 => 업무평가기간에 포함되는게 하나라도 있으면 등록출력X
           if( cdate >= evperiodStartDate && cdate <= evperiodEndDate ){
                count++;
           }
        }) // forEach end
        console.log(count)
        // 업무평가기간에 포함되는게 하나라도 있으면 등록출력X
        if( count > 0 ){
            // 등록출력구역 깡통만들기
            console.log("업무평가 기간에 평가등록된 것이 있음")
            setEvRegistBtn(<></>)
        }
        else{ // 업무평가기간에 등록된 업무평가 없음 => 등록출력
            // 등록출력구역 깡통만들기
            console.log("업무평가 기간에 평가등록된 것이 없음")
           let today = new Date();
           let todayYear = today.getFullYear();
           let todayMonth = today.getMonth()+1;
           let text = "";
           if ( todayMonth < 7 ){
               text = todayYear+"년 상반기 업무평가보고서 작성"
           }else{
               text = todayYear+"년 하반기 업무평가보고서 작성"
           }
           setEvRegistBtn(<>
               <List>
                   <ListItem disablePadding>
                       <ListItemButton>
                           <ListItemText primary={text} />
                           <Button variant="contained" onClick={evaluationPrint}>작성</Button>
                       </ListItemButton>
                   </ListItem>
               </List>
           </>);
        }


    },[selectEmployee] )



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

    // 미완료한 업무평가 있는지 확인
    const checkIncomplete = ()=>{
        axios.get("/evaluation/check/incomplete").then(r=>{
            console.log(r.date)
            if ( r.data == 1 ){ alert("로그인 하세요")}
            else if( r.data == 2 ){ alert("권한이 없습니다.")}
            else if( r.data == 3){ // 미완료 건이 있음.
                setAlerthtml( <> <Alert variant="filled" severity="warning"> 미완료된 업무평가보고서가 있습니다. </Alert> </> )
            }else if( r.data == 4){
                setEvperiod({...evperiod})
            }
        })
    }
    // 실행시 로그인한 사람의 부서직원리스트 가져오기 , 업무평가 보고서 제출기한인지 체크 , 미완료한 업무평가 있는지 확인
    useEffect(() => {
        // 업무평가 보고서 기한설정
        setUpEvperiod();
        // 실행시 로그인한 사람의 부서직원리스트 가져오기
        axios.get("/employee/department").then(r=>{setEmployeesByDepartment(r.data);})
        // 미완료한 업무평가 있는지 확인
        checkIncomplete();
    }, [])



    // 직원선택 , 선택한 직원의 evaluationList가져오기
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
                    e.halfPeriodTitle=year+"년 상반기 업무평가보고서"
                }else{
                    e.halfPeriodTitle=year+"년 하반기 업무평가보고서"
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
            setSelectEmployee({...e}) // 하위컴포넌트에서 업무평가했을때 동일번호가 선택되기 때문에 렌더링되어야해서 새로운 주소값으로 넣어줌
        })
    }

    // 미완료 또는 수정 버튼 누르면 => 수정컴포넌트 출력
    const updateEvaluation = (e)=>{
        setEvaluationComponent( <><UpdateEvaluation targetEmployee={ selectEmployee } evaluation={ e } listItemClick={listItemClick} removeComponentPrint={removeComponentPrint} checkIncomplete={checkIncomplete} /></> )
    }

    // 각 evaluation마다 현재총점 , 미완료/수정/완료 구분해서 버튼생성
    const getEvaluationBtnState = (e)=>{
        console.log("evscoreMap")
        console.log(e.evscoreMap)
        let sum = 0;
        let score = 0;
        for (const key in e.evscoreMap) {
            if (e.evscoreMap.hasOwnProperty(key)) {
                const value = e.evscoreMap[key];
                //console.log(`Key: ${key}, Value: ${value}`);
                let intkey = parseInt(key);
                sum = sum + intkey;
                let intvalue = parseInt(value);
                score = score + intvalue;
                //console.log("key : " + intkey)
                //console.log("sum : "+sum)
                console.log("intvalue :"+intvalue)
                console.log("score :"+score)
            }
        }
        let html = <ListItemText primary={score+"/100점"} />;
        sum != 55 || e.evopnion === null || e.evopnion === "" ? html = <>{html}<Button variant="contained" color="warning" onClick={()=>updateEvaluation(e)} >미완료</Button></>
            : e.disabled ? html = <>{html}<Button variant="contained" color="success" disabled={e.disabled}>완료</Button></>
            : html = <>{html}<Button variant="contained" disabled={e.disabled} onClick={()=>updateEvaluation(e)} >수정</Button></>
        console.log(html)
        return html;
    }

    // 각 evaluation 선택하면 선택한 업무평가 조회
    const evView = (evno)=>{
        console.log("업무평가 클릭함")
        console.log("evno : "+ evno)
        setEvaluationComponent(<><EvaluationView evno={evno} /></>)
    }


    return (
        <div>
            <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
                <Item>
                    <Grid container sx={{ p:'20px'}}>
                        <Grid item xs={12} sm={12}>
                            {alerthtml}
                        </Grid>
                    </Grid>
                    <Grid container sx={{ p:'20px'}}>
                        <Grid item xs={12} sm={5}>
                            <Box width='100%' maxWidth='400px' height='300px' marginRight='40px' padding='20px' sx={{boxShadow: 1, bgcolor: 'white' , overflow: 'auto'}}>
                                <nav aria-label="secondary mailbox folders">
                                    {
                                        employeesByDepartment.map(employee =>{
                                            return (
                                                <List key={employee.eno}>
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
                            <Box width='100%' maxWidth='640px' height='50px' padding='20px' sx={{boxShadow: 1, bgcolor: 'white'}}>
                                { Object.keys(selectEmployee).length !== 0 ?
                                    <Typography>사번 : {selectEmployee.eno} 성함 : {selectEmployee.ename} {selectEmployee.pname}</Typography>
                                : "" }
                            </Box>
                            <Box width='100%' maxWidth='640px' height='200px' padding='20px' sx={{boxShadow: 1, bgcolor: 'white' , overflow: 'auto'}}>
                                <nav aria-label="secondary mailbox folders">
                                    {evRegistBtn}
                                </nav>
                                <nav aria-label="secondary mailbox folders">
                                    {
                                        evaluationList.map(e =>{
                                            return (
                                                <List key={e.evno}>
                                                    <ListItem disablePadding>
                                                        <ListItemButton>
                                                            <ListItemText primary={e.halfPeriodTitle} onClick={ ()=> evView(e.evno) } />
                                                            {getEvaluationBtnState(e)}
                                                        </ListItemButton>
                                                    </ListItem>
                                                </List>
                                            );
                                        })
                                    }
                                </nav>
                            </Box>
                        </Grid>
                    </Grid>
                </Item>
          </Stack>

            {evaluationComponent}

        </div>
      );
}