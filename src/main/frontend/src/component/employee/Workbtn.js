import React,{useState,useEffect,useRef} from 'react';
import axios from 'axios';

export default function Workbtn(props) {

    const gowork=()=>{

    axios.post("/employee/gowork").then((r)=>{
            console.log(r.data)
            if(r.data==true){
                alert("출근등록 되었습니다.")
                 props.ws.current=new WebSocket("ws://localhost:8080/commute/"+props.eno);
                 props.gokDisabledHandler()
                 props.ws.current=new WebSocket("ws://localhost:8080/commute");
                 props.ws.current.onopen=()=>{ console.log("서버접속") }
                 props.ws.current.onclose=(e)=>{ console.log("나감") }
            }
         })
       }
const outwork=()=>{
        axios.put("/employee/outwork").then((r)=>{
            console.log(r.data);
            if(r.data==true){alert("퇴근등록 되었습니다.");
            props.gokDisabledHandler()
                props.ws.current.close()
            }
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
    </>)
 }
