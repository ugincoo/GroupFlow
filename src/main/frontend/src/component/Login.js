import React, { useState } from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { blue } from '@mui/material/colors';

export default function Login(props) {
  const [userId, setUserId] = useState('');
  const [userName, setUserName] = useState('');

  /*
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(`ID: ${userId}, Name: ${userName}`);
  };
  */

    const onLogin = () => {
            console.log('login 함수실행')
            let loginForm = document.querySelectorAll('.loginForm')[0];
            let loginFormData = new FormData(loginForm);

            axios.post("/employee/login",loginFormData)
                .then( r=>{
                    console.log(r)
                    if ( r.data == false ){
                        alert('동일한 직원정보가 없습니다.')
                    }else{
                        console.log('로그인 성공');
                        // JS 로컬 스토리지에 로그인 성공한 흔적 남기기 -> 로컬스토리지 껐다켜도 살아있음 , 도메인마다 따로 저장됨.
                        // localStorage.setItem("key",value ); // value는 String타입으로 들어가버림 [object]라고 스트링으로 들어감 -> 객체로 사용불가
                        // JSON.stringify( 객체) String타입의 json형식 : Object --> JSON형식String
                        // JSON.parse( String타입json ) : String --> JSON타입

                        // 로컬스토리지
                        //localStorage.setItem("login_token",JSON.stringify(r.data) );
                        // JS 세션 스토리지 [ 브라우저 모두 닫히면 사라진다. ]
                        localStorage.setItem("login_token",JSON.stringify(r.data) );
                        window.location.href="/allemployee";
                    }
                })

            /*
            $.ajax({
                url: '/member/login',
                method: 'POST',
                contentType : false ,
                processData: false ,
                data : loginFormData,
                success : (r)=>{
                  console.log(r)
                }
            })
            */
        }

  const colorPalette = {
    lightest: blue.A100,
    light: blue.A200,
    main: blue.A400,
    dark: blue.A700,
  };

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        height: '350px',
        bgcolor: '#EEF2F6',
        mt:'50px'
      }}
    >
      <Box
        sx={{
          p: 4,
          borderRadius: 1,
          boxShadow: 1,
          bgcolor: 'white',
          width: '100%',
          maxWidth: '400px',
        }}
      >
        <Typography
          sx={{
            fontFamily: "'Open Sans', sans-serif",
            fontWeight: 'bold',
            color: colorPalette.dark,
            mb: 2,
            textAlign: 'center',
          }}
          variant="h5"
          component="h1"
        >
          Welcome to the Company
        </Typography>
        <form className="loginForm">
            <Grid container spacing={2}>
                  <Grid item xs={12}>
                    <TextField
                      label="사번"
                      variant="outlined"
                      fullWidth
                      value={userId}
                      name="eno"
                      onChange={(e) => setUserId(e.target.value)}
                      sx={{ mb: 1 }}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      label="이름"
                      variant="outlined"
                      fullWidth
                      value={userName}
                      onChange={(e) => setUserName(e.target.value)}
                      sx={{ mb: 2 }}
                      name="ename"
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Button
                      variant="contained"
                      sx={{ bgcolor: colorPalette.main, color: 'white', mt: 2, width: '100%' }}
                      type="button"
                      onClick={onLogin}
                    >
                      로그인
                    </Button>
                  </Grid>
            </Grid>
        </form>
      </Box>
    </Box>
  );
}