import {useRef, useState} from 'react'
import reactLogo from './assets/react.svg'
import './App.css'
import {BrowserRouter, HashRouter, Link, Route, Routes, useNavigate, useParams} from "react-router-dom";
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {UserSelectionPage} from "./pages/user-selection-page.jsx";
import {CreateUserDialog} from "./components/create-user-dialog.jsx";
import {MessengerPage} from "./pages/message-page.jsx";
let selectedUser = null;





function WelcomeScreen() {
    const [createDialogVisible, setCreateDialogVisible] = useState(false);
    let history = useNavigate();

    function gotoLogin() {
        history("/login");
    }

    return (
        <div className={"welcome-screen"}>
            <h1>Kristiania Messenger</h1>

            <div className={"buttons"}>
                <Button onClick={() => setCreateDialogVisible(true)}>Opprett</Button>
                <Button onClick={gotoLogin}>Login</Button>
            </div>

            <CreateUserDialog open={createDialogVisible} setOpen={setCreateDialogVisible}></CreateUserDialog>
        </div>
    );
}

export default function App() {
    return (
        <HashRouter>
            <Routes>
                <Route path={"/"} element={<WelcomeScreen/>}></Route>
                <Route path={"/messages/:userId"} element={<MessengerPage/>}></Route>
                <Route path={"/messages/:userId/:panelDataId"} element={<MessengerPage/>}></Route>
                <Route path={"/login"} element={<UserSelectionPage/>}></Route>
            </Routes>
        </HashRouter>
    );
}
