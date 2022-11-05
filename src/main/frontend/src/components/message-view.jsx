import Skeleton from "@mui/material/Skeleton";

export function MessageView({ panelDataId, isLoading } ) {
    const data = [];
    const messages = [];

    if(!isLoading) {
        return (
            <div class="message-view">
                <div>
                    <h3 className={"secondary-title"}>Meldinger</h3>
                </div>

                <div className="content">
                    {messages.map(x =>
                        <div>{x.message}</div>
                    )}
                </div>
            </div>
        );
    }

    return MessageViewSkeleton();
}

function MessageViewSkeleton() {
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