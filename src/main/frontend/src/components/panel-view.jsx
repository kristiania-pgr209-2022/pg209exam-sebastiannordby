import {useState, useEffect} from "react";
import Button from "@mui/material/Button";
import Message from "@mui/icons-material/Message";
import Skeleton from "@mui/material/Skeleton";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import Checkbox from "@mui/material/Checkbox";
import DialogActions from "@mui/material/DialogActions";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";

export function PanelView({setPanelData, userId, isLoading }) {
    const [newGroupDialogOpen, setNewGroupDialogOpen] = useState(false);
    const [newPersonDialogOpen, setNewPersonDialogOpen] = useState(false);

    if(!isLoading) {
        return (
            <div className={"panel-view"}>
                <div class="header">
                    <h3 className={"secondary-title"}>Chatter</h3>

                    <Button
                        onClick={() => setNewPersonDialogOpen(true)}
                        color="primary"
                        aria-label="new message"
                        component="label"
                        startIcon={<Message />}>
                        Person
                    </Button>

                    <Button
                        onClick={() => setNewGroupDialogOpen(true)}
                        color="primary"
                        aria-label="new message"
                        component="label"
                        startIcon={<Message />}>
                        Gruppe
                    </Button>
                </div>
                <div class="content">

                </div>

                <NewPersonChatDialog
                    open={newPersonDialogOpen}
                    setOpen={newPersonDialogOpen}
                    signedOnUserId={userId}>
                </NewPersonChatDialog>
            </div>
        );
    }

    return PanelViewSkeleton();
}
/**
 *
 * {PANEL_DATA.map(x =>
 *     <Link key={x.id} to={`/messages/${userId}/${x.id}`}>
 *         <img className={"avatar"} />
 *         <span>{x.username}</span>
 *     </Link>
 * )}
 * */



function PanelViewSkeleton() {
    return (
        <div className={"panel-view"}>
            <div class="header">
                <h3 className={"secondary-title"}>Chatter</h3>
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


function NewPersonChatDialog({ open, setOpen, signedOnUserId }) {
    const [ users, setUsers ] = useState([]);
    const [ selectedUserId, setSelectedUserId ] = useState(0);
    const handleClose = () => {
        setOpen(false);
    };

    const createChat = () => {

    };

    useEffect(() => {
        (async () => {
            const result = await fetch(`/api/user`);
            const users = await result.json();

            console.log('Users: ', users);

            setUsers(users.filter(x => x.id !== signedOnUserId));
        })();
    }, [ setUsers ]);

    return (
        <Dialog open={open} onClose={handleClose} sx={{minWidth: '500px'}}>
            <DialogTitle>Ny chat</DialogTitle>
            <DialogContent>
                <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={selectedUserId}
                    label="Mottaker"
                    sx={{width: '100%'}}
                    onChange={e => setSelectedUserId(e.target.value)}>

                    <MenuItem disabled value="">
                        <em>Velg mottaker</em>
                    </MenuItem>

                    {users.map(user =>
                        <MenuItem value={user.id}>{user.name}</MenuItem>
                    )}
                </Select>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose}>Avbryt</Button>
                <Button onClick={createChat}>Opprett</Button>
            </DialogActions>
        </Dialog>
    );
}

function NewGroupChatDialog({ open, setOpen, signedOnUserId }) {
    const [ users, setUsers ] = useState([]);
    const [ selectedUsers, setSelectedUsers ] = useState([]);

    const handleClose = () => {
        setOpen(false);
    };

    const createChat = () => {

    };

    const selectUser = (user) => {
        setSelectedUsers([
            ...selectedUsers, user
        ]);
    };

    const isChecked = (userId) => {
        return selectedUsers.filter(x => x.id == userId).length > 0;
    };

    useEffect(() => {
        (async () => {
            const result = await fetch(`/api/user`);
            const users = await result.json();

            setUsers(users.filter(x => x.id !== signedOnUserId));
        })();
    }, [ setUsers ]);

    return (
        <Dialog open={open} onClose={handleClose} maxWidth={"md"}>
            <DialogTitle>Ny gruppechat</DialogTitle>
            <DialogContent>
                <div>
                    {users.map(user =>
                        <div>
                            <Checkbox
                                checked={isChecked(user.id)}
                                onChange={e => selectUser(user)}
                                defaultChecked
                                sx={{ '& .MuiSvgIcon-root': { fontSize: 28 } }}/>

                            <label>{user.name}</label>
                        </div>
                    )}
                </div>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose}>Avbryt</Button>
                <Button onClick={createChat}>Opprett</Button>
            </DialogActions>
        </Dialog>
    );
}