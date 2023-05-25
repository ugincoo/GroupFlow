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
import TipsAndUpdatesIcon from '@mui/icons-material/TipsAndUpdates';


//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';
import styles from '../../css/chat.css'; //css

export default function Chatting(props) {
let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) )
     const [open, setOpen] = React.useState(true); //mui
     let[alldepartment,setAlldepartment]= useState([]); //모든 부서 출력
     let[employee,setEmployee]= useState([]); //부서별직원

     let[sss,setSss]= useState( [] );// 소켓에 접속한 모든 명단(type1)
     const [msgs, setMsgs] = useState([ ]);


      const [selectE, setSelectE] = useState({ //선택한 직원의 정보
         selectEno: null,
         selectEname: null,
         selectEpname: null
       });

      let ws=useRef(null);
        useEffect( ()=>{
              ws.current=new WebSocket("ws://localhost:80/chat/"+login.eno);
              ws.current.onopen=()=>{
                    console.log("서버접속");
                    let msg = {
                        type : "1"
                    }
                    ws.current.send( JSON.stringify(msg)  )
                }
              ws.current.onclose=(e)=>{ console.log("나가..지마..") }
              ws.current.onmessage=(e)=>{
                  console.log("메세지")
                  console.log(e.data)
                    let result = JSON.parse(e.data);
                  console.log(result)
                  // 만약에 받은 메시지가 1이면 상태
                if( result.type == "1" ){
                    setSss( result.enos )
                }else if( result.type == "2") {
                    console.log(result)
                    // 본인이 받은 메시지들을 보관하는 useState 변수에 set
                    setMsgs( (msgs)=>[...msgs, result]); //한개밖에 안들어가는데 ? why 해결! (msgs)=> 이걸 앞에 써줘야한다

                }



               }
         },[])

console.log(msgs);


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



     const chat = (eno,ename,pname) =>  { //eno 누르고 전역변수에 담음

        setSelectE({
              ...selectE,
              selectEno: eno,
              selectEname: ename,
              selectEpname: pname
            });;


     }


  const onSend = () =>  { //전송버튼
     console.log('전송')
     let msgTextarea=document.getElementById("message").value;
     console.log(msgTextarea)
    let msg = {
              type : "2" ,
              msg : msgTextarea,
              toeno : selectE.selectEno,
              fromeno: login.eno,
              frompname:login.pname,
              fromdname:login.dname,
              fromename:login.ename
            }

    ws.current.send( JSON.stringify(msg)  )

  }



    return (
    <Container>
        <div className="chat">
          <List
            sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}
            component="nav"
            aria-labelledby="Group-Flow 사내메신저"
            subheader={
              <ListSubheader component="div" id="nested-list-subheader">
                Group-Flow 사내메신저
              </ListSubheader> } >

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
                    {employee==null?'':
                    employee.map((e) => (

                      e.dno==d.dno?
                       <ListItemButton sx={{ pl: 4 }} onClick={() => chat(e.eno,e.ename,e.pname)} key={e.eno}>
                        <ListItemIcon>
                          <TipsAndUpdatesIcon sx={sss.includes( e.eno+"" ) ? { color: "yellow" } : {}} />
                        </ListItemIcon>
                        <ListItemText primary={e.ename+e.pname} />
                      </ListItemButton>:''

                    ))}
                  </List>
                </Collapse>
              </React.Fragment>
            ))}
          </List>

           <div className="chatarea">
               <div className="chatBox">
                {selectE.selectEname!=null ?
                <h4>{selectE.selectEname}{selectE.selectEpname}님 과의 채팅창 입니다.</h4>
                : ''}

               {
                msgs.map((m) => {
                    return m.toeno == login.eno ?
                        <div>
                            <div className="toEno">
                                <div className="fromEname"><span>{m.fromdname}</span><span>{m.fromename}{m.frompname}</span></div>
                                <div className="fromMsg">{m.msg}</div>
                            </div>
                        </div>:
                        <div>
                            <div className="fromEno">{m.msg}</div>
                        </div>})
                }
                </div>


               <div  className="chatInputBox">
                  <textarea id="message" className="msgInput" placeholder="메세지를 입력하세요"/>
                  <button className="sendBtn" onClick={onSend}>전 송</button>
               </div>
           </div>

        </div>
   </Container>

    );
}
