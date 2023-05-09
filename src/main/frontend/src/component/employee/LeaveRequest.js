import React,{ useState, useEffect } from 'react';
import axios from 'axios';
/*------------------------------------------------------------------------------------------  mui modal */
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Container from '@mui/material/Container';



export default function LeaveRequest(props){

 /*   useEffect ( ()=>{
        axios
            .post("http://localhost:8080/dayoff")
            .then(r=>{
                console.log(r.data)
            })
            .catch(err=>{console.log("err : " + err)} )
    },[])
*/

// 모달 style
    const style = {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: 400,
      bgcolor: '#ffffff',
      border : '3px solid #2979ff',
      boxShadow : 24,
      borderRadius:'10px',
      pt: 2,
      px: 4,
      pb: 3,
    };

const [open, setOpen] = React.useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

const day1 = () => {
    document.querySelector('.day1').value =
    new Date().toISOString().substring(0, 10);
}

    return(<>
         <button onClick={handleOpen}>연차신청</button>
        <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="child-modal-title"
            aria-describedby="child-modal-description"
          >
            <Box sx={{ ...style, }}>
              <h2 id="child-modal-title"> 연차 신청 </h2>
              <p id="child-modal-description">
                <Container>
                    <div>
                        <div> 이름 : </div>
                        <div> 부서 : </div>
                        <div> 직급 : </div>
                        <div> 연차 사용일 </div>
                        <input type="date" className="date1" /> <span> ~ </span> <input type="date" className="date2"/>
                        <div>  연차 사유 : </div>
                        <textarea
                            style={{width: "100%", height: "60px" , border: "none", resize: "none"}}
                            placeholder="연차 사유를 상세하게 작성해주세요." / >
                    </div>
                </Container>
              </p>
              <button onClick={handleClose}>취소</button>
              <button onClick={handleClose}>신청</button>
            </Box>
          </Modal>
    </>);
}