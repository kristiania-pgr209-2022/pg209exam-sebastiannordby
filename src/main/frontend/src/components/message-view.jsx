import Skeleton from "@mui/material/Skeleton";
import InfoIcon from "@mui/icons-material/Info";
import { useEffect, useState } from "react";
import { restFetch } from "../rest/RestFetch";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";
import Avatar from "@mui/material/Avatar";
import Tooltip from "@mui/material/Tooltip";

export function MessageView({ messageThread, isLoading, userId }) {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [infoMessage, setInfoMessage] = useState();
  const [messageThreadMembers, setMessageThreadMembers] = useState([]);
  const [messageInfoDialogVisible, setMessageInfoDialogVisible] =
    useState(false);

  useEffect(() => {
    (async () => {
      if (messageThread) {
        await fetchMessages();
        await fetchMembers();
      }
    })();
  }, [messageThread]);

  const fetchMessages = async () => {
    if (messageThread) {
      setMessages(await restFetch(`/api/message/${messageThread.id}`));
    }
  };

  const fetchMembers = async () => {
    if (messageThread) {
      setMessageThreadMembers(
        await restFetch(`/api/message-thread/members/${messageThread.id}`)
      );
    }
  };

  const sendNewMessage = async () => {
    if (messageThread == null) return;
    if (newMessage.length == 0) return;

    const result = await fetch("/api/message", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: userId,
        message: newMessage,
        messageThreadId: messageThread.id,
      }),
    });

    if (result.ok) {
      setNewMessage("");
      await fetchMessages();
    }
  };

  const onNewMessageKeyUp = async (event) => {
    if (event.key === "Enter") {
      await sendNewMessage();
    }
  };

  const showMessageInfo = async (message) => {
    setInfoMessage(message);
    setMessageInfoDialogVisible(true);
  };

  const getInitials = (name) => {
    return name
      .split(" ")
      .map((n) => n[0])
      .join(".");
  };

  if (!isLoading) {
    return (
      <div className="message-view">
        <div className="header section-header">
          <h3 className="secondary-title">Meldinger</h3>
          <div className="members">
            {messageThreadMembers.map((x) => (
              <Tooltip title={`${x.name} - ${x.emailAddress} - ${x.nickname}`}>
                <Avatar>{getInitials(x.name)}</Avatar>
              </Tooltip>
            ))}
          </div>
        </div>

        <div className="content">
          {messages.map((x) => (
            <div key={x.id} className="message">
              <span className="time">
                {new Date(x.sentDate).toUTCString().replace("GMT", "")}
              </span>
              <span className="message-content">
                {x.userNickname}: {x.content}
              </span>
              <span className="message-info">
                <InfoIcon onClick={async () => await showMessageInfo(x)} />
              </span>
            </div>
          ))}
        </div>
        <div className="footer">
          <TextField
            sx={{ flex: "1" }}
            onKeyUp={onNewMessageKeyUp}
            placeholder="Melding.."
            disabled={!messageThread}
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
          />

          <Button onClick={sendNewMessage} disabled={!messageThread}>
            Send
          </Button>
        </div>

        <MessageInfoDialog
          userId={userId}
          message={infoMessage}
          open={messageInfoDialogVisible}
          setOpen={setMessageInfoDialogVisible}
        />
      </div>
    );
  }

  return MessageViewSkeleton();
}

function MessageInfoDialog({ open, setOpen, message, userId }) {
  const [readByUsers, setReadByUsers] = useState([]);

  useEffect(() => {
    (async () => {
      if (open) {
        const signedOnUserId = Number.parseInt(userId);
        const messagesReadBy = await restFetch(
          `/api/message-read/${message.messageId}`
        );

        setReadByUsers(
          messagesReadBy.filter((x) => x.userId != signedOnUserId)
        );
      }
    })();
  }, [open]);

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Meldingen er lest av</DialogTitle>
      <DialogContent>
        <div>
          <ul>
            {readByUsers.map((x) => (
              <li>
                {x.username} - {x.dateRead}
              </li>
            ))}
          </ul>
        </div>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Lukk</Button>
      </DialogActions>
    </Dialog>
  );
}

function MessageViewSkeleton() {
  return (
    <div className="message-view">
      <div className="header section-header">
        <h3 className="secondary-title">Meldinger</h3>
      </div>

      <div className="content">
        <div
          style={{
            display: "flex",
            gap: "1em",
            flexDirection: "column",
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
    </div>
  );
}
