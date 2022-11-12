import Skeleton from "@mui/material/Skeleton";
import InfoIcon from "@mui/icons-material/Info";
import { useEffect, useState } from "react";
import { restFetch } from "../rest/RestFetch";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";

export function MessageView({ messageThread, isLoading, userId }) {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [infoMessage, setInfoMessage] = useState();
  const [messageInfoDialogVisible, setMessageInfoDialogVisible] =
    useState(false);

  useEffect(() => {
    (async () => {
      if (messageThread) {
        await fetchMessages();
      }
    })();
  }, [messageThread]);

  const fetchMessages = async () => {
    if (messageThread) {
      setMessages(await restFetch(`/api/message/${messageThread.id}`));
    }
  };

  const sendNewMessage = async () => {
    if (messageThread == null) return;
    if (newMessage.length == 0) return;

    await fetch("/api/message", {
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

    setNewMessage("");
    await fetchMessages();
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

  if (!isLoading) {
    return (
      <div className="message-view">
        <div className="header section-header">
          <h3 className="secondary-title">Meldinger</h3>
        </div>

        <div className="content">
          {messages.map((x) => (
            <div key={x.id} className="message">
              <span className="message-content">
                {x.sentDate} | {x.userNickname}: {x.content}
              </span>
              <span className="message-info">
                <InfoIcon
                  onClick={async () => await showMessageInfo(x)}
                ></InfoIcon>
              </span>
            </div>
          ))}
        </div>
        <div className="footer">
          <input
            onKeyUp={onNewMessageKeyUp}
            placeholder="Melding.."
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
          />
          <button onClick={sendNewMessage}>Send</button>
        </div>

        <MessageInfoDialog
          message={infoMessage}
          open={messageInfoDialogVisible}
          setOpen={setMessageInfoDialogVisible}
        />
      </div>
    );
  }

  return MessageViewSkeleton();
}

function MessageInfoDialog({ open, setOpen, message }) {
  const [readByUsers, setReadByUsers] = useState([]);

  useEffect(() => {
    (async () => {
      if (open) {
        setReadByUsers(
          await restFetch(`/api/message-read/${message.messageId}`)
        );
      }
    })();
  }, [open]);

  console.log(readByUsers);

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Informasjon</DialogTitle>
      <DialogContent>
        <div>
          <h3>Meldingen er lest av</h3>
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
