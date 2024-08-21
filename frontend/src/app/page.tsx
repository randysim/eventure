'use client'
import { useContext } from "react";
import UserContext from "@/components/context/UserContext";

export default function Home() {
  const user = useContext(UserContext);

  return (
    <div>
      {JSON.stringify(user)}
      Sign in with Google <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
    </div>
  )
}
