import React from 'react';
import axios from 'axios';
import Workbtn from './employee/Workbtn';
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
import styles from '../css/header.css'; //css


export default function Header(props) {

    axios.get('/login/confirm').then( r => { console.log( r ) } )

      const [state, setState] = React.useState({
        left: false,
      });

        const toggleDrawer = (anchor, open) => (event) => {
          if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
          }
          setState({ ...state, [anchor]: open });
        };

        let 사원등록 =<a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/registration">사원등록</a>
        let 직원출력 = <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/allemployee">직원출력</a>
        let 연차모달 =<a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/off">연차모달</a>
        let 로그인 =<a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/login">로그인</a>
        let 로그아웃 =<a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/employee/logout">로그아웃</a>
        let 연차내역 = <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/offlist">연차내역</a> //유진 추가
        let 출근현황 = <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/attendancestatus">출근현황</a>
        let 마이페이지 =<a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/mypage">마이페이지</a>
        let 부서연차내역 = <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/pofflist">부서연차내역</a> //유진 추가
        const list = (anchor) => (
            <Box
              role="presentation"
              onClick={toggleDrawer(anchor, false)}
              onKeyDown={toggleDrawer(anchor, false)}
            >
              <List>
                {[사원등록, 직원출력, 연차모달, 로그인,로그아웃,연차내역,부서연차내역,마이페이지,<Workbtn/>].map((text, index) => (
                  <ListItem key={text} disablePadding>
                    <ListItemButton>
                      <ListItemIcon>
                        {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                      </ListItemIcon>
                      <ListItemText primary={text} />
                    </ListItemButton>
                  </ListItem>
                ))}
              </List>
              <Divider />
              <List>
                  {[출근현황].map((text, index) => (
                    <ListItem key={text} disablePadding>
                      <ListItemButton>
                        <ListItemIcon>
                          {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                        </ListItemIcon>
                        <ListItemText primary={text} />
                      </ListItemButton>
                    </ListItem>
                  ))}
                </List>

            </Box>
          );



 

    return (<>

              {['left'].map((anchor) => (
                <React.Fragment key={anchor}>
                  <Button onClick={toggleDrawer(anchor, true)}>메뉴</Button>
                  <Drawer
                    anchor={anchor}
                    open={state[anchor]}
                    onClose={toggleDrawer(anchor, false)}
                  >
                    {list(anchor)}
                  </Drawer>
                </React.Fragment>
              ))}

    </>)
    }