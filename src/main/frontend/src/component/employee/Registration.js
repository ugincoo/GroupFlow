import React, { useState , useEffect } from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';

export default function Registration(props) {
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
    let [ positionList , setPositionlist ] = useState([]);
    let [ departmentList , setDepartmentList ] = useState([]);
    console.log(positionList)
    console.log(departmentList)


    // 부서랑 직급목록 가져오기
    useEffect(()=>{
        axios.get("/position/all").then((r)=>{ setPositionlist(r.data) })
        axios.get("/department/all").then((r)=>{ setDepartmentList(r.data) })

    },[])

  const handleSubmit = (e) => {
    e.preventDefault();
    //console.log("info")
    //console.log(info)
    //console.log("ephoto")
    //console.log(ephoto)

    if (ephoto.size > 1048576) { // 1MB 제한 (1048576 바이트)
          alert("파일 크기가 너무 큽니다."); return;
    }
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
      .then(r => {
        console.log(r);
        if( r.data == 7 ){ alert('사원등록이 완료되었습니다.'); window.location.href ="/allemployee"; }
        else if( r.data == 1 ){ alert('이미 부장이 등록된 부서입니다.') }
        else{ alert('관리자문의 - 오류번호:'+r.data ) }

      })
      .catch(error => {
        console.log(error);
      });
  };

const handleImageChange = (e) => {
  const file = e.target.files[0];
  if (file.size > 1048576) { // 1MB 제한 (1048576 바이트)
      alert("파일 크기가 너무 큽니다."); return;
  }
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
        bgcolor: 'white'
      }}
    >
      <Box
        sx={{ px: 6, py:4, borderRadius: 3, boxShadow: 1, bgcolor: 'aliceblue', width: '100%', maxWidth: '1200px', mb : 4 }}
      >
        <Typography
          sx={{ fontFamily: "'Open Sans', sans-serif", fontWeight: 'bold', color: 'rgb(12, 82, 114)', textAlign: 'left', }}
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
                            {
                                positionList.map( (p) => {
                                    return   <MenuItem value={p.pno}>{ p.pname }</MenuItem>
                                })
                            }
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
                            {
                                departmentList.map( (d) => {
                                    return   <MenuItem value={d.dno}>{ d.dname }</MenuItem>
                                })
                            }
                            </Select>
                          </FormControl>
                    </Grid>
                </Grid>
            </Grid>


            <Grid item xs={12}>
              <Button
                variant="contained"
                sx={{ bgcolor: 'rgb(12, 82, 114)', color: 'white', width: '100%' }}
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
