import { useState } from 'react'
import reactLogo from './assets/react.svg'
import './App.css'

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

function UserSelector({onUserSelected}) {
    return (
        <div className={"user-selector"}>
            {USERS.map(x =>
                <div className={"user-card"} key={x.id} onClick={() => onUserSelected(x)}>
                    <label>{x.username}</label>
                    <p>{x.name}</p>
                </div>
            )}
        </div>
    );
}

function MainView() {
    const [panelData, setPanelData] = useState();

    return (
        <div className={"main-view"}>
            <header>
                <nav>
                    <h2>Kristiania Messenger</h2>
                </nav>
            </header>
            <div className={"content"}>
                <div class={"selector"}>
                    <PanelView setPanelData={setPanelData}></PanelView>
                </div>
                <div class={"messages"}>
                    <MessageView source={panelData}></MessageView>
                </div>
            </div>
        </div>
    );
}

function MessageView({ source }) {
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
                    <div key={x.displayName} onClick={() => setPanelData(x)}>
                        <span>{x.displayName}</span>
                    </div>
                )}
            </div>
        </div>
    );
}

function App() {
    const [user, setUser] = useState();

    function onUserSelected(user) {
        setUser(user);
    }

    if(user == null) {
        return (
            <div className={"welcome-screen"}>
                <h1>Kristiania Messenger</h1>
                <h2>Velg bruker for å fortsette</h2>
                <UserSelector onUserSelected={onUserSelected}></UserSelector>
            </div>
        );
    } else {
        return (<MainView/>);
    }
}

export default App
