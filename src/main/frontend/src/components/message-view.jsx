import Skeleton from "@mui/material/Skeleton";
import { useEffect, useState } from "react";

export function MessageView({ messageThread, isLoading, userId }) {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  useEffect(() => {
    (async () => {
      if (messageThread) {
        await fetchMessages();
      }
    })();
  }, [messageThread]);

  async function fetchMessages() {
    if (messageThread) {
      const res = await fetch(`/api/message/${messageThread.id}`);
      const messagesReceived = await res.json();

      console.log({ messagesReceived });

      setMessages(messagesReceived);
    }
  }

  async function sendNewMessage() {
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

    setNewMessage("");
    await fetchMessages();
  }

  async function onNewMessageKeyUp(event) {
    if (event.key === "Enter") {
      await sendNewMessage();
    }
  }

  if (!isLoading) {
    return (
      <div className="message-view">
        <div className="header section-header">
          <h3 className="secondary-title">Meldinger</h3>
        </div>

        <div className="content">
          {messages.map((x) => (
            <div key={x.id} className="message">
              {x.userNickname} - {x.sentDate} - {x.content}
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
