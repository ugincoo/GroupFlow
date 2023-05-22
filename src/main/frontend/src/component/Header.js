import React,{useState,useRef,useEffect} from 'react';
import axios from 'axios';

import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Button from '@mui/material/Button';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import Workbtn from './employee/Workbtn';
import AttendanceStatus from './employee/AttendanceStatus';
import styles from '../css/header.css'; //css


export default function Header(props) {
let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) )
    const [ eno, setEno] = useState();
    const [ gokDisabled, setGokDisabled] = useState(false);
    const gokDisabledHandler = () =>{
        if( gokDisabled == true  ){ setGokDisabled(false)  }
        else{  setGokDisabled(true )  }
    }

        //김은영
       const getAttendance =() => {
            axios.get('/employee/infostate').then(r=>{setGokDisabled(r.data)
            console.log(r.data)
            })
        }
        useEffect (()=>{getAttendance()

        ;}

        ,[])

        axios.get('/login/confirm').then( r => { setEno(r.data.eno) } )

        console.log(eno);
           let page=[
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/registration">사원등록</a>},
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/allemployee">직원출력</a>},
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/login">로그인</a>},
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/employee/logout">로그아웃</a>},
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/offlist">연차내역</a>}, //유진 추가
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/mypage">마이페이지</a>},
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/pofflist">부서연차내역</a>}, // 유진 추가 05/16
                    {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/adminofflist">전직원연차내역</a>},//유진 추가
                    {page : <Workbtn gokDisabled={gokDisabled} gokDisabledHandler={gokDisabledHandler} getAttendance={getAttendance} eno={eno}/>}

                ]

        let 출근내역=<AttendanceStatus  />



        const toggleDrawer = (anchor, open) => (event) => {
          if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
          }

        }; 
        const list = (anchor) => (

            <Box
              role="presentation"
              onClick={toggleDrawer(anchor, false)}
              onKeyDown={toggleDrawer(anchor, false)}
            >
              <List>
                {page.map((e, index) => (
                  <ListItem key={e.page} disablePadding>
                    <ListItemButton>
                      <ListItemIcon>
                        {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                      </ListItemIcon>
                      <ListItemText primary={e.page} />
                    </ListItemButton>
                  </ListItem>
                ))}
              </List>
              <Divider />
            </Box>

          );






const drawerWidth = 240;
    return (<>

             <Drawer
                     sx={{
                       width: drawerWidth,
                       flexShrink: 0,
                       '& .MuiDrawer-paper': {
                         width: drawerWidth,
                         boxSizing: 'border-box',
                       },
                     }}
                     variant="permanent"
                     anchor="left"
                   >

                    <List>
                      {page.map((e, index) => (
                        <ListItem key={e.page} disablePadding>
                          <ListItemButton>
                            <ListItemIcon>
                              {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                            </ListItemIcon>
                            <ListItemText primary={e.page} />
                          </ListItemButton>
                        </ListItem>
                      ))}
                    </List>
                    <Divider />

                   { !login?'':
                       ( login.pno >=6?
                        <List>
                          {[출근내역].map((text, index) => (
                            <ListItem key={text} disablePadding>
                              <ListItemButton>
                                <ListItemIcon>
                                  {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                                </ListItemIcon>
                                <ListItemText primary={text} />
                              </ListItemButton>
                            </ListItem>
                          ))}
                        </List> :'')
                   }

                   </Drawer>

    </>)
    }