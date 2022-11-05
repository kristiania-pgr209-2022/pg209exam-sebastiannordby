import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import IconButton from '@mui/material/IconButton';
import Skeleton from '@mui/material/Skeleton';
import ExitToApp from "@mui/icons-material/ExitToApp";
import {PanelView} from '../components/panel-view';
import {MessageView} from '../components/message-view';

export function MessengerPage() {
    const { userId, panelDataId } = useParams();
    const [ user, setUser ] = useState(null);
    const [panelData, setPanelData] = useState();

    const signOut = () => {

    };

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
                {user ?
                    <div style={{display: 'flex', justifyContent: 'space-between'}}>
                        <h1>Kristiania Messenger</h1>
                        <IconButton onClick={signOut} aria-label="sign out">
                            <ExitToApp/>
                        </IconButton>
                    </div>
                 : <Skeleton variant="text" sx={{ fontSize: '1rem' }} />}
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



