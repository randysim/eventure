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
        console.log("PLAN", plan);
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
                alert("Failed to save plan");
            }

            let json = await res.json();
            console.log(json);

            

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

            <input type="text" className="border-2 border-black" value={plan?.title} onChange={(e) => {
                setPlan({ ...plan, title: e.target.value });
                setEditing(true);
            }} />

            <button onClick={() => {
                setPlan({ ...plan, days: [...plan.days, { order: plan.days.length + 1, steps: [] }] });
                setEditing(true);
            }}>Add Day</button>

            {
                plan.days.sort((a, b) => a.order - b.order).map(day => (
                    <div key={day.id}>
                        Day {day.order}
                        <button onClick={
                            () => {
                                setPlan({ 
                                    ...plan, 
                                    days: [...plan.days.filter(d => d.order !== day.order), { id: day.id, order: day.order, steps: [...day.steps, { order: day.steps.length + 1, description: "testdesc", start: "12:00:00", end: "01:00:00" }] }]
                                });
                                setEditing(true);
                            }
                        }>Create Step</button>
                        {
                            day.steps.sort((a, b) => a.order - b.order).map(step => (
                                <div key={step.id}>
                                    {step.description}: {step.start} - {step.end}
                                </div>
                            ))
                        }
                    </div>
                ))
            }
        </div>
    )
}

export default Plan;