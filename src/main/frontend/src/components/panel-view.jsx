import { useState, useEffect } from "react";
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
import InputLabel from "@mui/material/InputLabel";
import FormControl from "@mui/material/FormControl";
import TextField from "@mui/material/TextField";
import { restFetch } from "../rest/RestFetch";
import Badge from "@mui/material/Badge";

export function PanelView({
  onMessageThreadSelected,
  userId,
  isLoading,
  messageThreads,
  onNewChatCreated,
}) {
  const [newPersonDialogOpen, setNewPersonDialogOpen] = useState(false);

  if (!isLoading) {
    return (
      <div className={"panel-view"}>
        <div class="header section-header">
          <h3 className={"secondary-title"}>Chatter</h3>

          <Button
            onClick={() => setNewPersonDialogOpen(true)}
            color="primary"
            aria-label="new message"
            component="label"
            startIcon={<Message />}
          >
            Ny meldingstråd
          </Button>
        </div>
        <div class="content">
          {messageThreads.map((thread) => (
            <MessageThread
              messageThread={thread}
              onMessageThreadSelected={onMessageThreadSelected}
              userId={userId}
            />
          ))}
        </div>

        <NewPersonChatDialog
          onNewChatCreated={onNewChatCreated}
          open={newPersonDialogOpen}
          setOpen={setNewPersonDialogOpen}
          signedOnUserId={userId}
        />
      </div>
    );
  }

  return PanelViewSkeleton();
}

function MessageThread({ messageThread, onMessageThreadSelected, userId }) {
  const renderTopic = () => {
    if (messageThread.unreadMessages > 0) {
      return (
        <>
          <span className="topic">{messageThread.topic}</span>
          <Badge color="secondary" badgeContent={messageThread.unreadMessages}>
            <Message />
          </Badge>
        </>
      );
    } else {
      return <span className="topic">{messageThread.topic}</span>;
    }
  };

  const selectMessageThread = async () => {
    await fetch("/api/message-read", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        messageThreadId: messageThread.id,
        userId: userId,
      }),
    });
    await onMessageThreadSelected(messageThread);
  };

  return (
    <div
      key={messageThread.id}
      className="message-thread"
      onClick={selectMessageThread}
    >
      {renderTopic()}
    </div>
  );
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
      <div class="header section-header">
        <h3 className={"secondary-title"}>Chatter</h3>
      </div>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          gap: "1em",
          padding: "1em",
        }}
      >
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

function NewPersonChatDialog({
  open,
  setOpen,
  signedOnUserId,
  onNewChatCreated,
}) {
  const [users, setUsers] = useState([]);
  const [selectedUsersIds, setSelectedUsersIds] = useState([]);
  const [message, setMessage] = useState("");
  const [subject, setSubject] = useState("");
  const [selectedUsersDisplayValue, setSelectedUsersDisplayValue] =
    useState("");

  const handleClose = () => {
    setSelectedUsersIds([]);
    setMessage("");
    setSubject("");
    setOpen(false);
  };

  const createChat = async () => {
    if (!selectedUsersIds) return;
    if (message.length == 0) return;
    if (subject.length == 0) return;

    await fetch("/api/message-thread", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        topic: subject,
        message: message,
        senderId: signedOnUserId,
        receivers: selectedUsersIds,
      }),
    });

    setOpen(false);
    if (onNewChatCreated) {
      await onNewChatCreated();
    }
  };

  useEffect(() => {
    (async () => {
      const result = await fetch(`/api/user`);
      const users = await result.json();

      console.log("Users: ", users);

      setUsers(users.filter((x) => x.id != Number.parseInt(signedOnUserId)));
    })();
  }, []);

  const selectUser = (hello) => {
    console.log(hello);
  };

  const isChecked = (user) => {
    return selectedUsersIds.find((x) => x == user.id) !== undefined;
  };

  const checkChanged = (user) => {
    if (isChecked(user)) {
      setSelectedUsersIds([...selectedUsersIds.filter((x) => x != user.id)]);
    } else {
      setSelectedUsersIds([...selectedUsersIds, user.id]);
    }
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Ny meldingstråd</DialogTitle>
      <DialogContent>
        <div
          style={{
            minWidth: "500px",
            padding: "1em",
            display: "flex",
            flexDirection: "column",
            gap: "1em",
          }}
        >
          <TextField
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
            label="Tittel"
            variant="outlined"
          />
          <TextField
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            label="Melding"
            variant="outlined"
          />

          <div>
            <h2>Mottakere</h2>
            <div>
              {users.map((x) => (
                <div>
                  <Checkbox
                    inputProps={{ "aria-labelledby": x.name }}
                    checked={isChecked(x)}
                    check
                    onChange={() => checkChanged(x)}
                  />

                  <label>
                    {x.name} - {x.nickname}
                  </label>
                </div>
              ))}
            </div>
          </div>
        </div>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Avbryt</Button>
        <Button onClick={createChat}>Send</Button>
      </DialogActions>
    </Dialog>
  );
}

function NewGroupChatDialog({ open, setOpen, signedOnUserId }) {
  const [users, setUsers] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([]);

  const handleClose = () => {
    setOpen(false);
  };

  const createChat = () => {};

  const selectUser = (user) => {
    setSelectedUsers([...selectedUsers, user]);
  };

  const isChecked = (userId) => {
    return selectedUsers.filter((x) => x.id == userId).length > 0;
  };

  useEffect(() => {
    (async () => {
      const result = await fetch(`/api/user`);
      const users = await result.json();

      setUsers(users.filter((x) => x.id !== signedOnUserId));
    })();
  }, [setUsers]);

  return (
    <Dialog open={open} onClose={handleClose} maxWidth={"md"}>
      <DialogTitle>Ny gruppechat</DialogTitle>
      <DialogContent>
        <div>
          {users.map((user) => (
            <div>
              <Checkbox
                checked={isChecked(user.id)}
                onChange={(e) => selectUser(user)}
                defaultChecked
                sx={{ "& .MuiSvgIcon-root": { fontSize: 28 } }}
              />

              <label>{user.name}</label>
            </div>
          ))}
        </div>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Avbryt</Button>
        <Button onClick={createChat}>Opprett</Button>
      </DialogActions>
    </Dialog>
  );
}
