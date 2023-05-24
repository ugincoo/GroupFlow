import React,{useState,useRef,useEffect} from 'react';
import axios from 'axios';
import MenuIcon from '@mui/icons-material/Menu';
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
import Notice from './employee/Notice';
import Weather from './employee/Weather';
import AttendanceStatus from './employee/AttendanceStatus';
import styles from '../css/header.css'; //css
import Fab from '@mui/material/Fab';
import LogoutIcon from '@mui/icons-material/Logout';

export default function Header(props) {
    const[weather, setWeather]=useState([]);
    let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) )
    const [ eno, setEno] = useState();
    const [ gokDisabled, setGokDisabled] = useState(false);
    const [ logoutBtn , setLogoutBtn ] = useState(<></>); // 로그아웃버튼 출력
    const gokDisabledHandler = () =>{
        if( gokDisabled == true  ){ setGokDisabled(false)  }
        else{  setGokDisabled(true )  }
    }
        //[날씨]
        axios.get('/employee/weather').then(r=>{setWeather(r.data)})


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
                {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/offlist">연차내역</a>}, //유진 추가
                {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/mypage">마이페이지</a>},
                {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/pofflist">부서연차내역</a>}, // 유진 추가 05/16
                {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/adminofflist">전직원연차내역</a>},//유진 추가
                {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/manageremployeelistview">업무평가</a>},//슬비 추가
                {page : <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/notice">공지사항</a>},
                {page : <Workbtn gokDisabled={gokDisabled} gokDisabledHandler={gokDisabledHandler} getAttendance={getAttendance}eno={eno}/>}


            ]

        let 출근내역=<AttendanceStatus/>
        let 날씨구현=<Weather/>
        // 로그인 로컬스토리지 세션 변경될때마다 로그아웃버튼 출력여부 확인
        useEffect(()=>{
            console.log("login!==null : "+login!==null)
            if( login !== null ){
                console.log("login===null이 아니다.");
                setLogoutBtn(
                   <>
                       <Fab variant="extended" color="primary" aria-label="add"  sx={{ position: 'fixed', bottom: 30, left: 39 }}>
                         <LogoutIcon sx={{ mr: 1 , color: 'rgb(219 223 235)' }} />
                         <a style={{textDecoration: 'none', color: 'rgb(219 223 235)',fontWeight: 'bold'}} href="/logout">로그아웃</a>
                       </Fab>
                   </>
                );
            }else{
                setLogoutBtn(<></>);
            }
        },[login])


         const [state, setState] = React.useState({ left: false, });

                const toggleDrawer = (anchor, open) => (event) => {
                  if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
                    return;
                  }
                  setState({ ...state, [anchor]: open });
                };
                const list = (anchor) => (
                    <Box
                      role="presentation"
                      onClick={toggleDrawer(anchor, true)}
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


                       { !login?'':
                         ( login.pno >=6?
                          <List >
                            {[출근내역,날씨구현].map((text, index) => (
                              <ListItem key={text} disablePadding>
                                <ListItemButton>
                                  <ListItemIcon>
                                    {index % 2 === 1 ? <InboxIcon /> : <MailIcon />}
                                  </ListItemIcon>
                                  <ListItemText className="aaa" primary={text} />
                                </ListItemButton>
                              </ListItem>
                            ))}
                          </List> :'')
                     }


                    </Box>
                  );






const drawerWidth = 240;
    return (<>
                 {['left'].map((anchor) => (
                    <React.Fragment key={anchor}>
                      <Button className="menu" onClick={toggleDrawer(anchor, true)}><MenuIcon/></Button>
                      <Drawer
                        anchor={anchor}
                        open={state[anchor]}
                        onClose={toggleDrawer(anchor, false)}
                      >
                        {list(anchor)}
                        {logoutBtn}
                      </Drawer>
                    </React.Fragment>
                  ))}
    </>)
    }