import React,{useState,useEffect} from 'react';
import axios from 'axios';
import Styles from '../../css/viewDetailEmployee.css'
import { Box, Typography, TextField, Button, Grid , Divider } from '@mui/material';
import { FormControl, InputLabel, MenuItem, Select } from '@mui/material';


export default function ViewDetailEmployee(props) {
    let [imagePreview, setImagePreview] = useState(null);
    let [ephoto, setEphoto] = useState(null);
    // props.oneEmployee로 전달받은 사번 상태변수
    let [ eno , setEno ] = useState(0);
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



    // 부서카테고리 axios통신으로 가져올 리스트 상태변수
    // 직급카테고리 axios통신으로 가져올 리스트 상태변수
    let [ positionList , setPositionlist ] = useState([]);
    let [ departmentList , setDepartmentList ] = useState([]);

    // 처음실행
    useEffect(()=>{
        //직급,부서 DB에서 가져오기
        axios.get("/position/all").then((r)=>{ setPositionlist(r.data) })
        axios.get("/department/all").then((r)=>{ setDepartmentList(r.data) })
    },[])

    // 선택한 사원이 바뀔때마다 실행
    useEffect(()=>{
        console.log(props.oneEmployee)
        info.eno = props.oneEmployee.eno
        info.ename = props.oneEmployee.ename
        info.eemail = props.oneEmployee.eemail
        info.ephone = props.oneEmployee.ephone
        info.esocialno = props.oneEmployee.esocialno
        info.hiredate = props.oneEmployee.hiredate
        setInfo({...info})
        // 사진띄우기
        setImagePreview('http://localhost:8080/static/media/'+props.oneEmployee.ephoto)
        // 퇴사처리용
        setEenddate(props.oneEmployee.eenddate)
        // 부서,직급
        let eno = props.oneEmployee.eno;
        console.log("eno : "+eno)
        axios.get("/employee/select/info",{params:{eno:eno}})
            .then(r=>{
                console.log(r.data)
                //직급
                positionSelect.eno = eno
                positionSelect.pno = r.data.pno
                setPositionSelect({...positionSelect})
                //부서
                departmentSelect.eno = eno
                departmentSelect.dno = r.data.dno
                setDepartmentSelect({...departmentSelect})
            })
    },[props.oneEmployee])
  // 사원정보 갖고오기


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

// 직급변경 입력값 change
const positionChange = (e) => {
    const { name, value } = e.target;
    setPositionSelect({...positionSelect, [name]: value })
}

// 부서변경 입력값 change
const departmentChange = (e) => {
    const { name, value } = e.target;
    setDepartmentSelect({...departmentSelect, [name]: value})
}

// 퇴사일 입력값
const eenddateChange = (e)=>{
    console.log(e.target.value)
    setEenddate( e.target.value )
}

// 퇴사처리
const resignation = (e)=>{
    console.log("resignation")
    console.log(eenddate)
    let info = { eenddate : eenddate , eno : props.oneEmployee.eno }
    axios.put("/employee/updateenddate",positionSelect).then(r=>{
        console.log(r.data)
    })
}
// 직급변경처리
const positionSubmit = (e)=>{
    console.log("PostionSubmit")
    console.log(positionSelect)
    axios.put("/employee/updateposition",positionSelect).then(r=>{
        console.log(r.data)
    })
}

// 부서변경처리
const departmentSubmit = (e)=>{
    console.log("departmentSubmit")
    console.log(departmentSelect)
    axios.put("/employee/updatedepartment",departmentSelect).then(r=>{
        console.log(r.data)
    })
}

    return (<>
                <Box
                    sx={{ px: 6, py:4, borderRadius: 3, boxShadow: 1, bgcolor: '#0c5272', width: '100%', maxWidth: '1200px', mb : 4 }}
                    >
                    <Typography
                      sx={{ fontFamily: "'Open Sans', sans-serif", fontWeight: 'bold', color: 'rgb(219 223 235)', textAlign: 'left', }}
                      variant="h5"
                      component="h1"
                    >
                      사원 조회/수정
                    </Typography>
                </Box>

                <Box sx={{ p: 6, borderRadius: 3, boxShadow: 1, bgcolor: 'aliceblue', width: '100%', maxWidth: '1200px', }} >
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
                                    <Grid item xs={12} sm={3}>
                                        <TextField label="퇴사일" variant="outlined" fullWidth type="date" name="eenddate" value={eenddate} onChange={eenddateChange} InputLabelProps={{ shrink: true }} sx={{ mb:2 }} />
                                    </Grid>
                                </Grid>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={9} >
                                        <Button
                                            variant="contained"
                                            sx={{ bgcolor: '#0c5272', color: 'white', width: '100%', mb:4 }}
                                            type="button"
                                            onClick={handleSubmit}
                                          >
                                            기본정보 수정하기
                                        </Button>
                                    </Grid>
                                    <Grid item xs={12} sm={3} >
                                    <Button
                                            variant="contained"
                                            sx={{ bgcolor: '#0c5272', color: 'white', width: '100%', mb:4 }}
                                            type="button"
                                            onClick={resignation}
                                          >
                                            퇴사
                                        </Button>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                </Box>



                            <Box sx={{ p: 6, mt:4, borderRadius: 3, boxShadow: 1, bgcolor: 'aliceblue', width: '100%', maxWidth: '1200px'}} >
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={6} >
                                        <Typography
                                          sx={{ fontFamily: "'Open Sans', sans-serif", fontWeight: 'bold', color: '#0c5272', textAlign: 'left', mb : 3 }}
                                          variant="h6"
                                          component="h2"
                                        >
                                          직급변경
                                        </Typography>
                                        <FormControl required fullWidth sx={{ mb:2 }}>
                                            <InputLabel id="demo-simple-select-label">직급</InputLabel>
                                            <Select
                                              required
                                              labelId="demo-simple-select-label"
                                              id="demo-simple-select"
                                              name = "pno"
                                              value={positionSelect.pno}
                                              label="직급"
                                              onChange={positionChange}
                                            >
                                                {
                                                    positionList.map( (p) => {
                                                        return   <MenuItem value={p.pno}>{ p.pname }</MenuItem>
                                                    })
                                                }
                                            </Select>
                                        </FormControl>

                                        <TextField required label="직급변경일" variant="outlined" fullWidth type="date" name="pcdate"  value={positionSelect.pcdate} onChange={positionChange} InputLabelProps={{ shrink: true }} sx={{ mb:2 }} />

                                        <TextField required id="outlined-required" label="직급변경사유" name="pcstartreason" value={positionSelect.pcstartreason} onChange={positionChange} sx={{ mb:2 }} fullWidth />

                                        <Button
                                            variant="contained"
                                            sx={{ bgcolor: '#0c5272', color: 'white', width: '100%', mb:4 }}
                                            type="button"
                                            onClick={positionSubmit}
                                          >
                                            직급변경
                                        </Button>
                                    </Grid>
                                    <Grid item xs={12} sm={6} >
                                        <Typography
                                          sx={{ fontFamily: "'Open Sans', sans-serif", fontWeight: 'bold', color: '#0c5272', textAlign: 'left', mb : 3 }}
                                          variant="h6"
                                          component="h2"
                                        >
                                          부서변경
                                        </Typography>
                                        <FormControl required fullWidth sx={{ mb:2 }}>
                                            <InputLabel id="demo-simple-select-label">부서</InputLabel>
                                            <Select
                                              labelId="demo-simple-select-label"
                                              id="demo-simple-select"
                                              name = "dno"
                                              value={departmentSelect.dno}
                                              label="부서"
                                              onChange={departmentChange}
                                            >
                                                {
                                                    departmentList.map( (d) => {
                                                        return   <MenuItem value={d.dno}>{ d.dname }</MenuItem>
                                                    })
                                                }
                                            </Select>
                                        </FormControl>

                                        <TextField required label="부서변경일" variant="outlined" fullWidth type="date" name="dcstartdate"  value={departmentSelect.dcstartdate} onChange={departmentChange} InputLabelProps={{ shrink: true }} sx={{ mb:2 }} />

                                        <TextField required id="outlined-required" label="부서변경사유" name="dcstartreason" value={departmentSelect.dcstartreason} onChange={departmentChange} sx={{ mb:2 }} fullWidth />

                                        <Button
                                            variant="contained"
                                            sx={{ bgcolor: '#0c5272', color: 'white', width: '100%', mb:4 }}
                                            type="button"
                                            onClick={departmentSubmit}
                                          >
                                            부서변경
                                        </Button>
                                    </Grid>
                                </Grid>
                            </Box>

    </>)
}