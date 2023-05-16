import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------키값-------------------------------------
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
//---------------------------인풋값-------------------------------------
import TextField from '@mui/material/TextField';
//---------------------------사용자-------------------------------------
import styles from '../../css/employee.css'; //css
import SearchIcon from '@mui/icons-material/Search';

export default function SearchEmplyee(props) {
  const [key, setKey] =useState(0);
  const [keyword, setKeyword] =useState();

  const changeKey = (e) => {    //키값 가져오기
    setKey(e.target.value);
  };
  const writeKeyWord = (e) => { //키워드값 가져오기
    setKeyword(e.target.value)
  };
  const SearchEmployee = () => {
        console.log('넘겨라')
        props.searchinfo(key,keyword)

    };



    return(<>

      <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
            <div className="keykeyword">
                <InputLabel id="demo-simple-select-standard-label"></InputLabel>
                <Select
                  labelId="demo-simple-select-standard-label"
                  id="demo-simple-select-standard"
                  value={key}
                  onChange={changeKey}>
                  <MenuItem value={0}>검색</MenuItem>
                  <MenuItem value={1}>이름</MenuItem>
                  <MenuItem value={2}>사번</MenuItem>
                </Select>
                <TextField onKeyUp={writeKeyWord} className="keyword"   id="standard-basic"  variant="standard" />
                <SearchIcon onClick={SearchEmployee}/>
            </div>
          </FormControl>

    </>)


}