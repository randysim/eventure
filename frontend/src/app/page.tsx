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
  }, [user]);

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
      <h1 className="text-4xl font-bold mb-4">Welcome to Eventure</h1>
      <p className="text-xl mb-6">Your ultimate tool for making plans effortlessly.</p>
      <a 
        href="http://localhost:8080/oauth2/authorization/google" 
        className="px-6 py-3 text-white bg-blue-500 rounded hover:bg-blue-600"
      >
        Sign in with Google
      </a>
    </div>
  )
}
