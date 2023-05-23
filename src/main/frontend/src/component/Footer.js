import React from 'react';
import ChatIcon from '@mui/icons-material/Chat';

import { Link } from 'react-router-dom';
export default function Footer(props) {




    return (<div>
        <Link to="/chatting"> <ChatIcon />사내메신저</Link>
    </div>)
    }