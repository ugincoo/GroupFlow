import React,{ useState, useEffect } from 'react';
import axios from 'axios';
/*------------------------------------------------------------------------------------------  mui modal */
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Container from '@mui/material/Container';

export default function LeaveRequest(props){
// 0. 로그인 정보 변수
const [ login,setLogin ] = useState( JSON.parse( sessionStorage.getItem('login_token') ) );
// mui 모달
let [open, setOpen] = React.useState(false);

// 1. 로그인 정보 호출
useEffect ( () => {
    axios.get('/login/confirm').then( r => {
        if(r.data != ''){  // 만약에 로그인이 null이 아니면
            sessionStorage.setItem( "login_token",JSON.stringify(r.data) ) ;
            setLogin( JSON.parse(sessionStorage.getItem( "login_token" ) ) );
            console.log(r.data);
        }
    } )
},[] );


}, [] );

const onApplication = () => {
    let info = {
        ename :document.querySelector('.ename').value,
        dno :document.querySelector('.dno').value,
        pno :document.querySelector('.pno').value,
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
                window.location.href="../component/employee/LeaveRequestList"
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
      bgcolor: '#ffffff',
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
                        <div> 이름 : <span className="ename">  </span> </div>
                        <div> 부서 : <span className="dno"> </span> </div>
                        <div> 직급 : <span className="pno"> </span> </div>
                        <div> 연차 사용일 </div>
                        <input type="date" className="lstart" /> <span> ~ </span> <input type="date" className="lend"/>
                        <div>  연차 사유 : </div>
                        <textarea className="requestReason"
                            style={{width: "100%", height: "60px" , border: "none", resize: "none"}}
                            placeholder="연차 사유를 상세하게 작성해주세요." / >
                    </div>
                </Container>
              </p>
              <button onClick={handleClose}>취소</button>
              <button onClick={onApplication}>신청</button>
            </Box>
          </Modal>
    </>);
}