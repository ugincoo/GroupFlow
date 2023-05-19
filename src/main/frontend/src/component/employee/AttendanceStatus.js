import React,{useState,useEffect,useRef}from 'react';
import axios from 'axios';
//------------------------모달------------------------------------------------------
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
//------------------------사용자----------------------------------------------------
import styles from '../../css/attendancestatus.css'; //css



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
let [connectEmployee,setConnectEmployee]=useState([]); //출근한 전직원들
let [open, setOpen] = React.useState(false);


    useEffect( ()=>{ //workbtn 컴포넌트에서 받아온 서버접속직원(출근한전직원)
        setConnectEmployee(props.connectEmployee)
    },[props.connectEmployee])

    useEffect( ()=>{ //나와같은 부서직원들뽑기
        axios
            .get("/employee/print/findmyemployees")
            .then(r=>{
                console.log(r.data)
                setMyEmployee(r.data)

            })

    },[connectEmployee])


      const handleClose = () => {
        setOpen(false);
      };
     const handleOpen = () => {
           setOpen(true);

     };






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
               <p id="parent-modal-description">
                 {
                    myEmployee==''? '' : myEmployee.map( (e)=>{ return( <div> {e.ename} </div>  ) })
                 }
               </p>

             </Box>
           </Modal>
        </div>
      );
}