import React,{useState,useEffect,useRef} from 'react';
import axios from 'axios';
import AttendanceStatus from './AttendanceStatus';

export default function Workbtn(props) {
 let ws=useRef(null);    //장민정 서버소켓
 const [connectEmployee,setConnectEmployee]=useState(); //출근한 사람들 (소켓에 접속한 전직원)
 const [work,setWork]=useState();
    const gowork=()=>{

    axios.post("/employee/gowork").then((r)=>{
            console.log(r.data);

            if(r.data==true){
                alert("출근등록 되었습니다.")
                 ws.current=new WebSocket("ws://localhost:8080/commute/"+props.eno);
                 props.gokDisabledHandler()


                 ws.current.onopen=()=>{ //서버접속과 동시에 메세지센드 함수발동
                  console.log("서버접속")
                 // ws.current.send(JSON.stringify({eno:props.eno}))
                  }

                 ws.current.onclose=(e)=>{ console.log("나감") }


                 ws.current.onmessage=(e)=>{
                 console.log("메세지")
                 const data = JSON.parse(e.data);
                 setConnectEmployee(data)
                  }

            }
         })
       }
console.log(connectEmployee)

const outwork=()=>{
        axios.put("/employee/outwork").then((r)=>{
            console.log(r.data);
            if(r.data==true){alert("퇴근등록 되었습니다.");
                props.gokDisabledHandler()
                ws.current.close() }
                })
             }

    return(<>
            <button
                type="button"
                className="goworkbtn"
               disabled={props.gokDisabled ? true : false}
                onClick={gowork}

                 > 출근 </button>

            <button
                type="button"  className="outworkbtn"
                disabled={props.gokDisabled ? false : true}
                onClick={outwork}  > 퇴근 </button>

            <AttendanceStatus  connectEmployee={connectEmployee}/>
    </>)
 }
