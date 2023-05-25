import React,{ useState, useEffect } from 'react';
import axios from 'axios';
/*------------------------------------------------------------------------------------------  mui modal */
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Container from '@mui/material/Container';
/*------------------------------------------------------------------------------------------------css-----*/
import styles from '../../css/leave.css'; //css

// 연차 신청 모달
export default function LeaveRequest(props){
// 0. 로그인 정보 변수
let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) )
// mui 모달
let [open, setOpen] = React.useState(false);


 
const onApplication = () => {
    let info = {
        lstart :document.querySelector('.lstart').value,
        lend :document.querySelector('.lend').value,
        requestreason :document.querySelector('.requestReason').value
    }
    axios
        .post("/dayoff",info)
        .then(r=>{
             console.log("r.data : " + r.data);
            if(r.data == 1){
               console.log(r.data+"번");
            }else if (r.data == 2){
                console.log(r.data+"번");

            }else if (r.data == 3){
                console.log(r.data+"번");
                alert('연차 신청 되었습니다.');
                window.location.href="/offlist"
            }

        })
        .catch(err=>{console.log("err : " + err)} )

}


// 모달 style
    const style = {
      position: 'absolute',
      top: '50%',
      left: '50%',
      transform: 'translate(-50%, -50%)',
      width: 400,
      bgcolor: '#F0F8FF',
      border : '3px solid #2979ff',
      boxShadow : 24,
      borderRadius:'10px',
      pt: 2,
      px: 4,
      pb: 3,
    };

// 모달 열기 / 닫기
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };


    return(<>
         <button className="LeaveButton" onClick={handleOpen}>연차신청</button>
        <Modal
            className="aria-describedby"
            open={open}
            onClose={handleClose}
            aria-labelledby="child-modal-title"
            aria-describedby="child-modal-description"
          >
            <div className="modalBox">
              <h2 id="child-modal-title"> 연차 신청 </h2>
              <p id="child-modal-description">
                <Container>
                    <div>
                        <div> 이름 : <span className="ename">  {login.ename}   </span> </div>
                        <div> 부서 : <span className="dno"> {login.dname}  </span> </div>
                        <div> 직급 : <span className="pno"> {login.pname}  </span> </div>
                        <div> 연차 사용일 </div>
                        <input type="date" className="lstart" /> <span> ~ </span> <input type="date" className="lend"/>
                        <div>  연차 사유 : </div>
                        <textarea className="requestReason"
                            style={{width: "100%", height: "60px" , border: "none", resize: "none"}}
                            placeholder="※남은 연차 개수는 결재완료 전 차감 개수이니 확인 후 신청 부탁드립니다." / >
                    </div>
                </Container>
              </p>
              <button className="modalButton" onClick={handleClose}>취소</button>
              <button className="modalButton" onClick={onApplication}>신청</button>
            </div>
          </Modal>
    </>);
}