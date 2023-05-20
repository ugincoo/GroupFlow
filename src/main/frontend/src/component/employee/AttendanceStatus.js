import React,{useState,useEffect,useRef}from 'react';
import axios from 'axios';
//------------------------모달------------------------------------------------------
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
//------------------------사용자----------------------------------------------------
import styles from '../../css/attendancestatus.css'; //css

import CircleIcon from '@mui/icons-material/Circle';


const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  pt: 2,
  px: 4,
  pb: 3,
};


export default function AttendanceStatus(props) {
let [myEmployee,setMyEmployee]=useState([]) //나와같은부서 직원들넣기
let [open, setOpen] = React.useState(false);

 let ws=useRef(null);    //장민정 서버소켓

    useEffect( ()=>{
         ws.current=new WebSocket("ws://localhost:8080/commute");
         ws.current.onopen=()=>{ console.log("서버접속") }
         ws.current.onclose=(e)=>{ console.log("나감") }
         ws.current.onmessage=(e)=>{
         console.log("메세지")
         const data = JSON.parse(e.data);
         getmyEmployee()
          }
    },[])

    const getmyEmployee =()=>{

        axios
        .get("/employee/print/findmyemployees")
        .then(r=>{
         setMyEmployee(r.data)

         } )
    }


      const handleClose = () => {
        setOpen(false);
      };
     const handleOpen = () => {
           setOpen(true);

     };



console.log(myEmployee)


      return (
        <div>
          <Button onClick={handleOpen}>출근현황</Button>
          <Modal
             open={open}
             onClose={handleClose}
             aria-labelledby="parent-modal-title"
             aria-describedby="parent-modal-description"
           >
             <Box sx={{ ...style, width: 400 }}>
               <div className="top">
                   <h2 id="parent-modal-title">출근현황</h2>
                   <Button onClick={handleClose}>x</Button>
               </div>
               <table className="attendancestatusTable">
                   <tr><th></th> <th></th> <th>출근시간 </th> <th>퇴근시간</th></tr>

                 {
                    myEmployee==''? '' : myEmployee.map( (e)=>{
                    return(

                        <tr>
                            <td><CircleIcon style={{color:e.color}}/></td><td>{e.ename}{e.pname}</td><td>{e.cdate}</td><td>{e.udate}</td>
                        </tr>

                          ) })
                 }

               </table>

             </Box>
           </Modal>
        </div>
      );
}