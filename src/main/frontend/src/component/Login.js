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
  };

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
        alignItems: 'center',
        height: '100vh',
        bgcolor: '#EEF2F6',
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
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              label="사번"
              variant="outlined"
              fullWidth
              value={userId}
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
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              variant="contained"
              sx={{ bgcolor: colorPalette.main, color: 'white', mt: 2, width: '100%' }}
              onClick={handleSubmit}
            >
              로그인
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
}