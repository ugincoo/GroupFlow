import React,{useState,useEffect} from 'react';
import axios from 'axios';
//---------------------------컨테이너-------------------------------------
import Container from '@mui/material/Container';
import { DataGrid } from '@mui/x-data-grid';

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
const onWriteHandler=()=>{
        let content=document.querySelector('.content').value
        axios.post("/notice/noticepost",{content:content}).then(r=>{
        if(r.data==true){
        console.log(r.data);getnotice();
        document.querySelector('.content').value='';
        }
        })
    }

    const[nnotice,setNotice]=useState([


    ]);
    const getnotice=()=>{
         axios.get("/notice/noticeget").then((r) => {
            setNotice(r.data);
            console.log(r.data);
         })
         }//함수만 정의해놓은것
     useEffect(()=>{getnotice()},[])
         console.log(nnotice);

    //삭제함수
    const onDeleteHandler=(nno)=>{
    axios.delete("/notice/noticedelete",{ params: { nno: nno } }).then((r) => {
    console.log(r.data);
    getnotice();
    })
    }


return (
  <>
  <Container>
      공지내용: <input type="text" className="content"  placeholder="내용을 입력하세요" />
      <button type="button" onClick={onWriteHandler}>등록 </button>

      <Grid item xs={12} md={6}>



      </Grid>

      <List>
        {nnotice.map((e) => (
          <div key={e.id}>
            <FolderIcon style={{color:"#5a5e6c"}}/>
           <span> {e.content}</span>
            <DeleteIcon onClick={() => onDeleteHandler(e.nno)} style={{color:"#5a5e6c"}} />
          </div>
        ))}
      </List>



    </Container>

  </>
);
}


/*//DataTable 필드 설정
const columns = [//row 객체내 필드명과 동일
  { field: 'nno', headerName: '번호', width: 150 },
  { field: 'content', headerName: '공지내용', width: 150 }


];

export default function Notice(props) {

     //등록
     const onWriteHandler=()=>{
             let content=document.querySelector('.content').value
             axios.post("/notice/noticepost",{content:content}).then(r=>{
             if(r.data==true){
             console.log(r.data);getnotice();
             document.querySelector('.content').value='';
             }
             })
         }
        //상태변수
         const[nnotice,setNotice]=useState([]);
         //호출
         const getnotice=()=>{
              axios.get("/notice/noticeget").then((r) => {
                 setNotice(r.data);
                 console.log(r.data);
              })
              }//함수만 정의해놓은것

console.log(nnotice);


     //4.데이터테이블에서 선택된 제품 리스트
     const [rowSelectionModel, setRowSelectionModel] = React.useState([]);
     //5.삭제함수
     const onDeleteHandler=()=>{
        let msg=window.confirm("정말 삭제하시겠습니까? 복구가 불가능합니다");
        if(msg==true){//확인 버튼을 클릭했을때
            //선택된 제품리스트를 하나씩 서버에게 전달
            rowSelectionModel.forEach(r=>{ //모델안에 들어있떤 키값을 하나씩 보내서  렌더링
             axios.delete("/notice/noticedelete",{params:{}})//id 인수명은 controller,service인수에 id랑 인수이름이 똑같아야함.
                .then(r=>{getnotice();})
            })

        }
     }
    return(<>
     공지내용: <input type="text" className="content"  placeholder="내용을 입력하세요" />
          <button type="button" onClick={onWriteHandler}>등록 </button>
         <button
            onClick={onDeleteHandler}
            disabled={rowSelectionModel.length==0? true:false}
            type="button"
         >선택삭제</button>
         <div style={{ height: 400, width: '100%' }}>
           <DataGrid
             rows={nnotice}
             columns={columns}
             initialState={{
               pagination: {
                 paginationModel: { page: 0, pageSize: 5 },
               },
             }}
             pageSizeOptions={[5, 10,15,20]}
             checkboxSelection
             onRowSelectionModelChange={(newRowSelectionModel) => {
               setRowSelectionModel(newRowSelectionModel);
             }}
           />
         </div>


     </>);

}*/

/*

const onWriteHandler=()=>{
        let content=document.querySelector('.content').value
        axios.post("/notice/noticepost",{content:content}).then(r=>{
        if(r.data==true){
        console.log(r.data);getnotice();
        document.querySelector('.content').value='';
        }
        })
    }

    const[nnotice,setNotice]=useState([]);
    const getnotice=()=>{
         axios.get("/notice/noticeget").then((r) => {
            setNotice(r.data);
            console.log(r.data);
         })
         }//함수만 정의해놓은것
    //삭제함수
    const getDelete=(nno)=>{
    axios.delete("/notice/noticedelete",{params:nno}).then((r) => {
    console.log(r.data)
    })
    }

return (
  <>
  <Container>
      공지내용: <input type="text" className="content"  placeholder="내용을 입력하세요" />
      <button type="button" onClick={onWriteHandler}>등록 </button>

      <Grid item xs={12} md={6}>



      </Grid>

      <List>
        {nnotice.map((e) => (
          <div key={e.id}>
            <FolderIcon style={{color:"#c9dced"}}/>
            {e.content}
            <DeleteIcon onClick={getDelete} style={{color:"#5a5e6c"}} />
          </div>
        ))}
      </List>



    </Container>

  </>
);
}*/
