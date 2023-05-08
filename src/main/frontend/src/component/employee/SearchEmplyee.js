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
  const [key, setKey] =useState(1);
  const [keyword, setKeyword] =useState();

  const handleChange = (e) => {
    setKey(e.target.value);
  };
  const SearchEmplyee = () => {
        console.log('넘겨라')
    };

    console.log(setKey)
    return(<>

      <FormControl variant="standard" sx={{ m: 1, minWidth: 120 }}>
            <div className="keykeyword">
                <InputLabel id="demo-simple-select-standard-label"></InputLabel>
                <Select
                  labelId="demo-simple-select-standard-label"
                  id="demo-simple-select-standard"
                  value={key}
                  onChange={handleChange}>

                  <MenuItem value={1}>이름</MenuItem>
                  <MenuItem value={2}>사번</MenuItem>
                </Select>
                <TextField className="keyword"   id="standard-basic"  variant="standard" />
                <SearchIcon onClick={SearchEmplyee}/>
            </div>
          </FormControl>

    </>)


}