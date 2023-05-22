import React,{useState,useEffect,useRef}from 'react';
import axios from 'axios';
//------------------------mui------------------------------------------------------
import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Collapse from '@mui/material/Collapse';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import DraftsIcon from '@mui/icons-material/Drafts';
import SendIcon from '@mui/icons-material/Send';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import StarBorder from '@mui/icons-material/StarBorder';

export default function Chatting(props) {
     const [open, setOpen] = React.useState(true);
     let[alldepartment,setAlldepartment]= useState([]); //모든 부서 출력
     let[employee,setEmployee]= useState([]); //부서별직원

      let ws=useRef(null);
        useEffect( ()=>{
              ws.current=new WebSocket("ws://localhost:8080/chat");
              ws.current.onopen=()=>{ console.log("서버접속") }
              ws.current.onclose=(e)=>{ console.log("나감") }
              ws.current.onmessage=(e)=>{
              console.log("메세지")
              const data = JSON.parse(e.data);
               }
         },[])



     useEffect(() => { //부서전체출력
        axios
        .get("/employee/print/department")
        .then(r=>{
            setAlldepartment(r.data)
        })
      },[])

      const handleClick = (dno) =>  { //오픈후 해당부서에 직원출력
        setOpen(!open);
        console.log(dno)

        axios
            .get("/employee/print/findbydno",{params:{dno:dno}})
            .then(r=>{
            console.log(r.data)
            setEmployee(r.data)
            })
      };


 const chat = (eno) =>  { //eno 누르고 채팅시작
    console.log(eno)
    ws.current.send(JSON.stringify({eno:eno}));
    ws.current.message=(e)=>{
    console.log(e.data)
    }

 }

 const test=() => {
    return employee.map((e) => (

           <Collapse in={open} timeout="auto" unmountOnExit key={e.eno}>
             <List component="div" disablePadding>
               <ListItemButton sx={{ pl: 4 }} onClick={() => chat(e.eno)}>
                 <ListItemIcon>
                   <StarBorder />
                 </ListItemIcon>
                 <ListItemText primary={e.ename} />
               </ListItemButton>
             </List>
           </Collapse>
         ))
}




console.log(alldepartment)

    return (
      <List
        sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}
        component="nav"
        aria-labelledby="Group-Flow 사내메신저"
        subheader={
          <ListSubheader component="div" id="nested-list-subheader">
            Group-Flow 사내메신저
          </ListSubheader>
        }
      >
        {alldepartment.map((d) => (
          <React.Fragment key={d.dno}>
            <ListItemButton onClick={() => handleClick(d.dno)}>
              <ListItemIcon>
                <DraftsIcon />
              </ListItemIcon>
              <ListItemText primary={d.dname} />
              {open ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <List component="div" disablePadding>
                {employee.map((e) => (
                  <ListItemButton sx={{ pl: 4 }} onClick={() => chat(e.eno)} key={e.eno}>
                    <ListItemIcon>
                      <StarBorder />
                    </ListItemIcon>
                    <ListItemText primary={e.ename} />
                  </ListItemButton>
                ))}
              </List>
            </Collapse>
          </React.Fragment>
        ))}
      </List>
    );
}
