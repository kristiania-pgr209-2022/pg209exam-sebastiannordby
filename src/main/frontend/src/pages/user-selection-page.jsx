import { Link } from "react-router-dom";
import {useEffect, useState} from "react";

export function UserSelectionPage() {
    const [ users, setUsers ] = useState([]);

    useEffect(() => {
        (async () => {
            const result = await fetch('api/user');
            const users = await result.json();

            setUsers(users);
        })();
    }, [setUsers]);

    return (
        <div className={"user-selector"}>
            <h1>Login</h1>

            <div className={"content"}>
                {users.map(x =>
                    <Link to={`/messages/${x.id}`} className={"user-card"} key={x.id}>
                        <p>{x.name} - {x.nickname}</p>
                        <label>{x.emailAddress}</label>
                    </Link>
                )}
            </div>
        </div>
    );
}