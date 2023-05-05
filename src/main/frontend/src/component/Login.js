import React, { useState } from 'react';
import styles from '../css/login.css'
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { blue } from '@mui/material/colors';

export default function Login(props) {
  const [userId, setUserId] = useState('');
  const [userName, setUserName] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(`ID: ${userId}, Name: ${userName}`);
  }

  const colorPalette = {
    lightest: blue.A100,
    light: blue.A200,
    main: blue.A400,
    dark: blue.A700,
  };

  return (
      <Grid
        container
        justifyContent="center"
        alignItems="center"
        sx={{ height: '100vh', bgcolor: '#EEF2F6' }}
      >
        <Grid item xs={12} md={6} lg={4}>
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignItems="center"
            p={4}
            borderRadius={1}
            boxShadow={1}
            bgcolor="white"
          >
            <Typography
              sx={{
                fontFamily: "'Open Sans', sans-serif",
                fontWeight: 'bold',
                color: colorPalette.dark,
                mb: 2,
              }}
              variant="h5"
              component="h1"
            >
              Welcome to the Company
            </Typography>
            <Grid item xs={12}>
              <TextField
                label="사번"
                variant="outlined"
                fullWidth
                value={userId}
                onChange={(e) => setUserId(e.target.value)}
                mb={1}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="이름"
                variant="outlined"
                fullWidth
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                mb={2}
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                variant="contained"
                sx={{
                  bgcolor: colorPalette.main,
                  color: 'white',
                  mt: 2,
                }}
                fullWidth
                onClick={handleSubmit}
              >
                로그인
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    );
}
