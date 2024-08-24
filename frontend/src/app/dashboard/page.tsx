"use client"
import UserContext from "@/components/context/UserContext"
import { useContext, useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import DashboardPlan from "@/components/pages/dashboard/DashboardPlan";

export default function Dashboard() {
    const user = useContext(UserContext);
    const router = useRouter();

    const [plans, setPlans] = useState<DashboardPlan[]>([]);
    const [title, setTitle] = useState("");

    useEffect(() => {
        if (!user.loading && !user.signedIn) {
            router.push("/");
        }
    }, [user]);

    useEffect(() => {
        const getPlans = async () => {
            try {
                let res = await fetch("http://localhost:8080/api/v1/user/plans", { method: "GET", credentials: "include" });
                let data = await res.json();

                setPlans(data);
            } catch(error) {
                console.error(error);
            }
        }

        getPlans();
    }, []);

    const createPlan = async () => {
        try {
            let res = await fetch("http://localhost:8080/api/v1/plans", { 
                method: "POST", 
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ 
                    title
                }) 
            });

            let data = await res.json();
            setTitle("");

            setPlans([...plans, data]);
        } catch(error) {
            console.error(error);
        }
    }

    return (
        <div>
            <div>
                {
                    plans.map(plan => (
                        <DashboardPlan 
                            key={plan.id} 
                            title={plan.title}
                            onView={() => router.push(`/plans/${plan.id}`)} 
                        />
                    ))
                }
            </div>
            <input className="border-4 border-black" value={title} onChange={e => setTitle(e.target.value)} />
            <button onClick={createPlan}>Create Plan</button>
        </div>
    )
}