import { useEffect, useState } from "react"
export default function ExcursionList() {
    const apiUrl = import.meta.env.VITE_API_URL;
    const [excursions, setExcursions] = useState([]);
    const [groupedByCategory, setGroupedByCategory] = useState({});

    useEffect(() => {
        fetch(`${apiUrl}/excursions`)
            .then(res => res.json())
            .then(data => {
                setExcursions(data);
                groupByCategory(data);
            })
            .catch(err => {
                console.error("Failed to fetch excursions:", err);
            })
    }, []);

    const groupByCategory = (items) => {
        const grouped = items.reduce((acc, excursion) => {
            const categoryName = excursion?.categoryName || "Uncategorized";
            if (!acc[categoryName]) acc[categoryName] = [];
            acc[categoryName].push(excursion);
            return acc;
        }, {})

        const sorted = Object.keys(grouped)
            .sort()
            .reduce((acc, key) => {
                acc[key] = grouped[key];
                return acc;
            }, {})

        setGroupedByCategory(sorted);
    }
    
    return (
        <div>
            <h1>Excursions</h1>
            {Object.entries(groupedByCategory).map(([categoryName, excursions]) => (
                <div key={categoryName} style={{marginBottom: "1.5rem"}}>
                    <h2>{categoryName}</h2>
                    <ul>
                        {excursions.map(exc => (
                            <li key={exc.id}>{exc.title}</li>
                        ))}
                    </ul>
                </div>
            ))}
        </div>
    )
}