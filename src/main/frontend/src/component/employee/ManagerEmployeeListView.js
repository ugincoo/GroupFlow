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
        justifyContent: 'flex-start',
        alignItems: 'flex-start',
        padding : '20px',
    }));

    // 부서내 직원리스트
    const [ employeesByDepartment , setEmployeesByDepartment ] = useState([]);
    console.log(employeesByDepartment)
    // 직원 한명의 인사평가 리스트
    const [ evaluationList , setEvaluationList ] = useState([]);
    console.log(evaluationList)

    // 실행시 로그인한 사람의 부서직원리스트 가져오기
    useEffect(() => {
        axios.get("/employee/department").then(r=>{setEmployeesByDepartment(r.data);})
    }, [])

    // 선택한 직원의 업무평가리스트 갖고올 때마다 업무평가리스트 항목에 반기 기준 항목추가
    useEffect(() => {
        evaluationList.forEach(e=>{
            //e.cdate // "2023-05-17 오후 19:35:45"
            let cdate = e.cdate.split(" ")[0];
            let year = parseInt(cdate.split("-")[0]);
            let month = parseInt(cdate.split("-")[1]);
            console.log(cdate);
            if ( month < 7 ){

            }else{

            }
            /*
            let today = new Date();
            console.log(today);
            console.log(today.getFullYear());
            console.log(today.getMonth()+1);
            todayMonth = today.getMonth()+1;
            if ( todayMonth)
            */


            //e.halfPeriodList
        })
    },[evaluationList])

    // 직원리스트중에서 선택했을때
    const listItemClick = (eno) => {
        console.log(eno);
        // 선택한 직원의 인사평가 리스트 가져오기
        axios.get("/evaluation/list" , {params: {eno:eno}}).then(r=>{ setEvaluationList(r.data)})
    }

    return (
        <Stack direction="column" justifyContent="flex-start" alignItems="center" spacing={2}>
            <Item>
                <Box width='100%' maxWidth='180px' marginRight='40px' sx={{boxShadow: 1, bgcolor: 'white'}}>
                    <nav aria-label="secondary mailbox folders">
                        {
                            employeesByDepartment.map(employee =>{
                                return (
                                    <List>
                                        <ListItem disablePadding>
                                            <ListItemButton onClick={ ()=> listItemClick(employee.eno)}>
                                                <ListItemText primary={employee.ename+" "+employee.pname} />
                                            </ListItemButton>
                                        </ListItem>
                                    </List>
                                );
                            })

                        }
                    </nav>
                </Box>
                <Box width='100%' maxWidth='400px' marginRight='40px' sx={{boxShadow: 1, bgcolor: 'white'}}>
                    <nav aria-label="secondary mailbox folders">
                        {
                            evaluationList.map(evaluation =>{
                                return (
                                    <List>
                                        <ListItem disablePadding>
                                            <ListItemButton>
                                                <ListItemText primary={evaluation.cdate} />
                                            </ListItemButton>
                                        </ListItem>
                                    </List>
                                );
                            })
                        }
                    </nav>
                </Box>
            </Item>
      </Stack>
      );
}