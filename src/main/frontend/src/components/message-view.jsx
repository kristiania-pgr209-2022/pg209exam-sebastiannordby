import Skeleton from "@mui/material/Skeleton";
import {useEffect, useState} from "react";

export function MessageView({ messageThread, isLoading, userId } ) {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        (async() => {
            if(messageThread) {
                const res = await fetch(`/api/message/${userId}/${messageThread.id}`);
                const messagesReceived = await res.json();

                setMessages(messagesReceived);
            }
        })();
    }, [ messageThread ]);

    if(!isLoading) {
        return (
            <div className="message-view">
                <div className="header section-header">
                    <h3 className="secondary-title">Meldinger</h3>
                </div>

                <div className="content">
                    {messages.map(x =>
                        <div key={x.id}>{x.content}</div>
                    )}
                </div>
            </div>
        );
    }

    return MessageViewSkeleton();
}

function MessageViewSkeleton() {
    return (
        <div className="message-view">
            <div className="header section-header">
                <h3 className="secondary-title">Meldinger</h3>
            </div>

            <div className="content">
                <div style={{display: 'flex', gap: '1em', flexDirection: 'column', padding: '1em'}}>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                    <Skeleton variant="rounded" width={"100%"} height={30}/>
                </div>
            </div>
        </div>
    );
}