import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";

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


export function MessengerPage() {
    const { userId, panelDataId } = useParams();
    const [ user, setUser ] = useState();
    const [panelData, setPanelData] = useState();

    useEffect(() => {
        (async () => {
            if(userId) {
                const result = await fetch(`/api/user/${userId}`);
                const user = await result.json();

                setUser(user)
            }
        })();
    }, [setUser]);

    if(user != null) {
        return (<div className={"main-view"}>
            <header>
                <nav>
                    <h2>Kristiania Messenger - {user.name}</h2>
                </nav>
            </header>
            <div className={"content"}>
                <div className={"selector"}>
                    <PanelView userId={userId} setPanelData={setPanelData}></PanelView>
                </div>
                <div className={"messages"}>
                    <MessageView panelDataId={panelDataId}></MessageView>
                </div>
            </div>
        </div>);
    } else {
        return (
            <p>Laster..</p>
        );
    }
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
