import './App.css'
import { useState } from 'react'
import { HashRouter, Route, Routes, useNavigate, useParams} from "react-router-dom";
import Button from '@mui/material/Button';
import {UserSelectionPage} from "./pages/user-selection-page";
import {CreateUserDialog} from "./components/create-user-dialog";
import {MessengerPage} from "./pages/message-page";

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
