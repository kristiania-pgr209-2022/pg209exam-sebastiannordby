import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Skeleton} from "@mui/material";

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
    const [ user, setUser ] = useState(null);
    const [panelData, setPanelData] = useState();

    useEffect(() => {
        (async () => {
            if(userId) {
                setTimeout(async() => {
                    const result = await fetch(`/api/user/${userId}`);
                    const user = await result.json();

                    setUser(user);
                }, 700); // To display skeleton
            }
        })();
    }, [setUser]);

    return (<div className={"main-view"}>
        <header>
            <nav>
                {user ? <h2>Kristiania Messenger - {user.name}</h2> : <Skeleton variant="text" sx={{ fontSize: '1rem' }} />}
            </nav>
        </header>
        <div className={"content"}>
            <div className={"selector"}>
                <PanelView isLoading={!user} userId={userId} setPanelData={setPanelData}></PanelView>
            </div>
            <div className={"messages"}>
                <MessageView isLoading={!user} panelDataId={panelDataId}></MessageView>
            </div>
        </div>
    </div>);
}


function MessageView({ panelDataId, isLoading } ) {
    const data = PANEL_DATA.find(x => x.id == panelDataId);
    const messages = MESSAGES.filter(x => x.panelDataId == panelDataId);
    console.log('MessageView: ', data);

    if(!isLoading) {
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
    } else {
        return (<div style={{display: 'flex', gap: '1em', flexDirection: 'column', padding: '1em' }}>
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
            <Skeleton variant="rounded" width={"100%"} height={30} />
        </div>);
    }
}

function PanelView({setPanelData, userId, isLoading }) {
    if(!isLoading) {
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
    } else {
        return (
            <div className={"panel-view"}>
                <div class="header">
                    <h3 className={"secondary-title"}>Personer</h3>
                </div>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '1em', padding: '1em'}}>
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                    <Skeleton variant="rounded" width={"100%"} height={30} />
                </div>
            </div>
        );
    }
}
