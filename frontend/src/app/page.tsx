'use client'
import { useContext, useEffect } from "react";
import UserContext from "@/components/context/UserContext";
import { useRouter } from "next/navigation";

export default function Home() {
  const user = useContext(UserContext);
  const router = useRouter();

  useEffect(() => {
    if (!user.loading && user.signedIn) {
      router.push("/dashboard");
    }
  }, [user])

  return (
    <div>
      {JSON.stringify(user)}
      Sign in with Google <a href="http://localhost:8080/oauth2/authorization/google">click here</a>
    </div>
  )
}
