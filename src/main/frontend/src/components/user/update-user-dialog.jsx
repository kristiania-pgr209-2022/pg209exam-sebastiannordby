import { useEffect, useState } from "react";
import { restFetch } from "../../rest/RestFetch.js";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";

export function UpdateUserDialog({ open, setOpen, userId }) {
  const [name, setName] = useState("");
  const [emailAddress, setEmailAddress] = useState("");
  const [nickname, setNickname] = useState("");
  const [bio, setBio] = useState("");
  const [user, setUser] = useState();

  useEffect(() => {
    (async () => {
      await fetchUser();
    })();
  }, [userId]);

  const fetchUser = async () => {
    const fetchedUser = await restFetch(`/api/user/${userId}`);

    console.log(fetchedUser);

    setName(fetchedUser.name);
    setEmailAddress(fetchedUser.emailAddress);
    setNickname(fetchedUser.nickname);
    setBio(fetchedUser.bio);
    setUser(fetchedUser);
  };

  const updateUser = async () => {
    const userToUpdate = {
      ...user,
      name,
      emailAddress,
      nickname,
      bio,
    };

    const result = await fetch("/api/user", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userToUpdate),
    });

    if (result.ok) {
      setOpen(false);
      await fetchUser();
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth={"md"}>
      <DialogTitle>Oppdater bruker</DialogTitle>
      <DialogContent>
        <TextField
          value={name}
          onChange={(e) => setName(e.target.value)}
          autoFocus
          margin="dense"
          id="name"
          label="Navn"
          type="name"
          fullWidth
          variant="standard"
          x
        />

        <TextField
          value={emailAddress}
          onChange={(e) => setEmailAddress(e.target.value)}
          margin="dense"
          id="name"
          label="E-post"
          type="email"
          fullWidth
          variant="standard"
        />

        <TextField
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          margin="dense"
          id="name"
          label="Nickname"
          type="nickname"
          fullWidth
          variant="standard"
        />

        <TextField
          value={bio}
          onChange={(e) => setBio(e.target.value)}
          margin="dense"
          id="name"
          label="Bio"
          type="bio"
          fullWidth
          variant="standard"
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>Avbryt</Button>
        <Button onClick={updateUser}>Oppdater</Button>
      </DialogActions>
    </Dialog>
  );
}
