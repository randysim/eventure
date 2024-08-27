interface User {
    id: number;
    username: string;
    email: string;
    picture: string;
    createdAt: string;
}

interface DashboardPlan {
    id: number;
    title: string;
    notes: string;
}

interface Plan {
    id: number;
    title: string;
    notes: string;
    isPublic: boolean;
    createdAt: string;
    updatedAt: string;
    days: Day[];
}

interface Day {
    id?: number;
    order: number;
    steps: Step[];
}

interface Step {
    id?: number;
    order: number;
    description: string;
    start: string;
    end: string;
}