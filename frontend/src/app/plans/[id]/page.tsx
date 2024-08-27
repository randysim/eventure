"use client";
import { useContext, useEffect, useState } from "react";
import UserContext from "@/components/context/UserContext";
import { useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

const Plan = ({ params }: { params: { id: number } }) => {
    const user = useContext(UserContext);
    const router = useRouter();
    const [plan, setPlan] = useState<Plan>({
        id: -1,
        title: "",
        notes: "",
        isPublic: false,
        createdAt: "",
        updatedAt: "",
        days: [],
    });

    const [editing, setEditing] = useState(false);
    const [stepDescs, setStepDescs] = useState<Record<number, { description: string; start: string; end: string }>>({});

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
                } catch (error) {
                    console.error(error);
                }
            };
            getPlan();
        }
    }, [editing]);

    const save = async () => {
        try {
            // Ensure all state changes are reflected in the plan object
            const updatedPlan = { ...plan };

            let res = await fetch(`http://localhost:8080/api/v1/plans/${params.id}`, {
                method: "PUT",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(updatedPlan),
            });

            if (!res.ok) {
                alert("Failed to save plan");
                return;
            }

            setEditing(false);
        } catch (error) {
            console.error(error);
        }
    };

    const handleCreateStep = (dayOrder: number) => {
        const day = plan.days.find((d) => d.order === dayOrder);
        if (day) {
            const updatedDay = {
                ...day,
                steps: [
                    ...day.steps,
                    {
                        order: day.steps.length + 1,
                        description: stepDescs[dayOrder]?.description || "",
                        start: stepDescs[dayOrder]?.start || "",
                        end: stepDescs[dayOrder]?.end || "",
                    },
                ],
            };

            setPlan((prev) => ({
                ...prev,
                days: prev.days.map((d) => (d.order === dayOrder ? updatedDay : d)),
            }));

            setStepDescs((prev) => ({
                ...prev,
                [dayOrder]: { description: "", start: "", end: "" },
            }));

            setEditing(true);
        }
    };

    const handleEditStep = (dayOrder: number, stepOrder: number, key: keyof { description: string; start: string; end: string }, value: string) => {
        const day = plan.days.find((d) => d.order === dayOrder);
        if (day) {
            const updatedDay = {
                ...day,
                steps: day.steps.map((step) =>
                    step.order === stepOrder ? { ...step, [key]: value } : step
                ),
            };

            setPlan((prev) => ({
                ...prev,
                days: prev.days.map((d) => (d.order === dayOrder ? updatedDay : d)),
            }));

            setEditing(true);
        }
    };

    const handleDeleteDay = (dayOrder: number) => {
        setPlan((prev) => ({
            ...prev,
            days: prev.days
                .filter((d) => d.order !== dayOrder)
                .map((d, index) => ({ ...d, order: index + 1 })), // Reorder remaining days
        }));
        setEditing(true);
    };

    const handleDeleteStep = (dayOrder: number, stepOrder: number) => {
        const day = plan.days.find((d) => d.order === dayOrder);
        if (day) {
            const updatedDay = {
                ...day,
                steps: day.steps
                    .filter((step) => step.order !== stepOrder)
                    .map((step, index) => ({ ...step, order: index + 1 })), // Reorder remaining steps
            };

            setPlan((prev) => ({
                ...prev,
                days: prev.days.map((d) => (d.order === dayOrder ? updatedDay : d)),
            }));

            setEditing(true);
        }
    };

    const moveItemUp = (items: any[], index: number) => {
        if (index <= 0) return items;

        const newItems = [...items];
        [newItems[index - 1], newItems[index]] = [newItems[index], newItems[index - 1]];
        return newItems;
    };

    const moveItemDown = (items: any[], index: number) => {
        if (index >= items.length - 1) return items;

        const newItems = [...items];
        [newItems[index], newItems[index + 1]] = [newItems[index + 1], newItems[index]];
        return newItems;
    };

    const handleMoveDayUp = (index: number) => {
        setPlan((prev) => ({
            ...prev,
            days: moveItemUp(prev.days, index).map((d, i) => ({ ...d, order: i + 1 })),
        }));
        setEditing(true);
    };

    const handleMoveDayDown = (index: number) => {
        setPlan((prev) => ({
            ...prev,
            days: moveItemDown(prev.days, index).map((d, i) => ({ ...d, order: i + 1 })),
        }));
        setEditing(true);
    };

    const handleMoveStepUp = (dayOrder: number, index: number) => {
        const day = plan.days.find((d) => d.order === dayOrder);
        if (day) {
            const updatedDay = {
                ...day,
                steps: moveItemUp(day.steps, index).map((step, i) => ({ ...step, order: i + 1 })),
            };

            setPlan((prev) => ({
                ...prev,
                days: prev.days.map((d) => (d.order === dayOrder ? updatedDay : d)),
            }));

            setEditing(true);
        }
    };

    const handleMoveStepDown = (dayOrder: number, index: number) => {
        const day = plan.days.find((d) => d.order === dayOrder);
        if (day) {
            const updatedDay = {
                ...day,
                steps: moveItemDown(day.steps, index).map((step, i) => ({ ...step, order: i + 1 })),
            };

            setPlan((prev) => ({
                ...prev,
                days: prev.days.map((d) => (d.order === dayOrder ? updatedDay : d)),
            }));

            setEditing(true);
        }
    };

    return (
        <div style={{ maxWidth: "800px", margin: "0 auto", paddingTop: "30px" }}>
            {editing && (
                <div style={{
                    position: "fixed",
                    bottom: "20px", // Adjust the gap from the bottom
                    right: "20px", // Adjust the gap from the right
                    display: "flex",
                    gap: "10px",
                    border: "2px solid #333", // Background color of the snackbar
                    padding: "10px",
                    borderRadius: "15px",
                    color: "#fff" // Text color of the snackbar
                }}>
                    <Button onClick={save}>Save</Button>
                    <Button onClick={() => { setEditing(false); }}>Cancel</Button>
                </div>
            )}
            <Input
                type="text"
                style={{ width: "100%", marginBottom: "20px" }}
                value={plan?.title}
                onChange={(e) => {
                    setPlan((prev) => ({ ...prev, title: e.target.value }));
                    setEditing(true);
                }}
            />

            <Button
                style={{ marginBottom: "20px" }}
                onClick={() => {
                    setPlan((prev) => ({
                        ...prev,
                        days: [...prev.days, { order: prev.days.length + 1, steps: [] }],
                    }));
                    setEditing(true);
                }}
            >
                Add Day
            </Button>

            {plan.days
                .sort((a, b) => a.order - b.order)
                .map((day, dayIndex) => (
                    <div key={day.id} style={{ marginBottom: "30px" }}>
                        <div style={{ display: "flex", gap: "10px", marginBottom: "10px", alignItems: "center" }}>
                            <div>Day {day.order}</div>
                            <Button onClick={() => handleMoveDayUp(dayIndex)}>Move Up</Button>
                            <Button onClick={() => handleMoveDayDown(dayIndex)}>Move Down</Button>
                            <Button variant="destructive" onClick={() => handleDeleteDay(day.order)}>Delete Day</Button>
                        </div>
                        <Button
                            onClick={() => handleCreateStep(day.order)}
                            style={{ marginBottom: "10px" }}
                        >
                            Add Step
                        </Button>

                        {day.steps
                            .sort((a, b) => a.order - b.order)
                            .map((step, stepIndex) => (
                                <div key={step.order} style={{ marginBottom: "10px", display: "flex", alignItems: "center", gap: "10px" }}>
                                    <Input
                                        placeholder="Description"
                                        value={step.description}
                                        onChange={(e) => handleEditStep(day.order, step.order, "description", e.target.value)}
                                    />
                                    <Input
                                        placeholder="Start Time"
                                        value={step.start}
                                        onChange={(e) => handleEditStep(day.order, step.order, "start", e.target.value)}
                                    />
                                    <Input
                                        placeholder="End Time"
                                        value={step.end}
                                        onChange={(e) => handleEditStep(day.order, step.order, "end", e.target.value)}
                                    />
                                    <Button onClick={() => handleMoveStepUp(day.order, stepIndex)}>Move Up</Button>
                                    <Button onClick={() => handleMoveStepDown(day.order, stepIndex)}>Move Down</Button>
                                    <Button variant="destructive" onClick={() => handleDeleteStep(day.order, step.order)}>Delete Step</Button>
                                </div>
                            ))}
                    </div>
                ))}
        </div>
    );
};

export default Plan;
