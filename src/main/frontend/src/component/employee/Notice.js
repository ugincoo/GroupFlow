import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';

//---------------------------출력 mui-------------------------------------
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import FolderIcon from '@mui/icons-material/Folder';
import DeleteIcon from '@mui/icons-material/Delete';






export default function Notice(props) {


const[write,setWrite]=useState([]);
const onWriteHandler=()=>{
        let content=document.querySelector('.content').value
        axios.post("/notice/noticepost",{content:content}).then(r=>{
        setWrite(r.data);
        console.log(r.data);
        document.querySelector('.content').value='';
        })
    }
    useEffect(()=>{},[])


return (
  <>
    <Container>
      공지내용: <input type="text" className="content" />
      <button type="button" onClick={onWriteHandler}>등록 </button>

      <Grid item xs={12} md={6}>
        <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
          Avatar with text and icon
        </Typography>
      </Grid>

      <List>
        {write.map((e) => (
          <div key={e.id}>
            <FolderIcon />
            {e.content}
            <DeleteIcon />
          </div>
        ))}
      </List>

    </Container>
  </>
);
}