import React,{useState,useEffect,useRef} from 'react';
import axios from 'axios';

export default function Workbtn(props) {


let ws=useRef(null);    //장민정 서버소켓
console.log(ws);

const [ gokDisabled, setGokDisabled] = useState(false);
console.log("gokDisabled")
console.log(gokDisabled)




    const gowork=()=>{

    axios.post("/employee/gowork").then((r)=>{
            console.log(r.data)
            if(r.data==true){
                alert("출근등록 되었습니다.")
                //document.querySelector('.goworkbtn').disabled=true;
                setGokDisabled(true);

                 ws.current=new WebSocket("ws://localhost:8080/commute");
                 ws.current.onopen=()=>{
                    console.log("서버접속")
                 }


            }
         })
       }
const outwork=()=>{
        axios.put("/employee/outwork").then((r)=>{
            console.log(r.data);
            if(r.data==true){alert("퇴근등록 되었습니다.");
            setGokDisabled(false);
            //document.querySelector('.outworkbtn').disabled=true;
                ws.current=new WebSocket("ws://localhost:8080/commute");
                 ws.current.onclose=(e)=>{
                    console.log("서버나감")
                 }
            }

        })

    }
    return(<>
            <button
                type="button"
                className="goworkbtn"
               disabled={gokDisabled ? true : false}
                onClick={gowork}

                 > 출근 </button>

            <button
                type="button"  className="outworkbtn"
                disabled={gokDisabled ? true : false}
                onClick={outwork}  > 퇴근 </button>
    </>)
 }
