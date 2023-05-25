import React from 'react';
import ChatIcon from '@mui/icons-material/Chat';
import styles from '../css/footer.css'; //css


import { Link } from 'react-router-dom';
export default function Footer(props) {




    return (
    <div className="enterChat">
        <Link to="/chatting" className="massage">메신저</Link>
    </div>)
    }