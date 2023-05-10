import React, { useState } from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';
import { blue } from '@mui/material/colors';

const colorPalette = {
  lightest: blue[50],
  light: blue[100],
  main: blue[500],
  dark: blue[700],
};

export default function Registration() {
    let [imagePreview, setImagePreview] = useState(null);
    let [ephoto, setEphoto] = useState(null);
    let [ info , setInfo ] = useState({
        ename : '',
        eemail : '',
        ephone : '',
        esocialno : '',
        hiredate : '',
        pno : 1 ,
        dno : 1
    })


  const handleSubmit = (e) => {
    e.preventDefault();
    //console.log("info")
    //console.log(info)
    //console.log("ephoto")
    //console.log(ephoto)
    const data = new FormData();
    data.append("ephotodata", ephoto );
    data.append(
      "info",
      new Blob([JSON.stringify(info)], { type: "application/json" })
    );

    for (const entry of data.entries()) {
      console.log(entry[0], entry[1]);
    }


    axios.post('/employee', data , { headers: { 'Content-Type': 'multipart/form-data'} } )
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

const handleImageChange = (e) => {
  const file = e.target.files[0];
  //console.log("file")
  //console.log(file)
  setEphoto(file)

  const reader = new FileReader();
  reader.onload = () => {
    setImagePreview(reader.result);
  };
  reader.readAsDataURL(file);
};



const handleChange = (e) => {
  const { name, value } = e.target;
  setInfo({ ...info, [name]: value  });
};

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100vh',
        bgcolor: colorPalette.light,
      }}
    >
      <Box
        sx={{ px: 6, py:4, borderRadius: 3, boxShadow: 1, bgcolor: 'white', width: '100%', maxWidth: '1200px', mb : 4 }}
      >
        <Typography
          sx={{ fontFamily: "'Open Sans', sans-serif", fontWeight: 'bold', color: colorPalette.dark, textAlign: 'left', }}
          variant="h5"
          component="h1"
        >
          사원 등록
        </Typography>
      </Box>

      <Box sx={{ p: 6, borderRadius: 3, boxShadow: 1, bgcolor: 'white', width: '100%', maxWidth: '1200px', }} >
        <form onSubmit={handleSubmit}>
          <Grid container spacing={5}>
            <Grid item xs={12} sm={2}>
                <input  accept="image/*" id="ephoto" type="file" onChange={handleImageChange} style={{ display: 'none' }} />
                <label htmlFor="ephoto">
                  <Box sx={{
                      width: '150px',
                      height: '193px',
                      border: '1px dashed #ddd',
                      borderRadius: 2,
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                    }}
                  >
                    {imagePreview ? ( <img
                        src={imagePreview}
                        alt="사원사진 미리보기"
                        style={{
                          width: '100%',
                          height: '193px',
                          objectFit: 'cover',
                          aspectRatio: '3/4',
                        }}
                      />
                    ) : (
                      <Typography variant="body2" color="text.secondary">
                        사원사진 업로드
                      </Typography>
                    )}
                  </Box>
                </label>

            </Grid>

            <Grid item xs={12} sm={10}>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={5} >
                        <TextField required id="outlined-required" label="성명" name="ename" value={info.ename} onChange={handleChange} sx={{ mb:2 }} fullWidth />
                    </Grid>
                    <Grid item xs={12} sm={7}>
                        <TextField required id="outlined-required" label="주민등록번호" name="esocialno" value={info.esocialno} onChange={handleChange} sx={{ mb: 2 }} fullWidth />
                    </Grid>
                </Grid>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={4}>
                        <TextField required id="outlined-required" label="email" name="eemail" value={info.eemail} onChange={handleChange} sx={{ mb: 2  }} fullWidth />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField required id="outlined-required" label="핸드폰" name="ephone" value={info.ephone} onChange={handleChange} sx={{ mb: 2  }} fullWidth />
                    </Grid>
                    <Grid item xs={12} sm={4}>
                        <TextField label="입사일" variant="outlined" fullWidth type="date" name="hiredate" value={info.hiredate} onChange={handleChange} InputLabelProps={{ shrink: true }} sx={{ mb:2 }} />
                    </Grid>
                </Grid>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6} >
                        <FormControl fullWidth sx={{ mb:2 }}>
                            <InputLabel id="demo-simple-select-label">직급</InputLabel>
                            <Select
                              labelId="demo-simple-select-label"
                              id="demo-simple-select"
                              name = "pno"
                              value={info.pno}
                              label="직급"
                              onChange={handleChange}
                            >
                              <MenuItem value={1}>사원</MenuItem>
                              <MenuItem value={2}>주임</MenuItem>
                              <MenuItem value={3}>대리</MenuItem>
                              <MenuItem value={4}>과장</MenuItem>
                              <MenuItem value={5}>차장</MenuItem>
                              <MenuItem value={6}>부장</MenuItem>
                              <MenuItem value={7}>이사</MenuItem>
                              <MenuItem value={8}>상무</MenuItem>
                              <MenuItem value={9}>사장</MenuItem>
                            </Select>
                          </FormControl>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">부서</InputLabel>
                            <Select
                              labelId="demo-simple-select-label"
                              id="demo-simple-select"
                              name = "dno"
                              value={info.dno}
                              label="부서"
                              onChange={handleChange}
                            >
                              <MenuItem value={1}>경영지원팀</MenuItem>
                              <MenuItem value={2}>인사팀</MenuItem>
                              <MenuItem value={3}>영업팀</MenuItem>
                              <MenuItem value={4}>마케팅팀</MenuItem>
                              <MenuItem value={5}>디자인팀</MenuItem>
                              <MenuItem value={6}>생산팀</MenuItem>
                              <MenuItem value={7}>자재팀</MenuItem>
                              <MenuItem value={8}>IT팀</MenuItem>
                              <MenuItem value={9}>회계팀</MenuItem>
                            </Select>
                          </FormControl>
                    </Grid>
                </Grid>
            </Grid>


            <Grid item xs={12}>
              <Button
                variant="contained"
                sx={{ bgcolor: colorPalette.main, color: 'white', width: '100%' }}
                type="submit"
              >
                등록하기
              </Button>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Box>
  );
}
