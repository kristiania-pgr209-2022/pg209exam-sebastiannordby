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
        username: "Alladin",
        type: "user",
        displayName: "Alladin"
    },
    {
        username: "Mohammed",
        type: "user",
        displayName: "Ola"
    },
    {
        username: "Ola",
        type: "user",
        displayName: "Ola"
    }
];

const MESSAGES = [

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
    const {userId} = useParams();
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
                    <PanelView setPanelData={setPanelData}></PanelView>
                </div>
                <div class={"messages"}>
                    <MessageView></MessageView>
                </div>
            </div>
        </div>
    );
}

function MessageView( ) {
    return (
        <div class="message-view">
            <h3 className={"secondary-title"}>Meldinger</h3>
        </div>
    );
}

function PanelView({setPanelData}) {
    return (
        <div className={"panel-view"}>
            <div>
                <h3 className={"secondary-title"}>Personer</h3>
            </div>
            <div>
                {PANEL_DATA.forEach(x =>
                    <Link key={x.displayName} onClick={() => setPanelData(x)}>
                        <span>{x.displayName}</span>
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
            </Routes>
        </HashRouter>
    );
}
