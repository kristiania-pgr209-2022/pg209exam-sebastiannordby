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
let selectedUser = null;


const USERS = [
    {
        id: 1,
        username: "Cyllon",
        name: "Sebastian Nordby"
    },
    {
        id: 2,
        username: "ridens",
        name: "Mats"
    },
    {
        id: 3,
        username: "alladin",
        name: "Alladin Muhammed"
    }
];

const PANEL_DATA = [
    {
        id: 1,
        username: "Alladin",
        type: "user",
        displayName: "Alladin"
    },
    {
        id: 2,
        username: "Mohammed",
        type: "user",
        displayName: "Ola"
    },
    {
        id: 3,
        username: "Ola",
        type: "user",
        displayName: "Ola"
    }
];

const MESSAGES = [
    {
        panelDataId: 1,
        message: "Hello"
    },
    {
        panelDataId: 1,
        message: "Hey how are your"
    },
];



function MainView() {
    const { userId, panelDataId } = useParams();
    const [panelData, setPanelData] = useState();
    const user = USERS.find(x => x.id == userId);

    return (
        <div className={"main-view"}>
            <header>
                <nav>
                    <h2>Kristiania Messenger - {user.name}</h2>
                </nav>
            </header>
            <div className={"content"}>
                <div class={"selector"}>
                    <PanelView userId={userId} setPanelData={setPanelData}></PanelView>
                </div>
                <div class={"messages"}>
                    <MessageView panelDataId={panelDataId}></MessageView>
                </div>
            </div>
        </div>
    );
}

function MessageView({ panelDataId } ) {
    const data = PANEL_DATA.find(x => x.id == panelDataId);
    const messages = MESSAGES.filter(x => x.panelDataId == panelDataId);
    console.log('MessageView: ', data);

    return (
        <div class="message-view">
            <h3 className={"secondary-title"}>Meldinger</h3>

            <div className="content">
                {messages.map(x =>
                    <div>{x.message}</div>
                )}
            </div>
        </div>
    );
}

function PanelView({setPanelData, userId}) {
    return (
        <div className={"panel-view"}>
            <div class="header">
                <h3 className={"secondary-title"}>Personer</h3>
            </div>
            <div class="content">
                {PANEL_DATA.map(x =>
                    <Link key={x.id} to={`/messages/${userId}/${x.id}`}>
                        <img className={"avatar"} />
                        <span>{x.username}</span>
                    </Link>
                )}
            </div>
        </div>
    );
}

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
                <Route path={"/messages/:userId"} element={<MainView/>}></Route>
                <Route path={"/messages/:userId/:panelDataId"} element={<MainView/>}></Route>
                <Route path={"/login"} element={<UserSelectionPage/>}></Route>
            </Routes>
        </HashRouter>
    );
}
