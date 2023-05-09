import React from 'react';

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

      const [state, setState] = React.useState({
        left: false,
      });

        const toggleDrawer = (anchor, open) => (event) => {
          if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
          }
          setState({ ...state, [anchor]: open });
        };

        let 사원등록 =<a style={{textDecoration: 'none', color: '#cdd1e1c7',fontWeight: 'bold'}} href="/registration">사원등록</a>
        let 직원출력 = <a style={{textDecoration: 'none', color: '#cdd1e1c7',fontWeight: 'bold'}} href="/allemployee">직원출력</a>
        let 연차모달 =<a style={{textDecoration: 'none', color: '#cdd1e1c7',fontWeight: 'bold'}} href="/dayoff">연차모달</a>
        let 마이페이지 =<a style={{textDecoration: 'none', color: '#cdd1e1c7',fontWeight: 'bold'}} href="/mypage">마이페이지</a>

        const list = (anchor) => (
            <Box
              role="presentation"
              onClick={toggleDrawer(anchor, false)}
              onKeyDown={toggleDrawer(anchor, false)}
            >
              <List>
                {[사원등록, 직원출력, 연차모달, 마이페이지].map((text, index) => (
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

            </Box>
          );




    return (<>

              {['left'].map((anchor) => (
                <React.Fragment key={anchor}>
                  <Button onClick={toggleDrawer(anchor, true)}>{anchor}</Button>
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