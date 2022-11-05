import {Link, useParams, useNavigate} from "react-router-dom";
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
    const navigate = useNavigate();

    const signOut = () => {
        navigate('/');
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
            {user ?
                <>
                    <h1>Kristiania Messenger</h1>
                    <IconButton onClick={signOut} aria-label="sign out" className="color-inherit" >
                        <ExitToApp/>
                    </IconButton>
                </>
            : <Skeleton variant="text" sx={{ fontSize: '1rem' }} />}
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



