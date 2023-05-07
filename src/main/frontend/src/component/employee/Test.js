import React, { useState } from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid, Container, Paper } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { blue } from '@mui/material/colors';

const colorPalette = {
  lightest: blue[50],
  light: blue[100],
  main: blue[500],
  dark: blue[700],
};

export default function Registration() {
  const [employeeName, setEmployeeName] = useState('');
  const [hiredDate, setHiredDate] = useState('');
  const [image, setImage] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = new FormData();
    data.append('employeeName', employeeName);
    data.append('hiredDate', hiredDate);
    data.append('employeePhoto', image);

    axios.post('/api/employee', data)
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);

    const reader = new FileReader();
    reader.onload = () => {
      setImagePreview(reader.result);
    };
    reader.readAsDataURL(file);
  };

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        bgcolor: colorPalette.light,
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
          사원 등록
        </Typography>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                label="이름"
                variant="outlined"
                fullWidth
                value={employeeName}
                onChange={(e) => setEmployeeName(e.target.value)}
                sx={{ mb: 1 }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="입사일"
                variant="outlined"
                fullWidth
                type="date"
                value={hiredDate}
                onChange={(e) => setHiredDate(e.target.value)}
                InputLabelProps={{ shrink: true }}
                sx={{ mb: 1 }}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                label="사원사진"
                variant="outlined"
                fullWidth
                type="file"
                onChange={handleImageChange}
                sx={{ mb: 2 }}
              />
              {imagePreview && (
                <img
                  src={imagePreview}
                  alt="사원사진 미리보기"
                  style={{ width: '100%', height: 'auto', marginBottom: 16 }}
                />
              )}
            </Grid>
            <Grid item xs={12}>
              <Button
                variant="contained"
                sx={{ bgcolor: colorPalette.main, color: 'white', mt: 2, width: '100%' }}
                type="submit"
              >
                등록하기
              </Button>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Box>
