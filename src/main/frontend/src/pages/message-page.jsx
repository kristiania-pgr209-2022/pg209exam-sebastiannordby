import { Link, useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { PanelView } from "../components/panel-view";
import { MessageView } from "../components/message-view";
import { restFetch } from "../rest/RestFetch";
import IconButton from "@mui/material/IconButton";
import Skeleton from "@mui/material/Skeleton";
import ExitToApp from "@mui/icons-material/ExitToApp";
import Settings from "@mui/icons-material/Settings";
import { UpdateUserDialog } from "../components/user/update-user-dialog.jsx";

export function MessengerPage() {
  const { userId, messageThreadId } = useParams();
  const [user, setUser] = useState(null);
  const [messageThread, setMessageThread] = useState();
  const [messageThreads, setMessageThreads] = useState([]);
  const [updateUserDialogOpen, setUpdateUserDialogOpen] = useState(false);
  const navigate = useNavigate();

  const signOut = () => {
    navigate("/");
  };

  const showSettingsDialog = () => {
    setUpdateUserDialogOpen(true);
  };

  const onPanelViewMessageThreadSelected = (messageThread) => {
    navigate(`/messages/${userId}/${messageThread.id}`);
  };

  const fetchUser = async () => {
    setUser(await restFetch(`/api/user/${userId}`));
  };

  useEffect(() => {
    (async () => {
      if (userId) {
        await fetchUser();
      }
    })();
  }, [setUser]);

  useEffect(() => {
    (async () => {
      const messageThreads = await restFetch(
        `/api/message-thread/userId/${userId}`
      );

      if (messageThreadId) {
        const messageThread = messageThreads.find(
          (x) => x.id == Number.parseInt(messageThreadId)
        );

        setMessageThread(messageThread);
      }

      setMessageThreads(messageThreads);
    })();
  }, [messageThreadId, userId]);

  return (
    <div className={"main-view"}>
      <header>
        {user ? (
          <>
            <h1>Kristiania Messenger</h1>

            <div>
              <IconButton
                onClick={showSettingsDialog}
                aria-label="settings"
                className="color-inherit"
              >
                <Settings />
              </IconButton>

              <IconButton
                onClick={signOut}
                aria-label="sign out"
                className="color-inherit"
              >
                <ExitToApp />
              </IconButton>
            </div>
          </>
        ) : (
          <Skeleton variant="text" sx={{ fontSize: "1rem" }} />
        )}
      </header>
      <div className={"content"}>
        <div className={"selector"}>
          <PanelView
            isLoading={!user}
            userId={userId}
            messageThreads={messageThreads}
            onMessageThreadSelected={onPanelViewMessageThreadSelected}
          />
        </div>
        <div className={"messages"}>
          <MessageView
            isLoading={!user}
            userId={userId}
            messageThread={messageThread}
          />
        </div>
      </div>

      <UpdateUserDialog
        userId={userId}
        open={updateUserDialogOpen}
        setOpen={setUpdateUserDialogOpen}
      />
    </div>
  );
}
