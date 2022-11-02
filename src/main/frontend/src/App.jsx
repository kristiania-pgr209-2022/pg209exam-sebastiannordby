import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'
import {BrowserRouter, HashRouter, Link, Route, Routes, useParams} from "react-router-dom";
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

function UserSelector() {
    return (
        <div className={"user-selector"}>
            {USERS.map(x =>
                <Link to={`/messages/${x.id}`} className={"user-card"} key={x.id}>
                    <label>{x.username}</label>
                    <p>{x.name}</p>
                </Link>
            )}
        </div>
    );
}

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
    return (
        <div className={"welcome-screen"}>
            <h1>Kristiania Messenger</h1>
            <h2>Velg bruker for å fortsette</h2>
            <UserSelector ></UserSelector>
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
            </Routes>
        </HashRouter>
    );
}
