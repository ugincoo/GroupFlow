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
        alignItems: 'center',
        padding : '20px',
    }));

    // 부서내 직원리스트
    const [ employeesByDepartment , setEmployeesByDepartment ] = useState([]);
    // 직원 한명의 인사평가 리스트
    const [ evaluationList , setEvaluationList ] = useState([]);

    // 실행시 로그인한 사람의 부서직원리스트 가져오기
    useEffect(() => {
        axios.get("/employee/department").then(r=>{console.log(r.data); setEmployeesByDepartment(r.data);})
    }, [])

    // 직원리스트중에서 선택했을때
    const listItemClick = (eno) => {
        console.log(eno);
        // 선택한 직원의 인사평가 리스트 가져오기
        axios.get("/evaluation/list" , {params: {eno:eno}}).then(r=>{console.log(r.data)})
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
            </Item>
      </Stack>
      );
}