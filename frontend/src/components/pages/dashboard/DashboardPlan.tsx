import { Button } from "@/components/ui/button";
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
        onView,
        onDelete
    } :
    {
        title: string,
        updatedAt: string,
        onView: () => void,
        onDelete: () => void
    }
) => {
    return (
        <Card className="w-[350px] m-10 p-5">
            <CardHeader>
                <CardTitle>{title}</CardTitle>
                <CardDescription>Last Updated On: {updatedAt}</CardDescription>
            </CardHeader>
            <CardFooter className="flex space-x-5">
                <Button onClick={onView}>View</Button>
                <Button onClick={onDelete}>Delete</Button>
            </CardFooter>
        </Card>
    );
}

export default DashboardPlan;