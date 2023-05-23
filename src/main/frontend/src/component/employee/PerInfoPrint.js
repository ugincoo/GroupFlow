import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { Box, Typography, TextField, Button, Grid , Divider } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';

export default function PerInfoPrint(props) {
let [ login , setLogin ] = useState( JSON.parse(localStorage.getItem("login_token")) );
let [imagePreview, setImagePreview] = useState(null);
    let [ephoto, setEphoto] = useState(null);

    // 기본정보 입력값
    let [ info , setInfo ] = useState({
        eno : 0 ,
        ename : '',
        eemail : '',
        ephone : '',
        esocialno : '',
        hiredate : '',
    })

    // 퇴사처리용 입력값
    let [ eenddate , setEenddate ] = useState('')

    // 직급변경용 입력값
    let [ positionSelect , setPositionSelect ] = useState({
        eno : 0 ,
        pno : 0 ,
        pcdate : '' ,
        pcstartreason : ''
    })
    console.log("positionSelect")
    console.log(positionSelect)
    // 부서변경용 입력값
    let [ departmentSelect , setDepartmentSelect ] = useState({
        eno : 0 ,
        dno : 0 ,
        dcstartdate : '',
        dcstartreason : ''
    })
    console.log("departmentSelect")
    console.log(departmentSelect)


     // 사원정보 갖고오기
    useEffect(()=>{
        if( login === null ){ return; }
        console.log(login)
        info.eno = login.eno
        info.ename = login.ename
        info.eemail = login.eemail
        info.ephone = login.ephone
        info.esocialno = login.esocialno
        info.hiredate = login.hiredate
        setInfo({...info})
        // 사진띄우기
        setImagePreview('http://localhost:8080/static/media/'+login.ephoto)

    },[])



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
    /*
    for (const entry of data.entries()) {
      console.log(entry[0], entry[1]);
    }*/


    axios.put('/employee', data , { headers: { 'Content-Type': 'multipart/form-data'} } )
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
    //console.log("reader.result")
    //console.log(reader.result)
    setImagePreview(reader.result);
  };
  reader.readAsDataURL(file);
};


// 기본정보 입력값 change
const handleChange = (e) => {
    const { name, value } = e.target;
    setInfo({ ...info, [name]: value });
};





    return (<>
                <Box sx={{ p: 6, borderRadius: 3, boxShadow: 1, bgcolor: 'white', width: '100%', maxWidth: '1200px', }} >
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
                                    <Grid item xs={12} sm={3}>
                                        <TextField required id="outlined-required" label="email" name="eemail" value={info.eemail} onChange={handleChange} sx={{ mb: 2  }} fullWidth />
                                    </Grid>
                                    <Grid item xs={12} sm={3}>
                                        <TextField required id="outlined-required" label="핸드폰" name="ephone" value={info.ephone} onChange={handleChange} sx={{ mb: 2  }} fullWidth />
                                    </Grid>
                                    <Grid item xs={12} sm={3}>
                                        <TextField disabled label="입사일" variant="outlined" fullWidth type="date" name="hiredate" value={info.hiredate} onChange={handleChange} InputLabelProps={{ shrink: true }} sx={{ mb:2 }} />
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                </Box>
    </>)
}