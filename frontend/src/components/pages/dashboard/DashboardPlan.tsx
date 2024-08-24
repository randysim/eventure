const DashboardPlan = (
    {
        title,
        onView
    } :
    {
        title: string,
        onView: () => void
    }
) => {
    return (
        <div>
            <h1>{title}</h1>
            <button onClick={onView}>View</button>
        </div>
    );
}

export default DashboardPlan;