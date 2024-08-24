"use client"
import { useContext, useEffect, useState } from "react";
import UserContext from "@/components/context/UserContext";
import { useRouter } from "next/navigation";

const Plan = ({ params } : { params: { id: number }}) => {
    const user = useContext(UserContext);
    const router = useRouter();
    const [plan, setPlan] = useState<Plan>(
        {
            id: -1,
            title: "",
            notes: "",
            isPublic: false,
            createdAt: "",
            updatedAt: "",
            days: []
        }
    );
    const [editing, setEditing] = useState(false);

    useEffect(() => {
        if (!user.loading && !user.signedIn) {
            router.push("/");
        }
    }, [user]);

    useEffect(() => {
        if (!editing) {
            const getPlan = async () => {
                try {
                    let res = await fetch(`http://localhost:8080/api/v1/plans/${params.id}`, { method: "GET", credentials: "include" });
                    let data = await res.json();

                    setPlan(data);
                } catch(error) {
                    console.error(error);
                }
            }

            getPlan();
        }
        
    }, [editing]);

    const save = async () => {
        try {
            let res = await fetch(`http://localhost:8080/api/v1/plans/${params.id}`, { 
                method: "PUT", 
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(plan) 
            });

            if (!res.ok) {
                console.log(res)
                let txt = await res.text();
                console.log(txt)
                alert("Failed to save plan");
            }

            setEditing(false);
        } catch(error) {
            console.error(error);
        }
    }

    return (
        <div>
            <div>
                {
                    editing ?
                    (
                        <>
                            <button onClick={save}>Save</button>
                            <button onClick={() => setEditing(false)}>Cancel</button>
                        </>
                    ) : (
                        <button onClick={() => setEditing(true)}>Edit</button>
                    )
                }
            </div>
            {
                editing ? (
                    <input type="text" className="border-2 border-black" value={plan?.title} onChange={(e) => {
                        setPlan({ ...plan, title: e.target.value });
                    }} />
                ) : (
                    <h1>{plan?.title}</h1>
                )
            }
        </div>
    )
}

export default Plan;