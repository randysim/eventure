import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
  } from "@/components/ui/card"

const DashboardPlan = (
    {
        title,
        updatedAt,
        onView
    } :
    {
        title: string,
        updatedAt: string,
        onView: () => void
    }
) => {
    return (
        <Card className="w-[350px]">
            <CardHeader>
                <CardTitle>{title}</CardTitle>
                <CardDescription>Last Updated On: {updatedAt}</CardDescription>
            </CardHeader>
            <button onClick={onView}>View</button>
        </Card>
    );
}

export default DashboardPlan;