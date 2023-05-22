import React,{useState,useEffect,useRef} from 'react';
import axios from 'axios';
import AttendanceStatus from './AttendanceStatus';

export default function Workbtn(props) {

    const gowork=()=>{

    axios.post("/employee/gowork").then((r)=>{
            console.log(r.data)
            if(r.data==true){
                alert("출근등록 되었습니다.")
                props.getAttendance();
                props.gokDisabledHandler()

            }
         }) 
       }

const outwork=()=>{
        axios.put("/employee/outwork").then((r)=>{
            console.log(r.data);
            if(r.data==true){alert("퇴근등록 되었습니다.");
               props.getAttendance();
                props.gokDisabledHandler()} 
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
