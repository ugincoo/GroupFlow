import React,{useState,useEffect} from 'react';
import axios from 'axios';

//----------------------------셀렉트------------------------------------
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';

export default function Department(props) {
console.log()
let [departments,setDepartments]=useState([]);  //카테고리 전체담기


    useEffect ( ()=>{
        axios
            .get("http://localhost:8080/employee/print/department")
            .then(r=>{
                setDepartments(r.data)
            })
            .catch(err=>{console.log(err)} )
    },[])   //[departments] 하니까 무한루프에 빠진다 왜??


    const [department,setDepartment]=useState(0);   //선택한 카테고리 하나 담기
    const [inout,setInout]=useState(1);   //선택한 입사 또는 퇴사

    const handleChange=(e)=>{//부서별 출력

        setDepartment(e.target.value);
        props.departmentchange(e.target.value)
    }

     const handleChange2 = (e) => {//입퇴사별출력
        setInout(e.target.value);
        props.inoutEmployee(e.target.value);
      }


  /*  const handleChange2=(e)=>{
        console.log(e)
        setDepartment(e.target.value);

        props.inoutEmployee(e.target.value)
    }*/







    return (<>
     <div>
          <FormControl sx={{ m: 1, minWidth: 120 }}>
            <InputLabel id="demo-simple-select-helper-label">부서</InputLabel>
            <Select
              labelId="demo-simple-select-helper-label"
              id="demo-simple-select-helper"
              value={department}
              label="부서"
              onChange={handleChange}
            >

              <MenuItem value={0}>전체보기</MenuItem>
              {
                departments.map( (d)=>{
                    return <MenuItem value={d.dno}>{d.dname}</MenuItem>
                })
              }


            </Select>

          </FormControl>



         <FormControl sx={{ m: 1, minWidth: 80 }}>
                <InputLabel id="demo-simple-select-autowidth-label">입/퇴</InputLabel>
                <Select
                  labelId="demo-simple-select-autowidth-label"
                  id="demo-simple-select-autowidth"
                  value={inout}
                  onChange={handleChange2}
                  autoWidth
                  label="입/퇴"
                >
                  <MenuItem value={1}>근무자</MenuItem>
                  <MenuItem value={2}>퇴사자</MenuItem>
                </Select>
              </FormControl>

     </div>
    </>)

 }